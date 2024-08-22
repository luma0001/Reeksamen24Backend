package com.example.reeksamen24backend.event;


import com.example.reeksamen24backend.discipline.Discipline;
import com.example.reeksamen24backend.timeslot.Timeslot;
import com.example.reeksamen24backend.track.Track;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class Event {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "timeslot_id", nullable = false)
    private Timeslot timeslot;

    @ManyToOne
    @JoinColumn(name = "track_id", nullable = false)
    private Track track;

    @ManyToOne
    @JoinColumn(name = "discipline_id", nullable = false)
    private Discipline discipline;

    private Long MinimumDuration;
    @Enumerated(EnumType.STRING)
    private ParticipantGender participantGender;
    @Enumerated(EnumType.STRING)
    private ParticipantAgeGroup ParticipantAgeGroup;
    private Integer MaximumParticipants;


    public Event(Timeslot timeslot, Track track, Discipline discipline, Long minimumDuration, ParticipantGender participantGender, com.example.reeksamen24backend.event.ParticipantAgeGroup participantAgeGroup, Integer maximumParticipants) {
        this.timeslot = timeslot;
        this.track = track;
        this.discipline = discipline;
        MinimumDuration = minimumDuration;
        this.participantGender = participantGender;
        ParticipantAgeGroup = participantAgeGroup;
        MaximumParticipants = maximumParticipants;
    }
}
