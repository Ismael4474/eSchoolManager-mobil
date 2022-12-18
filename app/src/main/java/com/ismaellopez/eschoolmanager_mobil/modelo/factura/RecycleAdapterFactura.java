package com.ismaellopez.eschoolmanager_mobil.modelo.factura;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ismaellopez.eschoolmanager_mobil.R;
import com.ismaellopez.eschoolmanager_mobil.modelo.estudiant.RecycleAdapterEstudiant;

import java.util.ArrayList;

public class RecycleAdapterFactura extends RecyclerView.Adapter<RecycleAdapterFactura.MyViewHolder> {

    ArrayList<Moviments> llistaMoviments = new ArrayList<>();

    public RecycleAdapterFactura(ArrayList<Moviments> llistaMoviments){
        this.llistaMoviments = llistaMoviments;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView textData,textServei,textImportBeca,textImportEstudiant;

        public MyViewHolder(final View view){
            super(view);
            textData = view.findViewById(R.id.textItemData);
            textServei= view.findViewById(R.id.textItemServei);
            textImportBeca = view.findViewById(R.id.textItemImportBeca);
            textImportEstudiant = view.findViewById(R.id.textItemImportEstudiant);
        }
    }

    @NonNull
    @Override
    public RecycleAdapterFactura.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_factura,parent,false);
        return new RecycleAdapterFactura.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleAdapterFactura.MyViewHolder holder, int position) {
        holder.textData.setText(llistaMoviments.get(position).getData());
        holder.textServei.setText(llistaMoviments.get(position).getServei());
        holder.textImportBeca.setText(String.valueOf(llistaMoviments.get(position).getImportBeca()) + " " +"€");
        holder.textImportEstudiant.setText(String.valueOf(llistaMoviments.get(position).getImportEstudiant()) + " " +"€");
    }

    @Override
    public int getItemCount() {
        return llistaMoviments.size();
    }
}
