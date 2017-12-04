package com.example.rodolfo.projetogatos;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class TelaCadastro extends AppCompatActivity {

    private EditText ed_nomeUser, ed_emailUser, ed_senhaUser,ed_ConfirmSenha;
    private final String TAG = "Tela Cadastro";
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_cadastro);
        ed_nomeUser = (EditText) findViewById(R.id.ed_nomeUser);
        ed_emailUser = (EditText) findViewById(R.id.ed_emailUser);
        ed_senhaUser = (EditText) findViewById(R.id.ed_senhaUser);
        ed_ConfirmSenha = (EditText) findViewById(R.id.ed_ConfirmSenha);

        mAuth = FirebaseAuth.getInstance();

    }

    public void cadastrar(View view) {
        String email = ed_emailUser.getText().toString();
        String password = ed_senhaUser.getText().toString();

        if (!email.isEmpty() && !password.isEmpty()) {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "createUserWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();

                                String nome = ed_nomeUser.getText().toString();

                                if (nome != null && !nome.isEmpty()) {
                                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                            .setDisplayName(nome)
                                            .build();

                                    user.updateProfile(profileUpdates);
                                }

                                Toast.makeText(TelaCadastro.this, "Cadastro Ok!",
                                        Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(TelaCadastro.this, MenuInicial.class);
                                startActivity(intent);

                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                Toast.makeText(TelaCadastro.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
        else{
            Toast.makeText(TelaCadastro.this, "Digite email é login",
                    Toast.LENGTH_SHORT).show();
        }
    }
}
