package com.ismaellopez.eschoolmanager_mobil.modelo.departaments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.ismaellopez.eschoolmanager_mobil.R;
import com.ismaellopez.eschoolmanager_mobil.controlador.Connexio;
import com.ismaellopez.eschoolmanager_mobil.modelo.PantallaPrincipal;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;


public class FragModiDepart extends Fragment {

    View view;
    EditText editTextCodiModi;
    Button buttonModiBuscar;


     public FragModiDepart() {
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
        view =  inflater.inflate(R.layout.fragment_frag_modi_depart, container, false);

        editTextCodiModi = view.findViewById(R.id.editTextModiDepart);
        buttonModiBuscar = view.findViewById(R.id.buttonBaixaTrobar);

        //colocamos el botón a la escucha
        buttonModiBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String resposataServidor = cercarDepartament(view);
                if (resposataServidor != null){
                    //Creamos el buble para pasar los datos al otro fragment
                    Bundle bundle = new Bundle();
                    bundle.putString("dadesResposta", resposataServidor);
                    getParentFragmentManager().setFragmentResult("resposta",bundle);
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    fragmentManager.beginTransaction().add(R.id.frameLayoutModiDepart, new FragModiDepartResult()).commit();

                }
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editTextCodiModi = view.findViewById(R.id.editTextModiDepart);
        buttonModiBuscar = view.findViewById(R.id.buttonBaixaTrobar);

        buttonModiBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String resposataServidor = cercarDepartament(view);
                if (resposataServidor != null){
                    //Creamos el buble para pasar los datos al otro fragment
                    Bundle bundle = new Bundle();
                    bundle.putString("dadesResposta", resposataServidor);
                    getParentFragmentManager().setFragmentResult("resposta",bundle);
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    fragmentManager.beginTransaction().add(R.id.frameLayoutModiDepart, new FragModiDepartResult()).commit();


                }
            }
        });
    }

    public String cercarDepartament(View view){
        //Creamos el objetos json que mandamos al servidor
        JSONObject json = new JSONObject();
        try {
            json.put("crida","CONSULTA DEPARTAMENT" );
            json.put("codiSessio", PantallaPrincipal.codiSessio);
            JSONObject jsonDades = new JSONObject();
            jsonDades.put("codiDepartament",editTextCodiModi.getText().toString());
            json.put("dades",jsonDades);
            //Iniciamos la conexión al servidor
            Connexio connexio = new Connexio();
            String respuestaServidor = connexio.execute(json.toString()).get();
        //    String respuestaServidor = "{\"resposta\":\"OK\"}";
            if (respuestaServidor != null) {
                JSONObject respostaServidorJson = new JSONObject(respuestaServidor);
                if (respostaServidorJson.getString("resposta") != null) {
                    if ("OK".equalsIgnoreCase(respostaServidorJson.getString("resposta"))){
                       return respuestaServidor;

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
        return null;
    }
}