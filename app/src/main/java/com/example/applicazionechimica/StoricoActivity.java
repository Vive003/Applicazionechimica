package com.example.applicazionechimica;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class StoricoActivity extends AppCompatActivity {


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        getSupportActionBar().hide(); //nasconde barra
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storico);
        ListView listView= findViewById(R.id.listaAnni);
        StoricoAdapter elementoAdapter= new StoricoAdapter();
        listView.setAdapter(elementoAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent= new Intent(StoricoActivity.this, ElementiAnnoStoricoActivity.class);
                intent.putExtra("indice",i);
                startActivity(intent);
            }
        });

        Button faiStorico = findViewById(R.id.faiStorico);
        faiStorico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(StoricoActivity.this).setMessage("Sei sicuro di voler fare lo storico di questo anno?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @RequiresApi(api = Build.VERSION_CODES.O)
                            public void onClick(DialogInterface dialog, int whichButton) {
                                Anno elementoStorico= new Anno();
                                LocalDate localDate= LocalDate.now();
                                int anno=localDate.getYear();
                                int mese= localDate.getMonth().getValue();
                                int giorno= localDate.getDayOfMonth();
                                    System.out.println(giorno);
                                if(mese<9)  {
                                    elementoStorico.anno=anno-1+"-"+anno;
                                }
                                else    {
                                    elementoStorico.anno=anno+"-"+anno+1;
                                }
                                FirebaseDatabase database= FirebaseDatabase.getInstance();
                                DatabaseReference reference= database.getReference("/storico/" + elementoStorico.anno);
                                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                        HashMap<String, HashMap<String, Object>> hashMapHashMap = (HashMap<String, HashMap<String, Object>>) snapshot.getValue();
                                        if(hashMapHashMap==null)    {
                                            for (int i = 0; i < MainActivity.listaElementi.size(); i++) {
                                                elementoStorico.listaElementoStorico.add(new ElementoStorico(MainActivity.listaElementi.get(i).nome, MainActivity.listaElementi.get(i).quantitaInizio, MainActivity.listaElementi.get(i).unitaMisura, MainActivity.listaElementi.get(i).numero));
                                                reference.child("/" + elementoStorico.listaElementoStorico.get(i).numero).updateChildren(elementoStorico.listaElementoStorico.get(i).toHashMap());
                                            }
                                        }
                                        else {

                                            for (int i = 0; i < MainActivity.listaStorico.size(); i++) {
                                                if(elementoStorico.anno.equals(MainActivity.listaStorico.get(i).anno))  {
                                                    for (int j = 0; j < MainActivity.listaStorico.get(i).listaElementoStorico.size(); j++) {
                                                        for (int k = 0; k < MainActivity.listaElementi.size(); k++) {
                                                            if (MainActivity.listaElementi.get(k).numero.equals(MainActivity.listaStorico.get(i).listaElementoStorico.get(j).numero))   {
                                                                MainActivity.listaStorico.get(i).listaElementoStorico.get(j).qta += MainActivity.listaElementi.get(k).quantitaInizio;
                                                                reference.child("/" +MainActivity.listaStorico.get(i).listaElementoStorico.get(j).numero).updateChildren(MainActivity.listaStorico.get(i).listaElementoStorico.get(j).toHashMap2());
                                                            }
                                                        }
                                                    }
                                                }
                                            }

                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                                    }
                                });


                                DatabaseReference reference1= database.getReference("/catalogo");
                                    reference1.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                            HashMap<String, HashMap<String, Object>> hashMapHashMap = (HashMap<String, HashMap<String, Object>>) snapshot.getValue();
                                            for (Map.Entry<String, HashMap<String, Object>> stringHashMapEntry : hashMapHashMap.entrySet()) {
                                                Map.Entry<String, HashMap<String, Object>> pair = (Map.Entry<String, HashMap<String, Object>>) stringHashMapEntry;
                                                for (int i = 0; i <MainActivity.listaElementi.size(); i++) {
                                                    HashMap<String, Object> map = new HashMap<>();
                                                    map.put("quantita_inizio", 0);
                                                    reference1.child("/"+(pair.getKey())).updateChildren(map);
                                                    MainActivity.listaElementi.get(i).quantitaInizio=0;
                                                }
                                            }
                                        }
                                        @Override
                                        public void onCancelled(@NonNull @NotNull DatabaseError error) {

                                        }
                                    });

                                Toast.makeText(StoricoActivity.this, "Storico scaricato con successo!", Toast.LENGTH_SHORT).show();
                               finish();
                            }
                        })
                        .setNegativeButton("Annulla", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });


        FloatingActionButton home= findViewById(R.id.home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StoricoActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
