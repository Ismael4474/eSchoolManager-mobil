package com.ismaellopez.eschoolmanager_mobil.modelo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.ismaellopez.eschoolmanager_mobil.R;
import com.ismaellopez.eschoolmanager_mobil.controlador.Connexio;
import com.ismaellopez.eschoolmanager_mobil.modelo.departaments.MenuDepartament;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;


public class PantallaPrincipal extends AppCompatActivity {

    public static String codiSessio = "";
    public static String nom = "";
    public static String nomDepartament = "";
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
        try {
            montarBotonera(getIntent().getStringExtra("permisos"),botoEmpleat,botoServei,botoBeca,botoDepartament,botoEstudiant,botoEscola,botoSessio);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        codiSessio = getIntent().getStringExtra("codiSessio");
        toolbar.setTitle("eSchoolManager");
        nom = getIntent().getStringExtra("nom");
        nomDepartament= getIntent().getStringExtra("nomDepartament");

        toolbar.setSubtitle(nomDepartament+ " -> "+nom);

    }

    private void montarBotonera(String permisos,
                                Button botoEmpleat,Button botoServei,Button botoBeca,
                                Button botoDepartament,Button botoEstudiant,Button botoEscola,
                                Button botoSessio) throws JSONException {

        JSONObject respostaServidorPermisos = new JSONObject(permisos);
        botoEmpleat.setActivated( respostaServidorPermisos.getBoolean("empleat"));
        botoEscola.setActivated(respostaServidorPermisos.getBoolean("escola"));
        botoDepartament.setActivated(respostaServidorPermisos.getBoolean("departament"));
        botoEstudiant.setActivated(respostaServidorPermisos.getBoolean("estudiant"));
        botoBeca.setActivated(respostaServidorPermisos.getBoolean("beca"));
        botoServei.setActivated(respostaServidorPermisos.getBoolean("servei"));
        botoSessio.setActivated(respostaServidorPermisos.getBoolean("sessio"));

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
    //Acces en menu per donar de alta/baixa/modificar departament
    public void accesMenuDepartament(View view){
        Intent menuDepartament= new Intent(this, MenuDepartament.class);
        startActivity(menuDepartament);
    }
}