package model;

import java.util.UUID;

public class Doctor extends User {
    public Doctor(UUID id, String firstName, String lastName, String username, String password) {
        super(id, firstName, lastName, username, password);
    }

    public String toString()
    {
        return "Doctor: " + super.toString();
    }
}
