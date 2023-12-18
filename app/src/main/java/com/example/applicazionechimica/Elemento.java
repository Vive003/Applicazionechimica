package com.example.applicazionechimica;

import java.util.HashMap;

public class Elemento {
    String armadio;
    String nome;
    double quantitaInizio;
    String unitaMisura;
    double quantita;
    String numero;


    public Elemento(String armadio, String nome, double quantitaInizio, String unitaMisura, String numero, double quantita) {
        this.armadio = armadio;
        this.nome = nome;
        this.quantitaInizio = quantitaInizio;
        this.unitaMisura = unitaMisura;
        this.quantita = quantita;
        this.numero = numero;
    }

    @Override
    public String toString() {
        return "Elemento{" +
                "armadio='" + armadio + '\'' +
                ", nome='" + nome + '\'' +
                ", quantitaInizio=" + quantitaInizio +
                ", unitaMisura='" + unitaMisura + '\'' +
                ", quantita='" + quantita + '\'' +
                ", numero=" + numero +
                '}';
    }
    public HashMap<String, Object> toHashMap() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("armadio", this.armadio);
        map.put("nome", String.valueOf(this.nome));
        map.put("quantita_inizio", this.quantitaInizio);
        map.put("u_misura", String.valueOf(this.unitaMisura));
        map.put("quantita", this.quantita);
        map.put("numero", String.valueOf(this.numero));
        return map;
    }

}
