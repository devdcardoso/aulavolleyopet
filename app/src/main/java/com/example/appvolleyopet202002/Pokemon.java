package com.example.appvolleyopet202002;

import java.util.ArrayList;
import java.util.List;

public class Pokemon {
    private String nome;
    private List<String> ataques;
    private List<String> tipos;

    public Pokemon(){
        this.ataques = new ArrayList<>();
        this.tipos = new ArrayList<>();
    }

    public Pokemon(String nome, List<String> ataques) {
        this.nome = nome;
        this.ataques = ataques;
    }

    public void addAtaque(String nome){
        this.ataques.add(nome);
    }

    public void addTipo(String nome){
        this.tipos.add(nome);
    }

    public int totalAtaques(){
        return this.ataques.size();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<String> getAtaques() {
        return ataques;
    }

    public void setAtaques(List<String> ataques) {
        this.ataques = ataques;
    }

    @Override
    public String toString() {
        return "Pokemon:\n" +
                "nome = " + nome + "\n" +
                "Total de ataques disponíveis = " + totalAtaques() + "\n" +
                //"Ataques: " + ataques + "\n" +
                "Tipos: " + tipos;
    }
}
