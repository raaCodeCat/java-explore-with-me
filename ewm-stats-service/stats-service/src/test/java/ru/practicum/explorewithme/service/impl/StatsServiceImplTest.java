package ru.practicum.explorewithme.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.explorewithme.dto.request.HitCreateDto;
import ru.practicum.explorewithme.dto.request.StatsRequestFilter;
import ru.practicum.explorewithme.dto.response.StatsView;
import ru.practicum.explorewithme.mapper.HitMapper;
import ru.practicum.explorewithme.model.Hit;
import ru.practicum.explorewithme.repository.StatsRepository;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StatsServiceImplTest {
    @Mock
    StatsRepository statsRepository;

    @Mock
    HitMapper hitMapper;

    @InjectMocks
    StatsServiceImpl statsService;

    @Test
    @DisplayName("Сохранение посещения")
    void createHit() {
        final HitCreateDto hitCreateDto = new HitCreateDto();
        final Hit hit = new Hit();
        when(hitMapper.convert(hitCreateDto)).thenReturn(hit);
        when(statsRepository.save(hit)).thenReturn(hit);

        statsService.createHit(hitCreateDto);

        verify(hitMapper, times(1)).convert(hitCreateDto);
        verify(statsRepository, times(1)).save(hit);
    }

    @Test
    @DisplayName("Получение статистики о посещениях.")
    void getStats_whenParamUniqueIsFalse_thenReturnCollectionOfStatsView() {
        final LocalDateTime start = LocalDateTime.now();
        final LocalDateTime end = LocalDateTime.now();
        final List<String> uris = List.of("/uri/1");
        final Boolean unique = false;
        StatsRequestFilter params = new StatsRequestFilter(start, end, uris, unique);
        List<StatsView> statsViewList = List.of(new StatsView(), new StatsView());
        when(statsRepository.getHitsStatistic(uris, start, end)).thenReturn(statsViewList);

        List<StatsView> actual = statsService.getStats(params);

        assertNotNull(actual);

        Mockito.verify(statsRepository, times(1)).getHitsStatistic(uris, start, end);
        Mockito.verify(statsRepository, never()).getUniqueHitsStatistic(uris, start, end);
    }

    @Test
    @DisplayName("Получение статистики об уникальных посещениях.")
    void getStats_whenParamUniqueIsTrue_thenReturnCollectionOfStatsView() {
        final LocalDateTime start = LocalDateTime.now();
        final LocalDateTime end = LocalDateTime.now();
        final List<String> uris = List.of("/uri/1");
        final Boolean unique = true;
        StatsRequestFilter params = new StatsRequestFilter(start, end, uris, unique);
        List<StatsView> statsViewList = List.of(new StatsView(), new StatsView());
        when(statsRepository.getUniqueHitsStatistic(uris, start, end)).thenReturn(statsViewList);

        List<StatsView> actual = statsService.getStats(params);

        assertNotNull(actual);

        Mockito.verify(statsRepository, never()).getHitsStatistic(uris, start, end);
        Mockito.verify(statsRepository, times(1)).getUniqueHitsStatistic(uris, start, end);
    }
}