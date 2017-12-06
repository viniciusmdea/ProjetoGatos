package com.example.rodolfo.projetogatos;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rodolfo.projetogatos.Classes.Usuario;
import com.example.rodolfo.projetogatos.Classes.UsuarioSt;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.UserProfileChangeRequest;

public class TelaUsuario extends AppCompatActivity {

    private Usuario userSt;

    private EditText ed_nome;
    private EditText ed_email;
    private ImageView im_foto;
    private final String TAG = "Tela Usuario";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_usuario);

        Intent intentRecebido = getIntent();
        userSt = UsuarioSt.getInstance().getUsuario();

        ed_nome = (EditText) findViewById(R.id.tv_nome);
        ed_email = (EditText) findViewById(R.id.tv_email);
        im_foto = (ImageView) findViewById(R.id.im_fotoUsua);

        ed_nome.setText(userSt.getNomeCompleto());
        ed_email.setText(userSt.getEmail());

        //ed_nome.setKeyListener(null);
        //ed_email.setKeyListener(null);
        getUser();
    }

    public void getUser(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String texto = "";
            // Name, email address, and profile photo Url
            String name = user.getDisplayName();
            if (name != null){
                ed_nome.setText(name);
            }
            String email = user.getEmail();
            if (email != null){
                ed_email.setText(email);
            }

            //Uri photoUrl = user.getPhotoUrl();

            boolean emailVerified = user.isEmailVerified();


            String uid = user.getUid();
            Log.e("Tela User",texto);
        }
    }

    public void editar(View view){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        String nomeEdit = ed_nome.getText().toString();
        if (nomeEdit != null && !nomeEdit.isEmpty()) {
            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                    .setDisplayName(nomeEdit)
                    .build();


            user.updateProfile(profileUpdates)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "User profile updated.");
                            }
                        }
                    });
        }
        String email = ed_email.getText().toString();
        if (email != null && !email.isEmpty())
        user.updateEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "User email address updated.");
                        }
                    }
                });

        String senha = null;//ed
        if (senha != null && !senha.isEmpty()) {
            user.updatePassword(senha).
                    addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                        }
                    });
        }

        finish();
    }
}
