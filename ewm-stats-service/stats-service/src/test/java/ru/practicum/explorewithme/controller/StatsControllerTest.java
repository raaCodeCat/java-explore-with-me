package ru.practicum.explorewithme.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.explorewithme.dto.request.HitCreateDto;
import ru.practicum.explorewithme.dto.request.StatsRequestFilter;
import ru.practicum.explorewithme.dto.response.StatsView;
import ru.practicum.explorewithme.service.StatsService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StatsControllerTest {
    @Mock
    StatsService statsService;

    @InjectMocks
    StatsController statsController;

    @Test
    @DisplayName("При вызове сохраняет информацию о посещении")
    void createHit_whenInvoked_thenSaveHit() {
        final HitCreateDto createDto = new HitCreateDto();
        doNothing().when(statsService).createHit(createDto);

        statsController.createHit(createDto);

        verify(statsService, times(1)).createHit(createDto);
    }

    @Test
    @DisplayName("При вызове возвращает список статистики посещения")
    void getStats_whenInvoked_thenReturnCollectionOfStatsView() {
        final String start = "2024-07-26 00:00:00";
        final String end = "2024-07-26 23:59:59";
        final List<String> uris = List.of();
        final Boolean unique = true;
        final List<StatsView> statsViewList = List.of();
        when(statsService.getStats(any(StatsRequestFilter.class))).thenReturn(statsViewList);

        List<StatsView> actual = statsController.getStats(start, end, uris, unique);

        assertNotNull(actual);

        verify(statsService, times(1)).getStats(any(StatsRequestFilter.class));
    }
}