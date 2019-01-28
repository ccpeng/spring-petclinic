package org.springframework.samples.petclinic.owner;

import java.io.Serializable;
import java.util.List;

public class SimpleOwner implements Serializable {
    private Integer id;
    private String firstName;
    private String lastName;
    private List<SimplePet> listOfPets;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<SimplePet> getListOfPets() {
        return listOfPets;
    }

    public void setListOfPets(List<SimplePet> listOfPets) {
        this.listOfPets = listOfPets;
    }
}
