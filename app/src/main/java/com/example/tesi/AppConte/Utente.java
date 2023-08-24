package com.example.tesi.AppConte;

public class Utente {

    private String immagine;
    private String username;

    public Utente() {
    }

    public Utente(String immagine, String username) {
        this.immagine = immagine;
        this.username = username;
    }

    @Override
    public String toString() {
        return "Utente{" +
                "immagine='" + immagine + '\'' +
                ", username='" + username + '\'' +
                '}';
    }

    public void setImmagine(String immagine) {
        this.immagine = immagine;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImmagine() {
        return immagine;
    }

    public String getUsername() {
        return username;
    }
}
