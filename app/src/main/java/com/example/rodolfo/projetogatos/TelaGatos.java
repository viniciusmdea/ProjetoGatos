package com.example.rodolfo.projetogatos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rodolfo.projetogatos.Classes.Gato;

import java.util.ArrayList;

public class TelaGatos extends AppCompatActivity {

    private Gato gato;
    private ImageView iv_Gato;
    private TextView tv_CaracteristicasGato;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_gatos);
        iv_Gato = (ImageView) findViewById(R.id.iv_Gato);
        tv_CaracteristicasGato = (TextView) findViewById(R.id.tv_CaracteristicasGato);

        iv_Gato.setImageResource(R.drawable.gatinho);
        tv_CaracteristicasGato.setText("E um gato");
        //tv_CaracteristicasGato.setText(gato.getCaracteristicas());
    }


}
