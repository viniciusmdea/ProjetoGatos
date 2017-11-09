package com.example.rodolfo.projetogatos.Classes;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by vinic on 02/11/2017.
 */

public class Gato {
    ArrayList<Integer> fotos;
    String Caracteristicas;
    int Cor;

    public Gato(ArrayList<Integer> fotos, String caracteristicas, int cor) {
        this.fotos = fotos;
        Caracteristicas = caracteristicas;
        Cor = cor;
    }

    public ArrayList<Integer> getFotos() {
        return fotos;
    }

    public void setFotos(ArrayList<Integer> fotos) {
        this.fotos = fotos;
    }

    public String getCaracteristicas() {
        return Caracteristicas;
    }

    public void setCaracteristicas(String caracteristicas) {
        Caracteristicas = caracteristicas;
    }

    public int getCor() {
        return Cor;
    }

    public void setCor(int cor) {
        Cor = cor;
    }
}
