package com.example.applicazionechimica;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class ListaEliminaQtaActivity extends AppCompatActivity {
    ElementoAdapter elementoAdapter= new ElementoAdapter();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide(); //nasconde barra
        ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            if(result.getData()==null)  {
                                Intent aggiungiReagente = new Intent(ListaEliminaQtaActivity.this, RimuoviQtaActivity.class);
                                setResult(Activity.RESULT_OK, aggiungiReagente);
                                ListView listView= findViewById(R.id.listaQtaEliminare);
                                listView.setAdapter(elementoAdapter);
                            }
                        }

                    }
                });

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_elimina_qta);
        ListView listView= findViewById(R.id.listaQtaEliminare);
        listView.setAdapter(elementoAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent= new Intent(ListaEliminaQtaActivity.this, RimuoviQtaActivity.class);
                intent.putExtra("indice",i);
                someActivityResultLauncher.launch(intent);
            }
        });
        FloatingActionButton home= findViewById(R.id.home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1= new Intent(ListaEliminaQtaActivity.this, MainActivity.class);
                startActivity(intent1);
            }
        });
    }
}