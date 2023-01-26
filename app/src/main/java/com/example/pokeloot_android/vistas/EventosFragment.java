package com.example.pokeloot_android.vistas;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PackageManagerCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.pokeloot_android.R;
import com.example.pokeloot_android.modelos.Evento;
import com.example.pokeloot_android.modelos.SingletonCartas;

public class EventosFragment extends Fragment {

    private static final int CAMERA_PERMISSION_CODE = 100;

    private TextView tvData, tvDescricao;

    private String eventoLatitude, eventoLongitude;

    private Button btnQRCode, btnMaps;

    public EventosFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_eventos, container, false);

        tvData = view.findViewById(R.id.tvEventoData);
        tvDescricao = view.findViewById(R.id.tvEventoDescricao);

        btnQRCode = view.findViewById(R.id.btnLerQrCode);
        btnMaps = view.findViewById(R.id.btnAbrirMapa);

        SingletonCartas.getInstance(getContext()).setEventoListener(this::onRefreshEvento);
        SingletonCartas.getInstance(getContext()).getEventoAPI(getContext());

        btnQRCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RequestCameraPermission(Manifest.permission.CAMERA, CAMERA_PERMISSION_CODE);
                Intent intent = new Intent(getActivity(), QRCodeActivity.class);
                startActivity(intent);
            }
        });

        btnMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MapaActivity.class);
                intent.putExtra("latitude", eventoLatitude);
                intent.putExtra("longitude", eventoLongitude);
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        SingletonCartas.getInstance(getContext()).setEventoListener(this::onRefreshEvento);
        SingletonCartas.getInstance(getContext()).getEventoAPI(getContext());
    }

    public void onRefreshEvento(Evento evento) {
        if (evento != null) {
            tvData.setText(evento.getData());
            tvDescricao.setText(evento.getDescricao());
            eventoLatitude = evento.getLatitude();
            eventoLongitude = evento.getLongitude();
        }
    }

    public void RequestCameraPermission(String permission, int requestCode) {
        if (ContextCompat.checkSelfPermission(getContext(), permission) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions((Activity) getContext(), new String[] { permission }, requestCode);
        }
    }
}