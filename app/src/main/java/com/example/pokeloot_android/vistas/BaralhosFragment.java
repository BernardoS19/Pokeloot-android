package com.example.pokeloot_android.vistas;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.pokeloot_android.R;
import com.example.pokeloot_android.adaptadores.ListaBaralhoAdaptador;
import com.example.pokeloot_android.modelos.Baralho;
import com.example.pokeloot_android.modelos.SingletonCartas;

import java.util.ArrayList;

public class BaralhosFragment extends Fragment {

    private ListView lvListaBaralhos;

    public BaralhosFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_baralhos, container, false);

        lvListaBaralhos = view.findViewById(R.id.lvListaBaralhos);

        SharedPreferences preferences = getContext().getSharedPreferences("DADOS_USER", Context.MODE_PRIVATE);
        String authkey = preferences.getString("AUTH_KEY", null);
        if (authkey != "Error, username or password may be wrong." && authkey != null && authkey != "null") {
            SingletonCartas.getInstance(getContext()).setBaralhoListener(this::onRefreshListaBaralho);
            SingletonCartas.getInstance(getContext()).getBaralhosDoUserAPI(getContext());
        } else {
            Toast.makeText(getContext(), "Erro. Autenticação não reconhecida.", Toast.LENGTH_SHORT).show();
        }

        return view;
    }

    public void onRefreshListaBaralho(ArrayList<Baralho> listaBaralho) {
        if (listaBaralho != null) {
            lvListaBaralhos.setAdapter(new ListaBaralhoAdaptador(getContext(), listaBaralho));
        }
    }

}