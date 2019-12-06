package app;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import app.controllers.Controller;
import app.controllers.FamilyMemberApiController;
import app.controllers.PatientApiController;
import model.BloodPressure;
import model.Patient;
import model.PatientMeasurement;
import spark.Spark;

import static spark.Spark.before;
import static spark.Spark.get;
import static spark.Spark.port;

public class Application {
    public static void main(String[] args) {

        port(4567);

        Spark.exception(Exception.class, (exception, request, response) -> {
            exception.printStackTrace();
        });

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

        ArrayList<Controller> controllers = new ArrayList<>();
        controllers.add(new PatientApiController());
        controllers.add(new FamilyMemberApiController());

        for (Controller controller : controllers) controller.registerRoutes();
    }
}