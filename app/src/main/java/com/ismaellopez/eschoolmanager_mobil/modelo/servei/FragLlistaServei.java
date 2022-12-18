package com.ismaellopez.eschoolmanager_mobil.modelo.servei;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ismaellopez.eschoolmanager_mobil.R;
import com.ismaellopez.eschoolmanager_mobil.controlador.Connexio;
import com.ismaellopez.eschoolmanager_mobil.modelo.PantallaPrincipal;
import com.ismaellopez.eschoolmanager_mobil.modelo.departaments.Departament;
import com.ismaellopez.eschoolmanager_mobil.modelo.departaments.RecycleAdapterDepart;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ExecutionException;


public class FragLlistaServei extends Fragment {

    View view;
    private RecyclerView recyclerView;

    public FragLlistaServei() {
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
        view =  inflater.inflate(R.layout.fragment_frag_llista_servei, container, false);
        recyclerView = view.findViewById(R.id.recicleServei);
        try {
            JSONArray arrayServei= aconseguirLlista();
            if(arrayServei!=null) {
                ArrayList<Servei> llistaServeis =montarLlista(arrayServei);
                RecycleAdapterServei adapter = new RecycleAdapterServei(llistaServeis);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(adapter);
            }else{
                Toast.makeText(getActivity(),"Error al obtenir la llista......",Toast.LENGTH_LONG).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return view;
    }

    //metodo per aconseguir la llista de departaments del servidor
    public JSONArray aconseguirLlista() throws JSONException, ExecutionException, InterruptedException {
        JSONArray arrayServeis = new JSONArray();
        JSONObject json = new JSONObject();
        json.put("crida","LLISTA SERVEIS" );
        json.put("codiSessio", PantallaPrincipal.codiSessio);
        JSONObject jsonDades = new JSONObject();
        jsonDades.put("ordre","");
        jsonDades.put("camp","");
        jsonDades.put("valor","");
        json.put("dades",jsonDades);
        //Iniciamos la conexión al servidor
        Connexio connexio = new Connexio();
        String respuestaServidor = connexio.execute(json.toString()).get();
        if (respuestaServidor != null) {
            JSONObject respostaServidorJson = new JSONObject(respuestaServidor);
            if (respostaServidorJson.getString("resposta") != null) {
                if ("OK".equalsIgnoreCase(respostaServidorJson.getString("resposta"))){
                    if (respostaServidorJson.getString("dades")!= null) {
                        JSONObject jsonDadesResposta = new JSONObject(respostaServidorJson.getString("dades"));
                        Iterator<String> x = jsonDadesResposta.keys();
                        while (x.hasNext()){
                            String key= (String)x.next();
                            arrayServeis.put(jsonDadesResposta.get(key));
                        }
                    }
                }else{
                    Toast.makeText(getActivity(), respostaServidorJson.getString("missatge"), Toast.LENGTH_LONG).show();
                }
            }
        }
        return arrayServeis;
    }
    //metode per montar una llista de departaments
    public ArrayList<Servei> montarLlista(JSONArray arrayServeis) throws JSONException {
        ArrayList<Servei> llistaServeis = new ArrayList<>();
        for (int i=0;i<arrayServeis.length();i++){
            JSONObject jsonServeis = arrayServeis.getJSONObject(i);
            Servei servei = new Servei();
            servei.setCodi(jsonServeis.getInt("codiServei"));
            servei.setNom(jsonServeis.getString("nomServei"));
            servei.setCost(jsonServeis.getDouble("cost"));
            servei.setDurada(jsonServeis.getInt("durada"));
            llistaServeis.add(servei);
        }
        return llistaServeis;
    }
}