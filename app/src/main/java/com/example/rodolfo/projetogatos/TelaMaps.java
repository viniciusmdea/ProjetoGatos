package com.example.rodolfo.projetogatos;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.rodolfo.projetogatos.Classes.Gato;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class TelaMaps extends AppCompatActivity {

    private MapFragment mapFragment;
    Intent intent;
    Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_maps);

        ctx = this;
        mapFragment = MapFragment.newInstance();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.mapFragment, mapFragment);
        fragmentTransaction.commit();

        mapFragment.getMapAsync(new MapFragmentClass());

        //Testando Gatos

    }

    private class MapFragmentClass extends FragmentActivity
            implements OnMapReadyCallback {
        @Override
        public void onMapReady(GoogleMap googleMap) {
            googleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(-50, -30))
                    .title("Marker"));

            googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    intent = new Intent(ctx, TelaGatos.class);
                    ctx.startActivity(intent);
                    return false;
                }
            });
        }
    }
}
