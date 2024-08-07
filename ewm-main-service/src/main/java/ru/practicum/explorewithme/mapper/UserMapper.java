package ru.practicum.explorewithme.mapper;

import org.mapstruct.Mapper;
import ru.practicum.explorewithme.dto.request.UserCreateDto;
import ru.practicum.explorewithme.dto.response.UserView;
import ru.practicum.explorewithme.model.User;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User convert(UserCreateDto source);

    UserView convert(User source);

    List<UserView> convert(List<User> source);
}
