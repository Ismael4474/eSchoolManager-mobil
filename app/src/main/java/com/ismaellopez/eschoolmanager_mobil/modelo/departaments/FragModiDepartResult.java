package com.ismaellopez.eschoolmanager_mobil.modelo.departaments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

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


public class FragModiDepartResult extends Fragment {

    EditText editTextModiDepart;
    CheckBox empleatCheck,departCheck,escolaCheck,becaCheck,sessioCheck,estudiantCheck,serveiCheck,informeCheck;
    Button botoModiAceptar;

    public FragModiDepartResult() {
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
                                    JSONObject respostaJsonDades = new JSONObject(respostaJson.getString("dades"));
                                    //rellenamos el editText con la respuesta del servidor
                                    editTextModiDepart.setText(respostaJsonDades.getString("nomDepartament"));
                                    if (respostaJsonDades.getString("permisos") != null){
                                        //rellenamos los checkBox con la respuest del servidor
                                        JSONObject respostaJsonPermisos = new JSONObject(respostaJsonDades.getString("permisos"));
                                        empleatCheck.setChecked(respostaJsonPermisos.getBoolean("empleat"));
                                        departCheck.setChecked(respostaJsonPermisos.getBoolean("departament"));
                                        escolaCheck.setChecked(respostaJsonPermisos.getBoolean("escola"));
                                        becaCheck.setChecked(respostaJsonPermisos.getBoolean("beca"));
                                        serveiCheck.setChecked(respostaJsonPermisos.getBoolean("servei"));
                                        sessioCheck.setChecked(respostaJsonPermisos.getBoolean("sessio"));
                                        estudiantCheck.setChecked(respostaJsonPermisos.getBoolean("estudiant"));
                                        informeCheck.setChecked(respostaJsonPermisos.getBoolean("informe"));
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
        return inflater.inflate(R.layout.fragment_frag_modi_depart_result, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editTextModiDepart = view.findViewById(R.id.editTextModiDepartResult);
        empleatCheck = view.findViewById(R.id.checkBoxModiEmpleat);
        departCheck = view.findViewById(R.id.checkBoxModiDepart);
        escolaCheck = view.findViewById(R.id.checkBoxModiEscola);
        becaCheck = view.findViewById(R.id.checkBoxModiBeques);
        sessioCheck = view.findViewById(R.id.checkBoxModiSessions);
        estudiantCheck = view.findViewById(R.id.checkBoxModiEstudiant);
        serveiCheck = view.findViewById(R.id.checkBoxModiServeis);
        informeCheck = view.findViewById(R.id.checkBoxModiInforme);

        //ponemos el boton a la escucha
        botoModiAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modifiDepart(view);
            }
        });
    }

    public void modifiDepart(View view){
        //Creamos el objetos json que mandamos al servidor
        JSONObject json = new JSONObject();
        try {
            json.put("crida","ALTA DEPARTAMENT" );
            json.put("codiSessio", PantallaPrincipal.codiSessio);
            JSONObject jsonDades = new JSONObject();
            jsonDades.put("nomDepartament",editTextModiDepart.getText().toString());
            JSONObject jsonPermisos = new JSONObject();
            jsonPermisos.put("empleat", empleatCheck.isChecked());
            jsonPermisos.put("estudiant", estudiantCheck.isChecked());
            jsonPermisos.put("servei", serveiCheck.isChecked());
            jsonPermisos.put("beca", becaCheck.isChecked());
            jsonPermisos.put("sessio", sessioCheck.isChecked());
            jsonPermisos.put("departament", departCheck.isChecked());
            jsonPermisos.put("escola", escolaCheck.isChecked());
            jsonPermisos.put("informe", informeCheck.isChecked());
            jsonDades.put("permisos",jsonPermisos);
            json.put("dades",jsonDades);
            //Iniciamos la conexión al servidor
            Connexio connexio = new Connexio();
            String respuestaServidor = connexio.execute(json.toString()).get();
    //        String respuestaServidor = "{\"resposta\":\"OK\"}";
            if (respuestaServidor != null) {
                JSONObject respostaServidorJson = new JSONObject(respuestaServidor);
                if (respostaServidorJson.getString("resposta") != null) {
                    if ("OK".equalsIgnoreCase(respostaServidorJson.getString("resposta"))){
                        Toast.makeText(getActivity(),"Departament modificat correctament",Toast.LENGTH_LONG).show();
                        //reiniciamos todos los camps
                        editTextModiDepart.setText("");
                        editTextModiDepart.requestFocus();
                        empleatCheck.setChecked(false);
                        departCheck.setChecked(false);
                        escolaCheck.setChecked(false);
                        becaCheck.setChecked(false);
                        sessioCheck.setChecked(false);
                        estudiantCheck.setChecked(false);
                        serveiCheck.setChecked(false);
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
}