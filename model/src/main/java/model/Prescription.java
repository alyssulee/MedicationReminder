package model;

import java.util.Date;
import java.util.UUID;

public class Prescription
{
    UUID prescriptionID;
    Medication medication;
    Date prescribedDate;
    int baseAmount;
    double amountPerDose;
    double strength;
    PrescriptionFrequency frequency;
    int remainingAmount;

    public Prescription(UUID pID, Medication medication, Date prescribedDate, int baseAmount, double amountPerDose, double strength,
                        PrescriptionFrequency frequency, int remainingAmount)
    {
        prescriptionID = pID;
        this.medication = medication;
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

    public Medication getMedication()
    {
        return medication;
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

    public double getAmountPerDose()
    {
        return amountPerDose;
    }

    public void setPrescriptionID(UUID prescriptionID)
    {
        this.prescriptionID = prescriptionID;
    }

    public void setMedication(Medication medication)
    {
        this.medication = medication;
    }

    public void setPrescribedDate(Date prescribedDate)
    {
        this.prescribedDate = prescribedDate;
    }

    public void setBaseAmount(int baseAmount)
    {
        this.baseAmount = baseAmount;
    }

    public void setAmountPerDose(double amountPerDose)
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
        String str = prescriptionID + " " + prescribedDate + "\t" + medication + ": " + " Strength: " + strength + " ";
        str += frequency.toString() + " " + " Base Amount: " + baseAmount + " Amount per Dose : " + amountPerDose + " Remaining: " + remainingAmount;
        return str;
    }
}
