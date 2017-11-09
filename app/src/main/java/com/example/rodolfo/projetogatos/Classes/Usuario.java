package com.example.rodolfo.projetogatos.Classes;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by vinic on 02/11/2017.
 */

public class Usuario implements Serializable {

    private int foto;
    private String email;
    private int telefone;
    private String nomeCompleto;
    private ArrayList<Gato> gatos;
    private LatLng local;
    private String senha;

    public Usuario(int foto, String email, int telefone, String nomeCompleto, String senha) {
        this.foto = foto;
        this.email = email;
        this.telefone = telefone;
        this.nomeCompleto = nomeCompleto;
        gatos = new ArrayList<Gato>();
        this.senha = senha;
    }

    public Usuario(String email, String senha) {
        this.email = email;
        this.senha = senha;
    }

    public Usuario(String email, String senha,ArrayList<Gato> gatos) {
        this.email = email;
        this.senha = senha;
        this.gatos = gatos;
    }


    public LatLng getLocal() {
        return local;
    }

    public void setLocal(LatLng local) {
        this.local = local;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public int getFoto() {
        return foto;
    }

    public void setFoto(int foto) {
        this.foto = foto;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getTelefone() {
        return telefone;
    }

    public void setTelefone(int telefone) {
        this.telefone = telefone;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }


    public void addGatos(Gato gato){
        gatos.add(gato);
    }

    public void removeGato(int id){
        gatos.remove(id);
    }
}
