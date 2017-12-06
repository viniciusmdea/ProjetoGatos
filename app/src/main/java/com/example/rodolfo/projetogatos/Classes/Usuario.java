package com.example.rodolfo.projetogatos.Classes;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by vinic on 02/11/2017.
 */

public class Usuario {

    private int foto;
    private String email;
    private String telefone;
    private String nomeCompleto;
    private ArrayList<Gato> gatos;
    private String senha;

    public Usuario(int foto, String email, String telefone, String nomeCompleto, String senha) {
        this.foto = foto;
        this.email = email;
        this.telefone = telefone;
        this.nomeCompleto = nomeCompleto;
        gatos = new ArrayList<Gato>();
        this.senha = senha;
    }

    public Usuario(String nomeCompleto, String email, String senha) {
        this.nomeCompleto = nomeCompleto;
        this.email = email;
        this.senha = senha;
    }

    public Usuario(String nomeCompleto, String email, String senha,ArrayList<Gato> gatos) {
        this.nomeCompleto = nomeCompleto;
        this.email = email;
        this.senha = senha;
        this.gatos = gatos;
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

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
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
