package com.ismaellopez.eschoolmanager_mobil.modelo.servei;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ismaellopez.eschoolmanager_mobil.R;
import com.ismaellopez.eschoolmanager_mobil.controlador.Connexio;
import com.ismaellopez.eschoolmanager_mobil.modelo.PantallaPrincipal;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;


public class FragModiServeiResult extends Fragment {

    View view;
    EditText editTextModiCodiServei,editTextModiServeiNom,editTextModiServeiDurada,editTextModiServeiCost;
    Button botoAceptarModiServei;

    public FragModiServeiResult() {
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
                                    editTextModiCodiServei.setText(respostaJsonDades.getString("codiServei"));
                                    editTextModiServeiNom.setText(respostaJsonDades.getString("nomServei"));
                                    editTextModiServeiDurada.setText(respostaJsonDades.getString("durada"));
                                    editTextModiServeiCost.setText(respostaJsonDades.getString("cost"));
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
        view= inflater.inflate(R.layout.fragment_frag_modi_servei_result, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editTextModiCodiServei = view.findViewById(R.id.editTextModiCodiServei);
        editTextModiServeiNom = view.findViewById(R.id.editTextModiNomServei);
        editTextModiServeiDurada = view.findViewById(R.id.editTextModiDuradaServei);
        editTextModiServeiCost = view.findViewById(R.id.editTextModiCostServei);

        botoAceptarModiServei = view.findViewById(R.id.buttonModiNovaServei);

        botoAceptarModiServei.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modifiServei(view);
            }
        });
    }

    public void modifiServei(View view){
        //Creamos el objetos json que mandamos al servidor
        JSONObject json = new JSONObject();
        try {
            json.put("crida","MODI SERVEI" );
            json.put("codiSessio", PantallaPrincipal.codiSessio);
            JSONObject jsonDades = new JSONObject();
            jsonDades.put("codiServei",editTextModiCodiServei.getText().toString());
            jsonDades.put("nomServei",editTextModiServeiNom.getText().toString());
            jsonDades.put("durada",editTextModiServeiDurada.getText().toString());
            jsonDades.put("cost",editTextModiServeiCost.getText().toString());
            json.put("dades",jsonDades);
            //Iniciamos la conexión al servidor
            Connexio connexio = new Connexio();
            String respuestaServidor = connexio.execute(json.toString()).get();
            //        String respuestaServidor = "{\"resposta\":\"OK\"}";
            if (respuestaServidor != null) {
                JSONObject respostaServidorJson = new JSONObject(respuestaServidor);
                if (respostaServidorJson.getString("resposta") != null) {
                    if ("OK".equalsIgnoreCase(respostaServidorJson.getString("resposta"))){
                        Toast.makeText(getActivity(),"Servei modificat correctament",Toast.LENGTH_LONG).show();

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