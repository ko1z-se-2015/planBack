package com.example.volunteer.storage;


import lombok.*;

@Data
@Getter
@Setter

public class ValidationError {
    private String fieldError;
    private String messageError;

    public ValidationError(String fieldError, String messageError) {
        this.fieldError = fieldError;
        this.messageError = messageError;
    }

}
