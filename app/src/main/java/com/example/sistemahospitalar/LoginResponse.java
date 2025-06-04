package com.example.sistemahospitalar;

public class LoginResponse {

    private String token;
    private String nome;
    private int id;
    private boolean success; // Adicione este campo se o backend retornar status

    public String getToken() {
        return token;
    }

    public String getNome() {
        return nome;
    }

    public int getId() {
        return id;
    }

    public boolean isSuccess() {
        return success;
    }
}
