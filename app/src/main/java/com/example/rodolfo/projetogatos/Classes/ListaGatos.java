package com.example.rodolfo.projetogatos.Classes;

import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Comment;

import java.util.ArrayList;

/**
 * Created by vinic on 13/11/2017.
 */

public class ListaGatos {
    ArrayList<Gato> gatos;
    private static final ListaGatos ourInstance = new ListaGatos();

    private static String TAG = "ListaGatos";

    public static ListaGatos getInstance() {
        //ListaGatosDB();
        return ourInstance;
    }


    private ListaGatos() {
        gatos = new ArrayList<Gato>();
    }

    public void addGato(Gato gato){
        gatos.add(gato);
    }

    public ArrayList<Gato> getGatos() {
        return gatos;
    }

    public void setGatos(ArrayList<Gato> gatos) {
        this.gatos = gatos;
    }
}
