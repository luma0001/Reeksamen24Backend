package com.example.reeksamen24backend.configuration;

import com.example.reeksamen24backend.discipline.Discipline;
import com.example.reeksamen24backend.discipline.DisciplineRepository;
import com.example.reeksamen24backend.event.Event;
import com.example.reeksamen24backend.event.EventRepository;
import com.example.reeksamen24backend.event.ParticipantAgeGroup;
import com.example.reeksamen24backend.event.ParticipantGender;
import com.example.reeksamen24backend.timeslot.Timeslot;
import com.example.reeksamen24backend.timeslot.TimeslotRepository;

import com.example.reeksamen24backend.track.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Component
public class InitData implements CommandLineRunner {
    @Autowired
    private DisciplineRepository disciplineRepository;

    @Autowired
    private TrackRepository trackRepository;

    @Autowired
    private TimeslotRepository timeslotRepository;

    @Autowired
    private EventRepository eventRepository;

    @Override
    public void run(String... args) throws Exception {
        // Create and save Disciplines
        Discipline sprint100m = new Discipline("100m Sprint", 60L);
        Discipline marathon = new Discipline("Marathon", 10800L);
        Discipline longJump = new Discipline("Long Jump", 180L);

        List<Discipline> disciplines = List.of(sprint100m, marathon, longJump);
        disciplineRepository.saveAll(disciplines);

        // Create and save Timeslots
        Timeslot morningSlot = new Timeslot(null, LocalDate.of(2024, 8, 22), LocalTime.of(8, 0), LocalTime.of(12, 0));
        Timeslot afternoonSlot = new Timeslot(null, LocalDate.of(2024, 8, 22), LocalTime.of(13, 0), LocalTime.of(17, 0));
        Timeslot eveningSlot = new Timeslot(null, LocalDate.of(2024, 8, 22), LocalTime.of(18, 0), LocalTime.of(22, 0));

        List<Timeslot> timeslots = List.of(morningSlot, afternoonSlot, eveningSlot);
        timeslotRepository.saveAll(timeslots);

        // Create and save Tracks
        Track outdoorTrack = new Track(null, List.of(sprint100m, marathon), Type.TRACK, Shape.OVAL, Surface.GRASS, 400, 8);
        Track fieldTrack = new Track(null, List.of(longJump), Type.FIELD, Shape.STRAIGHT, Surface.GRASS, 0, 0); // Adjusted values for field track

        List<Track> tracks = List.of(outdoorTrack, fieldTrack);
        trackRepository.saveAll(tracks);

        // Create and save Events
        Event sprint100mEvent = new Event(morningSlot, outdoorTrack, sprint100m, 60L, ParticipantGender.MALE, ParticipantAgeGroup.ADULT, 8);
        Event marathonEvent = new Event(afternoonSlot, outdoorTrack, marathon, 10800L, ParticipantGender.FEMALE, ParticipantAgeGroup.SENIOR, 1000);
        Event longJumpEvent = new Event(eveningSlot, fieldTrack, longJump, 180L, ParticipantGender.OTHER, ParticipantAgeGroup.YOUTH, 10);

        List<Event> events = List.of(sprint100mEvent, marathonEvent, longJumpEvent);
        eventRepository.saveAll(events);

        // Update Timeslots with Events
        morningSlot.setEvents(List.of(sprint100mEvent));
        afternoonSlot.setEvents(List.of(marathonEvent));
        eveningSlot.setEvents(List.of(longJumpEvent));

        timeslotRepository.saveAll(List.of(morningSlot, afternoonSlot, eveningSlot));
    }
}
