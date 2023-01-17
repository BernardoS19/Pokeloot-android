package com.example.pokeloot_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pokeloot_android.listeners.LoginListener;
import com.example.pokeloot_android.modelos.SingletonCartas;
import com.example.pokeloot_android.vistas.MenuActivity;
import com.example.pokeloot_android.vistas.RegisterActivity;

public class LoginActivity extends AppCompatActivity {

    private TextView tvRedirect;
    private EditText etUsername, etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);

        //region Redirect da Activity Login para a Activiy Registo
        tvRedirect = findViewById(R.id.tvRedirectCriarConta);
        tvRedirect.setMovementMethod(LinkMovementMethod.getInstance());
        SpannableString texto = new SpannableString(tvRedirect.getText());

        ClickableSpan linkTexto = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        };
        texto.setSpan(linkTexto, 15,31, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvRedirect.setText(texto);
        //endregion

        SingletonCartas.getInstance(getApplicationContext()).setLoginListener(this::onValidateLogin);
    }

    public void onClickLogin(View view) {
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();

        if(!isUsernameValido(username)){
            etUsername.setError("Nome invalido");
            return;
        }

        if(!isPasswordValido(password)){
            etPassword.setError("Password invalida");
            return;
        }

        SingletonCartas.getInstance(getApplicationContext()).loginAPI(username, password, getApplicationContext());

    }

    private boolean isUsernameValido(String username){
        if(username==null)
            return false;

        return username.length()>=1;
    }

    private boolean isPasswordValido(String password){
        if(password==null)
            return false;

        return password.length()>=4;
    }

    public void onValidateLogin(String auth_key, String username, Context context) {
        SharedPreferences userInfoPreferences = getSharedPreferences("DADOS_USER", Context.MODE_PRIVATE);
        userInfoPreferences.edit().putString("AUTH_KEY", auth_key).apply();
        if (auth_key != "Error, username or password may be wrong." && auth_key != null && auth_key != "null") {
            Intent menuIntent = new Intent(this, MenuActivity.class);
            startActivity(menuIntent);
        }
        else {
            Toast.makeText(context, "Erro, username ou password incorretos.", Toast.LENGTH_SHORT).show();
        }
    }
}