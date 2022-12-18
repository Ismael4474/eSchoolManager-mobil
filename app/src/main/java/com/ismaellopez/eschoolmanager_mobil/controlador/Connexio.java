package com.ismaellopez.eschoolmanager_mobil.controlador;

import android.os.AsyncTask;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class Connexio extends AsyncTask<String,Void,String> {

    Socket socket;
    InputStreamReader in;
    PrintWriter out;
    BufferedReader bufReader;
    String resposta ;
    int puerto = 8080;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected String doInBackground(String... strings) {
        Seguridad sec = new Seguridad();
        String json = strings[0];
        sec.addKey("IOC");
        try {
            socket = new Socket("10.2.55.226", puerto);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new InputStreamReader(socket.getInputStream());
            bufReader = new BufferedReader(in);
            out.println(sec.encriptar(json));
         //   out.println(json);
            resposta = bufReader.readLine();
            resposta = sec.desencriptar(resposta);
            socket.close();
          } catch (IOException | NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
    //    }catch (IOException e){
            e.printStackTrace();
        }
        return resposta;
    }

}


