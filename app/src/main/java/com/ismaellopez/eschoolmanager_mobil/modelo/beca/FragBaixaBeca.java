package com.ismaellopez.eschoolmanager_mobil.modelo.beca;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

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


public class FragBaixaBeca extends Fragment {

    View view;
    EditText editTextCodiBaixa;
    Button buttonAceptarBaixaBeca;

    public FragBaixaBeca() {
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
        view=  inflater.inflate(R.layout.fragment_frag_baixa_beca, container, false);
        editTextCodiBaixa = view.findViewById(R.id.editTextCodiBeca);
        buttonAceptarBaixaBeca = view.findViewById(R.id.buttonAceptarBaixaBeca);
        buttonAceptarBaixaBeca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aceptarBaixa(view);
            }
        });
        return view;
    }

    public void aceptarBaixa(View view){
        //Creamos el objetos json que mandamos al servidor
        JSONObject json = new JSONObject();
        try {
            json.put("crida","BAIXA BECA" );
            json.put("codiSessio", PantallaPrincipal.codiSessio);
            JSONObject jsonDades = new JSONObject();
            jsonDades.put("codiBeca",editTextCodiBaixa.getText().toString());
            json.put("dades",jsonDades);
            //Iniciamos la conexión al servidor
            Connexio connexio = new Connexio();
            String respuestaServidor = connexio.execute(json.toString()).get();
            //   String respuestaServidor = "{\"resposta\":\"OK\"}";
            if (respuestaServidor != null) {
                JSONObject respostaServidorJson = new JSONObject(respuestaServidor);
                if (respostaServidorJson.getString("resposta") != null) {
                    if ("OK".equalsIgnoreCase(respostaServidorJson.getString("resposta"))){
                        Toast.makeText(getActivity(),"Beca donada de baixa correctament",Toast.LENGTH_LONG).show();
                        editTextCodiBaixa.setText("");
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