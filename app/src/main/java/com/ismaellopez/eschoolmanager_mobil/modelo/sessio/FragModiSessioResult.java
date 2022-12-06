package com.ismaellopez.eschoolmanager_mobil.modelo.sessio;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ismaellopez.eschoolmanager_mobil.R;


public class FragModiSessioResult extends Fragment {



    public FragModiSessioResult() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_frag_modi_sessio_result, container, false);
    }
}