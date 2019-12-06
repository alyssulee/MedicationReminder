package model;

import java.util.Date;
import java.util.UUID;

public class Prescription
{
    private UUID prescriptionID;
    private String medicationId;
    private Date prescribedDate;
    private int baseAmount;
    private int amountPerDose;
    private double strength;
    private PrescriptionFrequency frequency;
    private int remainingAmount;

    public Prescription(UUID prescriptionID)
    {
        this.prescriptionID = prescriptionID;
    }

    public Prescription(UUID pID, Medication medication, Date prescribedDate, int baseAmount, int amountPerDose, double strength,
                        PrescriptionFrequency frequency, int remainingAmount)
    {
        this(pID, medication.getMedID(), prescribedDate, baseAmount, amountPerDose, strength, frequency, remainingAmount);
    }

    public Prescription(UUID pID, String medicationId, Date prescribedDate, int baseAmount, int amountPerDose, double strength,
                        PrescriptionFrequency frequency, int remainingAmount)
    {
        prescriptionID = pID;
        this.medicationId = medicationId;
        this.prescribedDate = prescribedDate;
        this.baseAmount = baseAmount;
        this.amountPerDose = amountPerDose;
        this.strength = strength;
        this.frequency = frequency;
        this.remainingAmount = remainingAmount;
    }

    public UUID getPrescriptionID()
    {
        return prescriptionID;
    }

    public Date getPrescribedDate()
    {
        return prescribedDate;
    }

    public int getBaseAmount()
    {
        return baseAmount;
    }

    public double getStrength()
    {
        return strength;
    }

    public PrescriptionFrequency getFrequency()
    {
        return frequency;
    }

    public int getRemainingAmount()
    {
        return remainingAmount;
    }

    public int getAmountPerDose()
    {
        return amountPerDose;
    }

    public void setPrescriptionID(UUID prescriptionID)
    {
        this.prescriptionID = prescriptionID;
    }

    public void setPrescribedDate(Date prescribedDate)
    {
        this.prescribedDate = prescribedDate;
    }

    public void setBaseAmount(int baseAmount)
    {
        this.baseAmount = baseAmount;
    }

    public void setAmountPerDose(int amountPerDose)
    {
        this.amountPerDose = amountPerDose;
    }

    public void setStrength(double strength)
    {
        this.strength = strength;
    }

    public void setFrequency(PrescriptionFrequency frequency)
    {
        this.frequency = frequency;
    }

    public void setRemainingAmount(int remainingAmount)
    {
        this.remainingAmount = remainingAmount;
    }

    public String toString()
    {
        String str = prescriptionID + " " + prescribedDate + "\t" + medicationId + ": " + " Strength: " + strength + " ";
        str += frequency.toString() + " " + " Base Amount: " + baseAmount + " Amount per Dose : " + amountPerDose + " Remaining: " + remainingAmount;
        return str;
    }

    public String getMedicationId() {
        return medicationId;
    }

    public void setMedicationId(String medicationId) {
        this.medicationId = medicationId;
    }
}
