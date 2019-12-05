package app.controllers;

import com.google.gson.Gson;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import app.JsonTransformer;
import model.Appointment;
import model.BloodPressure;
import model.Doctor;
import model.Dose;
import model.Medication;
import model.Patient;
import model.PatientMeasurement;
import spark.Request;

import static spark.Spark.get;
import static spark.Spark.post;

public class PatientApiController implements Controller {

    private static final String basePath = "/api/patient/";
    private Gson gson = new Gson();

    @Override
    public void registerRoutes() {

        get(basePath + "/verifyLoginAuthenticity", (req, res) -> {
            Patient patient = getPatientOrThrow(req);

            patient.setUsername(null);
            patient.setPassword(null);
            return patient;
        }, new JsonTransformer());

        get(basePath + "/getUpcomingAppointments", (req, res) -> {
            getPatientOrThrow(req);

            ArrayList<Appointment> appointments = new ArrayList<>();
            appointments.add(new Appointment(UUID.randomUUID(), UUID.randomUUID(), new Date(), new Time(0)));
            appointments.add(new Appointment(UUID.randomUUID(), UUID.randomUUID(), new Date(), new Time(0)));
            return appointments;
        }, new JsonTransformer());

        get(basePath + "/getCurrentStreak", (req, res) -> {
            getPatientOrThrow(req);

            return 5;
        }, new JsonTransformer());

        get(basePath + "/getLongestStreak", (req, res) -> {
            getPatientOrThrow(req);

            return 30;
        }, new JsonTransformer());

        get(basePath + "/getTodaysDoses", (req, res) -> {
            getPatientOrThrow(req);

            ArrayList<Dose> doses = new ArrayList<>();
            doses.add(new Dose(UUID.randomUUID(), new Timestamp(0), 1, "medIdHere1", UUID.randomUUID(), UUID.randomUUID()));
            doses.add(new Dose(UUID.randomUUID(), new Timestamp(0), 2, "medIdHere2", UUID.randomUUID(), UUID.randomUUID()));
            return doses;
        }, new JsonTransformer());

        post(basePath + "/confirmDoseTaken", "application/json", (req, res) -> {
            getPatientOrThrow(req);

            String json = req.body();
            Dose dose = gson.fromJson(json, Dose.class);
            dose.setConfirmerId(UUID.randomUUID());
            return dose;
        }, new JsonTransformer());

        post(basePath + "/markDoseUntaken", "application/json", (req, res) -> {
            getPatientOrThrow(req);

            String json = req.body();
            Dose dose = gson.fromJson(json, Dose.class);
            dose.setConfirmerId(null);
            return dose;
        }, new JsonTransformer());

        get(basePath + "/getAllMeasurements", (req, res) -> {
            getPatientOrThrow(req);

            ArrayList<PatientMeasurement> measurements = new ArrayList<>();
            measurements.add(new PatientMeasurement(new BloodPressure(5, 6), 54, new Timestamp(0)));
            measurements.add(new PatientMeasurement(new BloodPressure(3, 1), 44, new Timestamp(0)));
            return measurements;
        }, new JsonTransformer());


        post(basePath + "/addMeasurement", "application/json", (req, res) -> {
            getPatientOrThrow(req);

            String json = req.body();
            PatientMeasurement measurement = gson.fromJson(json, PatientMeasurement.class);

            return measurement;
        }, new JsonTransformer());

        get(basePath + "/getDoctor/:doctorId", (req, res) -> {
            getPatientOrThrow(req);

            UUID doctorId = UUID.fromString(req.params("doctorId"));
            return new Doctor(doctorId, "Doc", "Franklin", null, null);
            // Todo: Remember to clear username and password
        }, new JsonTransformer());

        get(basePath + "/getMedication/:medId", (req, res) -> {
            getPatientOrThrow(req);

            String medId = req.params("medId");

            ArrayList<String> names = new ArrayList<>();
            names.add("Big Med");
            names.add("Patient is sad \uD83D\uDE0A");

            return new Medication(medId, names);
        }, new JsonTransformer());

    }

    private Patient getPatientOrThrow(Request request){
        String username = request.headers("username");
        String password = request.headers("Password");

        if (username.isEmpty() || password.isEmpty())
            throw new Error("Username or password was missing.");

        return new Patient(UUID.randomUUID(), "first", "last", username, password);
    }
}
