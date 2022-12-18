package com.ismaellopez.eschoolmanager_mobil.modelo.factura;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.ismaellopez.eschoolmanager_mobil.R;
import com.ismaellopez.eschoolmanager_mobil.controlador.Connexio;
import com.ismaellopez.eschoolmanager_mobil.modelo.ConfiguracionPersonal;
import com.ismaellopez.eschoolmanager_mobil.modelo.MainActivity;
import com.ismaellopez.eschoolmanager_mobil.modelo.PantallaPrincipal;
import com.ismaellopez.eschoolmanager_mobil.modelo.estudiant.Estudiant;
import com.ismaellopez.eschoolmanager_mobil.modelo.estudiant.FragLlistaEstudiant;
import com.ismaellopez.eschoolmanager_mobil.modelo.estudiant.FragModiEstudiantResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class Factura extends AppCompatActivity {

    Toolbar toolbar;
    Spinner spinnerEstudiant, spinnerMes;
    ArrayList<Estudiant> llistaEstudiants;
    Button buttonFacturaAceptar,buttonFacturaTornar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_factura);
        toolbar = findViewById(R.id.toolbarFactura);
        setSupportActionBar(toolbar);
        toolbar.setTitle("eSchoolManager");
        toolbar.setSubtitle(PantallaPrincipal.nomDepartament + " -> " + PantallaPrincipal.nom);
        buttonFacturaAceptar = findViewById(R.id.buttonFacturaAceptar);
        buttonFacturaTornar = findViewById(R.id.buttonFacturaTornar);
        spinnerEstudiant = findViewById(R.id.spinnerEstudiant);
        spinnerMes = findViewById(R.id.spinnerFacturaMes);
        FragLlistaEstudiant fragLlistaEstudiant = new FragLlistaEstudiant();

        JSONArray arrayEstudiants = null;
        try {
            arrayEstudiants = fragLlistaEstudiant.aconseguirLlista();
            llistaEstudiants =fragLlistaEstudiant.montarLlista(arrayEstudiants);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String [] meses = {"Gener", "Febrer", "Març", "Abril", "Maig", "Juny", "Juliol", "Agost", "Septembre", "Octubre", "Novembre", "Desembre"};
        ArrayAdapter<Estudiant> adapterEstudiants = new ArrayAdapter<Estudiant>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,llistaEstudiants);
        spinnerEstudiant.setAdapter(adapterEstudiants);
        ArrayAdapter<String> adapterMes= new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,meses);
        spinnerMes.setAdapter(adapterMes);

        buttonFacturaTornar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        buttonFacturaAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              String respostaServidor = recuperarMovimientos();
              if (respostaServidor != null) {
                  Bundle bundle = new Bundle();
                  bundle.putString("dadesResposta", respostaServidor);
                  FragFactura fragFactura = new FragFactura();
                  fragFactura.setArguments(bundle);
                  FragmentManager fm = getSupportFragmentManager();
                  fm.beginTransaction()
                          .replace(R.id.frameLayoutFactura, fragFactura)
                          .commit();
              }
            }
        });
    }

    //metodo para buscar el codigo del estudiante a traves del nombre
    public String calcularCodiEstudiant(String nom){
        String codi = null;
        for (Estudiant estudiant: llistaEstudiants){
            if ((estudiant.getNom()+" " +estudiant.getCognoms()).equalsIgnoreCase(nom)){
                codi = String.valueOf(estudiant.getCodi());
            }
        }
        return codi;
    }


    public String recuperarMovimientos(){
        //Creamos el objetos json que mandamos al servidor
        JSONObject json = new JSONObject();
        try {
            json.put("crida","GENERA FACTURA" );
            json.put("codiSessio", PantallaPrincipal.codiSessio);
            JSONObject jsonDades = new JSONObject();
            jsonDades.put("codiEstudiant",calcularCodiEstudiant(String.valueOf(spinnerEstudiant.getSelectedItem())));
            jsonDades.put("mes",spinnerMes.getSelectedItemPosition()+1);
            json.put("dades",jsonDades);
            //Iniciamos la conexión al servidor
            Connexio connexio = new Connexio();
            String respuestaServidor = connexio.execute(json.toString()).get();
      //      String respuestaServidor = "{\"resposta\":\"OK\",\"dades\": {\"codiFactura\":\"123123\",\"nomEstudiant\":\"Ismael\",\"cognomsEstudiant\":\"Lopez Escude\",\"pagat\":true,\"sessions\":{\"0\":{\"dataIhora\":\"2022-09-10 12:30\",\"nomServei\":\"Psicologia\",\"importBeca\":10,\"importEstudiant\":20 },\"1\":{\"dataIhora\":\"2022-09-10 12:30\",\"nomServei\":\"pedagogia\",\"importBeca\":20,\"importEstudiant\":40 }}}}";
            if (respuestaServidor != null) {
                JSONObject respostaServidorJson = new JSONObject(respuestaServidor);
                if (respostaServidorJson.getString("resposta") != null) {
                    if ("OK".equalsIgnoreCase(respostaServidorJson.getString("resposta"))){
                        return respuestaServidor;

                    }else{
                        Toast.makeText(this, respostaServidorJson.getString("missatge"), Toast.LENGTH_LONG).show();
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
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