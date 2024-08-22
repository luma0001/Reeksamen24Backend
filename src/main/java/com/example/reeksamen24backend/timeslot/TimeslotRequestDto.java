package com.example.reeksamen24backend.timeslot;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record TimeslotRequestDto(
        Long id,
        List<Long> event_ids,
        LocalDate date,
        LocalTime startTime,
        LocalTime endTime) {
}
