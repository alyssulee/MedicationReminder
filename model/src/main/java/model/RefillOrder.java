package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class RefillOrder
{
    Date date;
    UUID OrderID;
    ArrayList<Prescription> prescriptionsToFill;

    public RefillOrder(Date date, UUID orderID, ArrayList<Prescription> prescriptionsToFill)
    {
        this.date = date;
        OrderID = orderID;
        this.prescriptionsToFill = prescriptionsToFill;
    }

    public Date getDate()
    {
        return date;
    }

    public UUID getOrderID()
    {
        return OrderID;
    }

    public ArrayList<Prescription> getPrescriptionsToFill()
    {
        return prescriptionsToFill;
    }
}
