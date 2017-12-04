package com.example.rodolfo.projetogatos;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rodolfo.projetogatos.Classes.Gato;

import java.util.ArrayList;

public class TelaGatos extends AppCompatActivity {

    private Gato gato;
    private ImageView iv_Gato;
    private TextView tv_gatoNome, tv_CaracteristicasGato;
    private final static String TAG = "TelaGato";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_gatos);

        iv_Gato = (ImageView) findViewById(R.id.iv_Gato);
        tv_CaracteristicasGato = (TextView) findViewById(R.id.tv_CaracteristicasGato);
        tv_gatoNome = (TextView) findViewById(R.id.tv_nomeGato);

        Intent intent = getIntent();
        gato = (Gato) intent.getExtras().getSerializable("GatoIntent");

        if (gato != null) {
            Log.i(TAG,"gat intent ok");
            if (gato.getNome() != null) {
                tv_gatoNome.setText(gato.getNome());
            } else {
                tv_gatoNome.setText("Sem nome");
            }

            if (gato.getCaracteristicas() != null) {
                tv_CaracteristicasGato.setText(gato.getCaracteristicas());
            } else {
                tv_CaracteristicasGato.setText("...");
            }

            if (gato.getFotos() != null) {
                iv_Gato.setImageBitmap(gato.getFotos().get(0));
            }
        }
        else{
            Log.e(TAG,"gato intent Error");
        }


        //tv_CaracteristicasGato.setText(gato.getCaracteristicas());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


    }

    public void ligar(View view){
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:11968616055"));

        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions((Activity) this,
                    new String[]{android.Manifest.permission.CALL_PHONE},1);
            return;
        }
        startActivity(callIntent);
    }


}
