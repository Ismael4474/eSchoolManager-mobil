package com.ismaellopez.eschoolmanager_mobil.modelo.factura;

public class Moviments {

    private String data;
    private String servei;
    private double importBeca;
    private double importEstudiant;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getServei() {
        return servei;
    }

    public void setServei(String servei) {
        this.servei = servei;
    }

    public double getImportBeca() {
        return importBeca;
    }

    public void setImportBeca(double importBeca) {
        this.importBeca = importBeca;
    }

    public double getImportEstudiant() {
        return importEstudiant;
    }

    public void setImportEstudiant(double importEstudiant) {
        this.importEstudiant = importEstudiant;
    }
}
