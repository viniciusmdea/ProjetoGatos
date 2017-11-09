package com.example.rodolfo.projetogatos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rodolfo.projetogatos.Classes.Gato;

import java.util.ArrayList;

public class CadastroGatos extends AppCompatActivity {

    Gato gato;
    EditText et_Descricao;
    ImageView im_gatoFoto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_gatos);
        et_Descricao = (EditText) findViewById(R.id.et_Descricao);
        im_gatoFoto = (ImageView) findViewById(R.id.im_gatoFoto);
        /*if(gato.getCaracteristicas() != null){
            et_Descricao.setText(gato.getCaracteristicas());
        }
        if(gato.getFotos() != null){
            et_Descricao.setText(gato.getFotos().get(0));
        }
        */
        im_gatoFoto.setImageResource(R.drawable.gatinho);
        et_Descricao.setText("Gatinho");
    }

    public void cadastro(View view){
        String caracteristica = et_Descricao.getText().toString();
    }

}
