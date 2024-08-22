package com.example.reeksamen24backend.timeslot;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponseDto {
        private String message;

        public ErrorResponseDto(String message) {
            this.message = message;
        }
}
