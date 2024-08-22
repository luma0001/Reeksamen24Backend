package com.example.reeksamen24backend.event;

public record EventRequestDto(
        Long id,
        Long timeslot_id,
        Long track_id,
        Long discipline_id,
        Long minimumDuration,
        String participantGender,
        String participantAgeGroup,
        Integer maximumParticipants
) {
}
