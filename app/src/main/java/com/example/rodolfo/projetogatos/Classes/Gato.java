package com.example.rodolfo.projetogatos.Classes;

import android.graphics.Bitmap;
import android.util.Base64;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by vinic on 02/11/2017.
 */

public class Gato implements Serializable{
    private int id;
    private String nome;
    private ArrayList<Bitmap> fotos;
    private String caracteristicas;
    private transient LatLng possicao;
    private String telefone;
    private static final String TAG = "Classe Gato";


    public Gato(String nome, ArrayList<Bitmap> fotos, String caracteristicas, LatLng possicao) {
        this.nome = nome;
        this.fotos = fotos;
        this.caracteristicas = caracteristicas;
        this.possicao = possicao;
    }

    public Gato(String nome, ArrayList<Bitmap> fotos, String caracteristicas, LatLng possicao, String telefone) {
        this.nome = nome;
        this.fotos = fotos;
        this.caracteristicas = caracteristicas;
        this.possicao = possicao;
        this.telefone = telefone;
    }


    public void salvaFirebase(){
        DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference().child("gatos");
        DatabaseReference  listaGatos = mDatabase.push();
        String gatoKey = listaGatos .getKey();

        listaGatos.child("nome").setValue(nome);
        listaGatos.child("caracteristicas").setValue(caracteristicas);
        listaGatos.child("possicao").setValue(String.valueOf(possicao.latitude)+','+String.valueOf(possicao.longitude));
        listaGatos.child("telefone").setValue(telefone);
        Log.i(TAG,"Gato cadastrado "+"tamanho fotos:"+fotos.size());
        if (fotos.size() > 0){
            salvarImage(listaGatos);
        }
        //mDatabase.child("gato").child(nome).child("imagem").setValue(possicao.toString());
    }

    public ArrayList<Bitmap> getFotos() {
        return fotos;
    }

    public void setFotos(ArrayList<Bitmap> fotos) {
        this.fotos = fotos;
    }

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


    public void salvarImage(DatabaseReference ref){
        Log.i(TAG,"Iniciando salvar Imagem");
        int cont = 0;
        for (Bitmap bitmap : fotos){
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            String imageEncoded = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
            ref.child("imagens").child(String.valueOf(cont)).setValue(imageEncoded);
            Log.i(TAG,"Imagem "+cont+" cadastrada");
            cont++;
        }
    }
}
