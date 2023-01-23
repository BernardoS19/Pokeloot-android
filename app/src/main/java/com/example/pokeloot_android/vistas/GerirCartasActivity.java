package com.example.pokeloot_android.vistas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.Toast;

import com.example.pokeloot_android.R;
import com.example.pokeloot_android.adaptadores.GridGerirCartasAdaptador;
import com.example.pokeloot_android.modelos.BaralhoCarta;
import com.example.pokeloot_android.modelos.SingletonCartas;

import java.util.ArrayList;

public class GerirCartasActivity extends AppCompatActivity {

    private GridView gridViewGerirCartas;
    private int baralhoId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gerir_cartas);

        baralhoId = getIntent().getIntExtra("baralhoId", 0);

        gridViewGerirCartas = findViewById(R.id.gridGerirCartas);

        SharedPreferences preferences = getApplicationContext().getSharedPreferences("DADOS_USER", Context.MODE_PRIVATE);
        String authkey = preferences.getString("AUTH_KEY", null);
        if (authkey != "Error, username or password may be wrong." && authkey != null && authkey != "null") {
            SingletonCartas.getInstance(getApplicationContext()).setBaralhoCartaListener(this::onRefreshGridGerirCarta);
            SingletonCartas.getInstance(getApplicationContext()).getGerirCartasBaralhoAPI(baralhoId, getApplicationContext());
        } else {
            Toast.makeText(GerirCartasActivity.this, "Erro. Autenticação não reconhecida.", Toast.LENGTH_SHORT).show();
        }

    }

    public void onRefreshGridGerirCarta(ArrayList<BaralhoCarta> arrayCartas) {
        if (arrayCartas != null) {
            gridViewGerirCartas.setAdapter(new GridGerirCartasAdaptador(baralhoId, getApplicationContext(), arrayCartas));
        }
    }
}