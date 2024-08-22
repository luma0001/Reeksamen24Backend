package com.example.reeksamen24backend.discipline;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DisciplineService {

    @Autowired
    private DisciplineRepository disciplineRepository;

    public List<DisciplineResponseDto> findAllDisciplines() {
        return disciplineRepository.findAll().stream()
                .map(this::toDisciplineResponseDto)
                .collect(Collectors.toList());
    }

    public Optional<DisciplineResponseDto> findDisciplineById(Long id) {
        return disciplineRepository.findById(id).map(this::toDisciplineResponseDto);
    }

    private DisciplineResponseDto toDisciplineResponseDto(Discipline discipline) {
        DisciplineResponseDto dto = new DisciplineResponseDto();
        dto.setId(discipline.getId());
        dto.setName(discipline.getName());
        dto.setApproxDuration(discipline.getApproxDuration());
        return dto;
    }

}
