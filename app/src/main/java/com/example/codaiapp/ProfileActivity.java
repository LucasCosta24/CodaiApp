package com.example.codaiapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;

public class ProfileActivity extends AppCompatActivity {

    private static final String PREFS_USER = "prefs_user";
    private static final String KEY_NAME   = "name";
    private static final String KEY_EMAIL  = "email";
    private static final String KEY_ROLE   = "role";
    private static final String KEY_ABOUT  = "about";

    private TextView tvName;
    private TextView tvEmail;
    private TextView tvRole;
    private TextView tvAbout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        MaterialToolbar toolbar = findViewById(R.id.profileToolbar);
        if (toolbar != null) {
            toolbar.setNavigationOnClickListener(v -> finish());
        }


        tvName  = findViewById(R.id.tvProfileName);
        tvEmail = findViewById(R.id.tvProfileEmail);
        tvRole  = findViewById(R.id.tvProfileRole);
        tvAbout = findViewById(R.id.tvProfileAbout);

        MaterialButton btnEdit = findViewById(R.id.btnEditProfile);
        MaterialButton btnLogout = findViewById(R.id.btnLogout);

        // Carrega dados salvos
        loadProfile();

        btnEdit.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, EditProfileActivity.class);
            startActivity(intent);
        });

        // Logout simples (por enquanto só limpa prefs)
        btnLogout.setOnClickListener(v -> {
            SharedPreferences prefs = getSharedPreferences(PREFS_USER, MODE_PRIVATE);
            prefs.edit().clear().apply();

            Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Quando voltar do EditProfileActivity, recarrega os dados
        loadProfile();
    }

    private void loadProfile() {
        SharedPreferences prefs = getSharedPreferences(PREFS_USER, MODE_PRIVATE);
        String name  = prefs.getString(KEY_NAME, "Nome do Usuário");
        String email = prefs.getString(KEY_EMAIL, "usuario@email.com");
        String role  = prefs.getString(KEY_ROLE, "Estudante / Dev em formação");
        String about = prefs.getString(KEY_ABOUT,
                "Adicione uma breve descrição sobre você, seus objetivos de aprendizado e áreas de interesse.");

        tvName.setText(name);
        tvEmail.setText(email);
        tvRole.setText(role);
        tvAbout.setText(about);
    }
}


