package ru.practicum.explorewithme.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.explorewithme.dto.response.RequestView;
import ru.practicum.explorewithme.service.RequestService;

import java.util.List;

/**
 * Контроллер для работы пользователя с заявками на участие в событие.
 */
@RestController
@RequestMapping("/users/{userId}/requests")
@RequiredArgsConstructor
@Slf4j
public class PrivateRequestController {
    private final RequestService requestService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RequestView addRequest(
            @PathVariable Long userId,
            @RequestParam Long eventId
    ) {
        log.info("Получен запрос POST /users/{}/requests с параметром {}", userId, eventId);

        return requestService.createRequest(userId, eventId);
    }

    @GetMapping
    public List<RequestView> getUserRequests(@PathVariable Long userId) {
        log.info("Получен запрос GET /users/{}/requests", userId);

        return requestService.getUserRequests(userId);
    }

    @PatchMapping("/{requestId}/cancel")
    public RequestView cancelUserRequest(
            @PathVariable Long userId,
            @PathVariable Long requestId
    ) {
        log.info("Получен запрос GET /users/{}/requests/{}/cancel", userId, requestId);

        return requestService.cancelUserRequest(userId, requestId);
    }
}
