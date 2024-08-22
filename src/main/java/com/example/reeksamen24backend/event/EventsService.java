package com.example.reeksamen24backend.event;
import com.example.reeksamen24backend.discipline.Discipline;
import com.example.reeksamen24backend.discipline.DisciplineRepository;
import com.example.reeksamen24backend.timeslot.Timeslot;
import com.example.reeksamen24backend.timeslot.TimeslotRepository;
import com.example.reeksamen24backend.track.Track;
import com.example.reeksamen24backend.track.TrackRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EventsService {

    private final EventRepository eventRepository;
    private final TrackRepository trackRepository;
    private final DisciplineRepository disciplineRepository;
    private final TimeslotRepository timeslotRepository;

    @Autowired
    public EventsService(EventRepository eventRepository, TrackRepository trackRepository,
                         DisciplineRepository disciplineRepository, TimeslotRepository timeslotRepository) {
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


    public Optional<EventResponseDto> findEventById(Long id) {
        return eventRepository.findById(id).map(this::toEventDto);
    }


//    public Optional<EventResponseDto> updateTrack(Long eventId, Long trackId) {
//        Event event = eventRepository.findById(eventId)
//                .orElseThrow(() -> new EntityNotFoundException("Event not found"));
//
//        Track track = trackRepository.findById(trackId)
//                .orElseThrow(() -> new EntityNotFoundException("Track not found"));
//
//        // Check if the event's discipline is in the track's disciplines list
//        boolean disciplineMatch = track.getDisciplines().stream()
//                .anyMatch(discipline -> discipline.equals(event.getDiscipline()));
//
//        if (!disciplineMatch) {
//            throw new IllegalArgumentException("Track disciplines do not match Event discipline");
//        }
//
//        event.setTrack(track);
//        eventRepository.save(event);
//
//        return Optional.of(toEventDto(event));
//    }




    public Optional<EventResponseDto> updateEventIfExists(Long id, EventRequestDto eventRequestDto) {
        if (eventRepository.existsById(id)) {
            Event entity = eventRequestToEntity(eventRequestDto);
            entity.setId(id);
            Event updatedEvent = eventRepository.save(entity);
            return Optional.of(toEventDto(updatedEvent));
        }
        return Optional.empty();
    }


    @Transactional
    public Optional<EventResponseDto> deleteById(Long id) {
        return eventRepository.findById(id).map(event -> {
            eventRepository.deleteById(id);
            return toEventDto(event);
        });
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

        if (entity.getTrack() != null) {
            dto.setTrack_id(entity.getTrack().getId());
        } else {
            dto.setTrack_id(null);
        }

        dto.setMaximumParticipants(entity.getMaximumParticipants());
        dto.setMinimumDuration(entity.getMinimumDuration());
        dto.setParticipantGender(entity.getParticipantGender().toString());
        dto.setParticipantAgeGroup(entity.getParticipantAgeGroup().toString());
        return dto;
    }
}


