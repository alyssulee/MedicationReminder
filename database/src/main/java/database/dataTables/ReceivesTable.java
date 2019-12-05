package database.dataTables;

import database.SQLDatabase;
import model.FamilyMember;
import model.Medication;
import model.Patient;
import model.Pharmacist;
import model.Prescription;
import model.RefillOrder;

import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class ReceivesTable extends SQLDatabase
{
    public ReceivesTable()
    {
        super();
        createReceivesTable();
    }

    private void createReceivesTable()
    {
        try
        {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet rs = metaData.getTables(null, null, "Receives", null);

            if (rs.next() == false)
            {
                String query = "CREATE TABLE Receives " +
                        "(PharmacistID VARCHAR(255) NOT NULL," +
                        "PatientID VARCHAR(255) NOT NULL,"+
                        "OrderNum VARCHAR(255) NOT NULL,"+
                        "PRIMARY KEY (PharmacistID, PatientID, OrderNum)," +
                        "FOREIGN KEY (PharmacistID) REFERENCES Pharmacist(IDNum)"+
                        "ON DELETE CASCADE ON UPDATE CASCADE,"+
                        "FOREIGN KEY (PatientID, OrderNum) REFERENCES RefillOrder(PatientID, OrderNum)"+
                        "ON DELETE CASCADE ON UPDATE CASCADE);";
                statement.executeUpdate(query);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //TODO: Add, remove, getAll, getReceivesBy...
    public boolean addPharmacistReceivesOrder(Pharmacist pharmacist, Patient patient, RefillOrder order)
    {
        try
        {
            String query = "INSERT INTO Receives (PharmacistID, PatientID, OrderNum)" +
                    "VALUES ((SELECT IDNum FROM Pharmacist WHERE IDNUm = ?), (SELECT PatientID FROM RefillOrder WHERE PatientID = ? AND OrderNum = ?), (SELECT OrderNum FROM RefillOrder WHERE PatientID = ? AND OrderNum = ?));";
            PreparedStatement pState = connection.prepareStatement(query);
            pState.setString(1, pharmacist.getId().toString());
            pState.setString(2, patient.getId().toString());
            pState.setString(3, order.getOrderID().toString());
            pState.setString(4, patient.getId().toString());
            pState.setString(5, order.getOrderID().toString());
            pState.execute();
            return true;
        } catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }
    }

    public ArrayList<RefillOrder> getAllPharmacistOrders(Pharmacist pharmacist)
    {
            ArrayList<RefillOrder> orderList = new ArrayList<>();
            try
            {
                String query = "SELECT RefillOrder.* FROM RefillOrder, Receives WHERE RefillOrder.OrderNum = Receives.OrderNum AND Receives.PharmacistID = ?";
                PreparedStatement pState = connection.prepareStatement(query);
                pState.setString(1, pharmacist.getId().toString());
                resultSet = pState.executeQuery();
                while (resultSet.next())
                {
                    UUID id = UUID.fromString(resultSet.getString("OrderNum"));
                    UUID prescID =  UUID.fromString(resultSet.getString("PrescriptionID"));
                    Date date = resultSet.getDate("Date");
                    int index = 0;
                    boolean newOrder = true;
                    for(RefillOrder order : orderList)
                    {
                        if(order.getOrderID().compareTo(id) == 0)
                        {
                            orderList.get(0).getPrescriptionsToFill().add(new Prescription(prescID));
                            newOrder = false;
                            break;
                        }
                        index++;
                    }
                    if(newOrder == true)
                    {
                        ArrayList<Prescription> prescriptions = new ArrayList<>();
                        prescriptions.add(new Prescription(prescID));
                        orderList.add(new RefillOrder(date, id, prescriptions));
                    }
                }
            } catch (SQLException e)
            {
                e.printStackTrace();
            }

            PrescriptionTable pTable = new PrescriptionTable();
            for(RefillOrder order : orderList)
            {
                int i = 0;
                for(Prescription p : order.getPrescriptionsToFill())
                {
                    order.getPrescriptionsToFill().set(i, pTable.getPrescriptionByID(p.getPrescriptionID()));
                    i++;
                }
            }
            return orderList;
    }

}
