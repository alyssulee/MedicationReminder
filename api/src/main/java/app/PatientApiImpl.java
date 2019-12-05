package app;

import com.example.apiabstractions.PatientApi;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import model.Appointment;
import model.BloodPressure;
import model.Doctor;
import model.Dose;
import model.Medication;
import model.Patient;
import model.PatientMeasurement;

public class PatientApiImpl implements PatientApi {

    private Patient patient;

    private PatientApiImpl() {
    }

    public static PatientApiImpl createFor(Patient patient) {
        PatientApiImpl api = new PatientApiImpl();
        api.patient = patient;
        return api;
    }

    @Override
    public List<Appointment> getUpcomingAppointments() {
        // Todo: Give real implementation
        ArrayList<Appointment> appointments = new ArrayList<>();
        appointments.add(new Appointment(UUID.randomUUID(), UUID.randomUUID(), new Date(), new Time(0)));
        appointments.add(new Appointment(UUID.randomUUID(), UUID.randomUUID(), new Date(), new Time(0)));
        return appointments;
    }

    @Override
    public int getCurrentStreak() {
        // Todo: Give real implementation
        return 5;
    }

    @Override
    public int getLongestStreak() {
        // Todo: Give real implementation
        return 30;
    }

    @Override
    public List<Dose> getTodaysDoses() {
        // Todo: Give real implementation
        ArrayList<Dose> doses = new ArrayList<>();
        doses.add(new Dose(UUID.randomUUID(), new Timestamp(0), 1, "medIdHere1", UUID.randomUUID(), UUID.randomUUID()));
        doses.add(new Dose(UUID.randomUUID(), new Timestamp(0), 2, "medIdHere2", UUID.randomUUID(), UUID.randomUUID()));
        return doses;
    }

    @Override
    public void confirmDoseTaken(Dose dose) {
        // Todo: Give real implementation
        dose.setConfirmerId(UUID.randomUUID());
    }

    @Override
    public void markDoseUntaken(Dose dose) {
        // Todo: Give real implementation
        dose.setConfirmerId(null);
    }

    @Override
    public List<PatientMeasurement> getAllMeasurements() {
        // Todo: Give real implementation
        ArrayList<PatientMeasurement> measurements = new ArrayList<>();
        measurements.add(new PatientMeasurement(new BloodPressure(5, 6), 54, new Timestamp(0)));
        measurements.add(new PatientMeasurement(new BloodPressure(3, 1), 44, new Timestamp(0)));
        return measurements;
    }

    @Override
    public void addMeasurement(PatientMeasurement measurement) {
        // Todo: Give real implementation
    }

    @Override
    public Doctor getDoctor(UUID doctorId) {
        // Todo: Give real implementation
        return new Doctor(doctorId, "Doc", "Franklin", null, null);
    }

    @Override
    public Medication getMedication(String medId) {
        // Todo: Give real implementation
        ArrayList<String> names = new ArrayList<>();
        names.add("Big Med");
        names.add("Patient is sad \uD83D\uDE0A");
        return new Medication(medId, names);
    }
}