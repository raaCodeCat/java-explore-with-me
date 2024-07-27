package ru.practicum.explorewithme.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import ru.practicum.explorewithme.dto.request.HitCreateDto;
import ru.practicum.explorewithme.dto.request.StatsRequestFilter;
import ru.practicum.explorewithme.dto.response.StatsView;
import ru.practicum.explorewithme.service.StatsService;

import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@Validated
public class StatsController {
    private final StatsService statsService;

    @PostMapping("/hit")
    @ResponseStatus(HttpStatus.CREATED)
    public void createHit(@RequestBody HitCreateDto hitCreateDto) {
        log.info("POST /hit with body = {}", hitCreateDto);

        statsService.createHit(hitCreateDto);
    }

    @GetMapping("/stats")
    @ResponseStatus(HttpStatus.OK)
    List<StatsView> getStats(
            @RequestParam @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}",
                    message = "Дата в параметре start должна быть в формате yyyy-MM-dd HH:mm:ss") String start,
            @RequestParam @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}",
                    message = "Дата в параметре end должна быть в формате yyyy-MM-dd HH:mm:ss")String end,
            @RequestParam(required = false) List<String> uris,
            @RequestParam(required = false, defaultValue = "false") Boolean unique
    ) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startDT = LocalDateTime.parse(start, formatter);
        LocalDateTime endDT = LocalDateTime.parse(end, formatter);

        StatsRequestFilter params = new StatsRequestFilter(startDT, endDT, uris, unique);
        log.info("GET /stats with params = {}", params);

        return statsService.getStats(params);
    }
}
