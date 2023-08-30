package com.example.tesi.AppConte;

public class AttivitaUtente {

    private String attivita;
    private String data;

    public void setAttivita(String attivita) {
        this.attivita = attivita;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getAttivita() {
        return attivita;
    }

    public String getData() {
        return data;
    }

    public AttivitaUtente(String attivita, String data) {
        this.attivita = attivita;
        this.data = data;
    }

    public AttivitaUtente() {
    }
}
