package com.example.reeksamen24backend.timeslot;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class TimeslotResponseDto {
    private Long id;
    private List<Long> event_ids;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
}
