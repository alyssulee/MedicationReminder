package app.controllers;

import com.example.apiabstractions.PatientApi;
import com.google.gson.Gson;

import java.util.UUID;

import app.JsonTransformer;
import app.PatientApiImpl;
import model.Doctor;
import model.Dose;
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

        get(basePath + "/verifyCredentials", (req, res) -> {
            createApiOrThrow(req);
            return true;
        }, new JsonTransformer());

        get(basePath + "/getUpcomingAppointments", (req, res) -> {
            PatientApi api = createApiOrThrow(req);
            return api.getUpcomingAppointments();
        }, new JsonTransformer());

        get(basePath + "/getCurrentStreak", (req, res) -> {
            PatientApi api = createApiOrThrow(req);
            return api.getCurrentStreak();
        }, new JsonTransformer());

        get(basePath + "/getLongestStreak", (req, res) -> {
            PatientApi api = createApiOrThrow(req);
            return api.getLongestStreak();
        }, new JsonTransformer());

        get(basePath + "/getTodaysDoses", (req, res) -> {
            PatientApi api = createApiOrThrow(req);
            return api.getTodaysDoses();
        }, new JsonTransformer());

        post(basePath + "/confirmDoseTaken", "application/json", (req, res) -> {
            PatientApi api = createApiOrThrow(req);
            String json = req.body();
            Dose dose = gson.fromJson(json, Dose.class);
            api.confirmDoseTaken(dose);
            return true;
        }, new JsonTransformer());

        post(basePath + "/markDoseUntaken", "application/json", (req, res) -> {
            PatientApi api = createApiOrThrow(req);
            String json = req.body();
            Dose dose = gson.fromJson(json, Dose.class);
            api.markDoseUntaken(dose);
            return true;
        }, new JsonTransformer());

        get(basePath + "/getAllMeasurements", (req, res) -> {
            PatientApi api = createApiOrThrow(req);
            return api.getAllMeasurements();
        }, new JsonTransformer());


        post(basePath + "/addMeasurement", "application/json", (req, res) -> {
            PatientApi api = createApiOrThrow(req);
            String json = req.body();
            PatientMeasurement measurement = gson.fromJson(json, PatientMeasurement.class);
            api.addMeasurement(measurement);
            return true;
        }, new JsonTransformer());

        get(basePath + "/getDoctor/:doctorId", (req, res) -> {
            PatientApi api = createApiOrThrow(req);
            UUID doctorId = UUID.fromString(req.params("doctorId"));
            Doctor doctor = api.getDoctor(doctorId);
            doctor.setUsername(null);
            doctor.setPassword(null);
            return doctor;
        }, new JsonTransformer());

        get(basePath + "/getMedication/:medId", (req, res) -> {
            PatientApi api = createApiOrThrow(req);
            String medId = req.params("medId");
            return api.getMedication(medId);
        }, new JsonTransformer());
    }

    private PatientApi createApiOrThrow(Request request) {
        Patient patient = getPatientOrThrow(request);
        return PatientApiImpl.createForPatient(patient);
    }

    private Patient getPatientOrThrow(Request request) {
        String username = request.headers("username");
        String password = request.headers("Password");

        if (username.isEmpty() || password.isEmpty())
            throw new Error("Username or password was missing.");

        return new Patient(UUID.randomUUID(), "first", "last", username, password);
    }
}
