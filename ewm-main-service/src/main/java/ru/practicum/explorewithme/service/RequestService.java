package ru.practicum.explorewithme.service;

import ru.practicum.explorewithme.dto.request.EventRequestStatusUpdateDto;
import ru.practicum.explorewithme.dto.response.EventRequestStatusUpdateResult;
import ru.practicum.explorewithme.dto.response.RequestView;

import java.util.List;

/**
 * Сервис для работы пользователя с заявками на участие в событие.
 */
public interface RequestService {
    RequestView createRequest(Long userId, Long eventId);

    List<RequestView> getUserRequests(Long userId);

    RequestView cancelUserRequest(Long userId, Long requestId);

    List<RequestView> getUserEventRequests(Long userId, Long eventId);

    EventRequestStatusUpdateResult updateEventRequestsStatus(Long userId, Long eventId,
                                                             EventRequestStatusUpdateDto updateDto);
}
