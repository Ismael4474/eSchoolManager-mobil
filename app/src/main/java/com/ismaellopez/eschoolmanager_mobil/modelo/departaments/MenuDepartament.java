package com.ismaellopez.eschoolmanager_mobil.modelo.departaments;

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
import com.ismaellopez.eschoolmanager_mobil.modelo.MainActivity;
import com.ismaellopez.eschoolmanager_mobil.modelo.PantallaPrincipal;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class MenuDepartament extends AppCompatActivity {

    Toolbar toolbar;
    Button botonAlta,botonModi,botonBaixa, botonLlistar, botonTornar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_departament);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("eSchoolManager");
        toolbar.setSubtitle(PantallaPrincipal.nomDepartament + " -> " + PantallaPrincipal.nom);
        Bundle datos = new Bundle();
        botonAlta =findViewById(R.id.buttonAltaDepart);
        botonModi = findViewById(R.id.buttonModiDepart);
        botonBaixa = findViewById(R.id.buttonBaixaDepart);
        botonLlistar = findViewById(R.id.buttonDepartLlistar);
        botonTornar = findViewById(R.id.buttonDepartTornar);
        datos.putString("titulo", "Departament");
        TituloFragment tituloFragment = new TituloFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.frameLayoutDepart, tituloFragment)
                .commit();

        botonModi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment( new FragModiDepart());
            }
        });

        botonAlta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                replaceFragment( new FragAltaDepart());
            }
        });

        botonBaixa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment( new FragBaixaDepart());
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
                replaceFragment(new FragLlistaDepart());
            }
        });

    }

    private void replaceFragment( Fragment fragment){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frameLayoutDepart, fragment)
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