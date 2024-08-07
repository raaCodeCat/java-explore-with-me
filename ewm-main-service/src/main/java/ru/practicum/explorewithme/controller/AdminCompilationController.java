package ru.practicum.explorewithme.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.explorewithme.dto.request.CompilationCreateDto;
import ru.practicum.explorewithme.dto.request.CompilationUpdateDto;
import ru.practicum.explorewithme.dto.response.CompilationView;
import ru.practicum.explorewithme.service.CompilationService;

@RestController
@RequestMapping("/admin/compilations")
@RequiredArgsConstructor
@Slf4j
public class AdminCompilationController {
    private final CompilationService compilationService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompilationView addCompilation(@RequestBody @Valid CompilationCreateDto createDto) {
        log.info("Получен запрос POST /admin/compilations c телом {}", createDto);

        return compilationService.addCompilation(createDto);
    }

    @DeleteMapping("/{compId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCompilation(@PathVariable Long compId) {
        log.info("Получен запрос DELETE /admin/compilations/{}", compId);

        compilationService.deleteCompilation(compId);
    }

    @PatchMapping("/{compId}")
    public CompilationView updateCompilation(
            @PathVariable Long compId,
            @RequestBody @Valid CompilationUpdateDto updateDto
    ) {
        log.info("Получен запрос PATH /admin/compilations/{} с телом {}", compId, updateDto);

        return compilationService.updateCompilation(compId, updateDto);
    }
}
