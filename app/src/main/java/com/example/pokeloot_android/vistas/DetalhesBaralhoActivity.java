package com.example.pokeloot_android.vistas;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pokeloot_android.R;
import com.example.pokeloot_android.adaptadores.GridCartasAdaptador;
import com.example.pokeloot_android.modelos.Baralho;
import com.example.pokeloot_android.modelos.Carta;
import com.example.pokeloot_android.modelos.SingletonCartas;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class DetalhesBaralhoActivity extends AppCompatActivity {

    private TextView tvNomeBaralho;
    private Baralho baralho;
    private int baralhoId;

    private GridView gridViewCartas;
    private FloatingActionButton fabAdicionarCarta;
    private FloatingActionButton fabEditarBaralho;
    private FloatingActionButton fabRemoverBaralho;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_baralho);

        tvNomeBaralho = findViewById(R.id.tvBaralhoDetalhesNome);

        int id = getIntent().getIntExtra("baralho_id", 0);
        baralho = SingletonCartas.getInstance(getApplicationContext()).getBaralho(id);

        if (baralho != null) {
            tvNomeBaralho.setText(baralho.getNome());
            baralhoId = baralho.getId();
        }

        gridViewCartas = findViewById(R.id.gridBaralhoCartas);

        fabAdicionarCarta = findViewById(R.id.fabAdicionarCarta);
        fabEditarBaralho = findViewById(R.id.fabEditarBaralho);
        fabRemoverBaralho = findViewById(R.id.fabRemoverBaralho);

        SharedPreferences preferences = getApplicationContext().getSharedPreferences("DADOS_USER", Context.MODE_PRIVATE);
        String authkey = preferences.getString("AUTH_KEY", null);
        if (authkey != "Error, username or password may be wrong." && authkey != null && authkey != "null") {
            SingletonCartas.getInstance(getApplicationContext()).setCartaListener(this::onRefreshGridCarta);
            SingletonCartas.getInstance(getApplicationContext()).getCartasDoBaralhoAPI(baralhoId, getApplicationContext());
        } else {
            Toast.makeText(getApplicationContext(), "Erro. Autenticação não reconhecida.", Toast.LENGTH_SHORT).show();
        }

        fabAdicionarCarta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(DetalhesBaralhoActivity.this, "Add Carta", Toast.LENGTH_SHORT).show();
            }
        });

        fabEditarBaralho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogEditar();
            }
        });

        fabRemoverBaralho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogRemover();
            }
        });

        gridViewCartas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {
                Intent intent = new Intent(getApplicationContext(), DetalhesCartaActivity.class);
                intent.putExtra("carta_id", (int) id);
                startActivity(intent);
            }
        });

    }

    public void onRefreshGridCarta(ArrayList<Carta> arrayCartas) {
        if (arrayCartas != null) {
            gridViewCartas.setAdapter(new GridCartasAdaptador(getApplicationContext(), arrayCartas));
        }
    }

    private void showDialogEditar() {
        AlertDialog.Builder alert;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            alert = new AlertDialog.Builder(DetalhesBaralhoActivity.this, com.google.android.material.R.style.Theme_MaterialComponents_Dialog_Alert);
        } else {
            alert = new AlertDialog.Builder(DetalhesBaralhoActivity.this);
        }

        LayoutInflater inflater = getLayoutInflater();

        View view = inflater.inflate(R.layout.fabitem, null);

        EditText baralhoNome = view.findViewById(R.id.etCriarNomeBaralho);
        Button btnEditarBaralho = view.findViewById(R.id.btnGuardarNovoBaralho);

        baralhoNome.setText(tvNomeBaralho.getText());

        alert.setView(view);

        alert.setCancelable(true);

        final AlertDialog dialog = alert.create();
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.show();

        btnEditarBaralho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nome = baralhoNome.getText().toString();
                SingletonCartas.getInstance(getApplicationContext()).updateBaralhoAPI(baralhoId, nome, getApplicationContext());
                tvNomeBaralho = findViewById(R.id.tvBaralhoDetalhesNome);
                tvNomeBaralho.setText(nome);
                dialog.dismiss();
            }
        });
    }

    private void showDialogRemover() {
        AlertDialog.Builder alert;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            alert = new AlertDialog.Builder(DetalhesBaralhoActivity.this, com.google.android.material.R.style.Theme_MaterialComponents_Dialog_Alert);
        } else {
            alert = new AlertDialog.Builder(DetalhesBaralhoActivity.this);
        }

        alert.setCancelable(true);
        alert.setTitle("Eliminar?");
        alert.setMessage("Atenção: Esta ação não pode ser revertida.");
        alert.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                SingletonCartas.getInstance(getApplicationContext()).deleteBaralhoAPI(baralhoId, getApplicationContext());
                finish();
                dialogInterface.dismiss();
            }
        });
        alert.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        AlertDialog dialog = alert.create();
        dialog.show();

    }
}