package com.example.rodolfo.projetogatos;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.rodolfo.projetogatos.Classes.Gato;
import com.example.rodolfo.projetogatos.Classes.ListaGatos;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by vinic on 15/11/2017.
 */

public class MapFragment extends FragmentActivity implements OnMapReadyCallback {

    private static final String TAG = "Map FragmentActivity";
    private Context ctx;
    private ListaGatos listaGatos = ListaGatos.getInstance();

    Map<Marker, Gato> mapMaker = new HashMap<Marker, Gato>();

    private GoogleMap mMap;
    //permite acesso ao GPS
    private LocationManager locationManager;

    LatLng localAtual;

    private FusedLocationProviderClient mFusedLocationClient;
    private int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1;

    MapFragment(Context context, LocationManager locationManager) {
        ctx = context;
        this.locationManager = locationManager;
    }

    //Desenhando gatos no mapa
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        updateLocationUI();

        getLatLng();

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Intent intent;
                intent = new Intent(ctx, TelaGatos.class);
                Gato gato = mapMaker.get(marker);
                intent.putExtra("GatoIntent",(Serializable) gato);
                Log.i("intent Gato","nome e:"+gato.getNome());
                ctx.startActivity(intent);
                return false;
            }
        });

        putMarkers(googleMap);
    }

    //Desenhando localização atual
    private void updateLocationUI() {
        if (mMap == null) {
            return;
        }
        try {
            if (true) {
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(true);
            } else {
                mMap.setMyLocationEnabled(false);
                mMap.getUiSettings().setMyLocationButtonEnabled(false);
            }
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }
    }


    //Pegando todos os gatos
    public void putMarkers(GoogleMap googleMap) {

        BitmapDrawable bitmapdraw=(BitmapDrawable) ctx.getResources().getDrawable(R.mipmap.caticon);
        Bitmap b = bitmapdraw.getBitmap();
        Bitmap smallMarker = Bitmap.createScaledBitmap(b, b.getWidth()/2, b.getHeight()/2, false);

        for (Gato gato : listaGatos.getGatos()) {
            if (gato.getPossicao()!=null) {
                Marker gatoMarker = googleMap.addMarker(new MarkerOptions()
                        .position(gato.getPossicao())
                        .title(gato.getNome())
                        .icon(BitmapDescriptorFactory.fromBitmap(smallMarker)));
                mapMaker.put(gatoMarker,gato);
            }
        }

    }

    //Pegando localização
    public void getLatLng() {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(ctx);

        if (ActivityCompat.checkSelfPermission(ctx, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(ctx, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.e(TAG,"Sem permissão");
            return;
        }

        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener((Activity) ctx, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            localAtual = new LatLng(location.getLatitude(), location.getLongitude());
                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(localAtual, 13f));
                            // Logic to handle location object
                        }
                        else{
                            Log.e(TAG,"Sem localização");
                        }
                    }
                });
    }
}
