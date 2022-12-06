package com.ismaellopez.eschoolmanager_mobil.modelo.beca;

public class Beca {

    private int codi;
    private String adjudicant;
    private double importInicial;
    private double importRestant;
    private String nomEstudiant;
    private String cognomEstudiant;
    private String nomServei;
    private boolean finalitzada;

    public int getCodi() {
        return codi;
    }

    public void setCodi(int codi) {
        this.codi = codi;
    }

    public String getAdjudicant() {
        return adjudicant;
    }

    public void setAdjudicant(String adjudicant) {
        this.adjudicant = adjudicant;
    }

    public double getImportInicial() {
        return importInicial;
    }

    public void setImportInicial(double importInicial) {
        this.importInicial = importInicial;
    }

    public double getImportRestant() {
        return importRestant;
    }

    public void setImportRestant(double importRestant) {
        this.importRestant = importRestant;
    }

    public String getNomEstudiant() {
        return nomEstudiant;
    }

    public void setNomEstudiant(String nomEstudiant) {
        this.nomEstudiant = nomEstudiant;
    }

    public String getCognomEstudiant() {
        return cognomEstudiant;
    }

    public void setCognomEstudiant(String cognomEstudiant) {
        this.cognomEstudiant = cognomEstudiant;
    }

    public String getNomServei() {
        return nomServei;
    }

    public void setNomServei(String nomServei) {
        this.nomServei = nomServei;
    }

    public boolean getFinalitzada() {
        return finalitzada;
    }

    public void setFinalitzada(boolean finalitzada) {
        this.finalitzada = finalitzada;
    }

}
