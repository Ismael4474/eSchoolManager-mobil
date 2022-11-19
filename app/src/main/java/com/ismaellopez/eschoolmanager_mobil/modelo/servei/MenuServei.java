package com.ismaellopez.eschoolmanager_mobil.modelo.servei;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.ismaellopez.eschoolmanager_mobil.R;
import com.ismaellopez.eschoolmanager_mobil.controlador.Connexio;
import com.ismaellopez.eschoolmanager_mobil.modelo.ConfiguracionPersonal;
import com.ismaellopez.eschoolmanager_mobil.modelo.MainActivity;
import com.ismaellopez.eschoolmanager_mobil.modelo.PantallaPrincipal;
import com.ismaellopez.eschoolmanager_mobil.modelo.TituloFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class MenuServei extends AppCompatActivity {

    Toolbar toolbar;
    Button botonAlta,botonModi,botonBaixa, botonLlistar, botonTornar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_servei);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("eSchoolManager");
        toolbar.setSubtitle(PantallaPrincipal.nomDepartament + " -> " + PantallaPrincipal.nom);
        Bundle datos = new Bundle();
        botonAlta =findViewById(R.id.buttonAltaServei);
        botonModi = findViewById(R.id.buttonModiServei);
        botonBaixa = findViewById(R.id.buttonBaixaServei);
        botonLlistar = findViewById(R.id.buttonServeiLlistar);
        botonTornar = findViewById(R.id.buttonServeiTornar);
        datos.putString("titulo", "Gestió Serveis");
        TituloFragment tituloFragment = new TituloFragment();
        tituloFragment.setArguments(datos);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.frameLayoutServei, tituloFragment)
                .commit();

        botonModi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment( new FragModiServei());
            }
        });

        botonAlta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                replaceFragment( new FragAltaServei());
            }
        });

        botonBaixa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment( new FragBaixaServei());
            }
        });

        botonTornar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        botonLlistar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new FragLlistaServei());
            }
        });

    }

    private void replaceFragment( Fragment fragment){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frameLayoutServei, fragment)
                .commit();
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
            Intent configurar = new Intent(this, ConfiguracionPersonal.class);
            startActivity(configurar);
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
                    Intent logout = new Intent(this, MainActivity.class);
                    startActivity(logout);
                }
            }
        }

    }
}