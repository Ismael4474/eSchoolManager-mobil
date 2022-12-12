package com.ismaellopez.eschoolmanager_mobil.modelo.sessio;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

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
import com.ismaellopez.eschoolmanager_mobil.modelo.empleat.Empleat;
import com.ismaellopez.eschoolmanager_mobil.modelo.empleat.FragLlistaEmpleat;
import com.ismaellopez.eschoolmanager_mobil.modelo.estudiant.Estudiant;
import com.ismaellopez.eschoolmanager_mobil.modelo.estudiant.FragLlistaEstudiant;
import com.ismaellopez.eschoolmanager_mobil.modelo.servei.FragLlistaServei;
import com.ismaellopez.eschoolmanager_mobil.modelo.servei.Servei;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.ExecutionException;


public class FragModiSessioResult extends Fragment {

    View view;
    EditText editTextData,editTextHora,editTextCodi;
    Button botoAceptar;
    ImageButton botonFecha, botonHora;
    DatePicker dataPickerAltaSessio;
    Spinner spinnerEstudiant, spinnerEmpleat, spinnerServei;

    ArrayList<Estudiant> llistaEstudiants;
    ArrayList<Empleat> llistaEmpleats;
    ArrayList<Servei> llistaServeis;

    public FragModiSessioResult() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getParentFragmentManager().setFragmentResultListener("resposta", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle bundle) {
                String resposta = bundle.getString("dadesResposta");
                if (resposta != null) {
                    JSONObject respostaJson = null;
                    try {
                        respostaJson = new JSONObject(resposta);

                        if (respostaJson.getString("resposta") != null) {
                            if ("OK".equalsIgnoreCase(respostaJson.getString("resposta"))) {
                                if (respostaJson.getString("dades")!= null) {
                                    JSONObject respostaJsonDades = new JSONObject(respostaJson.getString("dades"));
                                    //rellenamos el editText con la respuesta del servidor
                                    String[] dataIHora = respostaJsonDades.getString("dataIHora").split(" ");
                                    editTextCodi.setText(respostaJsonDades.getString("codiSessio"));
                                    editTextData.setText(dataIHora[0]);
                                    editTextHora.setText(dataIHora[1]);
                                    FragLlistaEstudiant fragLlistaEstudiant = new FragLlistaEstudiant();
                                    FragLlistaServei fragLlistaServei = new FragLlistaServei();
                                    FragLlistaEmpleat fragLlistaEmpleat = new FragLlistaEmpleat();
                                    JSONArray arrayEstudiants = null;
                                    JSONArray arrayServeis = null;
                                    JSONArray arrayEmpleats = null;
                                    try {
                                        //rellenasmos los spinners con los valores
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
                                }
                            }else{
                                Toast.makeText(getActivity(), respostaJson.getString("missatge"), Toast.LENGTH_LONG).show();

                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_frag_modi_sessio_result, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editTextCodi = view.findViewById(R.id.editTexModiCodiSessio);
        editTextData = view.findViewById(R.id.editTexModiDataSessio);
        editTextHora = view.findViewById(R.id.editTexModiHoraSessio);
        spinnerEmpleat = view.findViewById(R.id.spinnerModiEmpleatSessio);
        spinnerEstudiant = view.findViewById(R.id.spinnerModiEstudiantSessio);
        spinnerServei = view.findViewById(R.id.spinnerModiServeiSessio);
        botoAceptar = view.findViewById(R.id.buttonModiSessioAceptar);
        botonFecha =  view.findViewById(R.id.imageButtonDateModiSessio);
        botonHora =  view.findViewById(R.id.imageButtonHoraModiSessio);
        dataPickerAltaSessio = view.findViewById(R.id.dataPickerModiSessio);
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
                        FragAltaSessio fragAlta = new FragAltaSessio();
                        editTextHora.setText(fragAlta.formatarNumero(hourOfDay)+":"+fragAlta.formatarNumero(minute)+":"+"00");
                    }
                }, hora, minut,false);
                timePickerDialog.show();
            }
        });
        botoAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modifiSessio();
            }
        });
    }

    public void modifiSessio(){
        //Creamos el objetos json que mandamos al servidor
        JSONObject json = new JSONObject();

        try {
            json.put("crida","MODI SESSIO" );
            json.put("codiSessio", PantallaPrincipal.codiSessio);
            JSONObject jsonDades = new JSONObject();
            jsonDades.put("codiSessio",editTextCodi.getText().toString());
            jsonDades.put("codiEmpleat",calcularCodiEmpleat(String.valueOf(spinnerEmpleat.getSelectedItem())));
            jsonDades.put("codiEstudiant",calcularCodiEstudiant(String.valueOf(spinnerEstudiant.getSelectedItem())));
            jsonDades.put("codiServei",calcularCodiServei(String.valueOf(spinnerServei.getSelectedItem())));
            jsonDades.put("dataIHora",editTextData.getText().toString() + " " + editTextHora.getText().toString());
            json.put("dades",jsonDades);
            //Iniciamos la conexión al servidor
            Connexio connexio = new Connexio();
            String respuestaServidor = connexio.execute(json.toString()).get();
            //        String respuestaServidor = "{\"resposta\":\"OK\"}";
            if (respuestaServidor != null) {
                JSONObject respostaServidorJson = new JSONObject(respuestaServidor);
                if (respostaServidorJson.getString("resposta") != null) {
                    if ("OK".equalsIgnoreCase(respostaServidorJson.getString("resposta"))){
                        Toast.makeText(getActivity(),"Sessio modificada correctament",Toast.LENGTH_LONG).show();

                        getActivity().onBackPressed();
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