package com.ismaellopez.eschoolmanager_mobil.modelo.beca;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.ismaellopez.eschoolmanager_mobil.R;
import com.ismaellopez.eschoolmanager_mobil.controlador.Connexio;
import com.ismaellopez.eschoolmanager_mobil.modelo.PantallaPrincipal;
import com.ismaellopez.eschoolmanager_mobil.modelo.departaments.Departament;
import com.ismaellopez.eschoolmanager_mobil.modelo.estudiant.Estudiant;
import com.ismaellopez.eschoolmanager_mobil.modelo.estudiant.FragLlistaEstudiant;
import com.ismaellopez.eschoolmanager_mobil.modelo.servei.FragLlistaServei;
import com.ismaellopez.eschoolmanager_mobil.modelo.servei.Servei;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class FragAltaBeca extends Fragment {

    ArrayList<Estudiant> llistaEstudiants;
    ArrayList<Servei>llistaServeis;
    View view;
    EditText editTextAdjudicant,editTextImportInicial;
    Spinner spinnerNomEstudiant,spinnerNomServei;
    Button botoAltaBeca;

    public FragAltaBeca() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_frag_alta_beca, container, false);
        editTextAdjudicant = view.findViewById(R.id.editTexAltaBecaNom);
        editTextImportInicial = view.findViewById(R.id.editTexAltaBecatImport);
        spinnerNomEstudiant =  view.findViewById(R.id.spinnerAltaBecaEstudiant);
        spinnerNomServei =  view.findViewById(R.id.spinnerAltaBecaServei);
        botoAltaBeca = view.findViewById(R.id.buttonAltaBecaAceptar);
        FragLlistaEstudiant fragLlistaEstudiant = new FragLlistaEstudiant();
        FragLlistaServei fragLlistaServei = new FragLlistaServei();
        try {
            JSONArray arrayEstudiants = fragLlistaEstudiant.aconseguirLlista();
            llistaEstudiants =fragLlistaEstudiant.montarLlista(arrayEstudiants);
            JSONArray arrayServeis = fragLlistaServei.aconseguirLlista();
            llistaServeis = fragLlistaServei.montarLlista(arrayServeis);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ArrayAdapter<Estudiant> adapterEstudiants = new ArrayAdapter<Estudiant>(getContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,llistaEstudiants);
        spinnerNomEstudiant.setAdapter(adapterEstudiants);
        ArrayAdapter<Servei> adapterServei = new ArrayAdapter<Servei>(getContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,llistaServeis);
        spinnerNomServei.setAdapter(adapterServei);
        botoAltaBeca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                donarAlta(view);
            }
        });
        return view;
    }

    public void donarAlta(View view){
        //Creamos el objetos json que mandamos al servidor
        JSONObject json = new JSONObject();

        try {
            json.put("crida","ALTA BECA" );
            json.put("codiSessio", PantallaPrincipal.codiSessio);
            JSONObject jsonDades = new JSONObject();
            jsonDades.put("adjudicant",editTextAdjudicant.getText().toString());
            jsonDades.put("importInicial",editTextImportInicial.getText().toString());
            jsonDades.put("codiEstudiant",calcularCodiEstudiant(String.valueOf(spinnerNomEstudiant.getSelectedItem())));
            jsonDades.put("codiServei",calcularCodiServei(String.valueOf( spinnerNomServei.getSelectedItem())));

            json.put("dades",jsonDades);
            //Iniciamos la conexión al servidor
            Connexio connexio = new Connexio();
            String respuestaServidor = connexio.execute(json.toString()).get();
            //          String respuestaServidor = "{\"resposta\":\"OK\"}";
            if (respuestaServidor != null) {
                JSONObject respostaServidorJson = new JSONObject(respuestaServidor);
                if (respostaServidorJson.getString("resposta") != null) {
                    if ("OK".equalsIgnoreCase(respostaServidorJson.getString("resposta"))){
                        Toast.makeText(getActivity(),"Beca donada d'alta correctament",Toast.LENGTH_LONG).show();
                        //reiniciamos todos los camps
                        editTextAdjudicant.setText("");
                        editTextImportInicial.setText("");


                    }else{
                        Toast.makeText(getActivity(), respostaServidorJson.getString("missatge"), Toast.LENGTH_LONG).show();
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
    //metodo para buscar el codigo del estudiante a traves del nombre
    public String calcularCodiEstudiant(String nom){
        String codi = null;
        for (Estudiant estudiant: llistaEstudiants){
            if (estudiant.getNom().equalsIgnoreCase(nom)){
                codi = String.valueOf(estudiant.getCodi());
            }
        }
        return codi;
    }

     //metodo para buscar el codigo del servicio a traves del nom
    public String calcularCodiServei(String nom){
        String codi = null;
        for (Servei servei: llistaServeis){
            if (servei.getNom().equalsIgnoreCase(nom)){
                codi = String.valueOf(servei.getCodi());
            }
        }
        return codi;
    }
}