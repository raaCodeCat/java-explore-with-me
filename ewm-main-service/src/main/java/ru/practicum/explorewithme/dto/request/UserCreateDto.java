package ru.practicum.explorewithme.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * Данные для добавления нового пользователя.
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Данные для добавления нового пользователя")
public class UserCreateDto {
    /**
     * Адрес электронной почты.
     */
    @NotBlank(message = "Параметр не может быть пустым")
    @Email(message = "Параметр должен иметь формат адреса электронной почты")
    @Length(min = 6, max = 254, message = "Параметр должен быть длинной от {min} до {max} символов")
    @Schema(description = "Адрес электронной почты")
    private String email;

    /**
     * Имя пользователя.
     */
    @NotBlank(message = "Параметр не может быть пустым")
    @Length(min = 2, max = 250, message = "Параметр должен быть длинной от {min} до {max} символов")
    @Schema(description = "Имя пользователя")
    private String name;
}
