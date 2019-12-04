package model;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Medication
{
    String medID;
    ArrayList<String> medNames;

    public Medication(String id, ArrayList<String> names)
    {
        medID = id;
        medNames = names;
    }

    public Medication(String id)
    {
        medID = id;
        medNames = new ArrayList<>();
    }


    public String toString()
    {
        return medID + ":\t" + medNames;
    }

    public String getMedID()
    {
        return medID;
    }

    public void setMedID(String medID)
    {
        this.medID = medID;
    }

    public ArrayList<String> getMedNames()
    {
        return medNames;
    }

    public void setMedNames(ArrayList<String> medNames)
    {
        this.medNames = medNames;
    }
}
