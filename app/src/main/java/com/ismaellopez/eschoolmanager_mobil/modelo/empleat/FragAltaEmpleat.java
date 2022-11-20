package com.ismaellopez.eschoolmanager_mobil.modelo.empleat;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.ScaleAnimation;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.ismaellopez.eschoolmanager_mobil.R;
import com.ismaellopez.eschoolmanager_mobil.controlador.Connexio;
import com.ismaellopez.eschoolmanager_mobil.modelo.PantallaPrincipal;
import com.ismaellopez.eschoolmanager_mobil.modelo.departaments.Departament;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.concurrent.ExecutionException;


public class FragAltaEmpleat extends Fragment {

    ArrayList<Departament> llistaDepartaments;
    View view;
    EditText editTextNom,editTextCognom,editTextDni,editTextData,editTextAdre,editTextTelef,editTextMail,editTextUsuari,editTextContra;
    Spinner spinnerNomDepartament;
    Button botoAceptar;
    ImageButton botonFecha;
    DatePicker dataPickerAltaEmpl;

    public FragAltaEmpleat() {
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
        view = inflater.inflate(R.layout.fragment_frag_alta_empleat, container, false);
        editTextNom = view.findViewById(R.id.editTexAltaEmpleatNom);
        editTextCognom = view.findViewById(R.id.editTexAltaEmpleatCognom);
        editTextDni = view.findViewById(R.id.editTexAltaEmpleatDni);
        editTextData = view.findViewById(R.id.editTextAltaEmpleatDate);
        editTextAdre = view.findViewById(R.id.editTextAltaEmpleatPostalAddress);
        editTextTelef = view.findViewById(R.id.editTextAltaEmpleatPhone);
        editTextMail = view.findViewById(R.id.editTextAltaEmpleatEmailAddress);
        spinnerNomDepartament=view.findViewById(R.id.spinnerAltaEmpleat);
        editTextContra = view.findViewById(R.id.editTexAltaEmpleatContra);
        editTextUsuari= view.findViewById(R.id.editTexAltaEmpleatUsuari);
        botoAceptar = view.findViewById(R.id.buttonAltaEmpleatAceptar);
        dataPickerAltaEmpl = view.findViewById(R.id.dataPickerAltaEmpl);
        botonFecha = view.findViewById(R.id.imageButtonDateAltaEmpl);
        botonFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //introduccion de la fecha de naciomiento mediante un dataPicker
                final Calendar calendar = Calendar.getInstance();
                int dia = calendar.get(Calendar.DAY_OF_MONTH);
                int mes = calendar.get(Calendar.MONTH);
                int ano = calendar.get(Calendar.YEAR);
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month+1;
                        editTextData.setText(year+"-"+month+"-"+dayOfMonth);
                    }
                },dia,mes,ano);
                datePickerDialog.show();

            }
        });
        //omplim la llsita dels departament per utilitzar en el spinner
        try {
            omplirSpinnerDepartaments();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ArrayAdapter<Departament> adapter = new ArrayAdapter<Departament>(getContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,llistaDepartaments);
        spinnerNomDepartament.setAdapter(adapter);
        botoAceptar.setOnClickListener(new View.OnClickListener() {
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
            json.put("crida","ALTA EMPLEAT" );
            json.put("codiSessio", PantallaPrincipal.codiSessio);
            JSONObject jsonDades = new JSONObject();
            jsonDades.put("nom",editTextNom.getText().toString());
            jsonDades.put("cognoms",editTextCognom.getText().toString());
            jsonDades.put("dni",editTextDni.getText().toString());
            jsonDades.put("dataNaixement",(editTextData.getText().toString()));
            jsonDades.put("adreca",editTextAdre.getText().toString());
            jsonDades.put("telefon",editTextTelef.getText().toString());
            jsonDades.put("email",editTextMail.getText().toString());
            jsonDades.put("codiDepartament",calcularCodiDepartament(spinnerNomDepartament.getSelectedItem().toString()));
            jsonDades.put("usuari",editTextUsuari.getText().toString());
            jsonDades.put("contrasenya",editTextContra.getText().toString());
            json.put("dades",jsonDades);
            //Iniciamos la conexión al servidor
            Connexio connexio = new Connexio();
            String respuestaServidor = connexio.execute(json.toString()).get();
            //          String respuestaServidor = "{\"resposta\":\"OK\"}";
            if (respuestaServidor != null) {
                JSONObject respostaServidorJson = new JSONObject(respuestaServidor);
                if (respostaServidorJson.getString("resposta") != null) {
                    if ("OK".equalsIgnoreCase(respostaServidorJson.getString("resposta"))){
                        Toast.makeText(getActivity(),"Empleat donat d'alta correctament",Toast.LENGTH_LONG).show();
                        //reiniciamos todos los camps
                        editTextNom.setText("");
                        editTextCognom.setText("");
                        editTextDni.setText("");
                        editTextData.setText("");
                        editTextAdre.setText("");
                        editTextTelef.setText("");
                        editTextMail.setText("");
                        editTextContra.setText("");
                        editTextUsuari.setText("");

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

    public ArrayList<Departament> omplirSpinnerDepartaments() throws JSONException, ExecutionException, InterruptedException {
        llistaDepartaments= new ArrayList<Departament>();

        JSONArray arrayDepartament = new JSONArray();
        JSONObject json = new JSONObject();
        json.put("crida","LLISTA DEPARTAMENTS" );
        json.put("codiSessio", PantallaPrincipal.codiSessio);
        JSONObject jsonDades = new JSONObject();
        jsonDades.put("ordre","");
        jsonDades.put("camp","");
        json.put("dades",jsonDades);
        //Iniciamos la conexión al servidor
        Connexio connexio = new Connexio();
        String respuestaServidor = connexio.execute(json.toString()).get();
        if (respuestaServidor != null) {
            JSONObject respostaServidorJson = new JSONObject(respuestaServidor);
            if (respostaServidorJson.getString("resposta") != null) {
                if ("OK".equalsIgnoreCase(respostaServidorJson.getString("resposta"))){
                    if (respostaServidorJson.getString("dades")!= null) {
                        JSONObject jsonDadesResposta = new JSONObject(respostaServidorJson.getString("dades"));
                        Iterator<String> x = jsonDadesResposta.keys();
                        while (x.hasNext()){
                            String key= (String)x.next();
                            arrayDepartament.put(jsonDadesResposta.get(key));
                        }
                        for (int i=0;i<arrayDepartament.length();i++){
                            JSONObject jsonDepartament = arrayDepartament.getJSONObject(i);
                            Departament departament = new Departament();
                            departament.setCodiDepartament(jsonDepartament.getInt("codiDepartament"));
                            departament.setNomDepartament(jsonDepartament.getString("nomDepartament"));
                            llistaDepartaments.add(departament);
                        }
                    }
                }else{
                    Toast.makeText(getActivity(), respostaServidorJson.getString("missatge"), Toast.LENGTH_LONG).show();
                }
            }
        }
        return llistaDepartaments;
    }
    public String calcularCodiDepartament(String nom){
        String codi = null;
        for (Departament departament: llistaDepartaments){
            if (departament.getNomDepartament().equalsIgnoreCase(nom)){
                codi = String.valueOf(departament.getCodiDepartament());
            }
        }
        return codi;
    }
}