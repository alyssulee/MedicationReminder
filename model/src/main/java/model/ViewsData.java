package model;

import java.util.UUID;

public class ViewsData {
    private UUID familyMemberId;
    private UUID patientId;

    public ViewsData(FamilyMember familyMember, Patient patient) {
        this(familyMember.getId(), patient.getId());
    }

    public ViewsData(UUID familyMemberId, UUID patientId) {
        this.familyMemberId = familyMemberId;
        this.patientId = patientId;
    }

    public UUID getFamilyMemberId() {
        return familyMemberId;
    }

    public void setFamilyMemberId(UUID familyMemberId) {
        this.familyMemberId = familyMemberId;
    }

    public UUID getPatientId() {
        return patientId;
    }

    public void setPatientId(UUID patientId) {
        this.patientId = patientId;
    }
}
