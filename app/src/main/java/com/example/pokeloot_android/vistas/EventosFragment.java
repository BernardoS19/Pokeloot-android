package com.example.pokeloot_android.vistas;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pokeloot_android.R;
import com.example.pokeloot_android.modelos.Evento;
import com.example.pokeloot_android.modelos.SingletonCartas;

public class EventosFragment extends Fragment {

    private TextView tvData, tvDescricao;

    public EventosFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_eventos, container, false);

        tvData = view.findViewById(R.id.tvEventoData);
        tvDescricao = view.findViewById(R.id.tvEventoDescricao);

        SingletonCartas.getInstance(getContext()).setEventoListener(this::onRefreshEvento);
        SingletonCartas.getInstance(getContext()).getEventoAPI(getContext());

        return view;
    }

    public void onRefreshEvento(Evento evento) {
        if (evento != null) {
            tvData.setText(evento.getData());
            tvDescricao.setText(evento.getDescricao());
        }
    }
}