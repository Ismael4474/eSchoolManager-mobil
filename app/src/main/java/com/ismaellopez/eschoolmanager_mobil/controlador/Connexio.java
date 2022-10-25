package com.ismaellopez.eschoolmanager_mobil.controlador;

import android.os.AsyncTask;
import android.util.Log;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Connexio extends AsyncTask<String,Void,String> {

    Socket socket;
    InputStreamReader in;
    PrintWriter out;
    BufferedReader bufReader;
    String resposta ;
    int puerto = 8080;

    @Override
    protected String doInBackground(String... strings) {
        String json = strings[0];
        try{
            socket = new Socket("10.2.55.226", puerto);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new InputStreamReader(socket.getInputStream());
            bufReader = new BufferedReader(in);
            out.println(json);
            resposta = bufReader.readLine();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resposta;
    }

}


