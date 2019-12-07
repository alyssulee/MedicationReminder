package app;

import com.example.apiabstractions.FamilyMemberApi;
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
import model.FamilyMember;
import model.LoginCredentials;
import model.Medication;
import model.Patient;
import model.PatientMeasurement;
import repository.UserRepository;

public class FamilyMemberApiImpl implements FamilyMemberApi {

    private UserRepository userRepository = new UserRepository();
    private FamilyMember familyMember;

    private FamilyMemberApiImpl() {
    }

    public static FamilyMemberApiImpl createFor(FamilyMember familyMember) {
        FamilyMemberApiImpl api = new FamilyMemberApiImpl();
        api.familyMember = familyMember;
        return api;
    }

    @Override
    public List<Patient> getDependantPatients() {
        return userRepository.getPatientsDependantOn(familyMember.getId());
    }

    @Override
    public LoginCredentials getPatientCredentials(UUID patientId) {
        Patient patient = userRepository.getPatientByID(patientId);
        return new LoginCredentials(patient.getUsername(), patient.getPassword());
    }
}
