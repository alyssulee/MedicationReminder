package com.example.apiabstractions;

import model.LoginCredentials;
import model.UserType;

public interface GeneralApi {
    UserType getUserTypeOrNull(LoginCredentials credentials);
}
