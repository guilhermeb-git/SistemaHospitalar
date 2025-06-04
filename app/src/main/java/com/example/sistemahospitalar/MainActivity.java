package com.example.sistemahospitalar;

import static androidx.core.content.ContextCompat.startActivity;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.view.View;
import android.widget.Button;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {

    private Button btnPaciente, btnFuncionario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnPaciente = findViewById(R.id.btnPaciente);
        btnFuncionario = findViewById(R.id.btnFuncionario);


        btnPaciente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Abre a tela de login do paciente
                Intent intent = new Intent(MainActivity.this, LoginPacienteActivity.class);
                startActivity(intent);
            }
        });

        btnFuncionario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginFuncionarioActivity.class);
                startActivity(intent);
            }
        });
    }
}