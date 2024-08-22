package com.example.reeksamen24backend.timeslot;

import com.example.reeksamen24backend.event.Event;
import com.example.reeksamen24backend.event.EventRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TimeslotService {


    private TimeslotRepository timeslotRepository;
    private EventRepository eventRepository;

    private TimeslotService(TimeslotRepository timeslotRepository,EventRepository eventRepository ){
        this.timeslotRepository = timeslotRepository;
        this.eventRepository = eventRepository;
    }

    public List<TimeslotResponseDto> findAllTimeslots () {
        return timeslotRepository.findAll().stream().map(this::toTimeslotDto).toList();
    }

    public Optional<TimeslotResponseDto> findTimeslotById(Long id) {
        return timeslotRepository.findById(id).map(this::toTimeslotDto);
    }


    public Optional<TimeslotResponseDto> saveTimeslot(TimeslotRequestDto timeSlotRequestDto) {
        // Check if any of the events are already assigned to a different timeslot
        List<Long> conflictingEventIds = timeSlotRequestDto.event_ids().stream()
                .filter(eventId -> eventRepository.findById(eventId)
                        .map(Event::getTimeslot)
                        .isPresent())  // Check if the event is already associated with a timeslot
                .collect(Collectors.toList());

        if (!conflictingEventIds.isEmpty()) {
            throw new TimeslotException(
                    "The following event IDs are already associated with another timeslot: " + conflictingEventIds);
        }

        Timeslot savedEntity = timeslotRepository.save(requestToEntity(timeSlotRequestDto));
        return Optional.of(toTimeslotDto(savedEntity));
    }

    private Timeslot requestToEntity(TimeslotRequestDto requestDto) {
        Timeslot entity = new Timeslot();
        entity.setId(requestDto.id());
        entity.setDate(requestDto.date());
        entity.setStartTime(requestDto.startTime());
        entity.setEndTime(requestDto.endTime());

        List<Event> events = requestDto.event_ids().stream()
                .map(eventId -> eventRepository.findById(eventId)
                        .orElseThrow(() -> new RuntimeException("Event not found for ID: " + eventId)))
                .collect(Collectors.toList());

        entity.setEvents(events);

        return entity;
    }

    private TimeslotResponseDto toTimeslotDto(Timeslot entity) {
        TimeslotResponseDto responseDto = new TimeslotResponseDto();
        responseDto.setId(entity.getId());
        responseDto.setDate(entity.getDate());
        responseDto.setStartTime(entity.getStartTime());
        responseDto.setEndTime(entity.getEndTime());

        List<Long> eventIds = entity.getEvents().stream()
                .map(Event::getId)
                .collect(Collectors.toList());
        responseDto.setEvent_ids(eventIds);

        return responseDto;
    }
}
