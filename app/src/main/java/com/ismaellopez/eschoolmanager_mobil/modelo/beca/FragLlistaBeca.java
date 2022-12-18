package com.ismaellopez.eschoolmanager_mobil.modelo.beca;

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
import com.ismaellopez.eschoolmanager_mobil.modelo.empleat.Empleat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ExecutionException;


public class FragLlistaBeca extends Fragment {

    View view;
    private RecyclerView recyclerView;

    public FragLlistaBeca() {
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
        view = inflater.inflate(R.layout.fragment_frag_llista_beca, container, false);
        recyclerView = view.findViewById(R.id.recicleBeca);
        try {
            JSONArray arrayBeca= aconseguirLlista();
            if(arrayBeca!=null) {
                ArrayList<Beca> llistaBeques =montarLlista(arrayBeca);
                RecycleAdapterBeca adapter = new RecycleAdapterBeca(llistaBeques);
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

    //metodo per aconseguir la llista de empleats del servidor
    public JSONArray aconseguirLlista() throws JSONException, ExecutionException, InterruptedException {
        JSONArray arrayEmpleats = new JSONArray();
        JSONObject json = new JSONObject();
        json.put("crida","LLISTA BEQUES" );
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
                            arrayEmpleats.put(jsonDadesResposta.get(key));
                        }
                    }
                }else{
                    Toast.makeText(getActivity(), respostaServidorJson.getString("missatge"), Toast.LENGTH_LONG).show();
                }
            }
        }
        return arrayEmpleats;
    }
    //metode per montar una llista de empleats
    public ArrayList<Beca> montarLlista(JSONArray arrayBeques) throws JSONException {
        ArrayList<Beca> llistaBeques = new ArrayList<>();
        for (int i=0;i<arrayBeques.length();i++){
            JSONObject jsonBeques = arrayBeques.getJSONObject(i);
            Beca beca = new Beca();
            beca.setAdjudicant(jsonBeques.getString("adjudicant"));
            beca.setCodi(jsonBeques.getInt("codiBeca"));
            beca.setNomEstudiant(jsonBeques.getString("nomEstudiant"));
            beca.setCognomEstudiant(jsonBeques.getString("cognomsEstudiant"));
            beca.setFinalitzada(jsonBeques.getBoolean("finalitzada"));
            beca.setNomServei(jsonBeques.getString("nomServei"));
            beca.setImportInicial(jsonBeques.getDouble("importInicial"));
            beca.setImportRestant(jsonBeques.getDouble("importRestant"));
            llistaBeques.add(beca);
        }
        return llistaBeques;
    }
}