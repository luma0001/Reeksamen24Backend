package com.example.reeksamen24backend.track;

import com.example.reeksamen24backend.discipline.Discipline;
import com.example.reeksamen24backend.event.Event;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Track {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @ManyToMany
    @JoinTable(name = "track_event", joinColumns = @JoinColumn(name = "track_id"), inverseJoinColumns = @JoinColumn(name = "discipline_id"))
    private List<Event> Events;

    @OneToMany
    private List<Discipline> disciplines;

    private Type type;
    private Shape shape;
    private Surface surface;
    private Integer length;
    private Integer lanes;

    public Track(List<Event> events, List<Discipline> disciplines, Type type, Shape shape, Surface surface, Integer length, Integer lanes) {
        Events = events;
        this.disciplines = disciplines;
        this.type = type;
        this.shape = shape;
        this.surface = surface;
        this.length = length;
        this.lanes = lanes;
    }
}
