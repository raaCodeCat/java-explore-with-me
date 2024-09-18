package ru.practicum.explorewithme.service.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithme.dto.request.UserCreateDto;
import ru.practicum.explorewithme.dto.response.UserView;
import ru.practicum.explorewithme.exception.ConflictException;
import ru.practicum.explorewithme.mapper.UserMapper;
import ru.practicum.explorewithme.model.User;
import ru.practicum.explorewithme.repository.UserRepository;
import ru.practicum.explorewithme.service.EntityGettingService;
import ru.practicum.explorewithme.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Сервис для работы с пользователями.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final EntityGettingService entityGettingService;

    private final UserMapper userMapper;

    private  final UserRepository userRepository;

    @Override
    @Transactional
    public UserView create(UserCreateDto userCreateDto) {
        User userForCreate = userMapper.convert(userCreateDto);
        log.info("Добавление пользователя {}", userForCreate);

        try {
            User user = userRepository.save(userForCreate);
            log.info("Пользователь добавлен");

            return userMapper.convert(user);
        }  catch (DataIntegrityViolationException e) {
            throw new ConflictException(e.getMostSpecificCause().getMessage());
        }
    }

    @Override
    public List<UserView> getUsers(List<Long> ids, int from, int size) {
        if (ids == null) {
            log.info("Получение списка пользователей с параметрами from={}, size={}", from, size);
            int page = from / size;
            Pageable pageable = PageRequest.of(page, size);

            return userMapper.convert(userRepository.findAll(pageable).stream().collect(Collectors.toList()));
        } else {
            log.info("Получение списка пользователей с параметрами ids={}", ids);

            return userMapper.convert(userRepository.findByIdIn(ids));
        }
    }

    @Override
    @Transactional
    public void deleteUserById(Long userId) {
        User userForDelete = entityGettingService.getUserById(userId);

        log.info("Удаление пользователя {}", userForDelete);

        try {
            userRepository.delete(userForDelete);
            log.info("Пользователь удален");
        } catch (DataIntegrityViolationException e) {
            throw new ConflictException(e.getMostSpecificCause().getMessage());
        }
    }
}
