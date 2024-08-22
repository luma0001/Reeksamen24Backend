package com.example.reeksamen24backend.track;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("tracks")
public class TrackController {

    private final TrackService trackService;

    public TrackController(TrackService trackService) {
        this.trackService = trackService;
    }

    @GetMapping
    public ResponseEntity<List<TrackResponseDto>> getAllAthlete() {
        var athlets = trackService.findAllTracks();
        if(athlets != null) {
            return ResponseEntity.ok(athlets);
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("{id}")
    public ResponseEntity<TrackResponseDto> getAthleteById(@PathVariable Long id){
        var track = this.trackService.findTrackById(id);
        return  track.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }


}
