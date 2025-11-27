package com.example.codaiapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.example.codaiapp.dao.UserDAO;
import com.example.codaiapp.model.User;

public class RegisterActivity extends AppCompatActivity {

    private TextInputEditText etName, etEmail, etPassword, etConfirmPassword;
    private MaterialButton btnRegister;
    private TextView tvLogin;
    private UserDAO userDAO;
    private ImageButton backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        userDAO = new UserDAO(this);

        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        btnRegister = findViewById(R.id.btnRegister);
        tvLogin = findViewById(R.id.tvLogin);

        btnRegister.setOnClickListener(v -> handleRegistration());

        tvLogin.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void handleRegistration() {
        String name = etName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString();
        String confirmPassword = etConfirmPassword.getText().toString();

        if (!password.equals(confirmPassword) || TextUtils.isEmpty(email) || TextUtils.isEmpty(name)) {
            Toast.makeText(this, "Verifique os dados.", Toast.LENGTH_SHORT).show();
            return;
        }

        User newUser = new User(name, email, password);

        if (userDAO.registerUser(newUser)) {
            Toast.makeText(this, "✅ Cadastro sucesso! Faça login.", Toast.LENGTH_LONG).show();

            // SUCESSO: Apenas fecha esta Activity e retorna para a LoginActivity anterior.
            finish();
        } else {
            Toast.makeText(this, "❌ Erro. Email já pode estar registrado.", Toast.LENGTH_LONG).show();
        }
    }
}