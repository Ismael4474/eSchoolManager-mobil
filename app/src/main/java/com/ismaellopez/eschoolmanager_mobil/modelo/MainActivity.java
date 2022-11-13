package com.ismaellopez.eschoolmanager_mobil.modelo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.google.android.material.textfield.TextInputEditText;
import com.ismaellopez.eschoolmanager_mobil.R;
import com.ismaellopez.eschoolmanager_mobil.controlador.Connexio;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;


public class MainActivity extends AppCompatActivity {

    private TextInputEditText inputUser, inputPassword;
    private Button btoInici;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inputUser = findViewById(R.id.inputUsuari);
        inputPassword = findViewById(R.id.inputPassword);
        btoInici = findViewById(R.id.btoIniciarSessio);
        progressBar = findViewById(R.id.progressBar2);
    }
    //metodo para el boton
       public void iniciSessio(View view) throws JSONException, ExecutionException, InterruptedException {
        if (inputUser.length()==0){
            Toast.makeText(this, "Has d'afegir un usuari", Toast.LENGTH_LONG).show();
        }else if (inputPassword.length() == 0 ){
                Toast.makeText(this, "Has d'afegir una contrasenya", Toast.LENGTH_LONG).show();
        }else{
            progressBar.setVisibility(View.VISIBLE);
            btoInici.setActivated(false);
            //Creamos el objetos json que mandamos al servidor
            JSONObject json = new JSONObject();
            json.put("crida","LOGIN" );
            JSONObject jsonDades = new JSONObject();
            jsonDades.put("usuari",inputUser.getText().toString());
            jsonDades.put("contrasenya", inputPassword.getText().toString());
            json.put("dades",jsonDades);
            //Iniciamos la conexión al servidor
            Connexio connexio = new Connexio();
       //     String respuestaServidor = connexio.execute(json.toString()).get();
            String respuestaServidor = "{\"resposta\":\"OK\",\"dades\":{\"codiSessio\":\"123123hgsd\",\"nom\":\"Ismael Lopez\",\"nomDepartament\":\"Administracio\",\"permisos\":{\"escola\":true,\"departament\":true,\"empleat\":true,\"estudiant\":true,\"servei\":true,\"beca\":true,\"sessio\":true,\"informe\":true}}}";
            if (respuestaServidor != null) {
                JSONObject respostaServidorJson = new JSONObject(respuestaServidor);
                if (respostaServidorJson.getString("resposta") != null) {
                    if ("OK".equalsIgnoreCase(respostaServidorJson.getString("resposta"))) {
                        if (respostaServidorJson.getString("dades")!= null) {
                            accesoPrincipal(respostaServidorJson.getString("dades"));
                         }
                    }else{
                        Toast.makeText(this, respostaServidorJson.getString("missatge"), Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.INVISIBLE);
                        inputUser.setText("");
                        inputPassword.setText("");
                        inputUser.requestFocus();
                    }
                }
            }
         }

    }

    public void accesoPrincipal(String dades) throws JSONException {
        JSONObject respostaServidorDades = new JSONObject(dades);
        Intent pantallaPrincipal = new Intent(this, PantallaPrincipal.class);
        if (respostaServidorDades.getString("nom") != null
                && respostaServidorDades.getString("nomDepartament") != null
                && respostaServidorDades.getString("codiSessio") != null){
            JSONObject respostaServidorPermisos = new JSONObject(respostaServidorDades.getString("permisos"));
            if (respostaServidorPermisos != null) {
                pantallaPrincipal.putExtra("nom", respostaServidorDades.getString("nom"));
                pantallaPrincipal.putExtra("nomDepartament", respostaServidorDades.getString("nomDepartament"));
                pantallaPrincipal.putExtra("codiSessio", respostaServidorDades.getString("codiSessio"));
                pantallaPrincipal.putExtra("permisos", respostaServidorDades.getString("permisos"));
                startActivity(pantallaPrincipal);
            }
        }else{
            Toast.makeText(this,"Problemas amb la crida al servidor", Toast.LENGTH_SHORT);
            throw new RuntimeException("Problemas amb la crida al servidor.........");
        }

    }

}