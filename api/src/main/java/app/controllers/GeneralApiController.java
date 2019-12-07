package app.controllers;

import com.example.apiabstractions.FamilyMemberApi;

import java.util.UUID;

import app.FamilyMemberApiImpl;
import app.GeneralApiImpl;
import app.JsonTransformer;
import model.FamilyMember;
import model.LoginCredentials;
import model.User;
import repository.UserRepository;
import spark.Request;

import static spark.Spark.get;

public class GeneralApiController implements Controller {

    private static final String basePath = "/api/general/";

    @Override
    public void registerRoutes() {

        get(basePath + "/getUserTypeOrNull", (req, res) -> {

            String username = req.headers("username");
            String password = req.headers("Password");

            if (username.isEmpty() || password.isEmpty())
                throw new Error("Username or password was missing.");

            GeneralApiImpl api = new GeneralApiImpl();
            return api.getUserTypeOrNull(new LoginCredentials(username, password));
        }, new JsonTransformer());
    }
}
