package ru.practicum.explorewithme.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.explorewithme.dto.request.HitCreateDto;
import ru.practicum.explorewithme.dto.request.StatsRequestFilter;
import ru.practicum.explorewithme.dto.response.StatsView;
import ru.practicum.explorewithme.service.StatsService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StatsController.class)
@ExtendWith(MockitoExtension.class)
class StatsControllerTestIT {
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StatsService statsService;

    @Test
    @SneakyThrows
    @DisplayName("Сохранение посещения. При корректной работе возвращает статус 201 Created")
    void createHit_whenValid_thenResponseStatusCreated() {
        HitCreateDto createDto = new HitCreateDto();
        createDto.setApp("appName");
        createDto.setIp("192.168.0.1");
        createDto.setUri("/events");
        createDto.setTimestamp("2024-07-26 20:00:00");

        doNothing().when(statsService).createHit(createDto);

        mockMvc.perform(post("/hit").contentType("application/json")
                        .content(objectMapper.writeValueAsString(createDto)))
                .andExpect(status().isCreated());

        verify(statsService, times(1)).createHit(createDto);
    }

    @Test
    @SneakyThrows
    @DisplayName("Получение статистики. При корректных параметрах возвращает статус Ok 200" +
            " и список статистики по посещению в теле")
    void getStats_whenParamsIsValid_thenResponseStatusOkAndCollectionOfStatsViewInBody() {
        final String start = "2024-07-26 00:00:00";
        final String end = "2024-07-26 23:59:59";
        final List<String> uris = List.of();
        final Boolean unique = true;
        final List<StatsView> statsViewList = List.of();
        when(statsService.getStats(any(StatsRequestFilter.class))).thenReturn(statsViewList);

        String result = mockMvc.perform(get("/stats")
                        .param("start", start)
                        .param("end", end)
                        .param("uris", uris.toString())
                        .param("unique", unique.toString()))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertNotNull(result);
        assertEquals(objectMapper.writeValueAsString(statsViewList), result);

        verify(statsService, times(1)).getStats(any(StatsRequestFilter.class));
    }

    @Test
    @SneakyThrows
    @DisplayName("Получение статистики. Если дата начала периода не в формате yyyy-MM-dd HH:mm:ss, " +
            "то возвращается статус 400 BadRequest")
    void getStats_whenStartDateStringIsNotValid_thenResponseStatusBadRequest() {
        final String start = "2024-07-26T00:00:00";
        final String end = "2024-07-26 23:59:59";
        final List<String> uris = List.of();
        final Boolean unique = true;
        final List<StatsView> statsViewList = List.of();
        when(statsService.getStats(any(StatsRequestFilter.class))).thenReturn(statsViewList);

        mockMvc.perform(get("/stats")
                        .param("start", start)
                        .param("end", end)
                        .param("uris", uris.toString())
                        .param("unique", unique.toString()))
                .andExpect(status().isBadRequest());


        verify(statsService, never()).getStats(any(StatsRequestFilter.class));
    }

    @Test
    @SneakyThrows
    @DisplayName("Получение статистики. Если дата окнчения периода не в формате yyyy-MM-dd HH:mm:ss, " +
            "то возвращается статус 400 BadRequest")
    void getStats_whenEndDateStringIsNotValid_thenResponseStatusBadRequest() {
        final String start = "2024-07-26 00:00:00";
        final String end = "2024-07-26T23:59:59";
        final List<String> uris = List.of();
        final Boolean unique = true;
        final List<StatsView> statsViewList = List.of();
        when(statsService.getStats(any(StatsRequestFilter.class))).thenReturn(statsViewList);

        mockMvc.perform(get("/stats")
                        .param("start", start)
                        .param("end", end)
                        .param("uris", uris.toString())
                        .param("unique", unique.toString()))
                .andExpect(status().isBadRequest());


        verify(statsService, never()).getStats(any(StatsRequestFilter.class));
    }
}