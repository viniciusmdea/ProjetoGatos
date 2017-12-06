package com.example.rodolfo.projetogatos;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rodolfo.projetogatos.Classes.Gato;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class TelaGatos extends AppCompatActivity {

    private Gato gato;
    private ImageView iv_Gato;
    private TextView tv_gatoNome, tv_CaracteristicasGato;
    private final static String TAG = "TelaGato";
    private ArrayList<Bitmap> fotos;
    private StorageReference storageReference;
    private final Context ctx = this;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_gatos);

        storageReference = FirebaseStorage.getInstance().getReference().child("fotos");

        iv_Gato = (ImageView) findViewById(R.id.iv_Gato);
        tv_CaracteristicasGato = (TextView) findViewById(R.id.tv_CaracteristicasGato);
        tv_gatoNome = (TextView) findViewById(R.id.tv_nomeGato);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        Intent intent = getIntent();
        if (intent.hasExtra("GatoIntent")) {
            gato = (Gato) intent.getExtras().getSerializable("GatoIntent");
            Log.i(TAG,"Ok intent");
        }
        else{
            Log.e(TAG,"Error extras");
            gato = null;
        }
        if (gato != null) {
            Log.i(TAG,"gat intent ok");
            if (gato.getNome() != null) {
                tv_gatoNome.setText(gato.getNome());
            } else {
                tv_gatoNome.setText("Sem nome");
            }

            if (gato.getCaracteristicas() != null) {
                tv_CaracteristicasGato.setText(gato.getCaracteristicas());
            } else {
                tv_CaracteristicasGato.setText("...");
            }

            try {
                getImagens();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        else{
            Log.e(TAG,"gato intent Error");
        }
    }

    public void ligar(View view){
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:968616055"));

        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions((Activity) this,
                    new String[]{android.Manifest.permission.CALL_PHONE},1);
            return;
        }
        startActivity(callIntent);
    }


    public void getImagens() throws IOException {
        Bitmap imagem;
        StorageReference ref = storageReference.child(gato.getKey()).child("Foto-0");
        final File localFile = File.createTempFile("images", "jpg");

        ref.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                Bitmap foto = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                progressBar.setVisibility(View.GONE);
                iv_Gato.setVisibility(View.VISIBLE);
                iv_Gato.setImageBitmap(foto);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(TAG,"Foto testada:  fotos/"+gato.getKey()+"/"+"Foto-0");
                Log.e(TAG,e.toString());
                iv_Gato.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                iv_Gato.setImageResource(R.drawable.gatinho);
            }
        });

    }

}
