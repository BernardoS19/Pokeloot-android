package com.example.pokeloot_android.vistas;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.pokeloot_android.R;
import com.example.pokeloot_android.adaptadores.GridCartasAdaptador;

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

        String[] cartaNome = {"carta_teste", "carta_teste", "carta_teste", "carta_teste", "carta_teste"};
        int[] cartaImagens = {R.drawable.carta_teste, R.drawable.carta_teste, R.drawable.carta_teste, R.drawable.carta_teste, R.drawable.carta_teste};

        GridCartasAdaptador gridCartasAdaptador = new GridCartasAdaptador(this.getContext(), cartaNome, cartaImagens);
        gridViewCartas.setAdapter(gridCartasAdaptador);

        return view;
    }
}