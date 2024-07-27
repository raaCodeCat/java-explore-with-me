package ru.practicum.explorewithme.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.explorewithme.dto.request.HitCreateDto;
import ru.practicum.explorewithme.dto.request.StatsRequestFilter;
import ru.practicum.explorewithme.dto.response.StatsView;
import ru.practicum.explorewithme.mapper.HitMapper;
import ru.practicum.explorewithme.repository.StatsRepository;
import ru.practicum.explorewithme.service.StatsService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StatsServiceImpl implements StatsService {
    private final StatsRepository statsRepository;

    private final HitMapper hitMapper;

    @Override
    public void createHit(HitCreateDto hitCreateDto) {
        statsRepository.save(hitMapper.convert(hitCreateDto));
    }

    @Override
    public List<StatsView> getStats(StatsRequestFilter params) {
        List<String> urisParam = (params.getUris() == null || params.getUris().size() == 0) ? null : params.getUris();
        if (params.getUnique()) {
            return statsRepository.getUniqueHitsStatistic(urisParam, params.getStart(), params.getEnd());
        } else {
            return statsRepository.getHitsStatistic(urisParam, params.getStart(), params.getEnd());
        }
    }
}
