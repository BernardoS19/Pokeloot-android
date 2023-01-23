package com.example.pokeloot_android.modelos;

public class BaralhoCarta {
    private int id, adicionado;
    private String imagem, nome;

    public BaralhoCarta(int id, String imagem, String nome, int adicionado) {
        this.id = id;
        this.imagem = imagem;
        this.nome = nome;
        this.adicionado = adicionado;
    }

    public int getId() {
        return id;
    }

    public int getAdicionado() {
        return adicionado;
    }

    public String getImagem() {
        return imagem;
    }

    public String getNome() {
        return nome;
    }

}
