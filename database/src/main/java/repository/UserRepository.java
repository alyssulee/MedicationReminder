package repository;

import database.dataTables.*;
import model.*;
import repository.MedRepository;

import java.lang.reflect.Array;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.UUID;

public class UserRepository
{
    private AppUserTable userDatabase;
    private PatientTables patientDatabase;
    private ClientTable clientDatabase;
    private FamilyMemberTable familyDatabase;
    private DoctorTable doctorDatabase;
    private PharmacistTable pharmacistDatabase;
    private PrescriptionTable prescriptionDatabase;
    private DoseTable doseDatabase;
    private ViewsDataTable viewsDatabase;
    private AppointmentTable appointmentDatabase;
    private FillsTable fillsDatabase;
    private MonitorsTable monitorsDatabase;
    private RefillOrderTable refillOrderDatabase;
    private ReceivesTable receivesDatabase;


    public UserRepository()
    {
        userDatabase = new AppUserTable();
        clientDatabase = new ClientTable();
        patientDatabase = new PatientTables();
        familyDatabase = new FamilyMemberTable();
        doctorDatabase = new DoctorTable();
        pharmacistDatabase = new PharmacistTable();

        prescriptionDatabase = new PrescriptionTable();
        doseDatabase = new DoseTable();
        refillOrderDatabase = new RefillOrderTable();
        monitorsDatabase = new MonitorsTable();
        appointmentDatabase = new AppointmentTable();
        viewsDatabase = new ViewsDataTable();
        fillsDatabase = new FillsTable();
        receivesDatabase = new ReceivesTable();
    }

    public User login(String username, String password)
    {
        return userDatabase.login(username, password);
    }

    public boolean addUser(User user, UserType userType)
    {
        boolean returnVal = userDatabase.addUser(user, userType);

        switch (userType)
        {
            case Patient:
                returnVal = clientDatabase.addClient(user.getId());
                returnVal &= patientDatabase.addPatient(((Patient)user));
                break;
            case FamilyMember:
                returnVal = clientDatabase.addClient(user.getId());
                returnVal &= familyDatabase.addFamily((FamilyMember)user);
                break;
            //TODO finish addUser function
            case Doctor:
                returnVal = doctorDatabase.addDoctor((Doctor)user);
                break;
            case Pharmacist:
                returnVal = pharmacistDatabase.addPharmacist((Pharmacist)user);
                break;
        }

        if(returnVal == false)
        {
            removeUser(user);
        }
        return returnVal;
    }

    public boolean removeUser(User user)
    {
        return userDatabase.removeUser(user);
    }

    public ArrayList<Pharmacist> getAllPharmacists()
    {
        return pharmacistDatabase.getAllPharmacists();
    }

    public ArrayList<Doctor> getAllDoctors()
    {
        return doctorDatabase.getAllDoctors();
    }

    public ArrayList<Patient> getAllPatients()
    {
        return patientDatabase.getAllPatients();
    }

    public boolean addSymptom(UUID idNum, String symptom)
    {
        return patientDatabase.addSymptom(idNum, symptom);
    }

    public boolean addMeasurement(UUID idNum, PatientMeasurement measure)
    {
        return patientDatabase.addMeasurement(idNum, measure);
    }

    public boolean addPatientPrescription(Patient patient, Prescription prescription)
    {
        return prescriptionDatabase.addPatientPrescription(patient, prescription);
    }

    public ArrayList<Prescription> getAllPrescriptionsByPatient(Patient patient)
    {
        return prescriptionDatabase.getAllPrescriptionsByPatient(patient);
    }

    public ArrayList<Prescription> getPatientPrescriptionsByMedication(Patient patient, Medication med)
    {
        return prescriptionDatabase.getPatientPrescriptionsByMedication(patient, med);
    }

    //Note: addDose automatically increases currentStreak, successfulDoses and updates longest streak in db
    public boolean addDose(Patient patient, Prescription prescription, Time time)
    {
        return doseDatabase.addDose(patient, prescription, time);
    }

    public boolean confirmDose(Patient patient, Client confirmer, Time dosageTime, Prescription prescription)
    {
        return doseDatabase.confirmDose(patient, confirmer, dosageTime, prescription);
    }


    public int getCurrentStreak(Patient patient)
    {
        return patientDatabase.getCurrentStreak(patient);
    }

    public int getLongestStreak(Patient patient)
    {
        return patientDatabase.getLongestStreak(patient);
    }

    public int getMissedDoses(Patient patient)
    {
        return patientDatabase.getMissedDoses(patient);
    }

    public int getSuccessfulDoses(Patient patient)
    {
        return patientDatabase.getSuccessfulDoses(patient);
    }

    //Note: Automatically resets currentStreak back to zero
    public void increaseMissedDosesCount(Patient patient)
    {
        patientDatabase.increaseMissedDosesCount(patient);
    }

    public boolean addViewer(Patient patient, FamilyMember familyMember)
    {
        return viewsDatabase.addViewer(patient, familyMember);
    }

    public ArrayList<Patient> getPatientsByFamilyMember(FamilyMember familyMember)
    {
        return viewsDatabase.getPatientsByFamilyMember(familyMember);
    }

    public boolean addAppointment(Doctor doctor, Patient patient, Date date, Time time)
    {
        return appointmentDatabase.addAppointment(doctor, patient, date, time);
    }

    public boolean removeAppointment(Doctor doctor, Patient patient)
    {
        return appointmentDatabase.removeAppointment(doctor, patient);
    }

