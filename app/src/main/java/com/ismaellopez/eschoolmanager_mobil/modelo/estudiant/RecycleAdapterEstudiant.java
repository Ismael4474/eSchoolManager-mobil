package com.ismaellopez.eschoolmanager_mobil.modelo.estudiant;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ismaellopez.eschoolmanager_mobil.R;
import com.ismaellopez.eschoolmanager_mobil.modelo.servei.RecycleAdapterServei;

import java.util.ArrayList;

public class RecycleAdapterEstudiant extends RecyclerView.Adapter<RecycleAdapterEstudiant.MyViewHolder>{

    private ArrayList<Estudiant> llistaEstudiants;

    public RecycleAdapterEstudiant(ArrayList<Estudiant>llistaEstudiants){
        this.llistaEstudiants = llistaEstudiants;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView textNomEstudiant,textCognomEstudiant,textCodiEstudiant;

        public MyViewHolder(final View view){
            super(view);
            textNomEstudiant = view.findViewById(R.id.textNomEstudiant);
            textCognomEstudiant= view.findViewById(R.id.textCognomEstudiant);
            textCodiEstudiant = view.findViewById(R.id.textCodiEstudiant);

        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_estudiant,parent,false);
        return new RecycleAdapterEstudiant.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.textNomEstudiant.setText(llistaEstudiants.get(position).getNom());
        holder.textCognomEstudiant.setText(llistaEstudiants.get(position).getCognoms());
        holder.textCodiEstudiant.setText("Codi ->"+String.valueOf(llistaEstudiants.get(position).getCodi()));

    }

    @Override
    public int getItemCount() {
        return llistaEstudiants.size();
    }


}
