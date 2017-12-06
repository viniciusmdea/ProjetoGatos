package com.example.rodolfo.projetogatos;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.rodolfo.projetogatos.Classes.Gato;
import com.example.rodolfo.projetogatos.Classes.ListaGatos;
import com.example.rodolfo.projetogatos.Classes.Usuario;
import com.example.rodolfo.projetogatos.Classes.UsuarioSt;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.w3c.dom.Comment;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MenuInicial extends AppCompatActivity {
    private Intent intent;
    Usuario user;
    ArrayList<Gato> gatos;
    private static String TAG = "Menu Inicial";
    private static ListaGatos listaGatos = ListaGatos.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_inicial);

        listaGatosDB();

        user = UsuarioSt.getInstance().getUsuario();
        gatos = new ArrayList<Gato>();


        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions((Activity) this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION,
                            android.Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
        }
    }

    public void perfilClick(View view) {
        intent = new Intent(this, TelaUsuario.class);
        this.startActivity(intent);
    }

    public void adotarClick(View view) {
        intent = new Intent(this, TelaMaps.class);
        //intent.putExtra("MenuIntent",gatos);
        this.startActivity(intent);
    }

    public void disponibilizarClick(View view) {
        intent = new Intent(this, CadastroGatos.class);
        //intent.putExtra("MenuIntent",gatos);
        this.startActivity(intent);
    }


    private void listaGatosDB() {
        DatabaseReference mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("gatos");
        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                String keyGato = dataSnapshot.getKey();
                Log.d(TAG, "onChildAdded:" + dataSnapshot.getKey());

                String nomeGato = null, caractGato = null, str_possGato = null, teleGato = null;
                LatLng latLngGato = null;
                int gatoid = 0;
                final ArrayList<Bitmap> listaImagens = new ArrayList<Bitmap>();
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    if (child.getKey().equals("nome")) {
                        nomeGato = child.getValue().toString();
                    }
                    if (child.getKey().equals("caracteristicas")) {
                        caractGato = child.getValue().toString();
                    }
                    if (child.getKey().equals("telefone")) {
                        teleGato = child.getValue().toString();
                    }
                    if (child.getKey().equals("possicao")) {
                        str_possGato = child.getValue().toString();
                        String PossLit[] = str_possGato.split(",");
                        if (PossLit == null) {
                            latLngGato = null;
                        }
                        Log.e(TAG, PossLit.toString());
                        latLngGato = new LatLng(Double.valueOf(PossLit[0]), Double.valueOf(PossLit[1]));
                    }
                    Log.e(TAG, "Testes:" + nomeGato + ":" + caractGato + ":" + str_possGato + ":" + teleGato);
                    Gato gatoNovo = new Gato(keyGato,nomeGato, caractGato, latLngGato, teleGato);

                    listaGatos.addGato(gatoNovo);
                    gatoid++;
                }


                //Log.i(TAG,dataSnapshot.getValue().toString());
                ;
                // ...
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildChanged:" + dataSnapshot.getKey());

                String commentKey = dataSnapshot.getKey();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.d(TAG, "onChildRemoved:" + dataSnapshot.getKey());

                // A comment has changed, use the key to determine if we are displaying this
                // comment and if so remove it.
                String commentKey = dataSnapshot.getKey();

                // ...
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildMoved:" + dataSnapshot.getKey());

                // A comment has changed position, use the key to determine if we are
                // displaying this comment and if so move it.
                Comment movedComment = dataSnapshot.getValue(Comment.class);
                String commentKey = dataSnapshot.getKey();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "postComments:onCancelled", databaseError.toException());
            }
        };
        mDatabaseRef.addChildEventListener(childEventListener);
    }

    /*public void addGato(DataSnapshot child, String keyGato, final int gatoid){
        for (int i = 0; i < Integer.valueOf(child.getValue().toString()); i++) {
            StorageReference refFoto = storageReference.child(keyGato).child("Foto-" + i);
            Log.i(TAG, refFoto.toString());
            refFoto.getDownloadUrl().
                    addOnSuccessListener(new OnSuccessListener<Uri>() {
                                             @Override
                                             public void onSuccess(Uri uri) {
                                                 final String urlString = uri.toString();
                                                 new Thread(new Runnable() {
                                                     public void run() {
                                                         try {
                                                             URL url = new URL(urlString.toString());
                                                             HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                                                             Bitmap bitmap = BitmapFactory.decodeStream(urlConnection.getInputStream());
                                                             ListaGatos.getInstance().getGatos().get(gatoid).getFotos().add(bitmap);
                                                             Log.i(TAG, "Carregou Foto com sucesso, tamanho:"+String.valueOf(ListaGatos.getInstance().getGatos().get(gatoid).getFotos().size()));
                                                         } catch (IOException e) {
                                                             Log.e(TAG, "Error ao carregar Foto: " + e.toString());
                                                             e.printStackTrace();
                                                         }
                                                     }
                                                 }).start();


                                             }
                                         }
                    );
        }
    }*/

}
