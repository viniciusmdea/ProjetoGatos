package com.example.rodolfo.projetogatos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.rodolfo.projetogatos.Classes.Gato;
import com.example.rodolfo.projetogatos.Classes.Usuario;

import java.util.ArrayList;

public class MenuInicial extends AppCompatActivity {
    private Intent intent;
    Usuario user;
    ArrayList<Gato> gatos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_inicial);

        //Intent intentRecebido = getIntent();
        //user = (Usuario) intent.getSerializableExtra("TelaLogin");
        gatos = new ArrayList<Gato>();
    }

    public void perfilClick(View view){
        intent = new Intent(this, TelaUsuario.class);
        //intent.putExtra("MenuIntent",user);
        this.startActivity(intent);
    }

    public void adotarClick (View view){
        intent = new Intent(this, TelaMaps.class);
        //intent.putExtra("MenuIntent",gatos);
        this.startActivity(intent);
    }

    public void disponibilizarClick (View view){
        intent = new Intent(this, CadastroGatos.class);
        //intent.putExtra("MenuIntent",gatos);
        this.startActivity(intent);
    }
}
