package com.afocatsm.prueba1;

public class siniestros {
    private String placa, telefono, numeroSiniestroApp; //, nombres, paterno, materno, fecharegistiro, fechasiniestro, numeroSiniestro;
    //private int codigosiniestro;

    public siniestros (String placa, String telefono, String numeroSiniestroApp){
        this.placa= placa;
        this.telefono=telefono;
        this.numeroSiniestroApp=numeroSiniestroApp;
    }

    public String getPlaca() {
        return placa;
    }

    public String getCelular() {
        return telefono;
    }
    public String getNumeroSiniestroApp() {
        return numeroSiniestroApp;
    }
}
