package com.ismaellopez.eschoolmanager_mobil.controlador;


import android.widget.Toast;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class Seguridad {

    private SecretKey key;
    private Cipher cipher;
    private String algoritmo = "AES";
    private int keysize = 16;


    public void addKey(String value) {
        byte[] valuebytes = value.getBytes();
        key = new SecretKeySpec(Arrays.copyOf(valuebytes, keysize), algoritmo);
    }

    public byte[] encriptar(String texto) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        String value = "";
        cipher = Cipher.getInstance(algoritmo);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] textobytes = texto.getBytes();
        byte[] cipherbytes = cipher.doFinal(textobytes);

        return cipherbytes;
    }

    public String desencriptar(byte[] texto) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        String str = "";
        cipher = Cipher.getInstance(algoritmo);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] cipherbytes = cipher.doFinal(texto);
        str = new String(cipherbytes);

        return str;
    }
}