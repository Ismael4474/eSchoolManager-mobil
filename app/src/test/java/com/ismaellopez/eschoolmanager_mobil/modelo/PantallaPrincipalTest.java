package com.ismaellopez.eschoolmanager_mobil.modelo;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import android.os.Bundle;
import android.provider.SyncStateContract;
import android.widget.Button;



import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runner.manipulation.Ordering;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.mockk.impl.annotations.InjectMockKs;

@RunWith(MockitoJUnitRunner.class)
public class PantallaPrincipalTest {

    @Mock
    Button botoEmpleat,botoDepartament,botoServei, botoBeca,botoEstudiant,botoEscola,botoSessio ;

    @InjectMockKs
    PantallaPrincipal pantallaPrincipal;

    @Test
    public void nomDepartament(){
          pantallaPrincipal.onCreate(new Bundle());

    }

}