package com.example.reeksamen24backend.discipline;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DisciplineResponseDto {
    private Long id;
    private String name;
    private Long ApproxDuration;
}
