package com.ismaellopez.eschoolmanager_mobil.modelo.empleat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ismaellopez.eschoolmanager_mobil.R;
import com.ismaellopez.eschoolmanager_mobil.modelo.departaments.RecycleAdapterDepart;
import com.ismaellopez.eschoolmanager_mobil.modelo.servei.Servei;

import java.util.ArrayList;

public class RecycleAdapterEmpleat extends RecyclerView.Adapter<RecycleAdapterEmpleat.MyViewHolder> {

    private ArrayList<Empleat> llistaEmpleats;

    public RecycleAdapterEmpleat(ArrayList<Empleat> llistaEmpleats) {
        this.llistaEmpleats = llistaEmpleats;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView textNomEmpleat,textCognomEmpleat,textCodiEmpleat,textCodiDepartament,textNomDepartament;

        public MyViewHolder(final View view){
            super(view);
            textNomEmpleat = view.findViewById(R.id.textNomEmpleat);
            textCognomEmpleat = view.findViewById(R.id.textCognomEmpleat);
            textCodiEmpleat = view.findViewById(R.id.textCodiEmpleat);
            textCodiDepartament = view.findViewById(R.id.textCodiDepartEmpleat);
            textNomDepartament = view.findViewById(R.id.textNomDepartEmpleat);
        }
    }

    @NonNull
    @Override
    public RecycleAdapterEmpleat.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_empleat,parent,false);
        return new RecycleAdapterEmpleat.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleAdapterEmpleat.MyViewHolder holder, int position) {
        holder.textNomEmpleat.setText(llistaEmpleats.get(position).getNom());
        holder.textCognomEmpleat.setText(llistaEmpleats.get(position).getCognoms());
        holder.textCodiEmpleat.setText("Codi empleat->"+String.valueOf(llistaEmpleats.get(position).getCodiEmpleat()));
        holder.textCodiDepartament.setText("Codi depart->"+String.valueOf(llistaEmpleats.get(position).getCodiDepartament()));
        holder.textNomDepartament.setText(llistaEmpleats.get(position).getNomDepartament());

    }

    @Override
    public int getItemCount() {
        return llistaEmpleats.size();
    }
}
