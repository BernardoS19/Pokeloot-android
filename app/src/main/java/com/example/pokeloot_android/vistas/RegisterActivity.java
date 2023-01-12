package com.example.pokeloot_android.vistas;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import com.example.pokeloot_android.R;

public class RegisterActivity extends AppCompatActivity {

    private EditText etUsername, etEmail, etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etUsername = findViewById(R.id.etRegistoUsername);
        etEmail = findViewById(R.id.etRegistoEmail);
        etPassword = findViewById(R.id.etRegistoPassword);
    }
}