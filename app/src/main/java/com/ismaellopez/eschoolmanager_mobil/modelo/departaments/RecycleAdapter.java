package com.ismaellopez.eschoolmanager_mobil.modelo.departaments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ismaellopez.eschoolmanager_mobil.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.MyViewHolder>{

    private ArrayList<Departament> llistaDepartaments;

    public RecycleAdapter(ArrayList<Departament> llistaDepartaments) {
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
    public RecycleAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_depart,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleAdapter.MyViewHolder holder, int position) {
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
