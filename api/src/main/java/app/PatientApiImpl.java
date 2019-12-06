package app;

import com.example.apiabstractions.PatientApi;

import java.sql.Time;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import model.Appointment;
import model.Doctor;
import model.Dose;
import model.Medication;
import model.Patient;
import model.PatientMeasurement;
import model.Prescription;
import repository.MedRepository;
import repository.UserRepository;

public class PatientApiImpl implements PatientApi {

    private UserRepository userRepository = new UserRepository();
    private MedRepository medRepository = new MedRepository();
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
        return userRepository.getPatientsAppointments(patient.getId());
    }

    @Override
    public int getCurrentStreak() {
        return userRepository.getCurrentStreak(patient);
    }

    @Override
    public int getLongestStreak() {
        return userRepository.getLongestStreak(patient);
    }

    @Override
    public List<Dose> getTodaysDoses() {
        return userRepository.getPatientsDosesOn(patient.getId(), new Date());
    }

    @Override
    public void confirmDoseTaken(Dose dose) {
        dose.setConfirmerId(patient.getId());
        userRepository.confirmDose(dose, patient, Time.valueOf(LocalTime.now()));
    }

    @Override
    public void markDoseUntaken(Dose dose) {
        // Todo: Give real implementation
        dose.setConfirmerId(null);
    }

    @Override
    public List<PatientMeasurement> getAllMeasurements() {
        Patient container = userRepository.getPatientByID(patient.getId());
        return container.getMeasurements();
    }

    @Override
    public void addMeasurement(PatientMeasurement measurement) {
        userRepository.addMeasurement(patient.getId(), measurement);
    }

    @Override
    public Doctor getDoctor(UUID doctorId) {
        return userRepository.getDoctorByID(doctorId);
    }

    @Override
    public Medication getMedication(String medId) {
        return medRepository.getMedicationByID(medId).get();
    }
}
