package com.ismaellopez.eschoolmanager_mobil.modelo.sessio;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.ismaellopez.eschoolmanager_mobil.R;
import com.ismaellopez.eschoolmanager_mobil.controlador.Connexio;
import com.ismaellopez.eschoolmanager_mobil.modelo.PantallaPrincipal;
import com.ismaellopez.eschoolmanager_mobil.modelo.departaments.Departament;
import com.ismaellopez.eschoolmanager_mobil.modelo.empleat.Empleat;
import com.ismaellopez.eschoolmanager_mobil.modelo.empleat.FragLlistaEmpleat;
import com.ismaellopez.eschoolmanager_mobil.modelo.estudiant.Estudiant;
import com.ismaellopez.eschoolmanager_mobil.modelo.estudiant.FragAltaEstudiant;
import com.ismaellopez.eschoolmanager_mobil.modelo.estudiant.FragLlistaEstudiant;
import com.ismaellopez.eschoolmanager_mobil.modelo.servei.FragLlistaServei;
import com.ismaellopez.eschoolmanager_mobil.modelo.servei.Servei;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.ExecutionException;


public class FragAltaSessio extends Fragment {

    View view;
    EditText editTextData,editTextHora;
    Button botoAceptar;
    ImageButton botonFecha, botonHora;
    DatePicker dataPickerAltaSessio;
    Spinner spinnerEstudiant, spinnerEmpleat, spinnerServei;

    ArrayList<Estudiant> llistaEstudiants;
    ArrayList<Empleat> llistaEmpleats;
    ArrayList<Servei> llistaServeis;

    public FragAltaSessio() {
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
        view =  inflater.inflate(R.layout.fragment_frag_alta_sessio, container, false);
        editTextData = view.findViewById(R.id.editTexAltaDataSessio);
        editTextHora = view.findViewById(R.id.editTexAltaHoraSessio);
        botoAceptar = view.findViewById(R.id.buttonAltaSessioAceptar);
        botonFecha =  view.findViewById(R.id.imageButtonDateAltaSessio);
        botonHora =  view.findViewById(R.id.imageButtonHoraAltaSessio);
        dataPickerAltaSessio = view.findViewById(R.id.dataPickerAltaSessio);
        spinnerEstudiant = view.findViewById(R.id.spinnerAltaEstudiantSessio);
        spinnerEmpleat= view.findViewById(R.id.spinnerAltaEmpleatSessio);
        spinnerServei = view.findViewById(R.id.spinnerAltaServeiSessio);
        FragLlistaEstudiant fragLlistaEstudiant = new FragLlistaEstudiant();
        FragLlistaServei fragLlistaServei = new FragLlistaServei();
        FragLlistaEmpleat fragLlistaEmpleat = new FragLlistaEmpleat();
        JSONArray arrayEstudiants = null;
        JSONArray arrayServeis = null;
        JSONArray arrayEmpleats = null;
        try {
            arrayEstudiants = fragLlistaEstudiant.aconseguirLlista();
            llistaEstudiants =fragLlistaEstudiant.montarLlista(arrayEstudiants);
            arrayServeis= fragLlistaServei.aconseguirLlista();
            llistaServeis = fragLlistaServei.montarLlista(arrayServeis);
            //recuperamos un listado pero sólo de los profesores
            arrayEmpleats = fragLlistaEmpleat.aconseguirLlista("","");
            llistaEmpleats = fragLlistaEmpleat.montarLlista(arrayEmpleats);
            ArrayAdapter<Estudiant> adapterEstudiant = new ArrayAdapter<Estudiant>(getContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,llistaEstudiants);
            spinnerEstudiant.setAdapter(adapterEstudiant);
            ArrayAdapter<Servei> adapterServei = new ArrayAdapter<Servei>(getContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,llistaServeis);
            spinnerServei.setAdapter(adapterServei);
            ArrayAdapter<Empleat> adapterEmpleats = new ArrayAdapter<Empleat>(getContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,llistaEmpleats);
            spinnerEmpleat.setAdapter(adapterEmpleats);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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

        botonHora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //introduccion de la fecha de naciomiento mediante un dataPicker
                final Calendar calendar = Calendar.getInstance();
                int hora = calendar.get(Calendar.HOUR_OF_DAY);
                int minut = calendar.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        editTextHora.setText(formatarNumero(hourOfDay)+":"+formatarNumero(minute)+":"+"00");
                    }
                }, hora, minut,false);
                timePickerDialog.show();
            }
        });

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
            json.put("crida","ALTA SESSIO" );
            json.put("codiSessio", PantallaPrincipal.codiSessio);
            JSONObject jsonDades = new JSONObject();
            jsonDades.put("codiEmpleat",calcularCodiEmpleat(String.valueOf(spinnerEmpleat.getSelectedItem())));
            jsonDades.put("codiEstudiant",calcularCodiEstudiant(String.valueOf(spinnerEstudiant.getSelectedItem())));
            jsonDades.put("codiServei",calcularCodiServei(String.valueOf(spinnerServei.getSelectedItem())));
            jsonDades.put("dataIHora",editTextData.getText().toString() + " " + editTextHora.getText().toString());

            json.put("dades",jsonDades);
            //Iniciamos la conexión al servidor
            Connexio connexio = new Connexio();
            String respuestaServidor = connexio.execute(json.toString()).get();
            //          String respuestaServidor = "{\"resposta\":\"OK\"}";
            if (respuestaServidor != null) {
                JSONObject respostaServidorJson = new JSONObject(respuestaServidor);
                if (respostaServidorJson.getString("resposta") != null) {
                    if ("OK".equalsIgnoreCase(respostaServidorJson.getString("resposta"))){
                        Toast.makeText(getActivity(),"Sessió donada d'alta correctament",Toast.LENGTH_LONG).show();
                        //reiniciamos todos los camps
                        editTextHora.setText("");
                        editTextData.setText("");


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



    //metodo para formatar el numero de la hora y los minutos
    public String formatarNumero(int numero){
        String numeroFormatado = String.valueOf(numero);
        if (numeroFormatado.length()<2){
            numeroFormatado = "0"+ numeroFormatado;
        }
        return numeroFormatado;
    }
    //meteodos para encontrar los codigos a traves del nombre
    public String calcularCodiEmpleat(String nom){
        String [] nomEmpleat = nom.split("->");
        String codi = null;
        for (Empleat empleat: llistaEmpleats){
            if (empleat.getNom().equalsIgnoreCase(nomEmpleat[0])){
                codi = String.valueOf(empleat.getCodiEmpleat());
            }
        }
        return codi;
    }

    public String calcularCodiEstudiant(String nom){
        String codi = null;
        for (Estudiant estudiant: llistaEstudiants){
            if ((estudiant.getNom() + " " +estudiant.getCognoms()).equalsIgnoreCase(nom)){
                codi = String.valueOf(estudiant.getCodi());
            }
        }
        return codi;
    }

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