/*
 * Copyright 2012-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.owner;

import java.time.LocalDateTime;
import java.util.Collection;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.exception.PetClinicException400;
import org.springframework.samples.petclinic.exception.PetClinicException409;
import org.springframework.samples.petclinic.visit.Visit;
import org.springframework.samples.petclinic.visit.VisitRepository;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

/**
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 * @author Michael Isvy
 * @author Dave Syer
 */
@Controller
class VisitController {

    private final VisitRepository visits;
    private final PetRepository pets;


    public VisitController(VisitRepository visits, PetRepository pets) {
        this.visits = visits;
        this.pets = pets;
    }

    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    /**
     * Called before each and every @RequestMapping annotated method.
     * 2 goals:
     * - Make sure we always have fresh data
     * - Since we do not use the session scope, make sure that Pet object always has an id
     * (Even though id is not part of the form fields)
     *
     * @param petId
     * @return Pet
     */
//    @ModelAttribute("visit")
//    public Visit loadPetWithVisit(@PathVariable("petId") int petId, Map<String, Object> model) {
//        Pet pet = this.pets.findById(petId);
//        model.put("pet", pet);
//        Visit visit = new Visit();
//        pet.addVisit(visit);
//        return visit;
//    }

    // Spring MVC calls method loadPetWithVisit(...) before initNewVisitForm is called
    @GetMapping("/owners/*/pets/{petId}/visits/new")
    public String initNewVisitForm(@PathVariable("petId") int petId, Map<String, Object> model) {
        return "pets/createOrUpdateVisitForm";
    }

    // Spring MVC calls method loadPetWithVisit(...) before processNewVisitForm is called
    @PostMapping("/owners/{ownerId}/pets/{petId}/visits/new")
    public String processNewVisitForm(@Valid Visit visit, BindingResult result) {
        if (result.hasErrors()) {
            return "pets/createOrUpdateVisitForm";
        } else {
            this.visits.save(visit);
            return "redirect:/owners/{ownerId}";
        }
    }

    @GetMapping({ "/visits/all" })
    public @ResponseBody Iterable<Visit> getAllVisits() {
        Collection<Visit> results = this.visits.findAll();
        return results;
    }

    @GetMapping({ "visits/findByPetId/{petId}" })
    public @ResponseBody Iterable<Visit> getVisitsByPetId(@PathVariable("petId") int petId) {
        Collection<Visit> results = this.visits.findByPetId(petId);
        return results;
    }

//    @GetMapping({ "visits/findByVetIdAndDateTime" })
//    public @ResponseBody Iterable<Visit> getVisitsByVetIdAndDateTime(@RequestParam("vetId") int vetId,
//        @RequestParam("dateTime") LocalDateTime dateTime) {
//        Collection<Visit> results = this.visits.findByVetIdAndDateTime(vetId, dateTime);
//        return results;
//    }

    @PostMapping({ "visits/addAppointment" })
    @ResponseBody
    public ResponseEntity addAppointment(@Valid @RequestBody Visit visit, BindingResult result) {
        if (!result.hasErrors()) {

            Collection<Visit> existingAppointments = this.visits.findByVetIdAndDateTime(visit.getVetId(), visit.getDateTime());

            if (existingAppointments.size() == 0) {
                this.visits.save(visit);
                return new ResponseEntity<>(HttpStatus.CREATED);
            } else {
                throw new PetClinicException409("Appointment already exists for this vet at this time.");
            }
        }

        throw new PetClinicException400("Appointment request malformed");
    }

}
