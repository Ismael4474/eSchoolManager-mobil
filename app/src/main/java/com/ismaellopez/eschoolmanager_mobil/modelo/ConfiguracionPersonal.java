package com.ismaellopez.eschoolmanager_mobil.modelo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.ismaellopez.eschoolmanager_mobil.R;
import com.ismaellopez.eschoolmanager_mobil.controlador.Connexio;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class ConfiguracionPersonal extends AppCompatActivity {

    Toolbar toolbar;
    EditText editTextNom,editTextCognom,editTextDni,editTextData,editTextAdre,editTextTelef,editTextMail,editTextCodiDepart,editTextContra;
    TextView textCodiEmpleat;
    CheckBox checkBoxActiu;
    Button botoCancelar,botoAceptar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion_personal);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("eSchoolManager");
        toolbar.setSubtitle(PantallaPrincipal.nomDepartament + " -> " + PantallaPrincipal.nom);
        editTextNom = findViewById(R.id.editTexConfNom);
        editTextCognom = findViewById(R.id.editTexConfCognom);
        editTextDni = findViewById(R.id.editTexAltaEmpleatDni);
        editTextData = findViewById(R.id.editTextDate);
        editTextAdre = findViewById(R.id.editTextAltaEmpleatPostalAddress);
        editTextTelef = findViewById(R.id.editTextPhone);
        editTextMail = findViewById(R.id.editTextEmailAddress);
        editTextCodiDepart = findViewById(R.id.editTexConfCodDepart);
        editTextContra = findViewById(R.id.editTexConfContra);
        textCodiEmpleat = findViewById(R.id.textConfCodEmpleat);
        checkBoxActiu = findViewById(R.id.checkBoxActiu);
        botoCancelar = findViewById(R.id.buttonConfCancelar);
        botoAceptar = findViewById(R.id.buttonConfAceptar);

        botoCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        botoAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        try {
            cridaServidor();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void cridaServidor() throws JSONException, ExecutionException, InterruptedException {
        //Creamos el objetos json que mandamos al servidor
        JSONObject json = new JSONObject();
        json.put("crida","MODI EMPLEAT" );
        json.put("codiSessio", PantallaPrincipal.codiSessio);
        JSONObject jsonDades = new JSONObject();
        jsonDades.put("codiEmpleat","");
         json.put("dades",jsonDades);
        //Iniciamos la conexión al servidor
        Connexio connexio = new Connexio();
        String respuestaServidor = connexio.execute(json.toString()).get();
        //        String respuestaServidor = "{\"resposta\":\"OK\",\"dades\":{\"codiSessio\":\"123123hgsd\",\"nom\":\"Ismael Lopez\",\"nomDepartament\":\"Administracio\",\"permisos\":{\"escola\":true,\"departament\":true,\"empleat\":true,\"estudiant\":true,\"servei\":true,\"beca\":true,\"sessio\":true,\"informe\":true}}}";
        if (respuestaServidor != null) {
            JSONObject respostaServidorJson = new JSONObject(respuestaServidor);
            if (respostaServidorJson.getString("resposta") != null) {
                if ("OK".equalsIgnoreCase(respostaServidorJson.getString("resposta"))) {
                    if (respostaServidorJson.getString("dades")!= null) {
                         //rellenamos los checkBox con la respuest del servidor
                        JSONObject respostaJsonPermisos = new JSONObject(respostaServidorJson.getString("dades"));
                        textCodiEmpleat.setText(respostaJsonPermisos.getInt("codiEmpleat"));
                        editTextNom.setText(respostaJsonPermisos.getString("nom"));
                        editTextCognom.setText(respostaJsonPermisos.getString("cognoms"));
                        editTextDni.setText(respostaJsonPermisos.getString("dni"));
                        editTextData.setText(respostaJsonPermisos.getString("dataNaixament"));
                        editTextAdre.setText(respostaJsonPermisos.getString("adreca"));
                        editTextTelef.setText(respostaJsonPermisos.getString("telefon"));
                        editTextMail.setText(respostaJsonPermisos.getString("mail"));
                        editTextCodiDepart.setText(respostaJsonPermisos.getInt("codiDepartament"));
                        editTextContra.setText(respostaJsonPermisos.getString("contrasenya"));
                        checkBoxActiu.setChecked(respostaJsonPermisos.getBoolean("actiu"));

                    }
                }else{
                    Toast.makeText(this, respostaServidorJson.getString("missatge"), Toast.LENGTH_LONG).show();

                }
            }
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.pantalla_principal_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int opcio = item.getItemId();
        if (opcio == R.id.logout){
            try {
                logout();
            } catch (JSONException | ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public void logout() throws JSONException, ExecutionException, InterruptedException {
        JSONObject json = new JSONObject();
        json.put("crida","LOGOUT" );
        json.put("codiSessio",PantallaPrincipal.codiSessio);
        Connexio connexio = new Connexio();
        String respostaServidor=connexio.execute(json.toString()).get();
        if (respostaServidor != null) {
            JSONObject jsonRespostaCrida = new JSONObject(respostaServidor);
            if (jsonRespostaCrida.getString("resposta") != null) {
                if ("OK".equalsIgnoreCase(jsonRespostaCrida.getString("resposta"))) {
                    Intent logout = new Intent(this,MainActivity.class);
                    startActivity(logout);
                }
            }
        }

    }
}