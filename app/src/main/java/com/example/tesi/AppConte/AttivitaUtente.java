package com.example.tesi.AppConte;

public class AttivitaUtente {

    private String attivita;
    private String data;
    private String tempo;

    public void setAttivita(String attivita) {
        this.attivita = attivita;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setTempo(String tempo) {
        this.tempo = tempo;
    }

    public String getTempo() {
        return tempo;
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

    public AttivitaUtente(String attivita, String tempo, String data) {
        this.attivita = attivita;
        this.tempo = tempo;
    }

    public AttivitaUtente() {
    }
}
