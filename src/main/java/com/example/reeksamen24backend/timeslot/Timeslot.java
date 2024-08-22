package com.example.reeksamen24backend.timeslot;

import com.example.reeksamen24backend.event.Event;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Timeslot {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @OneToMany
    private List<Event> event;

    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;

    public Timeslot(List<Event> event, LocalDate date, LocalTime startTime, LocalTime endTime) {
        this.event = event;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
