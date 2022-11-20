package com.ismaellopez.eschoolmanager_mobil.modelo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.ismaellopez.eschoolmanager_mobil.R;
import com.ismaellopez.eschoolmanager_mobil.controlador.Connexio;
import com.ismaellopez.eschoolmanager_mobil.modelo.departaments.MenuDepartament;
import com.ismaellopez.eschoolmanager_mobil.modelo.empleat.MenuEmpleat;
import com.ismaellopez.eschoolmanager_mobil.modelo.servei.MenuServei;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;


public class PantallaPrincipal extends AppCompatActivity {

    public static String codiSessio = "";
    public static String nom = "";
    public static String nomDepartament = "";
    Toolbar toolbar;
    Button botoEmpleat,botoServei,botoBeca,botoDepartament,botoEstudiant,botoEscola,botoSessio,botoInforme;    @SuppressLint("RestrictedApi")
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
        botoInforme=findViewById(R.id.buttonInforme);
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
        if (respostaServidorPermisos.getBoolean("empleat")){
            botoEmpleat.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.purple_700)));
        }
        if (respostaServidorPermisos.getBoolean("escola")){
            botoEscola.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.purple_700)));
        }

        if (respostaServidorPermisos.getBoolean("departament")){
            botoDepartament.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.purple_700)));
        }

        if (respostaServidorPermisos.getBoolean("estudiant")){
            botoEstudiant.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.purple_700)));
        }

        if (respostaServidorPermisos.getBoolean("beca")){
            botoBeca.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.purple_700)));
        }

        if (respostaServidorPermisos.getBoolean("servei")){
            botoServei.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.purple_700)));
        }
        if (respostaServidorPermisos.getBoolean("sessio")){
            botoSessio.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.purple_700)));
        }
        if (respostaServidorPermisos.getBoolean("informe")){
            botoInforme.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.purple_700)));
        }
        botoEmpleat.setClickable( respostaServidorPermisos.getBoolean("empleat"));
        botoEscola.setClickable(respostaServidorPermisos.getBoolean("escola"));
        botoDepartament.setClickable(respostaServidorPermisos.getBoolean("departament"));
        botoEstudiant.setClickable(respostaServidorPermisos.getBoolean("estudiant"));
        botoBeca.setClickable(respostaServidorPermisos.getBoolean("beca"));
        botoServei.setClickable(respostaServidorPermisos.getBoolean("servei"));
        botoSessio.setClickable(respostaServidorPermisos.getBoolean("sessio"));
        botoInforme.setClickable((respostaServidorPermisos.getBoolean("informe")));
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
        }else if (opcio == R.id.configurar){
            Intent configurar = new Intent(this,ConfiguracionPersonal.class);
            startActivity(configurar);
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
    //Acces en menu per donar de alta/baixa/modificar serveis
    public void accesMenuServeis(View view){
        Intent menuServeis= new Intent(this, MenuServei.class);
        startActivity(menuServeis);
    }
    //Acces en menu per donar de alta/baixa/modificar Empleat
    public void accesMenuEmpleats(View view){
        Intent menuEmpleat= new Intent(this, MenuEmpleat.class);
        startActivity(menuEmpleat);
    }

}