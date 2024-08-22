package com.example.reeksamen24backend.track;

import com.example.reeksamen24backend.discipline.Discipline;
import com.example.reeksamen24backend.discipline.DisciplineRepository;
import com.example.reeksamen24backend.discipline.DisciplineResponseDto;
import com.example.reeksamen24backend.event.Event;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TrackService {

private final TrackRepository trackRepository;
    private final DisciplineRepository disciplineRepository;

    public TrackService(TrackRepository trackRepository, DisciplineRepository disciplineRepository) {
     this.trackRepository = trackRepository;
        this.disciplineRepository = disciplineRepository;
    }

    public List<TrackResponseDto> findAllTracks() {
        return trackRepository.findAll().stream()
                .map(this::toDisciplineResponseDto)
                .collect(Collectors.toList());
    }

    public Optional<TrackResponseDto> findTrackById(Long id) {
        return trackRepository.findById(id).map(this::toDisciplineResponseDto);
    }

    private TrackResponseDto toDisciplineResponseDto(Track track) {
        TrackResponseDto dto = new TrackResponseDto();
        dto.setId(track.getId());
        dto.setType(track.getType().toString());
        dto.setShape(track.getShape().toString());
        dto.setSurface(track.getSurface().toString());
        dto.setLanes(track.getLanes());
        dto.setLength(track.getLanes());


        // Convert disciplines to their IDs and set them in the DTO
        List<Long> disciplineIds = track.getDisciplines().stream()
                .map(discipline -> discipline.getId())
                .collect(Collectors.toList());
        dto.setDiscipline_ids(disciplineIds);

        // Convert events to their IDs and set them in the DTO
        List<Long> eventIds = track.getEvents().stream()
                .map(event -> event.getId())
                .collect(Collectors.toList());
        dto.setEvent_ids(eventIds);

        return dto;
    }



}
