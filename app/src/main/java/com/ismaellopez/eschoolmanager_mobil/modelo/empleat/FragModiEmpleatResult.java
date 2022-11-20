package com.ismaellopez.eschoolmanager_mobil.modelo.empleat;

import android.app.DatePickerDialog;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.ExecutionException;


public class FragModiEmpleatResult extends Fragment {

    ArrayList<Departament> llistaDepartaments ;
    View view;
    EditText editTextNom,editTextCognom,editTextDni,editTextData,editTextAdre,editTextTelef,editTextMail,editTextUsuari,editTextCodiEmpleat;
    Spinner spinnerNomDepartament;
    Button botoAceptar;
    ImageButton botonFecha;
    DatePicker dataPickerAltaEmpl;
    CheckBox checkBoxActiu;
    FragAltaEmpleat fragAlta = new FragAltaEmpleat();

    public FragModiEmpleatResult() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //recuperamos la respuesta que le hemos apsado en el otro fragment
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

                                    llistaDepartaments = fragAlta.omplirSpinnerDepartaments();
                                    ArrayAdapter<Departament> adapter = new ArrayAdapter<Departament>(getContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,llistaDepartaments);
                                    spinnerNomDepartament.setAdapter(adapter);
                                     JSONObject respostaJsonDades = new JSONObject(respostaJson.getString("dades"));
                                    //rellenamos el editText con la respuesta del servidor
                                    editTextNom.setText(respostaJsonDades.getString("nom"));
                                    editTextCognom.setText(respostaJsonDades.getString("cognoms"));
                                    editTextDni.setText(respostaJsonDades.getString("dni"));
                                    editTextData.setText(respostaJsonDades.getString("dataNaixement"));
                                    editTextAdre.setText(respostaJsonDades.getString("adreca"));
                                    editTextTelef.setText(respostaJsonDades.getString("telefon"));
                                    editTextMail.setText(respostaJsonDades.getString("email"));
                                    editTextUsuari.setText(respostaJsonDades.getString("usuari"));
                                    editTextCodiEmpleat.setText(String.valueOf(respostaJsonDades.getInt("codiEmpleat")));
                                    checkBoxActiu.setChecked(respostaJsonDades.getBoolean("actiu"));
                                }
                            }else{
                                Toast.makeText(getActivity(), respostaJson.getString("missatge"), Toast.LENGTH_LONG).show();

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
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_frag_modi_empleat_result, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editTextNom = view.findViewById(R.id.editTexAltaEmpleatNom);
        editTextCognom = view.findViewById(R.id.editTexAltaEmpleatCognom);
        editTextDni = view.findViewById(R.id.editTexAltaEmpleatDni);
        editTextData = view.findViewById(R.id.editTextAltaEmpleatDate);
        editTextAdre = view.findViewById(R.id.editTextAltaEmpleatPostalAddress);
        editTextTelef = view.findViewById(R.id.editTextAltaEmpleatPhone);
        editTextMail = view.findViewById(R.id.editTextAltaEmpleatEmailAddress);
        spinnerNomDepartament=view.findViewById(R.id.spinnerAltaEmpleat);
        editTextCodiEmpleat = view.findViewById(R.id.editTexAltaEmpleatCodiEmple);
        checkBoxActiu = view.findViewById(R.id.CheckBoxActiu);
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

        botoAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modificarEmpleat(view);
            }
        });
    }

    public void modificarEmpleat(View view){
        //Creamos el objetos json que mandamos al servidor
        JSONObject json = new JSONObject();
        try {
            json.put("crida","MODI EMPLEAT" );
            json.put("codiSessio", PantallaPrincipal.codiSessio);
            JSONObject jsonDades = new JSONObject();
            jsonDades.put("codiEmpleat", editTextCodiEmpleat.getText().toString());
            jsonDades.put("nom",editTextNom.getText().toString());
            jsonDades.put("cognoms",editTextCognom.getText().toString());
            jsonDades.put("dni",editTextDni.getText().toString());
            jsonDades.put("dataNaixement",(editTextData.getText().toString()));
            jsonDades.put("adreca",editTextAdre.getText().toString());
            jsonDades.put("telefon",editTextTelef.getText().toString());
            jsonDades.put("email",editTextMail.getText().toString());
            jsonDades.put("codiDepartament",fragAlta.calcularCodiDepartament(spinnerNomDepartament.getSelectedItem().toString()));
            jsonDades.put("usuari",editTextUsuari.getText().toString());
            jsonDades.put("contrasenya","");
            jsonDades.put("actiu",checkBoxActiu.isChecked());
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
}