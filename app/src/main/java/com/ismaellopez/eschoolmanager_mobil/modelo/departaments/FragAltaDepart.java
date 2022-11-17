package com.ismaellopez.eschoolmanager_mobil.modelo.departaments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.ismaellopez.eschoolmanager_mobil.R;
import com.ismaellopez.eschoolmanager_mobil.controlador.Connexio;
import com.ismaellopez.eschoolmanager_mobil.modelo.PantallaPrincipal;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;


public class FragAltaDepart extends Fragment {



    public FragAltaDepart() {
        // Required empty public constructor
    }


    View view;
    Button botoAlta;
    CheckBox empleatCheck,departCheck,escolaCheck,becaCheck,sessioCheck,estudiantCheck,serveiCheck,informeCheckBox;
    EditText editTextNomDepart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_frag_alta_depart, container, false);
        //asociamos los checkbox , el boton y el editText
        editTextNomDepart =view.findViewById(R.id.editTextTextNomDepart);
        botoAlta = view.findViewById(R.id.buttonAltaNovaDepart);
        empleatCheck = view.findViewById(R.id.checkBoxAltaEmpleat);
        departCheck = view.findViewById(R.id.checkBoxAltaDepart);
        escolaCheck = view.findViewById(R.id.checkBoxAltaEscola);
        becaCheck = view.findViewById(R.id.checkBoxAltaBeca);
        sessioCheck = view.findViewById(R.id.checkBoxAltaSessio);
        estudiantCheck = view.findViewById(R.id.checkBoxAltaEstudiant);
        serveiCheck = view.findViewById(R.id.checkBoxAltaServei);
        informeCheckBox = view.findViewById((R.id.checkBoxAltaInforme));

        botoAlta.setOnClickListener(new View.OnClickListener() {
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
            json.put("crida","ALTA DEPARTAMENT" );
            json.put("codiSessio", PantallaPrincipal.codiSessio);
            JSONObject jsonDades = new JSONObject();
            jsonDades.put("nomDepartament",editTextNomDepart.getText().toString());
            JSONObject jsonPermisos = new JSONObject();
            jsonPermisos.put("empleat", empleatCheck.isChecked());
            jsonPermisos.put("estudiant", estudiantCheck.isChecked());
            jsonPermisos.put("servei", serveiCheck.isChecked());
            jsonPermisos.put("beca", becaCheck.isChecked());
            jsonPermisos.put("sessio", sessioCheck.isChecked());
            jsonPermisos.put("departament", departCheck.isChecked());
            jsonPermisos.put("informe", informeCheckBox.isChecked());
            jsonPermisos.put("escola", escolaCheck.isChecked());
            jsonDades.put("permisos",jsonPermisos);
            json.put("dades",jsonDades);
            //Iniciamos la conexión al servidor
            Connexio connexio = new Connexio();
            String respuestaServidor = connexio.execute(json.toString()).get();
  //          String respuestaServidor = "{\"resposta\":\"OK\"}";
            if (respuestaServidor != null) {
                JSONObject respostaServidorJson = new JSONObject(respuestaServidor);
                if (respostaServidorJson.getString("resposta") != null) {
                    if ("OK".equalsIgnoreCase(respostaServidorJson.getString("resposta"))){
                        Toast.makeText(getActivity(),"Departament donat d'alta correctament",Toast.LENGTH_LONG).show();
                        //reiniciamos todos los camps
                        editTextNomDepart.setText("");
                        editTextNomDepart.requestFocus();
                        empleatCheck.setChecked(false);
                        departCheck.setChecked(false);
                        escolaCheck.setChecked(false);
                        becaCheck.setChecked(false);
                        sessioCheck.setChecked(false);
                        estudiantCheck.setChecked(false);
                        serveiCheck.setChecked(false);
                        informeCheckBox.setChecked(false);
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