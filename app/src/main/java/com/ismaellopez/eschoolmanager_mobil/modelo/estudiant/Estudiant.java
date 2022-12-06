package com.ismaellopez.eschoolmanager_mobil.modelo.estudiant;

import androidx.annotation.NonNull;

public class Estudiant {

    private String nom;
    private String cognoms;
    private int codi;
    private String dni;
    private String dataNaixement;
    private String adreca;
    private String telefon;
    private String email;
    private boolean registrat;

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCognoms() {
        return cognoms;
    }

    public void setCognoms(String cognoms) {
        this.cognoms = cognoms;
    }

    public int getCodi() {
        return codi;
    }

    public void setCodi(int codi) {
        this.codi = codi;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getDataNaixement() {
        return dataNaixement;
    }

    public void setDataNaixement(String dataNaixement) {
        this.dataNaixement = dataNaixement;
    }

    public String getAdreca() {
        return adreca;
    }

    public void setAdreca(String adreca) {
        this.adreca = adreca;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean getRegistrat() {
        return registrat;
    }

    public void setRegistrat(boolean registrat) {
        this.registrat = registrat;
    }

    @NonNull
    @Override
    public String toString() {
        return nom + " "+ cognoms;
    }
}
