package database.dataTables;

import database.SQLDatabase;
import model.FamilyMember;
import model.Patient;
import model.ViewsData;

import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.UUID;

public class ViewsDataTable extends SQLDatabase
{
    public ViewsDataTable()
    {
        super();
        createViewsDatatable();
    }

    public void createViewsDatatable()
    {
        try
        {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet rs = metaData.getTables(null, null, "ViewsData", null);

            if (rs.next() == false)
            {
                String query = "CREATE TABLE ViewsData " +
                        "(FamilyID VARCHAR(255) NOT NULL," +
                        "PatientID VARCHAR(255) NOT NULL,"+
                        "PRIMARY KEY (FamilyID, PatientID)," +
                        "FOREIGN KEY (FamilyID) REFERENCES FamilyMember(IDNum)"+
                        "ON DELETE CASCADE ON UPDATE CASCADE,"+
                        "FOREIGN KEY (PatientID) REFERENCES Patient(IDNum)"+
                        "ON DELETE CASCADE ON UPDATE CASCADE);";
                statement.executeUpdate(query);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //TODO: Add, remove, getAll, getPatientByFamilyMember...
    public boolean addViewer(Patient patient, FamilyMember familyMember)
    {
        try
        {
            String query = "INSERT INTO ViewsData (FamilyID, PatientID)"+
                            "VALUES ((SELECT IDNum FROM FamilyMember WHERE IDNum = ?), (SELECT IDNum FROM Patient WHERE IDNum = ?))";
            PreparedStatement pState = connection.prepareStatement(query);
            pState.setString(1, familyMember.getId().toString());
            pState.setString(2, patient.getId().toString());
            pState.execute();
            return true;
        } catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }
    }

    public ArrayList<Patient> getPatientsByFamilyMember(UUID familymemberID)
    {
        ArrayList<Patient> patientList = new ArrayList<>();
        try
        {
            String query = "SELECT AppUser.* FROM ViewsData, Patient, AppUser WHERE ViewsData.PatientID = Patient.IDNum AND AppUser.IDNum = Patient.IDNum AND ViewsData.FamilyID = ?;";
            PreparedStatement pState = connection.prepareStatement(query);
            pState.setString(1, familymemberID.toString());
            pState.executeQuery();
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

        PatientTables pTable = new PatientTables();
        pTable.getAllPatientSymptoms(patientList);
        pTable.getAllPatientMeasurement(patientList);
        return patientList;
    }

}
