package com.example.marketapp.models;

public class Produto {

    public int id;
    public String nome;
    public double preco;
    public String cod;
    public String foto;

    public Produto(){};

    public Produto(int id, String nome, double preco, String cod, String foto) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.cod = cod;
        this.foto = foto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

}
