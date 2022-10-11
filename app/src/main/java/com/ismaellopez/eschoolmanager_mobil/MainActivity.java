package com.ismaellopez.eschoolmanager_mobil;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {

    private TextInputEditText inputUser, inputPassword;
    private Button btoInici;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inputUser = findViewById(R.id.inputUsuari);
        inputPassword = findViewById(R.id.inputPassword);
        btoInici = findViewById(R.id.btoIniciarSessio);
    }
    //metodo para el boton
    public void iniciSessio(View view){
        Toast.makeText(this, inputUser.getText(), Toast.LENGTH_SHORT).show();
    }

    public void validarDades(View view){

    }

}