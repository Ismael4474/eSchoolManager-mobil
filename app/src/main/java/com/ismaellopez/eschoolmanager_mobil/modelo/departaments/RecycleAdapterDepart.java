package com.ismaellopez.eschoolmanager_mobil.modelo.departaments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ismaellopez.eschoolmanager_mobil.R;

import java.util.ArrayList;

public class RecycleAdapterDepart extends RecyclerView.Adapter<RecycleAdapterDepart.MyViewHolder>{

    private ArrayList<Departament> llistaDepartaments;

    public RecycleAdapterDepart(ArrayList<Departament> llistaDepartaments) {
        this.llistaDepartaments = llistaDepartaments;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView textNom,textCodiDepart;

        public MyViewHolder(final View view){
            super(view);
            textNom = view.findViewById(R.id.textNomDepart);
            textCodiDepart = view.findViewById(R.id.textCodiDepart);
        }
    }

    @NonNull
    @Override
    public RecycleAdapterDepart.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_depart,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleAdapterDepart.MyViewHolder holder, int position) {
        String nomDepart = llistaDepartaments.get(position).getNomDepartament();
        int codiDepart = llistaDepartaments.get(position).getCodiDepartament();
        holder.textNom.setText(nomDepart);
        holder.textCodiDepart.setText(String.valueOf(codiDepart));
    }

    @Override
    public int getItemCount() {
        return llistaDepartaments.size();
    }
}
