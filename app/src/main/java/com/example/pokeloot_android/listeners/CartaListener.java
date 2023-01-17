package com.example.pokeloot_android.listeners;

import com.example.pokeloot_android.modelos.Carta;

import java.util.ArrayList;

public interface CartaListener {
    void onRefreshGridCarta(ArrayList<Carta> arrayCartas);
}
