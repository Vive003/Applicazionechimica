package com.example.applicazionechimica;

import java.util.ArrayList;

public class Anno {
    String anno;
    ArrayList<ElementoStorico> listaElementoStorico = new ArrayList<>();

    public Anno(String anno) {
        this.anno = anno;
    }

    public Anno() {
    }


    @Override
    public String toString() {
        return "Anno{" +
                "anno='" + anno + '\'' +
                ", listaElementoStorico=" + listaElementoStorico +
                '}';
    }
}
