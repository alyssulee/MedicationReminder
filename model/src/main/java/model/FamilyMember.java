package model;

import java.util.UUID;

public class FamilyMember extends Client {

    private String relation;

    public FamilyMember(UUID id, String firstName, String lastName, String username, String password, String relation) {
        super(id, firstName, lastName, username, password);
        this.relation = relation;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String toString()
    {
        return "FamilyMember: " + super.toString() + " Relation: " + relation;
    }
}
