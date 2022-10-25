package com.ismaellopez.eschoolmanager_mobil.controlador;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.nullable;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Array;

import io.mockk.MockK;

@RunWith(MockitoJUnitRunner.class)
public class ConnexioTest {

    @Mock
    private Connexio connexio;
    @Mock
    private Socket socket;
    @Mock
    private InputStreamReader in;

    @Mock
    private PrintWriter out;
    @Mock
    private BufferedReader bufReader;

    @Test
    public void testConnexio() throws JSONException, IOException {

        String json[]  = new String[2];
        json[0] = "dades";
        json[1] = "dades";
        String hola="hola";
        when (bufReader.readLine()).thenReturn(hola);
        assertEquals(bufReader.readLine(), hola);

    }
}