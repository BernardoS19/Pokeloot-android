package com.example.pokeloot_android.vistas;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pokeloot_android.R;

public class DetalhesBaralhoFragment extends Fragment {

    public DetalhesBaralhoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detalhes_baralho, container, false);

        return view;
    }
}