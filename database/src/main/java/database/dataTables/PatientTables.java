package database.dataTables;

import database.SQLDatabase;
import model.BloodPressure;
import model.Client;
import model.Patient;
import model.PatientMeasurement;

import java.sql.*;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

public class PatientTables extends SQLDatabase
{
    public PatientTables()
    {
        super();
        createPatientTable();
        createPatientSymptomTable();
        ;
        createPatientMeasurementTable();
    }

    private void createPatientTable()
    {
        try
        {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet rs = metaData.getTables(null, null, "Patient", null);

            if (rs.next() == false)
            {
                String query = "CREATE TABLE Patient " +
                        "(IDNum VARCHAR(255) NOT NULL," +
                        "SuccessfulDoses INT DEFAULT 0," +
                        "MissedDoses INT DEFAULT 0," +
                        "CurrentStreak INT DEFAULT 0," +
                        "LongestStreak INT DEFAULT 0," +
                        "PRIMARY KEY (IDNum)," +
                        "FOREIGN KEY (IDNum) REFERENCES CLIENT(IDNum)" +
                        "ON DELETE CASCADE ON UPDATE CASCADE);";
                statement.executeUpdate(query);
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    private void createPatientSymptomTable()
    {
        try
        {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet rs = metaData.getTables(null, null, "PatientSymptom", null);

            if (rs.next() == false)
            {
                String query = "CREATE TABLE PatientSymptom " +
                        "(IDNum VARCHAR(255) NOT NULL," +
                        "Symptom VARCHAR(255)," +
                        "PRIMARY KEY (IDNum, Symptom)," +
                        "FOREIGN KEY (IDNum) REFERENCES PATIENT(IDNum)" +
                        "ON DELETE CASCADE ON UPDATE CASCADE);";
                statement.executeUpdate(query);
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    private void createPatientMeasurementTable()
    {
        try
        {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet rs = metaData.getTables(null, null, "PatientMeasurement", null);

            if (rs.next() == false)
            {
                String query = "CREATE TABLE PatientMeasurement " +
                        "(MeasurementID VARCHAR(255) NOT NULL," +
                        "IDNum VARCHAR(255) NOT NULL," +
                        "MeasurementTime TIMESTAMP NOT NULL," +
                        "BloodPressure VARCHAR(255), BloodSugar DOUBLE," +
                        "PRIMARY KEY (MeasurementID, IDNum, MeasurementTime)," +
                        "FOREIGN KEY (IDNum) REFERENCES PATIENT(IDNum)" +
                        "ON DELETE CASCADE ON UPDATE CASCADE);";
                statement.executeUpdate(query);
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public boolean addPatient(Patient patient)
    {
        try
        {
            String query = "INSERT INTO patient (IDNum)" +
                    "VALUES ((SELECT IDNum FROM client WHERE IDNum = ?));";
            PreparedStatement pState = connection.prepareStatement(query);
            pState.setString(1, patient.getId().toString());
            pState.execute();

            for (String s : patient.getSymptoms())
            {
                addSymptom(patient.getId(), s);
            }

            for (PatientMeasurement measurement : patient.getMeasurements())
            {
                addMeasurement(patient.getId(), measurement);
            }

            return true;
        } catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }
    }

    public boolean addSymptom(UUID idNum, String symptom)
    {
        try
        {
            String query = "INSERT INTO patientSymptom (IDNum, Symptom)" +
                    "VALUES ((SELECT IDNum FROM patient WHERE IDNum = ?), ?);";
            PreparedStatement pState = connection.prepareStatement(query);
            pState.setString(1, idNum.toString());
            pState.setString(2, symptom);
            pState.execute();
            return true;
        } catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }
    }

    public boolean addMeasurement(UUID idNum, PatientMeasurement measure)
    {
        try
        {
            String query = "INSERT INTO patientmeasurement (MeasurementID, IDNum, MeasurementTime, BloodPressure, BloodSugar)" +
                    "VALUES (?, (SELECT IDNum FROM patient WHERE IDNum = ?), ?, ?, ?);";
            PreparedStatement pState = connection.prepareStatement(query);
            pState.setString(1, UUID.randomUUID().toString());
            pState.setString(2, idNum.toString());
            pState.setDate(3, new Date(measure.getTimestamp().getTime()));
            pState.setString(4, measure.getBloodPressure().toString());
            pState.setDouble(5, measure.getBloodSugar());

            pState.execute();
            return true;
        } catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }
    }

    public ArrayList<Patient> getAllPatients()
    {
        ArrayList<Patient> patientList = new ArrayList<>();
        try
        {
            String query = "SELECT * FROM AppUser, Patient WHERE AppUser.IDNum = Patient.IDNum";
            resultSet = statement.executeQuery(query);
            while (resultSet.next())
            {
                UUID id = UUID.fromString(resultSet.getString("IDNum"));
                String firstname = resultSet.getString("Fname");
                String lastname = resultSet.getString("Lname");
                String username = resultSet.getString("Username");
                String password = resultSet.getString("Password");
                patientList.add(new Patient(id, firstname, lastname, username, password));
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        getAllPatientSymptoms(patientList);
        getAllPatientMeasurement(patientList);
        return patientList;
    }

    public void getAllPatientSymptoms(ArrayList<Patient> patientList)
    {
        for (Patient p : patientList)
        {
            getPatientSymptoms(p);
        }
    }

    public void getPatientSymptoms(Patient p)
    {
        try
        {
            String query = "SELECT PatientSymptom.* FROM Patient, PatientSymptom WHERE Patient.IDNum = PatientSymptom.IDNum AND Patient.IDNum = ?";
            PreparedStatement pState = connection.prepareStatement(query);
            pState.setString(1, p.getId().toString());
            resultSet = pState.executeQuery();
            ArrayList<String> symptom = new ArrayList<>();
            while (resultSet.next())
            {
                String s = resultSet.getString("Symptom");
                symptom.add(s);
            }
            p.setSymptoms(symptom);
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }


    public void getAllPatientMeasurement(ArrayList<Patient> patientList)
    {
        for (Patient p : patientList)
        {
            getPatientMeasurement(p);
        }
    }

    public void getPatientMeasurement(Patient p)
    {

        try
        {
            String query = "SELECT PatientMeasurement.* FROM Patient, PatientMeasurement WHERE Patient.IDNum = PatientMeasurement.IDNum AND Patient.IDNum = ?";
            PreparedStatement pState = connection.prepareStatement(query);
            pState.setString(1, p.getId().toString());
            resultSet = pState.executeQuery();
            ArrayList<PatientMeasurement> pMeasure = new ArrayList<>();
            while (resultSet.next())
            {
                java.util.Date date = resultSet.getDate("MeasurementTime");
                BloodPressure bp = new BloodPressure(resultSet.getString("BloodPressure"));
                Double bloodSugar = resultSet.getDouble("BloodSugar");
                pMeasure.add(new PatientMeasurement(bp, bloodSugar, date));
            }
            p.setMeasurements(pMeasure);
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public void increasePatientStreak(UUID patientId)
    {
        try
        {
            String query = "UPDATE PATIENT SET CurrentStreak = CurrentStreak + 1 WHERE IDNum = ?";
            PreparedStatement pState = connection.prepareStatement(query);
            pState.setString(1, patientId.toString());
            pState.executeUpdate();
            updateLongestStreak(patientId);
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public void decreasePatientStreak(UUID patientId)
    {
        try
        {
            String query = "UPDATE PATIENT SET CurrentStreak = CurrentStreak - 1 WHERE IDNum = ?";
            PreparedStatement pState = connection.prepareStatement(query);
            pState.setString(1, patientId.toString());
            pState.execute();
            updateLongestStreak(patientId);
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public void resetPatientStreak(UUID patientId)
    {
        try
        {
            String query = "UPDATE PATIENT SET CurrentStreak = 0 WHERE IDNum = ?";
            PreparedStatement pState = connection.prepareStatement(query);
            pState.setString(1, patientId.toString());
            pState.execute();
            updateLongestStreak(patientId);
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public void increaseMissedDosesCount(UUID patientId)
    {
        try
        {
            String query = "UPDATE PATIENT SET MissedDoses = MissedDoses + 1 WHERE IDNum = ?";
            PreparedStatement pState = connection.prepareStatement(query);
            pState.setString(1, patientId.toString());
            pState.execute();
            resetPatientStreak(patientId);
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public void decreasedMissedDosesCount(UUID patientId)
    {
        try
        {
            String query = "UPDATE PATIENT SET MissedDoses = MissedDoses - 1 WHERE IDNum = ?";
            PreparedStatement pState = connection.prepareStatement(query);
            pState.setString(1, patientId.toString());
            pState.execute();
            resetPatientStreak(patientId);
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public void increaseSuccessfulDoses(UUID patientId)
    {
        try
        {
            String query = "UPDATE PATIENT SET SuccessfulDoses = SuccessfulDoses + 1 WHERE IDNum = ?";
            PreparedStatement pState = connection.prepareStatement(query);
            pState.setString(1, patientId.toString());
            pState.executeUpdate();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public void decreaseSuccessfulDoses(UUID patientId)
    {
        try
        {
            String query = "UPDATE PATIENT SET SuccessfulDoses = SuccessfulDoses - 1 WHERE IDNum = ?";
            PreparedStatement pState = connection.prepareStatement(query);
            pState.setString(1, patientId.toString());
            pState.execute();
            resetPatientStreak(patientId);
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    private void updateLongestStreak(UUID patientId)
    {
        try
        {
            String query = "SELECT CurrentStreak, LongestStreak FROM Patient WHERE IDNum = ?";
            PreparedStatement pState = connection.prepareStatement(query);
            pState.setString(1, patientId.toString());
            resultSet = pState.executeQuery();
            while (resultSet.next())
            {
                int currStreak = resultSet.getInt("CurrentStreak");
                int longestStreak = resultSet.getInt("LongestStreak");
                if (currStreak > longestStreak)
                {
                    query = "UPDATE PATIENT SET LongestStreak = CurrentStreak WHERE IDNum = ?";
                    pState = connection.prepareStatement(query);
                    pState.setString(1, patientId.toString());
                    pState.executeUpdate();
                }
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public int getCurrentStreak(Patient patient)
    {
        try
        {
            String query = "SELECT CurrentStreak FROM Patient WHERE IDNum = ?";
            PreparedStatement pState = connection.prepareStatement(query);
            pState.setString(1, patient.getId().toString());
            resultSet = pState.executeQuery();
            while (resultSet.next())
            {
                return resultSet.getInt("CurrentStreak");
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return -1;
    }

    public int getLongestStreak(Patient patient)
    {
        try
        {
            String query = "SELECT LongestStreak FROM Patient WHERE IDNum = ?";
            PreparedStatement pState = connection.prepareStatement(query);
            pState.setString(1, patient.getId().toString());
            resultSet = pState.executeQuery();
            while (resultSet.next())
            {
                return resultSet.getInt("LongestStreak");
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return -1;
    }

    public int getMissedDoses(Patient patient)
    {
        try
        {
            String query = "SELECT MissedDoses FROM Patient WHERE IDNum = ?";
            PreparedStatement pState = connection.prepareStatement(query);
            pState.setString(1, patient.getId().toString());
            resultSet = pState.executeQuery();
            while (resultSet.next())
            {
                return resultSet.getInt("MissedDoses");
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return -1;
    }

    public int getSuccessfulDoses(Patient patient)
    {
        try
        {
            String query = "SELECT SuccessfulDoses FROM Patient WHERE IDNum = ?";
            PreparedStatement pState = connection.prepareStatement(query);
            pState.setString(1, patient.getId().toString());
            resultSet = pState.executeQuery();
            while (resultSet.next())
            {
                return resultSet.getInt("SuccessfulDoses");
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return -1;
    }

    public Optional<Patient> getPatientByID(UUID idNum)
    {
        Patient patient = null;
        try
        {
            String query = "SELECT * FROM Patient, AppUser WHERE Patient.IDNum = ? AND AppUser.IDNum = Patient.IDNum";
            PreparedStatement pState = connection.prepareStatement(query);
            pState.setString(1, idNum.toString());
            resultSet = pState.executeQuery();
            while (resultSet.next())
            {
                UUID id = UUID.fromString(resultSet.getString("IDNum"));
                String firstname = resultSet.getString("Fname");
                String lastname = resultSet.getString("Lname");
                String username = resultSet.getString("Username");
                String password = resultSet.getString("Password");
                patient = new Patient(id, firstname, lastname, username, password);
                getPatientMeasurement(patient);
                getPatientSymptoms(patient);
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return Optional.ofNullable(patient);
    }


    public ArrayList<Patient> getPatientsDependantOn(UUID familyMemberId) {
        ArrayList<Patient> patientList = new ArrayList<>();
        try
        {
            String query = "SELECT * FROM AppUser, Patient AS p WHERE AppUser.IDNum = Patient.IDNum AND EXISTS(SELECT * FROM ViewsData WHERE PatientID = p.IDNum AND FamilyID = ?)";
            PreparedStatement pState = connection.prepareStatement(query);
            pState.setString(1, familyMemberId.toString());
            resultSet = pState.executeQuery(query);
            while (resultSet.next())
            {
                UUID id = UUID.fromString(resultSet.getString("IDNum"));
                String firstname = resultSet.getString("Fname");
                String lastname = resultSet.getString("Lname");
                String username = resultSet.getString("Username");
                String password = resultSet.getString("Password");
                patientList.add(new Patient(id, firstname, lastname, username, password));
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        getAllPatientSymptoms(patientList);
        getAllPatientMeasurement(patientList);
        return patientList;
    }
}
