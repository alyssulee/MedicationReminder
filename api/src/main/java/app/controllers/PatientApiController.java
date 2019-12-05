package app.controllers;

import com.google.gson.Gson;

import java.util.Date;

import app.JsonTransformer;
import model.BloodPressure;
import model.PatientMeasurement;

import static spark.Spark.post;

public class PatientApiController implements Controller {

    private static final String basePath = "/api/patient/";
    private Gson gson = new Gson();

    @Override
    public void registerRoutes() {
        post(basePath + "/addMeasurement", "application/json", (req, res) -> {
            String json = req.body();
            PatientMeasurement measurement = gson.fromJson(json, PatientMeasurement.class);
            return measurement;
        }, new JsonTransformer());
    }
}
