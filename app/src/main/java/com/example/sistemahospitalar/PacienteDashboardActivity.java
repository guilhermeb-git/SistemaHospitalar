package com.example.sistemahospitalar;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.graphics.Insets;
import androidx.fragment.app.Fragment;
import com.example.sistemahospitalar.FilaFragment;
import com.example.sistemahospitalar.ConfirmacaoFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class PacienteDashboardActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paciente_dashboard);

        bottomNavigation = findViewById(R.id.bottomNavigation);
        // Selecionar fragment inicial
        loadFragment(new FilaFragment());

        bottomNavigation.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            switch(item.getItemId()) {
                case R.id.nav_inicio:
                    selectedFragment = new FilaFragment();
                    break;
                case R.id.nav_confirmacao:
                    selectedFragment = new ConfirmacaoFragment();
                    break;
                case R.id.nav_retorno:
                    selectedFragment = new AgendamentoFragment();
                    break;
                case R.id.nav_perfil:
                    selectedFragment = new PerfilFragment();
                    break;
            }
            if(selectedFragment != null){
                loadFragment(selectedFragment);
                return true;
            }
            return false;
        });
    }

    private void loadFragment(Fragment fragment){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.containerFragment, fragment)
                .commit();
    }
}

