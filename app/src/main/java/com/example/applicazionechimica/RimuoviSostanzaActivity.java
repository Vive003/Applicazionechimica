package com.example.applicazionechimica;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class RimuoviSostanzaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide(); //nasconde barra
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rimuovi_sostanza);
        ListView listView= findViewById(R.id.listaElementiEliminare);
        FloatingActionButton home= findViewById(R.id.home);
        ElementoAdapter elementoAdapter= new ElementoAdapter();
        listView.setAdapter(elementoAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                new AlertDialog.Builder(RimuoviSostanzaActivity.this).setMessage("SEI SICURO DI VOLER ELIMINARE LA SOSTANZA?")

                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                FirebaseDatabase firebaseDatabase= FirebaseDatabase.getInstance();
                               /* DatabaseReference reference = firebaseDatabase.getReference("catalogo/"+MainActivity.listaElementi.get(i).numero);
                                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        for (DataSnapshot snapshot1 : snapshot.getChildren())   {
                                            snapshot1.getRef().removeValue();
                                        }


                                    }
                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {}
                                });*/
                                Query query= FirebaseDatabase.getInstance().getReference("catalogo/").orderByChild("numero").equalTo(MainActivity.listaElementi.get(i).numero);
                                query.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                        for (DataSnapshot snapshot1 : snapshot.getChildren())   {
                                            snapshot1.getRef().removeValue();
                                        }
                                        MainActivity.listaElementi.remove(i);
                                    }

                                    @Override
                                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                                    }
                                });
                                Toast.makeText(RimuoviSostanzaActivity.this, "OPERAZIONE RIUSCITA CON SUCCESSO", Toast.LENGTH_LONG).show();
                                finish();
                                dialog.dismiss();

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
                Intent intent1= new Intent(RimuoviSostanzaActivity.this, MainActivity.class);
                startActivity(intent1);
            }
        });
    }
}