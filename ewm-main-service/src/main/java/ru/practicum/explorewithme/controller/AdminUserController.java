package ru.practicum.explorewithme.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.explorewithme.dto.request.UserCreateDto;
import ru.practicum.explorewithme.dto.response.UserView;
import ru.practicum.explorewithme.service.UserService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import java.util.List;

/**
 * Контроллер администратора для работы с пользователями.
 */
@RestController
@RequestMapping(path = "/admin/users")
@RequiredArgsConstructor
@Slf4j
@Validated
public class AdminUserController {
    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserView addUser(@RequestBody @Valid UserCreateDto userCreateDto) {
        log.info("Получен запрос POST /admin/users c телом {}", userCreateDto);

        return userService.create(userCreateDto);
    }

    @GetMapping
    public List<UserView> getUsers(
            @RequestParam(required = false) List<Long> ids,
            @RequestParam(defaultValue = "0")
            @Min(value = 0, message = "Параметр from не может быть меньше {value}") int from,
            @RequestParam(defaultValue = "10")
            @Min(value = 1, message = "Параметр size не может быть меньше {value}") int size
    ) {
        log.info("Получен запрос GET /admin/users?ids={}&from={}&size={}", ids, from, size);

        return userService.getUsers(ids, from, size);
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUserById(@PathVariable Long userId) {
        log.info("Получен запрос DELETE /admin/users/{}", userId);
        userService.deleteUserById(userId);
    }
}
