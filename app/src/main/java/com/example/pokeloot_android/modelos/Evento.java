package com.example.pokeloot_android.modelos;

public class Evento {
    private int id;
    private String descricao, data, latitude, longitude;

    public Evento(int id, String descricao, String data, String latitude, String longitude) {
        this.id = id;
        this.descricao = descricao;
        this.data = data;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getData() {
        return data;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }
}
