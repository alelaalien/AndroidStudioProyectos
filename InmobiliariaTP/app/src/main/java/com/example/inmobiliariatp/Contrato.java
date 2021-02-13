package com.example.inmobiliariatp;

import java.util.Date;

public class Contrato {
    private String inicio;
    private String fin;
    private String monto;


    public Contrato(String inicio, String fin, String monto) {
        this.inicio = inicio;
        this.fin = fin;
        this.monto = monto;
    }

    public String getInicio() {
        return inicio;
    }

    public void setInicio(String inicio) {
        this.inicio = inicio;
    }

    public String getFin() {
        return fin;
    }

    public void setFin(String fin) {
        this.fin = fin;
    }

    public String getMonto() {
        return monto;
    }

    public void setMonto(String monto) {
        this.monto = monto;
    }
}
