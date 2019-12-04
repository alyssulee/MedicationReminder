package repository;

import database.dataTables.MedicationTable;
import model.Medication;

import java.util.ArrayList;
import java.util.Optional;

public class MedRepository
{
    private MedicationTable medDatabase;

    public MedRepository()
    {
        medDatabase = new MedicationTable();
    }

    public ArrayList<Medication> getAllMedications()
    {
        return medDatabase.getAllMedications();
    }

    public boolean addMedication(Medication med)
    {
        return medDatabase.addMedication(med.getMedID(), med.getMedNames());
    }

    public Optional<Medication> getMedicationByName(String name)
    {
        return medDatabase.getMedicationByName(name);
    }

    public Optional<Medication> getMedicationByID(String id)
    {
        return medDatabase.getMedicationByID(id);
    }


}
