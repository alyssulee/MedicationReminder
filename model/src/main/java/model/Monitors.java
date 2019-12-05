package model;

import java.util.Date;
import java.util.UUID;

public class Monitors {
    private UUID doctorId;
    private UUID patientId;
    private Date startDate;
    private Date endDate;

    public Monitors(Doctor doctor, Patient patient, Date startDate, Date endDate) {
        this(doctor.getId(), patient.getId(), startDate, endDate);
    }

    public Monitors(UUID doctorId, UUID patientId, Date startDate, Date endDate) {
        this.doctorId = doctorId;
        this.patientId = patientId;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public UUID getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(UUID doctorId) {
        this.doctorId = doctorId;
    }

    public UUID getPatientId() {
        return patientId;
    }

    public void setPatientId(UUID patientId) {
        this.patientId = patientId;
    }

    public Date getstartDate() {
        return startDate;
    }

    public void setstartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
