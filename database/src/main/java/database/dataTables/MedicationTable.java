package database.dataTables;

import database.SQLDatabase;
import model.Medication;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

public class MedicationTable extends SQLDatabase
{
    public MedicationTable()
    {
        super();
        createMedicationTable();
        createMedNameTable();
        readTextFile();
    }

    private void createMedicationTable()
    {
        try
        {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet rs = metaData.getTables(null, null, "Medication", null);

            if (rs.next() == false)
            {
                String query = "CREATE TABLE Medication " +
                        "(MedID VARCHAR(255) NOT NULL," +
                        "PRIMARY KEY (MedID));";
                statement.executeUpdate(query);
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    private void createMedNameTable()
    {
        try
        {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet rs = metaData.getTables(null, null, "MedName", null);

            if (rs.next() == false)
            {
                String query = "CREATE TABLE MedName " +
                        "(MedID VARCHAR(255) NOT NULL," +
                        "MedName VARCHAR(255) NOT NULL," +
                        "PRIMARY KEY (MedID, MedName)," +
                        "FOREIGN KEY (MedID) REFERENCES Medication(MedID)" +
                        "ON DELETE CASCADE ON UPDATE CASCADE);";
                statement.executeUpdate(query);
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public boolean addMedication(String id, ArrayList<String> names)
    {
        try
        {
            String query = "INSERT INTO Medication (MedID)" +
                    "VALUES (?);";
            PreparedStatement pState = connection.prepareStatement(query);
            pState.setString(1, id.toString());
            pState.execute();

            for (String n : names)
            {
                addMedName(id, n);
            }

            return true;
        } catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }
    }

    private boolean addMedName(String id, String name)
    {
        try
        {
            String query = "INSERT INTO MedName (MedID, MedName)" +
                    "VALUES ((SELECT MedID FROM Medication WHERE MedID = ?), ?);";
            PreparedStatement pState = connection.prepareStatement(query);
            pState.setString(1, id.toString());
            pState.setString(2, name);
            pState.execute();
            return true;
        } catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }
    }

    public ArrayList<Medication> getAllMedications()
    {
        ArrayList<Medication> medications = new ArrayList<>();
        try
        {
            String query = "SELECT * FROM Medication";
            resultSet = statement.executeQuery(query);
            while (resultSet.next())
            {
                String id = resultSet.getString("medID");
                medications.add(new Medication(id));
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        getAllMedicationNames(medications);
        return medications;
    }

    public Optional<Medication> getMedicationByName(String name)
    {
        Medication medication = null;
        try
        {
            String query = "SELECT MedID FROM MedName WHERE MedName = ?";
            PreparedStatement pState = connection.prepareStatement(query);
            pState.setString(1, name);
            resultSet = pState.executeQuery();
            while (resultSet.next())
            {
                String id = resultSet.getString("medID");
                System.out.println(id);
                medication = new Medication(id);
                getAllMedicationNames(medication);
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return Optional.ofNullable(medication);
    }

    public Optional<Medication> getMedicationByID(String mId)
    {
        Medication medication = null;
        try
        {
            String query = "SELECT MedID FROM MedName WHERE MedID = ?";
            PreparedStatement pState = connection.prepareStatement(query);
            pState.setString(1, mId);
            resultSet = pState.executeQuery();
            while (resultSet.next())
            {
                String id = resultSet.getString("medID");
                System.out.println(id);
                medication = new Medication(id);
                getAllMedicationNames(medication);
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return Optional.ofNullable(medication);
    }

    private void getAllMedicationNames(ArrayList<Medication> medications)
    {
        for (Medication m : medications)
        {
            try
            {
                String query = "SELECT MedName.MedName FROM Medication, MedName WHERE MedName.MedID = Medication.MedID AND Medication.MedID = ?";
                PreparedStatement pState = connection.prepareStatement(query);
                pState.setString(1, m.getMedID());
                resultSet = pState.executeQuery();
                ArrayList<String> names = new ArrayList<>();
                while (resultSet.next())
                {
                    String name = resultSet.getString("MedName");
                    names.add(name);
                }
                m.setMedNames(names);
            } catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
    }

    public void getAllMedicationNames(Medication medication)
    {
        try
        {
            String query = "SELECT MedName.MedName FROM Medication, MedName WHERE MedName.MedID = Medication.MedID " +
                    "AND Medication.MedID = ?";
            PreparedStatement pState = connection.prepareStatement(query);
            pState.setString(1, medication.getMedID());
            resultSet = pState.executeQuery();
            ArrayList<String> names = new ArrayList<>();
            while (resultSet.next())
            {
                String name = resultSet.getString("MedName");
                names.add(name);
            }
            medication.setMedNames(names);
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    private void readTextFile()
    {
        boolean bool = false;
        try
        {
            String query = "SELECT * FROM Medication";
            resultSet = statement.executeQuery(query);
            bool = resultSet.next();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        //If no prior data exists, read from file
        if (bool == false)
        {
            try
            {
                FileReader fileReader = new FileReader("Medications.txt");
                BufferedReader bufferedReader = new BufferedReader(fileReader);

                String line;
                while ((line = bufferedReader.readLine()) != null)
                {
                    String[] temp = line.split(",");
                    String id = temp[0];
                    ArrayList<String> names = new ArrayList<>();
                    for (int i = 1; i < temp.length; i++)
                    {
                        System.out.println(temp[i]);
                        names.add(temp[i]);
                    }
                    addMedication(id, names);
                }
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }

    }
}
