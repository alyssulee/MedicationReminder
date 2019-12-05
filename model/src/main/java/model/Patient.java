package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

public class Patient extends Client {

    private ArrayList<String> symptoms;
    private ArrayList<PatientMeasurement> measurements;

    public Patient(UUID id, String firstName, String lastName, String username, String password) {
        this(id, firstName, lastName, username, password, new ArrayList<>(), new ArrayList<>());
    }

    public Patient(UUID id, String firstName, String lastName, String username, String password, Collection<String> symptoms,
                   Collection<PatientMeasurement> measurements) {

        super(id, firstName, lastName, username, password);
        this.symptoms = new ArrayList<>(symptoms);
        this.measurements = new ArrayList<>(measurements);
    }

    public ArrayList<String> getSymptoms() {
        return symptoms;
    }

    public ArrayList<PatientMeasurement> getMeasurements() {
        return measurements;
    }

    public void setSymptoms(ArrayList<String> symptoms)
    {
        this.symptoms = symptoms;
    }

    public void setMeasurements(ArrayList<PatientMeasurement> measurements)
    {
        this.measurements = measurements;
    }

    public String toString()
    {
        String str =  super.toString() + "\nSymptoms:\t ";
        str += symptoms;
        str += "\nMeasurements:\t";
        for(PatientMeasurement m : measurements)
        {
            str += m.toString() + "\n";
        }
        return str;
    }

    // Todo: Make all of score inferred attributes (successfulDosages, missedDosages, longestStreak, and currentStreak) and remove those attributes from the database
}
