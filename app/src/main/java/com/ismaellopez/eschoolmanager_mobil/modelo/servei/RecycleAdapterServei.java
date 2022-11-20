package com.ismaellopez.eschoolmanager_mobil.modelo.servei;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ismaellopez.eschoolmanager_mobil.R;


import java.util.ArrayList;

public class RecycleAdapterServei extends RecyclerView.Adapter<RecycleAdapterServei.MyViewHolder> {

    private ArrayList<Servei> llistaServeis;

    public RecycleAdapterServei (ArrayList<Servei> llistaServeis) {
        this.llistaServeis = llistaServeis;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView textNomServei,textCodiServei,textDurada,textCost;

        public MyViewHolder(final View view){
            super(view);
            textNomServei = view.findViewById(R.id.textNomServei);
            textCodiServei = view.findViewById(R.id.textCodiServei);
            textDurada = view.findViewById(R.id.textDuradaServei);
            textCost = view.findViewById(R.id.textCostServei);
        }
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_servei,parent,false);
        return new RecycleAdapterServei.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        int codiServei = llistaServeis.get(position).getCodi();
        String nomServei = llistaServeis.get(position).getNom();
        int duradaServei = llistaServeis.get(position).getDurada();;
        double costServei = llistaServeis.get(position).getCost();
        holder.textCodiServei.setText("Codi-> "+String.valueOf(codiServei));
        holder.textNomServei.setText(nomServei);
        holder.textDurada.setText("Durada-> " + String.valueOf(duradaServei)+ " hores");
        holder.textCost.setText("Cost-> " +String.valueOf(costServei)+ " €");

    }

    @Override
    public int getItemCount() {
        return llistaServeis.size();
    }
}
