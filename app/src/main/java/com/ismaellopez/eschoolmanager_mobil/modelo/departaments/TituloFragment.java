package com.ismaellopez.eschoolmanager_mobil.modelo.departaments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ismaellopez.eschoolmanager_mobil.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TituloFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TituloFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "titulo";
    private static final String ARG_PARAM2 = "param2";

    private TextView textTitulo;

    // TODO: Rename and change types of parameters
    private String titulo;
    private String mParam2;

    public TituloFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param titulo Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TituloFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TituloFragment newInstance(String titulo, String param2) {
        TituloFragment fragment = new TituloFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, titulo);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            titulo = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_titulo, container, false);
        return view;
    }


}