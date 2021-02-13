package com.example.aletp;

public class Contratos {
    private String inicio;
    private String fin;
    private int monto;

    public Contratos(String inicio, String fin, int monto) {
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

    public int getMonto() {
        return monto;
    }

    public void setMonto(int monto) {
        this.monto = monto;
    }
}
