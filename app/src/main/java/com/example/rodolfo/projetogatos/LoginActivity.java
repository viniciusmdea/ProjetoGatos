package com.example.rodolfo.projetogatos;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.TextureView;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.rodolfo.projetogatos.Classes.Gato;
import com.example.rodolfo.projetogatos.Classes.Usuario;

import java.io.Serializable;
import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    private Intent intent;
    private ArrayList<Usuario> usuarios;
    private Usuario usuario;

    private EditText editLogin;
    private EditText editsenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editLogin = (EditText) findViewById(R.id.editLogin);
        editsenha = (EditText) findViewById(R.id.editSenha);


        //Teste
        ArrayList<Integer> fotos = new ArrayList<Integer>();
        ArrayList<Gato> gatos = new ArrayList<Gato>();
        fotos.add(R.drawable.gatinho);
        fotos.add(R.drawable.perfil);
        gatos.add(new Gato(fotos,"E um gato ai", Color.RED));
        gatos.add(new Gato(fotos,"Mais um gato", Color.BLUE));
        usuario = new Usuario("login","123456",gatos);

    }


    private boolean login(String login, String senha){
        return login.equals(usuario.getEmail()) && senha.equals(usuario.getSenha());
    }


    public void entrarClick(View view){

        String login = editLogin.getText().toString();
        String senha = editsenha.getText().toString();

        Log.i("ProjetoGatos:Login", "Login:" + login + " Senha:" + senha);
        Log.i("ProjetoGatos:Login", "UsuarioLogin:" + usuario.getEmail() + " UsuarioSenha:" + usuario.getSenha());

        if(login(login,senha)) {
            intent = new Intent(this, MenuInicial.class);
            //intent.putExtra("TelaLogin", (Serializable) usuario); //Optional parameters
            this.startActivity(intent);
        }
        else{
            Toast.makeText(this,"Login ou senha invalido",Toast.LENGTH_LONG).show();
        }
    }
}
