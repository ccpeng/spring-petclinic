package org.springframework.samples.petclinic.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class PetClinicException409 extends RuntimeException {

    public PetClinicException409(String message) {
        super(message);
    }
}
