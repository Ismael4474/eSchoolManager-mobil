package com.ismaellopez.eschoolmanager_mobil.modelo.estudiant;

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

import java.util.Calendar;
import java.util.concurrent.ExecutionException;


public class FragModiEstudiantResult extends Fragment {

    View view;
    EditText editTextNom,editTextCognom,editTextDni,editTextData,editTextAdre,editTextTelef,editTextMail,editTextCodiEstudiant;
    Button botoAceptar;
    ImageButton botonFecha;
    DatePicker dataPickerModiEstudiant;
    CheckBox checkBoxRegsitrat;
    public FragModiEstudiantResult() {
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
                                    editTextNom.setText(respostaJsonDades.getString("nomEstudiant"));
                                    editTextCognom.setText(respostaJsonDades.getString("cognoms"));
                                    editTextDni.setText(respostaJsonDades.getString("dni"));
                                    editTextData.setText(respostaJsonDades.getString("dataNaixement"));
                                    editTextAdre.setText(respostaJsonDades.getString("adreca"));
                                    editTextTelef.setText(respostaJsonDades.getString("telefon"));
                                    editTextMail.setText(respostaJsonDades.getString("email"));
                                    editTextCodiEstudiant.setText(String.valueOf(respostaJsonDades.getInt("codiEstudiant")));
                                    checkBoxRegsitrat.setChecked(respostaJsonDades.getBoolean("registrat"));
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
        return inflater.inflate(R.layout.fragment_frag_modi_estudiant_result, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editTextNom = view.findViewById(R.id.editTextModiEstudiantNom);
        editTextCognom = view.findViewById(R.id.editTextModiEstudiantCognom);
        editTextDni = view.findViewById(R.id.editTextModiEstudiantDni);
        editTextData = view.findViewById(R.id.editTextModiEstudiantDataNa);
        editTextAdre = view.findViewById(R.id.editTextModiEstudiantAdreca);
        editTextTelef = view.findViewById(R.id.editTextModiEstudiantTelefon);
        editTextMail = view.findViewById(R.id.editTextModiEstudiantEmail);
        editTextCodiEstudiant = view.findViewById(R.id.editTextModiEstudiantCodi);
        checkBoxRegsitrat = view.findViewById(R.id.checkBoxRegistrat);
        botoAceptar = view.findViewById(R.id.buttonModiEstudiantAceptar);
        dataPickerModiEstudiant = view.findViewById(R.id.dataPickerModiEstudiant);
        botonFecha = view.findViewById(R.id.imageButtonDateModiEstudiant);
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
                modificarEstudiant(view);
            }
        });
    }

    public void modificarEstudiant(View view){
        //Creamos el objetos json que mandamos al servidor
        JSONObject json = new JSONObject();
        try {
            json.put("crida","MODI ESTUDIANT" );
            json.put("codiSessio", PantallaPrincipal.codiSessio);
            JSONObject jsonDades = new JSONObject();
            jsonDades.put("codiEstudiant", editTextCodiEstudiant.getText().toString());
            jsonDades.put("nomEstudiant",editTextNom.getText().toString());
            jsonDades.put("cognoms",editTextCognom.getText().toString());
            jsonDades.put("dni",editTextDni.getText().toString());
            jsonDades.put("dataNaixement",(editTextData.getText().toString()));
            jsonDades.put("adreca",editTextAdre.getText().toString());
            jsonDades.put("telefon",editTextTelef.getText().toString());
            jsonDades.put("email",editTextMail.getText().toString());
            jsonDades.put("registrat",checkBoxRegsitrat.isChecked());
            json.put("dades",jsonDades);
            //Iniciamos la conexión al servidor
            Connexio connexio = new Connexio();
            String respuestaServidor = connexio.execute(json.toString()).get();
            //          String respuestaServidor = "{\"resposta\":\"OK\"}";
            if (respuestaServidor != null) {
                JSONObject respostaServidorJson = new JSONObject(respuestaServidor);
                if (respostaServidorJson.getString("resposta") != null) {
                    if ("OK".equalsIgnoreCase(respostaServidorJson.getString("resposta"))){
                        Toast.makeText(getActivity(),"Estudiant modificat correctament",Toast.LENGTH_LONG).show();
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