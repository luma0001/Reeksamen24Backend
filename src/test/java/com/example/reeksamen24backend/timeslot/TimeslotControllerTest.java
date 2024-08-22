package com.example.reeksamen24backend.timeslot;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class TimeslotControllerTest {

    @Autowired
    WebTestClient webClient;

    @MockBean
    TimeslotService timeslotService;

    private TimeslotRequestDto timeslotRequestDto1;
    private TimeslotRequestDto timeslotRequestDto2;
    private TimeslotResponseDto timeslotResponseDto1;
    private TimeslotResponseDto timeslotResponseDto2;
    @BeforeEach
    void setUp() {
        timeslotRequestDto1 = new TimeslotRequestDto(
                1L,
                List.of(1L),
                LocalDate.parse("2024-08-22"),
                LocalTime.parse("08:00:00"),
                LocalTime.parse("12:00:00")
        );

        timeslotRequestDto2 = new TimeslotRequestDto(
                2L,
                List.of(2L),
                LocalDate.parse("2024-08-22"),
                LocalTime.parse("13:00:00"),
                LocalTime.parse("17:00:00")
        );

        timeslotResponseDto1 = new TimeslotResponseDto();
        timeslotResponseDto1.setId(1L);
        timeslotResponseDto1.setEvent_ids(List.of(1L));
        timeslotResponseDto1.setDate(LocalDate.parse("2024-08-22"));
        timeslotResponseDto1.setStartTime(LocalTime.parse("08:00:00"));
        timeslotResponseDto1.setEndTime(LocalTime.parse("12:00:00"));

        timeslotResponseDto2 = new TimeslotResponseDto();
        timeslotResponseDto2.setId(2L);
        timeslotResponseDto2.setEvent_ids(List.of(2L));
        timeslotResponseDto2.setDate(LocalDate.parse("2024-08-22"));
        timeslotResponseDto2.setStartTime(LocalTime.parse("13:00:00"));
        timeslotResponseDto2.setEndTime(LocalTime.parse("17:00:00"));
    }

    @Test
    void getAllTimeslots() {
        when(timeslotService.findAllTimeslots()).thenReturn(List.of(timeslotResponseDto1, timeslotResponseDto2));

        webClient.get()
                .uri("/timeslots")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(TimeslotResponseDto.class)
                .hasSize(2)
                .contains(timeslotResponseDto1, timeslotResponseDto2);
    }

    @Test
    void getTimeslotById() {
        when(timeslotService.findTimeslotById(1L)).thenReturn(Optional.of(timeslotResponseDto1));

        webClient.get()
                .uri("/timeslots/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody(TimeslotResponseDto.class)
                .isEqualTo(timeslotResponseDto1);
    }

    @Test
    void postTimeslot() {
        when(timeslotService.saveTimeslot(timeslotRequestDto1)).thenReturn(Optional.of(timeslotResponseDto1));

        webClient.post()
                .uri("/timeslots")
                .bodyValue(timeslotRequestDto1)
                .exchange()
                .expectStatus().isOk()
                .expectBody(TimeslotResponseDto.class)
                .isEqualTo(timeslotResponseDto1);
    }

    @Test
    void postTimeslot_WithConflictingEvents() {
        // Simulate a conflict where the event is already assigned to another timeslot
        List<Long> conflictingEventIds = List.of(1L);
        doThrow(new TimeslotException("The following event IDs are already associated with another timeslot: " + conflictingEventIds))
                .when(timeslotService).saveTimeslot(timeslotRequestDto1);

        webClient.post()
                .uri("/timeslots")
                .bodyValue(timeslotRequestDto1)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .jsonPath("$.message").isEqualTo("The following event IDs are already associated with another timeslot: " + conflictingEventIds);
    }
}