package com.ismaellopez.eschoolmanager_mobil.modelo.estudiant;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.ismaellopez.eschoolmanager_mobil.R;
import com.ismaellopez.eschoolmanager_mobil.controlador.Connexio;
import com.ismaellopez.eschoolmanager_mobil.modelo.PantallaPrincipal;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.concurrent.ExecutionException;

public class FragAltaEstudiant extends Fragment {

    View view;
    EditText editTextNom,editTextCognom,editTextDni,editTextData,editTextAdre,editTextTelef,editTextMail;
    Button botoAceptar;
    ImageButton botonFecha;
    DatePicker dataPickerAltaEmpl;

    public FragAltaEstudiant() {
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
        view=  inflater.inflate(R.layout.fragment_frag_alta_estudiant, container, false);
        editTextNom = view.findViewById(R.id.editTextAltaEstudiantNom);
        editTextCognom = view.findViewById(R.id.editTextAltaEstudiantCognom);
        editTextDni = view.findViewById(R.id.editTextAltaEstudiantDni);
        editTextData = view.findViewById(R.id.editTextAltaEstudiantDataNa);
        editTextAdre = view.findViewById(R.id.editTextAltaEstudiantAdreca);
        editTextTelef = view.findViewById(R.id.editTextAltaEstudiantTelefon);
        editTextMail = view.findViewById(R.id.editTextAltaEstudiantEmail);
        botoAceptar = view.findViewById(R.id.buttonAltaEstudiantAceptar);
        dataPickerAltaEmpl = view.findViewById(R.id.dataPickerAltaEstudiant);
        botonFecha = view.findViewById(R.id.imageButtonDateAltaEstudiant);
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
                donarAlta(view);
            }
        });
        return view;
    }

    public void donarAlta(View view){
        //Creamos el objetos json que mandamos al servidor
        JSONObject json = new JSONObject();

        try {
            json.put("crida","ALTA ESTUDIANT" );
            json.put("codiSessio", PantallaPrincipal.codiSessio);
            JSONObject jsonDades = new JSONObject();
            jsonDades.put("nomEstudiant",editTextNom.getText().toString());
            jsonDades.put("cognoms",editTextCognom.getText().toString());
            jsonDades.put("dni",editTextDni.getText().toString());
            jsonDades.put("dataNaixement",(editTextData.getText().toString()));
            jsonDades.put("adreca",editTextAdre.getText().toString());
            jsonDades.put("telefon",editTextTelef.getText().toString());
            jsonDades.put("email",editTextMail.getText().toString());
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
                        editTextNom.requestFocus();
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