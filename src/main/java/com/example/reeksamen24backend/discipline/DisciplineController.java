package com.example.reeksamen24backend.discipline;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/disciplines")
public class DisciplineController {

    @Autowired
    private DisciplineService disciplineService;

    private DisciplineController(DisciplineService disciplineService){
        this.disciplineService = disciplineService;
    }

    @GetMapping
    public ResponseEntity<List<DisciplineResponseDto>> getAllDisciplines() {
        List<DisciplineResponseDto> disciplines = disciplineService.findAllDisciplines();
        if (disciplines != null && !disciplines.isEmpty()) {
            return ResponseEntity.ok(disciplines);
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DisciplineResponseDto> getDisciplineById(@PathVariable Long id) {
        Optional<DisciplineResponseDto> discipline = disciplineService.findDisciplineById(id);
        return discipline.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

}


