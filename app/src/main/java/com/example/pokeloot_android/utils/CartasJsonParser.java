package com.example.pokeloot_android.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.pokeloot_android.modelos.Baralho;
import com.example.pokeloot_android.modelos.BaralhoCarta;
import com.example.pokeloot_android.modelos.Carta;
import com.example.pokeloot_android.modelos.Evento;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CartasJsonParser {
    //verificar conexão à internet
    public static Boolean isConnectionInternet(Context context) {
        ConnectivityManager cn = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cn.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    //receber auth_key no login
    public static String parserJsonLogin(JSONObject response) {
        try {
            if (response.getString("auth_key") != null) {
                return response.getString("auth_key");
            }
            else {
                return response.getString("error");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    //receber cartas do utilizador
    public static ArrayList<Carta> parserJsonCartas(JSONArray response) {
        ArrayList<Carta> cartas = new ArrayList<>();
        try {
            for (int i = 0; i<response.length(); i++) {
                JSONObject carta = (JSONObject) response.get(i);
                int id = carta.getInt("id");
                String imagem = carta.getString("imagem");
                String nome = carta.getString("nome");
                String descricao = carta.getString("descricao");
                String tipo = carta.getString("tipo");
                String elemento = carta.getString("elemento");
                String colecao = carta.getString("colecao");
                Carta auxCarta = new Carta(id, imagem, nome, descricao, tipo, elemento, colecao);
                cartas.add(auxCarta);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return cartas;
    }

    //receber baralhos do utilizador
    public static ArrayList<Baralho> parserJsonBaralhos(JSONArray response) {
        ArrayList<Baralho> baralhos = new ArrayList<>();
        try {
            for (int i = 0; i<response.length(); i++) {
                JSONObject baralho = (JSONObject) response.get(i);
                int id = baralho.getInt("id");
                String nome = baralho.getString("nome");
                Baralho auxBaralho = new Baralho(id, nome);
                baralhos.add(auxBaralho);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return baralhos;
    }

    //receber cartas para colocar ou retirar do baralho
    public static ArrayList<BaralhoCarta> parserJsonGerirCartas(JSONArray response) {
        ArrayList<BaralhoCarta> cartas = new ArrayList<>();
        try {
            for (int i = 0; i<response.length(); i++) {
                JSONObject carta = (JSONObject) response.get(i);
                int id = carta.getInt("id");
                String imagem = carta.getString("imagem");
                String nome = carta.getString("nome");
                int adicionado = carta.getInt("adicionado");
                BaralhoCarta auxBaralhoCarta = new BaralhoCarta(id, imagem, nome, adicionado);
                cartas.add(auxBaralhoCarta);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return cartas;
    }

    //receber evento
    public static Evento parserJsonEvento(JSONObject response) {
        try {
            int id = response.getInt("id");
            String descricao = response.getString("descricao");
            String data = response.getString("data");
            String latitude = response.getString("latitude");
            String longitude = response.getString("longitude");

            Evento auxEvento = new Evento(id, descricao, data, latitude, longitude);
            return auxEvento;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }


}
