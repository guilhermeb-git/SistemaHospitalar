package com.example.sistemahospitalar;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {
    @POST("/pacientes/login")
    Call<LoginResponse> loginPaciente(@Body LoginRequest request);

    @POST("/funcionarios/login")
    Call<LoginResponse> loginFuncionario(@Body LoginRequest request);
}

