package com.ismaellopez.eschoolmanager_mobil.modelo.empleat;

public class Empleat {

    private String nom;
    private String cognoms;
    private int codiEmpleat;
    private String dni;
    private String dataNaixament;
    private String adreca;
    private String telefon;
    private String email;
    private int codiDepartament;
    private String nomDepartament;
    private String usuari;
    private String contrasenya;
    private boolean actiu;

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

    public int getCodiEmpleat() {
        return codiEmpleat;
    }

    public void setCodiEmpleat(int codiEmpleat) {
        this.codiEmpleat = codiEmpleat;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getDataNaixament() {
        return dataNaixament;
    }

    public void setDataNaixament(String dataNaixament) {
        this.dataNaixament = dataNaixament;
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

    public int getCodiDepartament() {
        return codiDepartament;
    }

    public void setCodiDepartament(int codiDepartament) {
        this.codiDepartament = codiDepartament;
    }

    public String getUsuari() {
        return usuari;
    }

    public void setUsuari(String usuari) {
        this.usuari = usuari;
    }

    public String getContrasenya() {
        return contrasenya;
    }

    public void setContrasenya(String contrasenya) {
        this.contrasenya = contrasenya;
    }

    public boolean getActiu() {
        return actiu;
    }

    public void setActiu(boolean actiu) {
        this.actiu = actiu;
    }

    public String getNomDepartament() {
        return nomDepartament;
    }

    public void setNomDepartament(String nomDepartament) {
        this.nomDepartament = nomDepartament;
    }
}
