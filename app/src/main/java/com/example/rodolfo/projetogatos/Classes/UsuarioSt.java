package com.example.rodolfo.projetogatos.Classes;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by vinic on 12/11/2017.
 */

public class UsuarioSt {

    private Usuario usuario;

    private static final UsuarioSt ourInstance = new UsuarioSt();

    public static UsuarioSt getInstance() {
        return ourInstance;
    }

    private UsuarioSt() {
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
