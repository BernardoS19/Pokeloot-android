package com.example.pokeloot_android.vistas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;

import com.example.pokeloot_android.R;
import com.example.pokeloot_android.modelos.SingletonCartas;

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

    public void onClickCriarConta(View view) {
        String username = etUsername.getText().toString();
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();

        if(!isUsernameValido(username)){
            etUsername.setError("Nome invalido");
            return;
        }

        if(!isEmailValido(email)){
            etEmail.setError("Nome invalido");
            return;
        }

        if(!isPasswordValido(password)){
            etPassword.setError("Password invalida");
            return;
        }

        SingletonCartas.getInstance(getApplicationContext()).registerAPI(username, email, password, getApplicationContext());
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }

    private boolean isUsernameValido(String username){
        if(username==null)
            return false;

        return username.length()>=1;
    }

    private boolean isEmailValido(String email){
        if(email==null)
            return false;

        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isPasswordValido(String password){
        if(password==null)
            return false;

        return password.length()>=9;
    }
}