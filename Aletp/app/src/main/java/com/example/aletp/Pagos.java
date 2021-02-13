package com.example.aletp;

public class Pagos {
    private String fecha;
    private int importe;
    private int nroPago;

    public Pagos(String fecha, int importe, int nroPago) {
        this.fecha = fecha;
        this.importe = importe;
        this.nroPago = nroPago;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getImporte() {
        return importe;
    }

    public void setImporte(int importe) {
        this.importe = importe;
    }

    public int getNroPago() {
        return nroPago;
    }

    public void setNroPago(int nroPago) {
        this.nroPago = nroPago;
    }
}
