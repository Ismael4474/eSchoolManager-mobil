package com.ismaellopez.eschoolmanager_mobil.clases;

import android.text.Editable;

import java.io.Serializable;

public class Login implements Serializable {

    private String user;
    private String password;

    public Login(String user, String password){
        this.user = user;
        this.password = password;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
