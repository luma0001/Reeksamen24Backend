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


    @OneToMany
    private List<Event> events;

    @ManyToMany
    @JoinTable(
            name = "track_discipline",
            joinColumns = @JoinColumn(name = "track_id"),
            inverseJoinColumns = @JoinColumn(name = "discipline_id")
    )
    private List<Discipline> disciplines;


    private Type type;
    private Shape shape;
    private Surface surface;
    private Integer length;
    private Integer lanes;

    public Track(List<Event> events, List<Discipline> disciplines, Type type, Shape shape, Surface surface, Integer length, Integer lanes) {
        this.events = events;
        this.disciplines = disciplines;
        this.type = type;
        this.shape = shape;
        this.surface = surface;
        this.length = length;
        this.lanes = lanes;
    }

    @Override
    public String toString() {
        return "Track{" +
                "id=" + id +
                ", events=" + events +
                ", disciplines=" + disciplines +
                ", type=" + type +
                ", shape=" + shape +
                ", surface=" + surface +
                ", length=" + length +
                ", lanes=" + lanes +
                '}';
    }
}
