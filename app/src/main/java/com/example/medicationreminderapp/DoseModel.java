package com.example.medicationreminderapp;

import java.sql.Timestamp;
import java.util.UUID;

public class DoseModel {
    private String mName;
    private int mAmount;
    private boolean mOnline;
    private UUID prescriptionId;
    private Timestamp dosageTime;

    public DoseModel(int amount, String name, boolean online, UUID prescriptionId, Timestamp dosageTime) {
        mAmount = amount;
        mName = name;
        mOnline = online;
        this.prescriptionId = prescriptionId;
        this.dosageTime = dosageTime;
    }

    public String getName() {
        return mName;
    }

    public int getmAmount() {
        return mAmount;
    }

    public boolean isOnline() {
        return mOnline;
    }

    public void toggleOnline() {
        mOnline = !mOnline;
    }

    private static int lastContactId = 0;

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
}
