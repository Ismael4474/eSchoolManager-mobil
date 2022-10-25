package com.ismaellopez.eschoolmanager_mobil.modelo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.ismaellopez.eschoolmanager_mobil.R;
import com.ismaellopez.eschoolmanager_mobil.controlador.Connexio;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;


public class PantallaPrincipal extends AppCompatActivity {

    public static String codiSessio = "";
    Toolbar toolbar;
    Button botoEmpleat,botoServei,botoBeca,botoDepartament,botoEstudiant,botoEscola,botoSessio;    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_principal);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        botoEmpleat = findViewById(R.id.buttonEmpleat);
        botoDepartament =findViewById(R.id.buttonDepartament);
        botoServei =findViewById(R.id.buttonServei);
        botoBeca =findViewById(R.id.buttonBeca);
        botoEstudiant=findViewById(R.id.buttonEstudiant);
        botoEscola=findViewById(R.id.buttonEscola);
        botoSessio =findViewById(R.id.buttonSessio);
        montarBotonera(getIntent().getStringExtra("codiDepartament"),botoEmpleat,botoServei,botoBeca,botoDepartament,botoEstudiant,botoEscola,botoSessio);

        codiSessio = getIntent().getStringExtra("codiSessio");
        toolbar.setTitle("eSchoolManager");
        String nom = getIntent().getStringExtra("nom");
        String departament = nomDepartement(getIntent().getStringExtra("codiDepartament"));

        toolbar.setSubtitle(departament+ " -> "+nom);

    }

    private void montarBotonera(String codiDepartament,
                                Button botoEmpleat,Button botoServei,Button botoBeca,
                                Button botoDepartament,Button botoEstudiant,Button botoEscola,
                                Button botoSessio) {
       switch (codiDepartament){
            case "1":
                break;
            case "2":
                break;
            case "3":
                botoEscola.setActivated(false);
                botoEmpleat.setActivated(false);
                botoBeca.setActivated(false);
                botoDepartament.setActivated(false);
                botoEstudiant.setActivated(false);
                botoServei.setActivated(false);
                botoSessio.setActivated(false);
                break;

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

    //metodo para sacar el nombre del departamento a traves del codigo
    private String nomDepartement(String codiDepartament){
        String nomDepartament = "";
        switch (codiDepartament){
            case "1":
               nomDepartament = "Administrador";
               break;

            case "2":
                nomDepartament = "Administratiu";
                break;

            case "3":
                nomDepartament = "Docent";
                break;
            default:
                break;
        }
        return nomDepartament;
    }

    public void logout() throws JSONException, ExecutionException, InterruptedException {
        JSONObject json = new JSONObject();
        json.put("crida","LOGOUT" );
        json.put("codiSessio",codiSessio);
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