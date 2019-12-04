package app;

import model.BloodPressure;
import model.Patient;
import model.PatientMeasurement;

import java.util.Date;
import java.util.UUID;

import static spark.Spark.*;

public class Application {
    public static void main(String[] args) {

        port(4567);

        // Make response Content-Type "application/json" by default
        before((req, res) -> res.type("application/json"));

        get("/", (req, res) -> "Hello World");

        get("/jimmy", (req, res) -> {
            Patient patient = new Patient(UUID.randomUUID(), "Jimmy", "J.", "username", "password");
            patient.getSymptoms().add("Stubbed toe");
            patient.getSymptoms().add("Upset belly");
            patient.getMeasurements().add(new PatientMeasurement(new BloodPressure(100, 200), 3.0, new Date()));
            patient.getMeasurements().add(new PatientMeasurement(new BloodPressure(150, 250), 2.0, new Date()));

            return patient;
        }, new JsonTransformer());
    }
}