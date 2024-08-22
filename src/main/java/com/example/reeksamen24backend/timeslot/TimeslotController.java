package com.example.reeksamen24backend.timeslot;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("timeslots")
public class TimeslotController {

    private TimeslotService timeslotService;

    private TimeslotController(TimeslotService timeslotService){
        this.timeslotService = timeslotService;
    }

    @GetMapping
    public ResponseEntity<List<TimeslotResponseDto>> getAllTimeslots() {
        var timeslots = timeslotService.findAllTimeslots();
        if(timeslots != null) {
            return ResponseEntity.ok(timeslots);
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("{id}")
    public ResponseEntity<TimeslotResponseDto> getTimeslotById(@PathVariable Long id){
        var timeslot = this.timeslotService.findTimeslotById(id);
        return  timeslot.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping()
    public Optional<TimeslotResponseDto> postTimeslot(@RequestBody TimeslotRequestDto requestDto){
        return timeslotService.saveTimeslot(requestDto);
    }


    // Have en patch? der kan ændre på events/ slette dem
    // Kalender med timeslots og deres events


}
