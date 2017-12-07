package com.example.rodolfo.projetogatos;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.rodolfo.projetogatos.Classes.Gato;
import com.example.rodolfo.projetogatos.Classes.ListaGatos;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Comment;

import java.util.ArrayList;

public class TelaMaps extends AppCompatActivity {

    private com.google.android.gms.maps.MapFragment mapFragment;
    private Intent intent;
    private static String TAG = "TelaMaps";
    Context ctx;

    private Location currentLocation = null;
    //permite acesso ao GPS
    private LocationManager locationManager;
    //notificado quando eventos de GPS acontecem
    private LocationListener locationListener =
            new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    currentLocation = location;
                }
                @Override
                public void onStatusChanged(String provider, int status,
                                            Bundle extras) {
                }
                @Override
                public void onProviderEnabled(String provider) {
                }
                @Override
                public void onProviderDisabled(String provider) {
                }
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_maps);

        ctx = this;
        mapFragment = com.google.android.gms.maps.MapFragment.newInstance();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.mapFragment, mapFragment);
        fragmentTransaction.commit();
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        mapFragment.getMapAsync(new MapFragment(ctx,locationManager));

    }


}
