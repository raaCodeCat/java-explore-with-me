package ru.practicum.explorewithme.mapper;

import org.mapstruct.Mapper;
import ru.practicum.explorewithme.dto.request.CompilationCreateDto;
import ru.practicum.explorewithme.dto.request.CompilationUpdateDto;
import ru.practicum.explorewithme.dto.response.CompilationView;
import ru.practicum.explorewithme.model.Compilation;

import java.util.List;

@Mapper(componentModel = "spring",
        uses = {EventMapper.class})
public interface CompilationMapper {
    Compilation convert(CompilationCreateDto createDto);

    Compilation convert(CompilationUpdateDto updateDto);

    CompilationView convert(Compilation compilation);

    List<CompilationView> convert(List<Compilation> compilations);
}
