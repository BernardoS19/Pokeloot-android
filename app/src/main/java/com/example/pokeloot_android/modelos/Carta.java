package com.example.pokeloot_android.modelos;

public class Carta {
    private int id;
    private String imagem, nome, descricao, tipo, elemento, colecao;

    public Carta(int id, String imagem, String nome, String descricao, String tipo, String elemento, String colecao) {
        this.id = id;
        this.imagem = imagem;
        this.nome = nome;
        this.descricao = descricao;
        this.tipo = tipo;
        this.elemento = elemento;
        this.colecao = colecao;
    }

    public int getId() {
        return id;
    }

    public String getImagem() {
        return imagem;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getTipo() {
        return tipo;
    }

    public String getElemento() {
        return elemento;
    }

    public String getColecao() {
        return colecao;
    }
}
