package com.example.rodolfo.projetogatos.Classes;

import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Base64;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by vinic on 02/11/2017.
 */

public class Gato implements Serializable{
    private int id;
    private String nome;
    //private transient ArrayList<Bitmap> fotos;
    private String caracteristicas;
    private transient LatLng possicao;
    private String key;

    private String telefone;
    private static final String TAG = "Classe Gato";

    public Gato(String key, String nome, String caracteristicas, LatLng possicao, String telefone) {
        this.nome = nome;
        this.key = key;
        this.caracteristicas = caracteristicas;
        this.possicao = possicao;
        this.telefone = telefone;
    }

    public Gato(String nome, ArrayList<Bitmap> fotos, String caracteristicas, LatLng possicao) {
        this.nome = nome;
        //this.fotos = fotos;
        this.caracteristicas = caracteristicas;
        this.possicao = possicao;
    }

    public Gato(String nome, ArrayList<Bitmap> fotos, String caracteristicas, LatLng possicao, String telefone) {
        this.nome = nome;
        //this.fotos = fotos;
        this.caracteristicas = caracteristicas;
        this.possicao = possicao;
        this.telefone = telefone;
    }


    public void salvaFirebase(ArrayList<Bitmap> fotos){
        DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference().child("gatos");
        DatabaseReference  listaGatos = mDatabase.push();
        String gatoKey = listaGatos .getKey();
        this.key = gatoKey;

        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        StorageReference fotosReference = storageReference.child("fotos").child(gatoKey);

        listaGatos.child("nome").setValue(nome);
        listaGatos.child("caracteristicas").setValue(caracteristicas);
        listaGatos.child("possicao").setValue(String.valueOf(possicao.latitude)+','+String.valueOf(possicao.longitude));
        listaGatos.child("telefone").setValue(telefone);
        Log.i(TAG,"Gato cadastrado "+"tamanho fotos:"+fotos.size());
        if (fotos.size() > 0){
            salvarImage(fotosReference,listaGatos,fotos);
        }
    }
/*
    public ArrayList<Bitmap> getFotos() {
        return fotos;
    }

    public void setFotos(ArrayList<Bitmap> fotos) {
        this.fotos = fotos;
    }*/

    public String getCaracteristicas() {
        return caracteristicas;
    }

    public void setCaracteristicas(String caracteristicas) {
        caracteristicas = caracteristicas;
    }


    public LatLng getPossicao() {
        return possicao;
    }

    public void setPossicao(LatLng possicao) {
        this.possicao = possicao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void salvarImage(StorageReference ref, DatabaseReference refData, ArrayList<Bitmap> fotos){
        Log.i(TAG,"Iniciando salvar Imagem");
        refData.child("imagens").setValue(String.valueOf(fotos.size()));

        int cont =0;
        for (Bitmap bitmap : fotos) {
            StorageReference FotoReferencia = ref.child("Foto-"+cont);
            cont++;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] data = baos.toByteArray();
            UploadTask uploadTask = FotoReferencia.putBytes(data);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    Log.e("Imagem Upload","Error");
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Uri downloadUrl = taskSnapshot.getDownloadUrl();
                    Log.i("Imagem Upload","Ok");
                }
            });
         }
    }
}
