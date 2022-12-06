package com.ismaellopez.eschoolmanager_mobil.modelo.beca;

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
import com.ismaellopez.eschoolmanager_mobil.modelo.empleat.FragAltaEmpleat;
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


public class FragModiBecaResult extends Fragment {

    View view;
    ArrayList<Estudiant> llistaEstudiants ;
    ArrayList<Servei> llistaServeis;
    EditText editTextCodiBeca,editTextAdjudicant,editTextImportInicial,editTextImportRestant;
    Spinner spinnerNomEstudiant,spinnerNomServei;
    Button botoAceptar;
    CheckBox checkFinalitzat;
    public FragModiBecaResult() {
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
                                    FragLlistaEstudiant fragLlistaEstudiant = new FragLlistaEstudiant();
                                    FragLlistaServei fragLlistaServei = new FragLlistaServei();
                                    JSONArray arrayEstudiants = fragLlistaEstudiant.aconseguirLlista();
                                    llistaEstudiants =fragLlistaEstudiant.montarLlista(arrayEstudiants);
                                    JSONArray arrayServeis = fragLlistaServei.aconseguirLlista();
                                    llistaServeis = fragLlistaServei.montarLlista(arrayServeis);
                                    ArrayAdapter<Estudiant> adapterEstudiants = new ArrayAdapter<Estudiant>(getContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,llistaEstudiants);
                                    spinnerNomEstudiant.setAdapter(adapterEstudiants);
                                    ArrayAdapter<Servei> adapterServei = new ArrayAdapter<Servei>(getContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,llistaServeis);
                                    spinnerNomServei.setAdapter(adapterServei);
                                    JSONObject respostaJsonDades = new JSONObject(respostaJson.getString("dades"));
                                    //rellenamos el editText con la respuesta del servidor
                                    editTextCodiBeca.setText(String.valueOf(respostaJsonDades.getInt("codiBeca")));
                                    editTextAdjudicant.setText(respostaJsonDades.getString("adjudicant"));
                                    editTextImportInicial.setText(String.valueOf(respostaJsonDades.getDouble("importInicial")));
                                    editTextImportRestant.setText(String.valueOf(respostaJsonDades.getDouble("importRestant")));
                                    editTextImportRestant.setEnabled(false);
                                    checkFinalitzat.setChecked(respostaJsonDades.getBoolean("finalitzada"));
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
        view = inflater.inflate(R.layout.fragment_frag_modi_beca_result, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editTextAdjudicant = view.findViewById(R.id.editTexModiBecaNom);
        editTextCodiBeca = view.findViewById(R.id.editTexModiBecaCodi);
        editTextImportInicial = view.findViewById(R.id.editTexAltaBecatImport);
        spinnerNomEstudiant = view.findViewById(R.id.spinnerModiBecaEstudiant);
        spinnerNomServei = view.findViewById(R.id.spinnerModiBecaServei);
        editTextImportRestant = view.findViewById(R.id.editTexAltaBecatImportRestant);
        checkFinalitzat = view.findViewById(R.id.checkBoxModiBecaFinal);
        botoAceptar = view.findViewById(R.id.buttonModiBecaAceptar);


        botoAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modificarBeca(view);
            }
        });
    }

    public void modificarBeca(View view){
        //Creamos el objetos json que mandamos al servidor
        JSONObject json = new JSONObject();
        try {
            json.put("crida","MODI BECA" );
            json.put("codiSessio", PantallaPrincipal.codiSessio);
            JSONObject jsonDades = new JSONObject();
            jsonDades.put("codiBeca", editTextCodiBeca.getText().toString());
            jsonDades.put("adjudicant",editTextAdjudicant.getText().toString());
            jsonDades.put("importInicial",editTextImportInicial.getText().toString());
            jsonDades.put("importRestant",editTextImportRestant.getText().toString());
            jsonDades.put("finalitzada",checkFinalitzat.isChecked());
            jsonDades.put("codiEstudiant",calcularCodiEstudiant(spinnerNomEstudiant.getSelectedItem().toString()));
            jsonDades.put("codiServei",calcularCodiServei(spinnerNomServei.getSelectedItem().toString()));
            json.put("dades",jsonDades);
            //Iniciamos la conexión al servidor
            Connexio connexio = new Connexio();
            String respuestaServidor = connexio.execute(json.toString()).get();
            //          String respuestaServidor = "{\"resposta\":\"OK\"}";
            if (respuestaServidor != null) {
                JSONObject respostaServidorJson = new JSONObject(respuestaServidor);
                if (respostaServidorJson.getString("resposta") != null) {
                    if ("OK".equalsIgnoreCase(respostaServidorJson.getString("resposta"))){
                        Toast.makeText(getActivity(),"Beca modificada correctament",Toast.LENGTH_LONG).show();


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

    public String calcularCodiEstudiant(String nom){
        String codi = null;
        for (Estudiant estudiant: llistaEstudiants){
            if ((estudiant.getNom() +" "+ estudiant.getCognoms()).equalsIgnoreCase(nom) ){
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