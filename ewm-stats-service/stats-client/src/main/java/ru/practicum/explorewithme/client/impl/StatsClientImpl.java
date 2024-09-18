package ru.practicum.explorewithme.client.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.practicum.explorewithme.client.StatsClient;
import ru.practicum.explorewithme.dto.request.HitCreateDto;

import jakarta.servlet.http.HttpServletRequest;
import ru.practicum.explorewithme.dto.response.StatsView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class StatsClientImpl implements StatsClient {
    @Value("${ewm.stats.service.url}")
    private String statsServiceUrl;

    private final WebClient webClient;

    @Override
    public void saveHit(HttpServletRequest request, String appName) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String ip = request.getRemoteAddr();
        String uri = request.getRequestURI();
        String timestamp = LocalDateTime.now().format(formatter);

        HitCreateDto hitCreateDto = new HitCreateDto(appName, uri, ip, timestamp);

        webClient.post()
                .uri(uriBuilder -> uriBuilder
                        .path("/hit")
                        .build())
                .body(Mono.just(hitCreateDto), HitCreateDto.class)
                .retrieve().bodyToMono(String.class).block();

        log.info("Отправлен запрос POST {}/hit с телом {}", statsServiceUrl, hitCreateDto);
    }

    @Override
    public List<StatsView> getStats(String start, String end, List<String> uris, Boolean unique) {
        return webClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/stats")
                        .queryParam("start", start)
                        .queryParam("end", end)
                        .queryParam("uris", String.join(",", uris))
                        .queryParam("unique", unique)
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<StatsView>>() {})
                .block();
    }
}
