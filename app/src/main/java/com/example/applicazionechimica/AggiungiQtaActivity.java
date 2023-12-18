package com.example.applicazionechimica;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class AggiungiQtaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide(); //nasconde barra
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aggiungi_qta);
        EditText qtaAggiungere= findViewById(R.id.scriviQta);
        Button conferma= findViewById(R.id.conferma);
        TextView unita= findViewById(R.id.unita);
        Intent intent=new Intent();
        int i=getIntent().getExtras().getInt("indice");
        System.out.println(i);
        FloatingActionButton home= findViewById(R.id.home);
        unita.setText(MainActivity.listaElementi.get(i).unitaMisura);
        conferma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(AggiungiQtaActivity.this).setMessage("SEI SICURO DI VOLER AGGIUNGERE QUESTA QUANTITA'?")

                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("catalogo");
                                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                        HashMap<String, HashMap<String, Object>> hashMapHashMap = (HashMap<String, HashMap<String, Object>>) snapshot.getValue();
                                        for (Map.Entry<String, HashMap<String, Object>> stringHashMapEntry : hashMapHashMap.entrySet()) {
                                            Map.Entry<String, HashMap<String, Object>> pair = (Map.Entry<String, HashMap<String, Object>>) stringHashMapEntry;
                                            HashMap<String, Object> hashMapPair = pair.getValue();
                                            if(String.valueOf(hashMapPair.get("numero")).equals(String.valueOf((MainActivity.listaElementi.get(i).numero))))     {
                                                HashMap<String, Object> map = new HashMap<>();
                                                double qta;
                                                if(Double.parseDouble(qtaAggiungere.getText().toString())==0 || qtaAggiungere.getText().toString().equals("") || Double.parseDouble(qtaAggiungere.getText().toString()) <0)     {
                                                    qta=0;
                                                    Toast.makeText(AggiungiQtaActivity.this, "Non è possibile inserire una quantità negativa",Toast.LENGTH_LONG).show();
                                                }
                                                else    {
                                                    qta=Double.parseDouble(qtaAggiungere.getText().toString());
                                                    Toast.makeText(AggiungiQtaActivity.this, "Il reagente è stato aggiunto", Toast.LENGTH_SHORT).show();
                                                }
                                                map.put("quantita",(MainActivity.listaElementi.get(i).quantita+qta));
                                                databaseReference.child("/"+(pair.getKey())).updateChildren(map);
                                                MainActivity.listaElementi.get(i).quantita=MainActivity.listaElementi.get(i).quantita+qta;
                                            }

                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                                    }
                                });
                                dialog.dismiss();
                                setResult(Activity.RESULT_OK, intent);
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
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1= new Intent(AggiungiQtaActivity.this, MainActivity.class);
                startActivity(intent1);
            }
        });


    }
}