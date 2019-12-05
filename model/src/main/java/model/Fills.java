package model;

import java.util.UUID;

public class Fills {
    private UUID pharmacistId;
    private UUID prescriptionId;

    public Fills(Pharmacist pharmacist, Prescription prescription) {
        this(pharmacist.getId(), prescription.getPrescriptionID());
    }

    public Fills(UUID pharmacistId, UUID prescriptionId) {
        this.pharmacistId = pharmacistId;
        this.prescriptionId = prescriptionId;
    }

    public UUID getPharmacistId() {
        return pharmacistId;
    }

    public void setPharmacistId(UUID pharmacistId) {
        this.pharmacistId = pharmacistId;
    }

    public UUID getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(UUID prescriptionId) {
        this.prescriptionId = prescriptionId;
    }
}
