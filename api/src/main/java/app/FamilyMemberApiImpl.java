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

public class FamilyMemberApiImpl implements FamilyMemberApi {

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
        // Todo: Give real implementation
        ArrayList<Patient> patients = new ArrayList<>();
        patients.add(new Patient(UUID.randomUUID(), "lefirst", "lelast", "leuser", "lepass"));
        patients.add(new Patient(UUID.randomUUID(), "lafirst", "lalast", "lauser", "lapass"));
        return patients;
    }

    @Override
    public LoginCredentials getPatientCredentias(UUID patientId) {
        // Todo: Give real implementation
        return new LoginCredentials("myuser", "mypass");
    }
}
