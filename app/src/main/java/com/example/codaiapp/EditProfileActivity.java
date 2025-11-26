package com.example.codaiapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class EditProfileActivity extends AppCompatActivity {

    private static final String PREFS_USER = "prefs_user";
    private static final String KEY_NAME   = "name";
    private static final String KEY_EMAIL  = "email";
    private static final String KEY_ROLE   = "role";
    private static final String KEY_ABOUT  = "about";

    private TextInputEditText etName, etEmail, etRole, etAbout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        MaterialToolbar toolbar = findViewById(R.id.editProfileToolbar);
        if (toolbar != null) {
            toolbar.setNavigationOnClickListener(v -> finish());
        }

        etName  = findViewById(R.id.etEditName);
        etEmail = findViewById(R.id.etEditEmail);
        etRole  = findViewById(R.id.etEditRole);
        etAbout = findViewById(R.id.etEditAbout);

        MaterialButton btnSave = findViewById(R.id.btnSaveProfile);

        // Carrega os valores atuais
        SharedPreferences prefs = getSharedPreferences(PREFS_USER, MODE_PRIVATE);
        etName.setText(prefs.getString(KEY_NAME, ""));
        etEmail.setText(prefs.getString(KEY_EMAIL, ""));
        etRole.setText(prefs.getString(KEY_ROLE, ""));
        etAbout.setText(prefs.getString(KEY_ABOUT, ""));

        btnSave.setOnClickListener(v -> {
            String name  = etName.getText() != null ? etName.getText().toString().trim() : "";
            String email = etEmail.getText() != null ? etEmail.getText().toString().trim() : "";
            String role  = etRole.getText() != null ? etRole.getText().toString().trim() : "";
            String about = etAbout.getText() != null ? etAbout.getText().toString().trim() : "";

            SharedPreferences.Editor editor = prefs.edit();
            editor.putString(KEY_NAME, name);
            editor.putString(KEY_EMAIL, email);
            editor.putString(KEY_ROLE, role);
            editor.putString(KEY_ABOUT, about);
            editor.apply();

            Toast.makeText(this, "Perfil salvo!", Toast.LENGTH_SHORT).show();
            finish(); // volta pra ProfileActivity
        });
    }
}

