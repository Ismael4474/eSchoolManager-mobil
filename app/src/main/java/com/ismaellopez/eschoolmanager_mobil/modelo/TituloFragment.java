package com.ismaellopez.eschoolmanager_mobil.modelo;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ismaellopez.eschoolmanager_mobil.R;

public class TituloFragment extends Fragment {

    private TextView textTitulo;


    public TituloFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_titulo, container, false);
        textTitulo = view.findViewById(R.id.textViewTitulo);
        String titulo = getArguments().getString("titulo");
        textTitulo.setText(titulo);

        return view;
    }


}