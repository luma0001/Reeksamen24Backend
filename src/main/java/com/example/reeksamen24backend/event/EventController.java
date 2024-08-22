package com.example.reeksamen24backend.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("events")
public class EventController {

    private final EventsService eventsService;

    @Autowired
    public EventController(EventsService eventsService) {
        this.eventsService = eventsService;
    }

    @GetMapping
    public ResponseEntity<List<EventResponseDto>> getAllEvents() {
        var events = eventsService.findAllEvents();
        // Here we test if there are any events
        if (events != null && !events.isEmpty()) {
            return ResponseEntity.ok(events);
        }
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<Optional<EventResponseDto>> postEvent(@RequestBody EventRequestDto requestDto) {
        Optional<EventResponseDto> eventResponseDto = eventsService.saveEvent(requestDto);

        if (eventResponseDto.isPresent()) {
            return ResponseEntity.ok(eventResponseDto);
        }
        // Returnere en 400 hvis der ikke er fundet nogle 'required enities'
        return ResponseEntity.badRequest().body(Optional.empty());
    }


    @PutMapping("{id}")
    public ResponseEntity<EventResponseDto> updateEvent(@PathVariable Long id, @RequestBody EventRequestDto requestDto) {
        return eventsService.updateEventIfExists(id, requestDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}
