package ru.practicum.explorewithme.service.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithme.dto.request.EventRequestStatusUpdateDto;
import ru.practicum.explorewithme.dto.response.EventRequestStatusUpdateResult;
import ru.practicum.explorewithme.dto.response.RequestView;
import ru.practicum.explorewithme.enums.EventState;
import ru.practicum.explorewithme.enums.RequestStatus;
import ru.practicum.explorewithme.exception.ConflictException;
import ru.practicum.explorewithme.exception.NotFoundException;
import ru.practicum.explorewithme.mapper.RequestMapper;
import ru.practicum.explorewithme.model.Event;
import ru.practicum.explorewithme.model.Request;
import ru.practicum.explorewithme.model.User;
import ru.practicum.explorewithme.repository.RequestRepository;
import ru.practicum.explorewithme.service.EntityGettingService;
import ru.practicum.explorewithme.service.RequestService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Сервис для работы пользователя с заявками на участие в событие.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class RequestServiceImpl implements RequestService {
    private final EntityGettingService entityGettingService;

    private final RequestRepository requestRepository;

    private final RequestMapper requestMapper;

    @Override
    @Transactional
    public RequestView createRequest(Long userId, Long eventId) {
        log.info("Пользователь с идентификатором {} создает заявку на участие в событии с идентификатором {}",
                userId, eventId);
        User user = entityGettingService.getUserById(userId);
        Event event = entityGettingService.getEventById(eventId);

        checkForEventInitiator(userId, event);
        checkForEventPublication(event);
        checkForEventParticipantLimit(event);

        RequestStatus status = RequestStatus.PENDING;

        if (!event.getRequestModeration() || event.getParticipantLimit() == 0) {
            status = RequestStatus.CONFIRMED;
        }

        Request requestForCreate = new Request();
        requestForCreate.setEvent(event);
        requestForCreate.setRequester(user);
        requestForCreate.setStatus(status);
        requestForCreate.setCreated(LocalDateTime.now());

        try {
            Request request = requestRepository.save(requestForCreate);
            log.info("Заявка на участие в событии создана {}", request);

            return requestMapper.convert(request);
        }  catch (DataIntegrityViolationException e) {
            throw new ConflictException(e.getMostSpecificCause().getMessage());
        }
    }

    @Override
    public List<RequestView> getUserRequests(Long userId) {
        User requester = entityGettingService.getUserById(userId);

        return requestMapper.convert(requestRepository.findAllByRequesterId(requester.getId()));
    }

    @Override
    @Transactional
    public RequestView cancelUserRequest(Long userId, Long requestId) {
        log.info("Отмена заявки на участие с идентификатором {} от пользователя с идентификатором {}",
                requestId, userId);
        User requester = entityGettingService.getUserById(userId);
        Request request = entityGettingService.getRequestById(requestId);

        if (!request.getRequester().getId().equals(requester.getId())) {
            throw new NotFoundException(String.format("Отмена заявки с идентификатором %d не " +
                    "доступна пользователю с идентификатором %d", requestId, userId));
        }

        request.setStatus(RequestStatus.CANCELED);
        Request updatedRequest = requestRepository.save(request);
        log.info("Заявка на участие с идентификатором {} отменена", requestId);

        return requestMapper.convert(updatedRequest);
    }

    @Override
    public List<RequestView> getUserEventRequests(Long userId, Long eventId) {
        User user = entityGettingService.getUserById(userId);
        Event event = entityGettingService.getEventById(eventId);

        checkUserIsInitiatorOfEvent(user, event);

        List<Request> requests = requestRepository.findAllByEventId(eventId);

        return requestMapper.convert(requests);
    }

    @Override
    @Transactional
    public EventRequestStatusUpdateResult updateEventRequestsStatus(Long userId, Long eventId,
                                                                    EventRequestStatusUpdateDto updateDto) {
        User user = entityGettingService.getUserById(userId);
        Event event = entityGettingService.getEventById(eventId);

        checkUserIsInitiatorOfEvent(user, event);
        checkForEventParticipantLimit(event);

        List<Request> requests = requestRepository.findAllByEventIdAndIdInOrderByIdAsc(eventId,
                updateDto.getRequestIds());

        if (requests.size() == 0) {
            throw new NotFoundException(
                    String.format("У события с идентификаторов %d не отсутствуют заявки с идентификаторами %s",
                            eventId, updateDto.getRequestIds().toString())
            );
        }

        EventRequestStatusUpdateResult result = new EventRequestStatusUpdateResult();

        if (!event.getRequestModeration() || event.getParticipantLimit() == 0) {
            result.setConfirmedRequests(requestMapper.convert(requests));

            return result;
        }

        Long currentLimit = getCountConfirmedRequestByEventId(eventId);
        RequestStatus newStatus = updateDto.getStatus();

        for (Request request : requests) {
            if (!request.getStatus().equals(RequestStatus.PENDING)) {
                throw new ConflictException(
                        String.format("Невозможно изменить статус заявки с идентификатором %d. " +
                                "Статус можно изменить только у заявок, находящихся в состоянии ожидания",
                                request.getId()),
                        "Конфликт при изменении статуса заявки");
            }

            if (newStatus.equals(RequestStatus.CONFIRMED)) {
                currentLimit++;
                if (currentLimit <= event.getParticipantLimit()) {
                    request.setStatus(RequestStatus.CONFIRMED);
                } else {
                    request.setStatus(RequestStatus.REJECTED);
                }
            } else if (newStatus.equals(RequestStatus.REJECTED)) {
                request.setStatus(RequestStatus.REJECTED);
            }
        }

        requestRepository.saveAll(requests);

        result.setConfirmedRequests(requestMapper.convert(
                requests.stream()
                        .filter(request -> request.getStatus().equals(RequestStatus.CONFIRMED))
                        .collect(Collectors.toList())
        ));

        result.setRejectedRequests(requestMapper.convert(
                requests.stream()
                        .filter(request -> request.getStatus().equals(RequestStatus.REJECTED))
                        .collect(Collectors.toList())
        ));

        return result;
    }

    private void checkForEventInitiator(Long userId, Event event) {
        if (userId.equals(event.getInitiator().getId())) {
            throw new ConflictException("Инициатор события не может добавить запрос на участие в своём событии");
        }
    }

    private void checkForEventPublication(Event event) {
        if (!event.getState().equals(EventState.PUBLISHED)) {
            throw new ConflictException("Нельзя участвовать в неопубликованном событии");
        }
    }

    private void checkForEventParticipantLimit(Event event) {
        if (event.getParticipantLimit() != 0) {
            if (getCountConfirmedRequestByEventId(event.getId()) >= event.getParticipantLimit()) {
                throw new ConflictException("Достигнут лимит заявок на участие в событии");
            }
        }
    }

    private Long getCountConfirmedRequestByEventId(Long eventId) {
        return requestRepository.getCountConfirmedRequestByEventId(eventId);
    }

    private void checkUserIsInitiatorOfEvent(User user, Event event) {
        if (!event.getInitiator().equals(user)) {
            throw new NotFoundException(
                    String.format("Пользователю с идентификатором %d не доступно событие с идентификатором %d",
                            user.getId(), event.getInitiator().getId())
            );
        }
    }
}
