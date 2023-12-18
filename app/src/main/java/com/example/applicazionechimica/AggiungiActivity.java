package com.example.applicazionechimica;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Collections;
import java.util.Comparator;


public class AggiungiActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide(); //nasconde barra
        ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        Intent data = result.getData();
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            if (data != null) {
                                Intent aggiungiReagente = new Intent(AggiungiActivity.this, AggiungiNuoviReagenti.class);
                                setResult(Activity.RESULT_OK, aggiungiReagente);
                            }
                        }
                    }
                });
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_aggiungi);

        Button nuovo= findViewById(R.id.nuova);
        Button esistente = findViewById(R.id.esistente);
        FloatingActionButton home= findViewById(R.id.home);



        esistente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AggiungiActivity.this, ListaAggiungiQtaActivity.class);
                someActivityResultLauncher.launch(intent);
            }
        });

        nuovo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AggiungiActivity.this, AggiungiNuoviReagenti.class);
                someActivityResultLauncher.launch(intent);
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}

