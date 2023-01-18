package com.example.pokeloot_android.vistas;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.viewmodel.CreationExtras;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.pokeloot_android.R;
import com.example.pokeloot_android.adaptadores.GridCartasAdaptador;
import com.example.pokeloot_android.modelos.Carta;
import com.example.pokeloot_android.modelos.SingletonCartas;

import java.util.ArrayList;

public class CartasFragment extends Fragment {

    private GridView gridViewCartas;


    public CartasFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_cartas, container, false);

        setHasOptionsMenu(true);

        gridViewCartas = view.findViewById(R.id.gridCartas);

        SharedPreferences preferences = getContext().getSharedPreferences("DADOS_USER", Context.MODE_PRIVATE);
        String authkey = preferences.getString("AUTH_KEY", null);
        if (authkey != "Error, username or password may be wrong." && authkey != null && authkey != "null") {
            SingletonCartas.getInstance(getContext()).setCartaListener(this::onRefreshGridCarta);
            SingletonCartas.getInstance(getContext()).getCartasCompradasAPI(getContext());
        } else {
            Toast.makeText(getContext(), "Erro. Autenticação não reconhecida.", Toast.LENGTH_SHORT).show();
        }

        gridViewCartas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {
                Intent intent = new Intent(getContext(), DetalhesCartaActivity.class);
                intent.putExtra("carta_id", (int) id);
                startActivity(intent);
            }
        });

        return view;
    }

    public void onRefreshGridCarta(ArrayList<Carta> arrayCartas) {
        if (arrayCartas != null) {
            gridViewCartas.setAdapter(new GridCartasAdaptador(getContext(), arrayCartas));
        }
    }

}