    public boolean updateAppointment(Doctor doctor, Patient patient, Date newDate, Time newTime)
    {
        return appointmentDatabase.updateAppointment(doctor, patient, newDate, newTime);
    }

    public boolean addRefillOrder(Patient patient, RefillOrder order)
    {
        return refillOrderDatabase.addRefillOrder(patient, order);
    }

    public ArrayList<Prescription> getOrderedPrescriptions(RefillOrder order)
    {
        return refillOrderDatabase.getOrderPrescriptions(order);
    }

    public boolean addPharmacistReceivesOrder(Pharmacist pharmacist, Patient patient, RefillOrder order)
    {
        return receivesDatabase.addPharmacistReceivesOrder(pharmacist, patient, order);
    }

    public ArrayList<RefillOrder> getAllPharmacistOrders(Pharmacist pharmacist)
    {
        return receivesDatabase.getAllPharmacistOrders(pharmacist);
    }

    //automatically increases remaining amount to base amount in prescription
    public boolean addPharmacistFillsPrescription(Pharmacist pharmacist, Prescription prescription)
    {
        prescriptionDatabase.refillPrescription(prescription);
        return fillsDatabase.addPharmacistFillsPrescription(pharmacist, prescription);
    }

    public boolean addDoctorMonitorsPatient(Doctor doctor, Patient patient, Date startDate, Date endDate)
    {
        return monitorsDatabase.addDoctorMonitorsPatient(doctor, patient, startDate, endDate);
    }

    public ArrayList<Patient> getMonitoredPatientsByDoctor(Doctor doctor)
    {
        return monitorsDatabase.getMonitoredPatientsByDoctor(doctor);
    }

    public ArrayList<Appointment> getPatientsAppointments(UUID patientId)
    {
        return appointmentDatabase.getPatientsAppointments(patientId);
    }


        //Tests
    public static void main(String[] args)
    {
        MedRepository medRepo = new MedRepository();
        UserRepository database = new UserRepository();

        ArrayList<String> symptoms = new ArrayList<>();
        symptoms.add("Headache");
        symptoms.add("Itchy Eyes");

        ArrayList<PatientMeasurement> pmeasure = new ArrayList<>();
        pmeasure.add(new PatientMeasurement(new BloodPressure(100, 150), 40, new Date()));
        pmeasure.add(new PatientMeasurement(new BloodPressure(500, 250), 30, new Date()));
        Patient user = new Patient (UUID.fromString("f23c4b5b-d964-475b-9622-c7d497ae7e72"), "a", "b", "c", "d", symptoms, pmeasure);
        database.addUser(user, UserType.Patient);

        FamilyMember family = new FamilyMember(UUID.randomUUID(), "a", "b", "x", "x", "Fam");
        database.addUser(family, UserType.FamilyMember);
        database.addViewer(user, family);

        ArrayList<Pharmacist> pharmacistList = database.getAllPharmacists();
        ArrayList<Doctor> doctorList = database.getAllDoctors();

        System.out.println(database.login(user.getUsername(), user.getPassword()));
        System.out.println(database.login(family.getUsername(), family.getPassword()));
        System.out.println(database.login(pharmacistList.get(0).getUsername(), pharmacistList.get(0).getPassword()));
        System.out.println(database.login(doctorList.get(0).getUsername(), doctorList.get(0).getPassword()));

        Prescription p = new Prescription(UUID.randomUUID(), new Medication("DB00005"), new Date(), 15, 2, 0.9 ,PrescriptionFrequency.BID, 15);
        database.addPatientPrescription((Patient)user, p);
        System.out.println(database.getAllPrescriptionsByPatient((Patient) user));
        System.out.println(database.getPatientPrescriptionsByMedication((Patient) user, new Medication("DB00005")));

        database.addDose(user, database.getAllPrescriptionsByPatient(user).get(0),  new Time(5, 10, 15));
        database.confirmDose(user, user, new Time(5, 10, 15), p);

        database.increaseMissedDosesCount(user);
        System.out.println("MissedDoses: " + database.getMissedDoses(user) );
        System.out.println("SuccessfulDoses: " + database.getSuccessfulDoses(user) );
        System.out.println("Longest Streak: " + database.getLongestStreak(user) );
        System.out.println("Current Streak: " + database.getCurrentStreak(user) );

        RefillOrder order = new RefillOrder(new Date(), UUID.randomUUID(), database.getAllPrescriptionsByPatient(user));
        database.addRefillOrder(user, order);
        System.out.println(database.getOrderedPrescriptions(order));


        database.addPharmacistReceivesOrder(pharmacistList.get(0), user, order);
        database.addPharmacistFillsPrescription(pharmacistList.get(0), database.getAllPrescriptionsByPatient(user).get(0));
        System.out.println(database.getAllPharmacistOrders(pharmacistList.get(0)));

        database.addAppointment(doctorList.get(0), user, new Date(2020, 2, 3), new Time(10, 30, 0));
        database.updateAppointment(doctorList.get(0), user, new Date(2021, 3, 4), new Time(11, 30, 0));
        System.out.println("Doctor Appointments: " + database.getPatientsAppointments(user.getId()));

        //database.removeAppointment(doctorList.get(0), user);
        database.addDoctorMonitorsPatient(doctorList.get(0), user, new Date(2019, 1, 2), new Date(2019, 6, 7));
        System.out.println(database.getMonitoredPatientsByDoctor(doctorList.get(0)));

/*        ArrayList<Patient> patientList = database.getAllPatients();
        for(Patient p : patientList)
            System.out.println(p.toString());*/

//        System.out.println(database.removeUser(user));
//        System.out.println(database.login("c", "d"));


    }
}
