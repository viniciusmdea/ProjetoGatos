package com.example.rodolfo.projetogatos;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.TextureView;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.rodolfo.projetogatos.Classes.Gato;
import com.example.rodolfo.projetogatos.Classes.ListaGatos;
import com.example.rodolfo.projetogatos.Classes.Usuario;
import com.example.rodolfo.projetogatos.Classes.UsuarioSt;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Comment;

import java.io.Serializable;
import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    private Intent intent;
    private ArrayList<Usuario> usuarios;
    private Usuario usuario;
    private static ListaGatos listaGatos = ListaGatos.getInstance();

    private FirebaseAuth mAuth;

    private EditText editLogin;
    private EditText editsenha;

    private final static String TAG = "Tela Login";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editLogin = (EditText) findViewById(R.id.editLogin);
        editsenha = (EditText) findViewById(R.id.editSenha);


        mAuth = FirebaseAuth.getInstance();

        //Teste
        ArrayList<Bitmap> fotos = new ArrayList<Bitmap>();
        ArrayList<Gato> gatos = new ArrayList<Gato>();
        Bitmap imagem = BitmapFactory.decodeResource(this.getResources(), R.drawable.gatinho);
        fotos.add(imagem);
        fotos.add(BitmapFactory.decodeResource(this.getResources(), R.drawable.perfil));
        listaGatos.addGato(new Gato("Bichano",fotos,"E um gato ai",new LatLng(-23.547428, -46.719366)));
        listaGatos.addGato(new Gato("Bichinho",fotos,"Mais um gato",new LatLng(-23.589454, -46.661284)));

        usuario = new Usuario("joao Silva","login","123456",gatos);
        UsuarioSt usuarioSt = UsuarioSt.getInstance();
        usuarioSt.setUsuario(usuario);

    }



    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }


    public void cadastroClick(View view) {
        Intent intent = new Intent(LoginActivity.this, TelaCadastro.class);
        startActivity(intent);
    }

    public void entrarClick(View view){

        String email = editLogin.getText().toString();
        String password = editsenha.getText().toString();

        if (!email.isEmpty() && !password.isEmpty()) {
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "Cadastro:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                Intent intent = new Intent(LoginActivity.this, MenuInicial.class);
                                startActivity(intent);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "signInWithEmail:failure", task.getException());
                                Toast.makeText(LoginActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
        else{
            Toast.makeText(LoginActivity.this, "Digite email é login",
                    Toast.LENGTH_SHORT).show();
        }
    }



}
