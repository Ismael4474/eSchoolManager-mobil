package com.ismaellopez.eschoolmanager_mobil.modelo.departaments;

import androidx.annotation.NonNull;

public class Departament {

    private int codiDepartament;
    private String nomDepartament;

    public int getCodiDepartament() {
        return codiDepartament;
    }

    public void setCodiDepartament(int codiDepartament) {
        this.codiDepartament = codiDepartament;
    }

    public String getNomDepartament() {
        return nomDepartament;
    }

    public void setNomDepartament(String nomDepartament) {
        this.nomDepartament = nomDepartament;
    }

    @NonNull
    @Override
    public String toString() {
        return nomDepartament;
    }
}
