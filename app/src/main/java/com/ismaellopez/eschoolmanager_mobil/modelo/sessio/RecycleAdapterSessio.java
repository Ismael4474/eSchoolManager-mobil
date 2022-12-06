package com.ismaellopez.eschoolmanager_mobil.modelo.sessio;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ismaellopez.eschoolmanager_mobil.R;
import com.ismaellopez.eschoolmanager_mobil.modelo.servei.RecycleAdapterServei;

import java.util.ArrayList;

public class RecycleAdapterSessio extends RecyclerView.Adapter<RecycleAdapterSessio.MyViewHolder> {

    ArrayList<Sessio> llistaSessions;
    public RecycleAdapterSessio(ArrayList<Sessio> llistaSessions){
        this.llistaSessions = llistaSessions;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView textCodiSessio,textNomEmpleat,textNomEstudiant,textNomServei,textData;

        public MyViewHolder(final View view){
            super(view);
            textCodiSessio = view.findViewById(R.id.textCodiLlistaSessio);
            textNomEmpleat = view.findViewById(R.id.textEmpleatLlistaSessio);
            textNomEstudiant = view.findViewById(R.id.textEstudiantLlistaSessio);
            textNomServei= view.findViewById(R.id.textServeiLlistaSessio);
            textData = view.findViewById(R.id.textDataLlistaSessio);
        }
    }
    @NonNull
    @Override
    public RecycleAdapterSessio.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sessio,parent,false);
        return new RecycleAdapterSessio.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleAdapterSessio.MyViewHolder holder, int position) {

        holder.textCodiSessio.setText("Codi Sessio-> "+String.valueOf(llistaSessions.get(position).getCodiSessio()));
        holder.textNomEmpleat.setText(("Empleat: "+llistaSessions.get(position).getNomEmpleat() + " " + llistaSessions.get(position).getCognomsEmpleat()));
        holder.textNomEstudiant.setText(("Estudiant: " + llistaSessions.get(position).getNomEstudiant() + " " + llistaSessions.get(position).getCognomsEstudiant()));
        holder.textNomServei.setText("Servei: "+ llistaSessions.get(position).getNomServei());
        holder.textData.setText("Data: "+ llistaSessions.get(position).getDataSessio());
    }

    @Override
    public int getItemCount() {
        return llistaSessions.size();
    }
}
