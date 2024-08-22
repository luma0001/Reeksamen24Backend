package com.example.reeksamen24backend.track;

import com.example.reeksamen24backend.discipline.Discipline;
import com.example.reeksamen24backend.event.Event;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class TrackResponseDto {
    private Long id;
    private List<Long> event_ids;
    private List<Long> discipline_ids;
    private String type;
    private String shape;
    private String surface;
    private Integer length;
    private Integer lanes;
}
