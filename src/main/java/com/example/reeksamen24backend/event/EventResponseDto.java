package com.example.reeksamen24backend.event;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EventResponseDto {
    private Long id;
    private Long timeslot_id;
    private Long track_id;
    private Long discipline_id;
    private Long minimumDuration;
    private String participantGender;
    private String participantAgeGroup;
    private Integer maximumParticipants;
}
