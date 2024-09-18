package ru.practicum.explorewithme.service;

import ru.practicum.explorewithme.dto.request.UserCreateDto;
import ru.practicum.explorewithme.dto.response.UserView;

import java.util.List;

/**
 * Сервис для работы с пользователями.
 */
public interface UserService {
    UserView create(UserCreateDto userCreateDto);

    List<UserView> getUsers(List<Long> ids, int from, int size);

    void deleteUserById(Long userId);
}
