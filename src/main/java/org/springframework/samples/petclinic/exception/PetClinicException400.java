package org.springframework.samples.petclinic.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PetClinicException400 extends RuntimeException {

    public PetClinicException400(String message) {
        super(message);
    }
}
