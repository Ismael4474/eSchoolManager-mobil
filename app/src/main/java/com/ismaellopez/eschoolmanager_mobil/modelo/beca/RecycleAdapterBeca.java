package com.ismaellopez.eschoolmanager_mobil.modelo.beca;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ismaellopez.eschoolmanager_mobil.R;
import com.ismaellopez.eschoolmanager_mobil.modelo.departaments.Departament;
import com.ismaellopez.eschoolmanager_mobil.modelo.departaments.RecycleAdapterDepart;

import java.util.ArrayList;

public class RecycleAdapterBeca extends RecyclerView.Adapter<RecycleAdapterBeca.MyViewHolder> {

    private ArrayList<Beca> llistaBeques ;

    public RecycleAdapterBeca(ArrayList<Beca> llistaBeques) {
        this.llistaBeques = llistaBeques;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView textCodiBeca,textAdjudicant, textNomEstudiant,textNomServei,textImportInicial,textImportRestant,textViewFinalitzada;

        public MyViewHolder(final View view){
            super(view);
            textAdjudicant = view.findViewById(R.id.textAdjudicant);
            textCodiBeca = view.findViewById(R.id.textCodiBeca);
            textNomEstudiant = view.findViewById(R.id.textNomEstudiant);
            textNomServei = view.findViewById(R.id.textNomServei);
            textImportInicial = view.findViewById(R.id.textImportInicial);
            textImportRestant = view.findViewById(R.id.textImportRestant);
            textViewFinalitzada =  view.findViewById(R.id.textFinalitzadaBeca);
        }
    }
    @NonNull
    @Override
    public RecycleAdapterBeca.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_beca,parent,false);
        return new RecycleAdapterBeca.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleAdapterBeca.MyViewHolder holder, int position) {

        holder.textAdjudicant.setText(llistaBeques.get(position).getAdjudicant());
        holder.textCodiBeca.setText(("Codi: "+llistaBeques.get(position).getCodi()));
        holder.textNomEstudiant.setText("Estudiant: " +String.valueOf(llistaBeques.get(position).getNomEstudiant()) + " "+ String.valueOf(llistaBeques.get(position).getCognomEstudiant()));
        holder.textImportInicial.setText("Import Inicial: " + String.valueOf(llistaBeques.get(position).getImportInicial()));
        holder.textImportRestant.setText("Import Restant: "+String.valueOf(llistaBeques.get(position).getImportRestant()));
        holder.textNomServei.setText("Servei: "+String.valueOf(llistaBeques.get(position).getNomServei()));
        holder.textViewFinalitzada.setText("Finalitzada: " +String.valueOf(llistaBeques.get(position).getFinalitzada()));

    }

    @Override
    public int getItemCount() {

        return llistaBeques.size();
    }
}
