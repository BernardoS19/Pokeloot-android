package com.example.pokeloot_android.vistas;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.pokeloot_android.R;
import com.example.pokeloot_android.adaptadores.ListaBaralhoAdaptador;
import com.example.pokeloot_android.modelos.Baralho;
import com.example.pokeloot_android.modelos.SingletonCartas;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class BaralhosFragment extends Fragment {

    private ListView lvListaBaralhos;

    private FloatingActionButton fabCriarBaralho;

    public BaralhosFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_baralhos, container, false);

        setHasOptionsMenu(true);

        lvListaBaralhos = view.findViewById(R.id.lvListaBaralhos);

        fabCriarBaralho = view.findViewById(R.id.fabCriarBaralho);

        SharedPreferences preferences = getContext().getSharedPreferences("DADOS_USER", Context.MODE_PRIVATE);
        String authkey = preferences.getString("AUTH_KEY", null);
        if (authkey != "Error, username or password may be wrong." && authkey != null && authkey != "null") {
            SingletonCartas.getInstance(getContext()).setBaralhoListener(this::onRefreshListaBaralho);
            SingletonCartas.getInstance(getContext()).getBaralhosDoUserAPI(getContext());
        } else {
            Toast.makeText(getContext(), "Erro. Autenticação não reconhecida.", Toast.LENGTH_SHORT).show();
        }

        fabCriarBaralho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });

        lvListaBaralhos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {
                Intent intent = new Intent(getContext(), DetalhesBaralhoActivity.class);
                intent.putExtra("baralho_id", (int) id);
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        SingletonCartas.getInstance(getContext()).setBaralhoListener(this::onRefreshListaBaralho);
        SingletonCartas.getInstance(getContext()).getBaralhosDoUserAPI(getContext());
    }

    public void onRefreshListaBaralho(ArrayList<Baralho> listaBaralho) {
        if (listaBaralho != null) {
            lvListaBaralhos.setAdapter(new ListaBaralhoAdaptador(getContext(), listaBaralho));
        }
    }

    private void showDialog() {
        AlertDialog.Builder alert;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            alert = new AlertDialog.Builder(getContext(), com.google.android.material.R.style.Theme_MaterialComponents_Dialog_Alert);
        } else {
            alert = new AlertDialog.Builder(getContext());
        }

        LayoutInflater inflater = getLayoutInflater();

        View view = inflater.inflate(R.layout.fabitem, null);

        EditText baralhoNome = view.findViewById(R.id.etCriarNomeBaralho);
        Button btnCriarBaralho = view.findViewById(R.id.btnGuardarNovoBaralho);

        alert.setView(view);

        alert.setCancelable(true);

        AlertDialog dialog = alert.create();
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.show();

        btnCriarBaralho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nome = baralhoNome.getText().toString();
                SingletonCartas.getInstance(getContext()).createBaralhoAPI(nome, getContext());
                SingletonCartas.getInstance(getContext()).setBaralhoListener(BaralhosFragment.this::onRefreshListaBaralho);
                SingletonCartas.getInstance(getContext()).getBaralhosDoUserAPI(getContext());
                dialog.dismiss();
            }
        });


    }

}