package model;

import java.sql.Timestamp;
import java.util.UUID;

public class Dose {
    private UUID prescriptionId;
    private Timestamp dosageTime;
    private int amountPerDose;
    private String medId;
    private UUID confirmerId;
    private UUID patientId;

    public Dose(Prescription prescription, Timestamp dosageTime, int amountPerDose, Medication medication, Client confirmer, Patient patient) {
        this(prescription.getPrescriptionID(), dosageTime, amountPerDose, medication.getMedID(), confirmer.getId(), patient.getId());
    }

    public Dose(UUID prescriptionId, Timestamp dosageTime, int amountPerDose, String medId, UUID patientId)
    {
        this.prescriptionId = prescriptionId;
        this.dosageTime = dosageTime;
        this.amountPerDose = amountPerDose;
        this.medId = medId;
        this.patientId = patientId;
    }

    public Dose(UUID prescriptionId, Timestamp dosageTime, int amountPerDose, String medId, UUID confirmerId, UUID patientId) {
        this.prescriptionId = prescriptionId;
        this.dosageTime = dosageTime;
        this.amountPerDose = amountPerDose;
        this.medId = medId;
        this.confirmerId = confirmerId;
        this.patientId = patientId;
    }

    public UUID getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(UUID prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    public Timestamp getDosageTime() {
        return dosageTime;
    }

    public void setDosageTime(Timestamp dosageTime) {
        this.dosageTime = dosageTime;
    }

    public int getAmountPerDose() {
        return amountPerDose;
    }

    public void setAmountPerDose(int amountPerDose) {
        this.amountPerDose = amountPerDose;
    }

    public String getMedId() {
        return medId;
    }

    public void setMedId(String medId) {
        this.medId = medId;
    }

    public UUID getConfirmerId() {
        return confirmerId;
    }

    public void setConfirmerId(UUID confirmerId) {
        this.confirmerId = confirmerId;
    }

    public UUID getPatientId() {
        return patientId;
    }

    public void setPatientId(UUID patientId) {
        this.patientId = patientId;
    }

    public String toString()
    {
        return "Dose: " + dosageTime + "Prescription: " + prescriptionId;
    }

    public boolean isTaken(){
        return confirmerId != null;
    }

}
