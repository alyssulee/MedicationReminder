package model;

import java.sql.Time;
import java.util.Date;
import java.util.UUID;

public class Appointment {
    private UUID DoctorId;
    private UUID PatientId;
    private Date date;
    private Time time;


    public Appointment(Doctor doctor, Patient patient, Date date, Time time) {
        this(doctor.getId(), patient.getId(), date, time);
    }

    public Appointment(UUID doctorId, UUID patientId, Date date, Time time) {
        DoctorId = doctorId;
        PatientId = patientId;
        this.date = date;
        this.time = time;
    }

    public UUID getDoctorId() {
        return DoctorId;
    }

    public void setDoctorId(UUID doctorId) {
        DoctorId = doctorId;
    }

    public UUID getPatientId() {
        return PatientId;
    }

    public void setPatientId(UUID patientId) {
        PatientId = patientId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public String toString()
    {
        return "Appointment: " + date.toString() + " " + time.toString() + " with " + DoctorId;
    }
}
