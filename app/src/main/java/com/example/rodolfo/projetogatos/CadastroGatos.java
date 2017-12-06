package com.example.rodolfo.projetogatos;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.rodolfo.projetogatos.Classes.Gato;
import com.example.rodolfo.projetogatos.Classes.ListaGatos;
import com.example.rodolfo.projetogatos.Classes.Usuario;
import com.example.rodolfo.projetogatos.Classes.UsuarioSt;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class CadastroGatos extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final String TAG = "Tela Cadastro Gato";

    private FusedLocationProviderClient mFusedLocationClient;
    private Gato gato;
    private EditText et_Descricao, et_Nome,et_telefone;
    private ImageView im_gatoFoto;
    private ArrayList<Bitmap> fotos;

    private LatLng latLng;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_gatos);
        et_Nome = (EditText) findViewById(R.id.et_Nome);
        et_Descricao = (EditText) findViewById(R.id.et_Descricao);
        et_telefone = (EditText) findViewById(R.id.et_telefone);
        im_gatoFoto = (ImageView) findViewById(R.id.im_gatoFoto);
        im_gatoFoto.setImageResource(R.drawable.gatinho);
        fotos = new ArrayList<Bitmap>();
        getLatLng();
    }


    @Override
    //Esta função le o retorno do app de camera, salvando o dado em um bitmap
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
//            storageReference = FirebaseStorage.getInstance().getReference().child("fotos");
//            Uri uri = imageBitmap.t
            im_gatoFoto.setImageBitmap(imageBitmap);
            fotos.add(imageBitmap);
        }
    }

    //Função chama aplicativo de camera atraves do intent
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //Verifica se exite um app de camera e chama ele para tirar a foto
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    //Tirando a foto
    public void tirarfoto(View view){
        dispatchTakePictureIntent();
    }

    //Função para criar e salvar o gato
    public void cadastro(View view) {
        String nome = et_Nome.getText().toString();
        String caracteristica = et_Descricao.getText().toString();
        String telefone =  et_telefone.getText().toString();

        gato = new Gato(nome,fotos,caracteristica,latLng,telefone);
        ListaGatos.getInstance().addGato(gato);
        gato.salvaFirebase(fotos);
        Toast.makeText(this,"Gato Cadastrado",Toast.LENGTH_SHORT).show();
        finish();
    }

    //Função para Pegando a localização
    public void getLatLng() {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                Log.e(TAG,"Sem permissão");
            return;
        }

        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            latLng = new LatLng(location.getLatitude(), location.getLongitude());
                        }
                        else{
                            Log.e(TAG,"Sem localização");
                        }
                    }
                });
    }

}
