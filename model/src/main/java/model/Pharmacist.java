package model;

import java.util.UUID;

public class Pharmacist extends User {
    public Pharmacist(UUID id, String firstName, String lastName, String username, String password) {
        super(id, firstName, lastName,  username,  password);
    }

    public String toString()
    {
        return "Pharmacist: " + super.toString();
    }
}
