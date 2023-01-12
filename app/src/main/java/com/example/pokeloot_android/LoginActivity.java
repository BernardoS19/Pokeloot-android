package com.example.pokeloot_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

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
    }

    public void onClickLogin(View view) {
    }
}