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


    @Transactional
    public Optional<EventResponseDto> updateEventIfExists(Long id, EventRequestDto eventRequestDto) {
        // Check if the event exists
        return eventRepository.findById(id).map(existingEvent -> {
            // Convert the request DTO to an entity
            Event newEvent = eventRequestToEntity(eventRequestDto);

            // If the event has a track, check if the track's discipline matches the event's discipline
            if (newEvent.getTrack() != null) {
                Track track = newEvent.getTrack();
                Discipline eventDiscipline = newEvent.getDiscipline();

                // Ensure that the discipline of the track is one of the disciplines associated with the track
                boolean disciplineMatch = track.getDisciplines().stream()
                        .anyMatch(discipline -> discipline.equals(eventDiscipline));

                if (!disciplineMatch) {
                    throw new IllegalArgumentException("Track's disciplines do not match the Event's discipline");
                }
            }

            // Update the event with the new values
            existingEvent.setTimeslot(newEvent.getTimeslot());
            existingEvent.setTrack(newEvent.getTrack());
            existingEvent.setDiscipline(newEvent.getDiscipline());
            existingEvent.setMinimumDuration(newEvent.getMinimumDuration());
            existingEvent.setParticipantGender(newEvent.getParticipantGender());
            existingEvent.setParticipantAgeGroup(newEvent.getParticipantAgeGroup());
            existingEvent.setMaximumParticipants(newEvent.getMaximumParticipants());

            // Save and return the updated event
            Event updatedEvent = eventRepository.save(existingEvent);
            return toEventDto(updatedEvent);
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


