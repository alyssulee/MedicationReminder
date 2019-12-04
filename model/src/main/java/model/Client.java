package model;

import java.util.UUID;

public abstract class Client extends User {
    public Client(UUID id, String firstName, String lastName, String username, String password) {
        super(id, firstName, lastName, username, password);
    }
}
