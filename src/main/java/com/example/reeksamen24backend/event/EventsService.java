package com.example.reeksamen24backend.event;
import com.example.reeksamen24backend.discipline.Discipline;
import com.example.reeksamen24backend.discipline.DisciplineRepository;
import com.example.reeksamen24backend.discipline.DisciplineService;
import com.example.reeksamen24backend.timeslot.Timeslot;
import com.example.reeksamen24backend.timeslot.TimeslotRepository;
import com.example.reeksamen24backend.track.Track;
import com.example.reeksamen24backend.track.TrackRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EventsService {

    @Autowired
    private final EventRepository eventRepository;
    private final TrackRepository trackRepository;
    private final DisciplineRepository disciplineRepository;
    private final TimeslotRepository timeslotRepository;

    private EventsService(EventRepository eventRepository, TrackRepository trackRepository, DisciplineRepository disciplineRepository, TimeslotRepository timeslotRepository) {
        this.eventRepository = eventRepository;
        this.trackRepository = trackRepository;
        this.disciplineRepository = disciplineRepository;
        this.timeslotRepository = timeslotRepository;
    }

    public Optional<EventResponseDto> saveEvent(EventRequestDto eventRequestDto) {
        return Optional.of(toEventDto(eventRepository.save(eventRequestToEntity(eventRequestDto))));
    }

    public List<EventResponseDto> findAllEvents() {
        return eventRepository.findAll().stream()
                .map(this::toEventDto)
                .collect(Collectors.toList());
    }


    private Event eventRequestToEntity(EventRequestDto requestDto) {
        Event event = new Event();
        Timeslot timeslot = timeslotRepository.findById(requestDto.timeslot_id())
                .orElseThrow(() -> new EntityNotFoundException("Timeslot not found"));
        Discipline discipline = disciplineRepository.findById(requestDto.discipline_id())
                .orElseThrow(() -> new EntityNotFoundException("Discipline not found"));

        event.setTimeslot(timeslot);
        event.setDiscipline(discipline);

        // Check if track_id is provided
        if (requestDto.track_id() != null) {
            Track track = trackRepository.findById(requestDto.track_id())
                    .orElseThrow(() -> new EntityNotFoundException("Track not found"));
            event.setTrack(track);
        }

        event.setMaximumParticipants(requestDto.maximumParticipants());
        event.setMinimumDuration(requestDto.minimumDuration());
        event.setParticipantGender(ParticipantGender.valueOf(requestDto.participantGender()));
        event.setParticipantAgeGroup(ParticipantAgeGroup.valueOf(requestDto.participantAgeGroup()));

        return event;
    }


    public EventResponseDto toEventDto(Event entity) {
        EventResponseDto dto = new EventResponseDto();
        dto.setId(entity.getId());
        dto.setTimeslot_id(entity.getTimeslot().getId());
        dto.setDiscipline_id(entity.getDiscipline().getId());

        // Check if track is not null before accessing its properties
        if (entity.getTrack() != null) {
            dto.setTrack_id(entity.getTrack().getId());
        } else {
            dto.setTrack_id(null);  // or handle it as appropriate
        }

        dto.setMaximumParticipants(entity.getMaximumParticipants());
        dto.setMinimumDuration(entity.getMinimumDuration());
        dto.setParticipantGender(entity.getParticipantGender().toString());
        dto.setParticipantAgeGroup(entity.getParticipantAgeGroup().toString());
        return dto;
    }
}


