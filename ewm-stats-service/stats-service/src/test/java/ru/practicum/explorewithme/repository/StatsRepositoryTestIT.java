package ru.practicum.explorewithme.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.practicum.explorewithme.dto.response.StatsView;
import ru.practicum.explorewithme.model.Hit;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class StatsRepositoryTestIT {
    @Autowired
    private StatsRepository statsRepository;

    private Hit hit1;
    private Hit hit2;
    private Hit hit3;
    private Hit hit4;
    private LocalDateTime created;

    @BeforeEach
    void setUp() {
        final String appName = "ExploreWithMe";
        created = LocalDateTime.of(2024,7,26,11,40);
        hit1 = new Hit(null, appName, "/events", "192.168.0.1", created);
        hit2 = new Hit(null, appName, "/events/1", "192.168.0.1", created);
        hit3 = new Hit(null, appName, "/events/1", "192.168.0.1", created);
        hit4 = new Hit(null, appName, "/events/2", "192.168.0.2", created);

        statsRepository.save(hit1);
        statsRepository.save(hit2);
        statsRepository.save(hit3);
        statsRepository.save(hit4);
    }

    @Test
    @DisplayName("getHitsStatistic. Если список uris отсутствует, должен вернуться список содержащий все uri")
    void getHitsStatistic_whenUrisListIsNull_thenReturnCollectionOfAllUrisWithHitsCount() {
        final LocalDateTime start = LocalDateTime.of(2024,7,26, 0, 0, 0);
        final LocalDateTime end = LocalDateTime.of(2024,7,26, 23, 59, 59);

        List<StatsView> actual = statsRepository.getHitsStatistic(null, start, end);

        assertNotNull(actual);
        assertEquals(3, actual.size());
        assertEquals("/events/1", actual.get(0).getUri());
        assertEquals("/events", actual.get(1).getUri());
        assertEquals("/events/2", actual.get(2).getUri());
    }

    @Test
    @DisplayName("getHitsStatistic. Должен вернуться список содержащий все uri с подсчетом всех посещений")
    void getHitsStatistic_returnCollectionUrisWithHitsCount() {
        final LocalDateTime start = LocalDateTime.of(2024,7,26, 0, 0, 0);
        final LocalDateTime end = LocalDateTime.of(2024,7,26, 23, 59, 59);

        List<StatsView> actual = statsRepository.getHitsStatistic(null, start, end);

        assertNotNull(actual);
        assertEquals(3, actual.size());
        assertEquals("/events/1", actual.get(0).getUri());
        assertEquals(2, actual.get(0).getHits());
        assertEquals("/events", actual.get(1).getUri());
        assertEquals(1, actual.get(1).getHits());
        assertEquals("/events/2", actual.get(2).getUri());
        assertEquals(1, actual.get(2).getHits());
    }

    @Test
    @DisplayName("getHitsStatistic. Если список uris не пуст, должен вернуться список содержащий uri из списка uris")
    void getHitsStatistic_whenUrisListIsNoEmpty_thenReturnCollectionOfUris() {
        final LocalDateTime start = LocalDateTime.of(2024,7,26, 0, 0, 0);
        final LocalDateTime end = LocalDateTime.of(2024,7,26, 23, 59, 59);
        final List<String> uris = List.of("/events", "/events/1");

        List<StatsView> actual = statsRepository.getHitsStatistic(uris, start, end);

        assertNotNull(actual);
        assertEquals(2, actual.size());
        assertEquals("/events/1", actual.get(0).getUri());
        assertEquals("/events", actual.get(1).getUri());
    }

    @Test
    @DisplayName("getHitsStatistic. Если список uris содержит отсутствующие в БД uri, должен вернуться пустой")
    void getHitsStatistic_whenUrisListOfAnotherUri_thenReturnEmptyCollection() {
        final LocalDateTime start = LocalDateTime.of(2024,7,26, 0, 0, 0);
        final LocalDateTime end = LocalDateTime.of(2024,7,26, 23, 59, 59);
        final List<String> uris = List.of("/events/4");

        List<StatsView> actual = statsRepository.getHitsStatistic(uris, start, end);

        assertNotNull(actual);
        assertEquals(0, actual.size());
    }

    @Test
    @DisplayName("getHitsStatistic. Если в период дат не попадают посещения, должен вернуться пустой")
    void getHitsStatistic_whenDatePeriodNotInDB_thenReturnEmptyCollection() {
        final LocalDateTime start = LocalDateTime.of(2024,8,26, 0, 0, 0);
        final LocalDateTime end = LocalDateTime.of(2024,8,26, 23, 59, 59);

        List<StatsView> actual = statsRepository.getHitsStatistic(null, start, end);

        assertNotNull(actual);
        assertEquals(0, actual.size());
    }

    @Test
    @DisplayName("getUniqueHitsStatistic. Если список uris отсутствует, должен вернуться список содержащий все uri")
    void getUniqueHitsStatistic_whenUrisListIsNull_thenReturnCollectionOfAllUrisWithUniqueHitsCount() {
        final LocalDateTime start = LocalDateTime.of(2024,7,26, 0, 0, 0);
        final LocalDateTime end = LocalDateTime.of(2024,7,26, 23, 59, 59);

        List<StatsView> actual = statsRepository.getUniqueHitsStatistic(null,start,end);

        assertNotNull(actual);
        assertEquals(3, actual.size());
        assertEquals("/events", actual.get(0).getUri());
        assertEquals("/events/1", actual.get(1).getUri());
        assertEquals("/events/2", actual.get(2).getUri());
    }

    @Test
    @DisplayName("getUniqueHitsStatistic. Должен вернуться список содержащий все uri с подсчетом уникальных посещений")
    void getUniqueHitsStatistic_thenReturnCollectionUrisWithUniqueHitsCount() {
        final LocalDateTime start = LocalDateTime.of(2024,7,26, 0, 0, 0);
        final LocalDateTime end = LocalDateTime.of(2024,7,26, 23, 59, 59);

        List<StatsView> actual = statsRepository.getUniqueHitsStatistic(null,start,end);

        assertNotNull(actual);
        assertEquals(3, actual.size());
        assertEquals("/events", actual.get(0).getUri());
        assertEquals(1, actual.get(0).getHits());
        assertEquals("/events/1", actual.get(1).getUri());
        assertEquals(1, actual.get(1).getHits());
        assertEquals("/events/2", actual.get(2).getUri());
        assertEquals(1, actual.get(2).getHits());
    }


    @Test
    @DisplayName("getUniqueHitsStatistic. Если список uris не пуст, должен вернуться список содержащий uri из списка uris")
    void getUniqueHitsStatistic_whenUrisListIsNoEmpty_thenReturnCollectionOfUris() {
        final LocalDateTime start = LocalDateTime.of(2024,7,26, 0, 0, 0);
        final LocalDateTime end = LocalDateTime.of(2024,7,26, 23, 59, 59);
        final List<String> uris = List.of("/events", "/events/1");

        List<StatsView> actual = statsRepository.getUniqueHitsStatistic(uris, start, end);

        assertNotNull(actual);
        assertEquals(2, actual.size());
        assertEquals("/events", actual.get(0).getUri());
        assertEquals("/events/1", actual.get(1).getUri());
    }

    @Test
    @DisplayName("getUniqueHitsStatistic. Если список uris содержит отсутствующие в БД uri, должен вернуться пустой")
    void getUniqueHitsStatistic_whenUrisListOfAnotherUri_thenReturnEmptyCollection() {
        final LocalDateTime start = LocalDateTime.of(2024,7,26, 0, 0, 0);
        final LocalDateTime end = LocalDateTime.of(2024,7,26, 23, 59, 59);
        final List<String> uris = List.of("/events/4");

        List<StatsView> actual = statsRepository.getUniqueHitsStatistic(uris, start, end);

        assertNotNull(actual);
        assertEquals(0, actual.size());
    }

    @Test
    @DisplayName("getUniqueHitsStatistic. Если в период дат не попадают посещения, должен вернуться пустой")
    void getUniqueHitsStatistic_whenDatePeriodNotInDB_thenReturnEmptyCollection() {
        final LocalDateTime start = LocalDateTime.of(2024,8,26, 0, 0, 0);
        final LocalDateTime end = LocalDateTime.of(2024,8,26, 23, 59, 59);

        List<StatsView> actual = statsRepository.getUniqueHitsStatistic(null, start, end);

        assertNotNull(actual);
        assertEquals(0, actual.size());
    }
}