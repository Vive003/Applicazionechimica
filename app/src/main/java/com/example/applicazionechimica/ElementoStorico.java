package com.example.applicazionechimica;

import java.util.HashMap;
import java.util.Set;

public class ElementoStorico {

    String nome;
    double qta;
    String unita;
    String numero;

    public ElementoStorico(String nome, double qta, String unita, String numero) {
        this.nome = nome;
        this.qta = qta;
        this.unita = unita;
        this.numero = numero;
    }

    @Override
    public String toString() {
        return "ElementoStorico{" +
                "nome='" + nome + '\'' +
                ", qta=" + qta +
                ", unita='" + unita + '\'' +
                ", numero='" + numero + '\'' +
                '}';
    }
    public HashMap<String, Object> toHashMap() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("nome", this.nome);
        map.put("quantita_usata", String.valueOf(this.qta));
        map.put("u_misura", String.valueOf(this.unita));
        return map;
    }
    public HashMap<String, Object> toHashMap2() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("quantita_usata", String.valueOf(this.qta));
        return map;
    }
}
