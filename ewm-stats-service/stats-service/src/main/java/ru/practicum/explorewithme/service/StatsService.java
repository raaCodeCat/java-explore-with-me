package ru.practicum.explorewithme.service;

import ru.practicum.explorewithme.dto.request.HitCreateDto;
import ru.practicum.explorewithme.dto.request.StatsRequestFilter;
import ru.practicum.explorewithme.dto.response.StatsView;

import java.util.List;

public interface StatsService {
    void createHit(HitCreateDto hit);

    List<StatsView> getStats(StatsRequestFilter params);
}
