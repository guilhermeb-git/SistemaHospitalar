package com.example.sistemahospitalar;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginPacienteActivity extends AppCompatActivity {

    private EditText editTextCpf, editTextPassword;
    private Button buttonLogin;
    private TextView textViewForgotPassword;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_paciente);

        editTextCpf = findViewById(R.id.editTextCpf);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        textViewForgotPassword = findViewById(R.id.textViewForgotPassword);

        apiService = RetrofitClient.getClient().create(ApiService.class);

        // Máscara CPF (mesmo código de antes)
        editTextCpf.addTextChangedListener(new TextWatcher() {
            private boolean isUpdating;
            private String oldText = "";

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String str = s.toString().replaceAll("[^\\d]", "");
                if (isUpdating) {
                    oldText = str;
                    isUpdating = false;
                    return;
                }
                String formatted = "";
                if (str.length() <= 11) {
                    if (str.length() >= 3)
                        formatted += str.substring(0, 3) + ".";
                    if (str.length() >= 6)
                        formatted += str.substring(3, 6) + ".";
                    if (str.length() >= 9)
                        formatted += str.substring(6, 9) + "-";
                    if (str.length() >= 10)
                        formatted += str.substring(9);
                    else if (str.length() > 6 && str.length() < 9)
                        formatted += str.substring(6);
                    else if (str.length() > 3 && str.length() < 6)
                        formatted += str.substring(3);
                    else if (str.length() < 3)
                        formatted = str;
                } else {
                    formatted = oldText;
                }
                isUpdating = true;
                editTextCpf.setText(formatted);
                editTextCpf.setSelection(formatted.length());
            }
            @Override
            public void afterTextChanged(Editable s) { }
        });

        buttonLogin.setOnClickListener(v -> {
            String cpf = editTextCpf.getText().toString().trim();
            String senha = editTextPassword.getText().toString().trim();

            if (!isValidCPF(cpf)) {
                editTextCpf.setError("CPF inválido");
                return;
            }
            if (senha.isEmpty()) {
                editTextPassword.setError("Senha é obrigatória");
                return;
            }

            loginPaciente(cpf, senha);
        });

        textViewForgotPassword.setOnClickListener(v -> {
            Toast.makeText(this, "Fluxo de recuperação ainda não implementado.", Toast.LENGTH_SHORT).show();
        });
    }

    private boolean isValidCPF(String cpf) {
        return cpf != null && cpf.length() == 14;
    }

    private void loginPaciente(String cpf, String senha) {
        LoginRequest request = new LoginRequest(cpf, senha);
        Call<LoginResponse> call = apiService.loginPaciente(request);

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    LoginResponse loginResponse = response.body();
                    if (loginResponse.isSuccess()) {
                        Toast.makeText(LoginPacienteActivity.this, "Login bem-sucedido!", Toast.LENGTH_SHORT).show();
                        // Redirecionar para PacienteDashboardActivity
                        Intent intent = new Intent(LoginPacienteActivity.this, PacienteDashboardActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(LoginPacienteActivity.this, loginResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginPacienteActivity.this, "Erro na resposta da API", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(LoginPacienteActivity.this, "Falha na conexão: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
