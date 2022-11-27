package com.ismaellopez.eschoolmanager_mobil.modelo.escola;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ismaellopez.eschoolmanager_mobil.R;
import com.ismaellopez.eschoolmanager_mobil.controlador.Connexio;
import com.ismaellopez.eschoolmanager_mobil.modelo.PantallaPrincipal;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class ModiEscola extends AppCompatActivity {

    Toolbar toolbar;
    EditText editTextNom,editTextAdreca,editTextTel;
    Button botoModificar,botoTornar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modi_escola);
        toolbar = findViewById(R.id.toolbarEscola);
        setSupportActionBar(toolbar);
        toolbar.setTitle("eSchoolManager");
        toolbar.setSubtitle(PantallaPrincipal.nomDepartament + " -> " + PantallaPrincipal.nom);
        editTextNom = findViewById(R.id.editTextTextNomEscola);
        editTextAdreca = findViewById(R.id.editTextTextAdrecaEscola);
        editTextTel = findViewById(R.id.editTextTextTelefonEscola);
        consultaEscola();
        botoModificar = findViewById(R.id.buttonAltaNovaEscola);
        botoTornar = findViewById(R.id.buttonTornarEscola);
        botoTornar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        botoModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modificarEscola(v);
            }
        });

    }

    public void consultaEscola(){
        //Creamos el objetos json que mandamos al servidor
        JSONObject json = new JSONObject();
        try {
            json.put("crida","CONSULTA ESCOLA" );
            json.put("codiSessio", PantallaPrincipal.codiSessio);
            JSONObject jsonDades = new JSONObject();
            jsonDades.put("codiEscola","");
            json.put("dades",jsonDades);
            //Iniciamos la conexión al servidor
            Connexio connexio = new Connexio();
            String respuestaServidor = connexio.execute(json.toString()).get();
            //    String respuestaServidor = "{\"resposta\":\"OK\"}";
            if (respuestaServidor != null) {
                JSONObject respostaServidorJson = new JSONObject(respuestaServidor);
                if (respostaServidorJson.getString("resposta") != null) {
                    if ("OK".equalsIgnoreCase(respostaServidorJson.getString("resposta"))){
                        JSONObject respostaServidorJDades= new JSONObject(respostaServidorJson.getString("dades"));
                        if (respostaServidorJDades != null){
                            editTextNom.setText(respostaServidorJDades.getString("nomEscola"));
                            editTextAdreca.setText(respostaServidorJDades.getString("adreca"));
                            editTextTel.setText(respostaServidorJDades.getString("telefon"));
                        }



                    }else{
                        Toast.makeText(this, respostaServidorJson.getString("missatge"), Toast.LENGTH_LONG).show();
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void modificarEscola(View view){
        //Creamos el objetos json que mandamos al servidor
        JSONObject json = new JSONObject();
        try {
            json.put("crida","MODI ESCOLA" );
            json.put("codiSessio", PantallaPrincipal.codiSessio);
            JSONObject jsonDades = new JSONObject();
            jsonDades.put("nomEscola",editTextNom.getText().toString());
            jsonDades.put("adreca",editTextAdreca.getText().toString());
            jsonDades.put("telefon",editTextTel.getText().toString());
            json.put("dades",jsonDades);
            //Iniciamos la conexión al servidor
            Connexio connexio = new Connexio();
            String respuestaServidor = connexio.execute(json.toString()).get();
            //        String respuestaServidor = "{\"resposta\":\"OK\"}";
            if (respuestaServidor != null) {
                JSONObject respostaServidorJson = new JSONObject(respuestaServidor);
                if (respostaServidorJson.getString("resposta") != null) {
                    if ("OK".equalsIgnoreCase(respostaServidorJson.getString("resposta"))){
                        Toast.makeText(this,"Servei modificat correctament",Toast.LENGTH_LONG).show();
                            editTextNom.requestFocus();
                    }else{
                        Toast.makeText(this, respostaServidorJson.getString("missatge"), Toast.LENGTH_LONG).show();
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}