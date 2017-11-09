package com.example.rodolfo.projetogatos;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

/**
 * Created by vinic on 02/11/2017.
 */

public class CustomMarker extends FragmentActivity implements GoogleMap.OnMarkerClickListener{
    private Marker maker;
    Intent intent;
    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }
}
