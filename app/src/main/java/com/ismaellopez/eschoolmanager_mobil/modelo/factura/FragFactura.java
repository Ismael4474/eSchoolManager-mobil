package com.ismaellopez.eschoolmanager_mobil.modelo.factura;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ismaellopez.eschoolmanager_mobil.R;
import com.ismaellopez.eschoolmanager_mobil.controlador.Connexio;
import com.ismaellopez.eschoolmanager_mobil.modelo.PantallaPrincipal;
import com.ismaellopez.eschoolmanager_mobil.modelo.beca.Beca;
import com.ismaellopez.eschoolmanager_mobil.modelo.empleat.RecycleAdapterEmpleat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.concurrent.ExecutionException;


public class FragFactura extends Fragment {

    private RecyclerView recyclerView;
    TextView textData, textCodi,textNom,textFacturat,textTotal;
    View view;
    double total;
    Button botoPagar;

    ArrayList<Moviments> llistaMoviments = new ArrayList<>();

    public FragFactura() {
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
        view =  inflater.inflate(R.layout.fragment_frag_factura, container, false);
        textData = view.findViewById(R.id.textFacturaData);
        textCodi= view.findViewById(R.id.textFacturaCodi);
        textNom = view.findViewById(R.id.textFacturaNom);
        textFacturat = view.findViewById(R.id.textFacturaPagada);
        textTotal = view.findViewById(R.id.textFacturaTotal);
        botoPagar = view.findViewById(R.id.buttonFacturaPagar);
        recyclerView = view.findViewById(R.id.recicleFactura);
        if (getArguments() != null){
            String resposta = getArguments().getString("dadesResposta");
            if (resposta != null) {
                JSONObject respostaJson = null;
                try {
                    respostaJson = new JSONObject(resposta);

                    if (respostaJson.getString("resposta") != null) {
                        if ("OK".equalsIgnoreCase(respostaJson.getString("resposta"))) {
                            if (respostaJson.getString("dades") != null) {
                                rellenarFactura(respostaJson.getString("dades"));
                            }
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }else{
            Toast.makeText(getContext(),"Error en les dades....", Toast.LENGTH_LONG).show();
        }

        return view;
    }

    public void rellenarFactura(String resposta) throws JSONException {
        boolean facturat = false;
        JSONObject respostaJsonDades = new JSONObject(resposta);
        textData.setText(obtenerFechaActual());
        textCodi.setText(String.valueOf(respostaJsonDades.getInt("codiFactura")));
        textNom.setText(respostaJsonDades.getString("nomEstudiant") + " "+ respostaJsonDades.getString("cognomsEstudiant"));
        facturat = respostaJsonDades.getBoolean("pagat");
        if (facturat){
            textFacturat.setText("Si");
            botoPagar.setVisibility(View.INVISIBLE);
        }else{
            textFacturat.setText("No");
            botoPagar.setVisibility(View.VISIBLE);
        }
        montarMovimientos(respostaJsonDades.getString("sessions"));
        textTotal.setText(String.valueOf(total)+ " "+ "€");
        RecycleAdapterFactura adapter = new RecycleAdapterFactura(llistaMoviments);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);


        botoPagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pagar();
            }
        });
    }

    public void montarMovimientos(String sessions) throws JSONException {
        JSONArray arrayMoviments = new JSONArray();
        if (sessions != null) {
            JSONObject jsonDadesResposta = new JSONObject(sessions);
            Iterator<String> x = jsonDadesResposta.keys();
            while (x.hasNext()) {
                String key = (String) x.next();
                arrayMoviments.put(jsonDadesResposta.get(key));
            }
            //si la lista está vacio enviamos un mensaje
            if (arrayMoviments.length()<1){
                botoPagar.setVisibility(View.INVISIBLE);
                Toast.makeText(getContext(), "No hi ha serveis a facturar......", Toast.LENGTH_SHORT).show();
            }else {
                //rellenamos la lista de movimientos
                for (int i = 0; i < arrayMoviments.length(); i++) {
                    JSONObject jsonMoviments = arrayMoviments.getJSONObject(i);
                    Moviments mov = new Moviments();
                    mov.setData(jsonMoviments.getString("dataIHora"));
                    mov.setServei(jsonMoviments.getString("nomServei"));
                    mov.setImportBeca(jsonMoviments.getDouble("importBeca"));
                    mov.setImportEstudiant(jsonMoviments.getDouble("importEstudiant"));
                    total = total + mov.getImportEstudiant();
                    llistaMoviments.add(mov);
                }
            }

        }else{
            Toast.makeText(getContext(), "Error al obtenir les dades", Toast.LENGTH_SHORT).show();
        }


    }

    //obtenemos la fecha actual
    public String obtenerFechaActual(){
        Calendar calendar = Calendar.getInstance();
        int dia = calendar.get(Calendar.DAY_OF_MONTH);
        int mes = calendar.get(Calendar.MONTH);
        int ano = calendar.get(Calendar.YEAR);

        return dia+"/"+mes+"/"+ano;
    }

    public void pagar(){
        //Creamos el objetos json que mandamos al servidor
        JSONObject json = new JSONObject();

        try {
            json.put("crida","PAGA FACTURA" );
            json.put("codiSessio", PantallaPrincipal.codiSessio);
            JSONObject jsonDades = new JSONObject();
            jsonDades.put("codiFactura",textCodi.getText());
            jsonDades.put("pagat","true");
            json.put("dades",jsonDades);
            //Iniciamos la conexión al servidor
            Connexio connexio = new Connexio();
            String respuestaServidor = connexio.execute(json.toString()).get();
            //          String respuestaServidor = "{\"resposta\":\"OK\"}";
            if (respuestaServidor != null) {
                JSONObject respostaServidorJson = new JSONObject(respuestaServidor);
                if (respostaServidorJson.getString("resposta") != null) {
                    if ("OK".equalsIgnoreCase(respostaServidorJson.getString("resposta"))){
                        Toast.makeText(getActivity(),"Factura pagada correctament.......",Toast.LENGTH_LONG).show();
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