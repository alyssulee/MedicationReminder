package app;

import com.example.apiabstractions.GeneralApi;
import com.example.apiabstractions.PatientApi;

import java.sql.Time;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.print.Doc;

import model.Appointment;
import model.Doctor;
import model.Dose;
import model.FamilyMember;
import model.LoginCredentials;
import model.Medication;
import model.Patient;
import model.PatientMeasurement;
import model.Pharmacist;
import model.User;
import model.UserType;
import repository.MedRepository;
import repository.UserRepository;

public class GeneralApiImpl implements GeneralApi {

    private UserRepository userRepository = new UserRepository();

    @Override
    public UserType getUserTypeOrNull(LoginCredentials credentials) {
        User user = userRepository.login(credentials.getUsername(), credentials.getPassword());
        if (user instanceof Patient) return UserType.Patient;
        if (user instanceof FamilyMember) return UserType.FamilyMember;
        if (user instanceof Doctor) return UserType.Doctor;
        if (user instanceof Pharmacist) return UserType.Pharmacist;
        return null;
    }
}
