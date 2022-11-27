package com.ismaellopez.eschoolmanager_mobil.modelo.empleat;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import android.os.AsyncTask;

import com.ismaellopez.eschoolmanager_mobil.controlador.Connexio;
import com.ismaellopez.eschoolmanager_mobil.modelo.PantallaPrincipal;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runner.manipulation.Ordering;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.concurrent.ExecutionException;

import io.mockk.impl.annotations.InjectMockKs;

@RunWith(MockitoJUnitRunner.class)
public class MenuEmpleatTest {

    @Mock
    Connexio connexioMocK;




    @Test
    public void test() throws JSONException, ExecutionException, InterruptedException {

        String resposta = "'resposta:''OK";
        when(connexioMocK.execute()).thenReturn(new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... strings) {
                return resposta;
            }
        });
    }



}
