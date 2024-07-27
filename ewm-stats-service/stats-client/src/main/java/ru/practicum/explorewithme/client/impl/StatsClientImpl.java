package ru.practicum.explorewithme.client.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.practicum.explorewithme.client.StatsClient;
import ru.practicum.explorewithme.dto.request.HitCreateDto;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class StatsClientImpl extends RestTemplate implements StatsClient {
    @Value("${ewm.stats.service.url}")
    private String statsServiceUrl;

    @Override
    public void saveHit(HttpServletRequest request, String appName) {
        // Не понимаю этого требования. Если дата передается между сервисами, без участия человека,
        // то для чего эти заморочки с форматом даты?
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String ip = request.getRemoteAddr();
        String uri = request.getRequestURI();
        String timestamp = LocalDateTime.now().format(formatter);

        HitCreateDto hitCreateDto = new HitCreateDto(appName, uri, ip, timestamp);

        log.info("Отправлен запрос POST {}/hit с телом {}", statsServiceUrl, hitCreateDto);

        postForLocation(statsServiceUrl + "/hit", hitCreateDto);
    }

    @Override
    public ResponseEntity<Object> getStats(String start, String end, List<String> uris, Boolean unique) {
        StringBuilder paramsUrl = new StringBuilder();
        Map<String, Object> params = new HashMap<>();

        if (start != null) {
            paramsUrl.append("&start={start}");
            params.put("start", start);
        }

        if (end != null) {
            paramsUrl.append("&end={end}");
            params.put("end", end);
        }

        if (uris != null) {
            paramsUrl.append("&uris={uris}");
            params.put("uris", uris.toArray());
        }

        if (unique != null) {
            paramsUrl.append("&unique={unique}");
            params.put("unique", unique);
        }

        String url = statsServiceUrl + "/stats?" + (paramsUrl.toString().length() > 0 ? paramsUrl.substring(1) : "");

        log.info("Отправлен запрос GET {} с параметрами {}", url, params);

        return getForEntity(url, Object.class, params);
    }
}
