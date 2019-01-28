package org.springframework.samples.petclinic.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PetClinicException extends RuntimeException {

    public PetClinicException(String message) {
        super(message);
    }
}
