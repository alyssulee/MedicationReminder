package model;

import java.util.Date;

public class PatientMeasurement {
    private BloodPressure bloodPressure;
    private double bloodSugar;
    private Date timestamp;

    public PatientMeasurement(BloodPressure bloodPressure, double bloodSugar, Date timestamp) {
        this.bloodPressure = bloodPressure;
        this.bloodSugar = bloodSugar;
        this.timestamp = timestamp;
    }

    public BloodPressure getBloodPressure() {
        return bloodPressure;
    }

    public void setBloodPressure(BloodPressure bloodPressure) {
        this.bloodPressure = bloodPressure;
    }

    public double getBloodSugar() {
        return bloodSugar;
    }

    public void setBloodSugar(double bloodSugar) {
        this.bloodSugar = bloodSugar;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String toString()
    {
        return "Date: " + timestamp + " \t" + "BloodPressure: " + bloodPressure.toString() + "\tBloodSugar: " + bloodSugar;
    }
}
