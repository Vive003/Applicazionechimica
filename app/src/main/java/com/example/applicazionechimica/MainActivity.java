package com.example.applicazionechimica;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

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

public class MainActivity extends AppCompatActivity {
    static ArrayList<Elemento> listaElementi = new ArrayList<>();
    static ArrayList<Anno> listaStorico = new ArrayList<>();
    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            getSupportActionBar().hide(); //nasconde barra
            setContentView(R.layout.activity_main);

            Button inventario= findViewById(R.id.inventario);
            Button aggiungi= findViewById(R.id.aggiungi);
            Button rimuovi= findViewById(R.id.rimuovi);
            Button storico = findViewById(R.id.storico);
            FloatingActionButton logout = findViewById(R.id.logout);

            DatabaseReference firebaseDatabase= FirebaseDatabase.getInstance().getReference("catalogo/");
            firebaseDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    listaElementi.clear();
                    if (snapshot.exists()) {
                        HashMap<String, HashMap<String, Object>> hashMapHashMap = (HashMap<String, HashMap<String, Object>>) snapshot.getValue();
                        for (Map.Entry<String, HashMap<String, Object>> stringHashMapEntry : hashMapHashMap.entrySet()) {
                            Map.Entry<String, HashMap<String, Object>> pair = (Map.Entry<String, HashMap<String, Object>>) stringHashMapEntry;
                            HashMap<String, Object> hashMapPair = pair.getValue();
                            listaElementi.add(new Elemento(String.valueOf(hashMapPair.get("armadio")),String.valueOf(hashMapPair.get("nome")),Double.parseDouble(String.valueOf(hashMapPair.get("quantita_inizio"))),String.valueOf(hashMapPair.get("u_misura")), String.valueOf(hashMapPair.get("numero")),Double.parseDouble(String.valueOf(hashMapPair.get("quantita")))));
                        }
                       /* DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("catalogo");
                        for (int i = 0; i < listaElementi.size(); i++) {
                            int posizione = i;
                            databaseReference.child(String.valueOf(listaElementi.get(i).numero)).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                    if (snapshot.exists()) {
                                        HashMap<String, Object> hashMap = (HashMap<String, Object>) snapshot.getValue();
                                        listaElementi.get(posizione).armadio= ;
                                        listaElementi.get(posizione).nome= ;
                                        listaElementi.get(posizione).quantitaInizio= ;
                                        listaElementi.get(posizione).unitaMisura= ;
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                                }
                            });
                        }*/
                        Collections.sort(listaElementi, new Comparator<Elemento>() {
                            @Override
                            public int compare(Elemento e1, Elemento e2) {
                                return e1.armadio.compareTo(e2.armadio);
                            }
                        });
                        // TODO: sistemare con numeri scritti
                        inventario.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                             /*   Collections.sort(listaElementi, new Comparator<Elemento>() {
                                    @Override
                                    public int compare(Elemento e1, Elemento e2) {
                                        return e1.armadio.compareTo(e2.armadio);
                                    }
                                });*/
                                Intent intent = new Intent(MainActivity.this, InventarioActivity.class);
                                startActivity(intent);
                            }
                        });

                        aggiungi.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                /*Collections.sort(listaElementi, new Comparator<Elemento>() {
                                    @Override
                                    public int compare(Elemento e1, Elemento e2) {
                                        return e1.armadio.compareTo(e2.armadio);
                                    }
                                });*/
                                Intent intent = new Intent(MainActivity.this,AggiungiActivity.class);
                                startActivity(intent);
                            }
                        });

                        rimuovi.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(MainActivity.this,RimuoviActivity.class);
                                startActivity(intent);
                            }
                        });


                        storico.setOnClickListener(new View.OnClickListener() {
                           @Override
                           public void onClick(View view) {
                               FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                               DatabaseReference firebaseReference = firebaseDatabase.getReference("storico/");
                               firebaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                   @Override
                                   public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                       listaStorico.clear();
                                       if (snapshot.exists()) {
                                           int i = -1;
                                           HashMap<String, HashMap<String, HashMap<String, Object>>> hashMapHashMap = (HashMap<String, HashMap<String, HashMap<String, Object>>>) snapshot.getValue();
                                           for (Map.Entry<String, HashMap<String, HashMap<String, Object>>> stringHashMapEntry : hashMapHashMap.entrySet()) {
                                               Map.Entry<String, HashMap<String, HashMap<String, Object>>> pair = (Map.Entry<String, HashMap<String, HashMap<String, Object>>>) stringHashMapEntry;
                                               HashMap<String, HashMap<String, Object>> hashMapPair = (HashMap<String, HashMap<String, Object>>) pair.getValue();
                                               listaStorico.add(new Anno(pair.getKey()));
                                               i++;
                                               for (Map.Entry<String, HashMap<String, Object>> hashMapEntry2 : hashMapPair.entrySet()) {
                                                   Map.Entry<String, HashMap<String, Object>> pair2 = (Map.Entry<String, HashMap<String, Object>>) hashMapEntry2;
                                                   HashMap<String, Object> hashMapPair2 = (HashMap<String, Object>) pair2.getValue();
                                                   listaStorico.get(i).listaElementoStorico.add(new ElementoStorico(String.valueOf(hashMapPair2.get("nome")), Double.parseDouble(String.valueOf(hashMapPair2.get("quantita_usata"))), String.valueOf(hashMapPair2.get("u_misura")), pair2.getKey()));
                                                   System.out.println(listaStorico.toString());
                                               }
                                           }
                                          Collections.sort(listaStorico, new Comparator<Anno>() {
                                              @Override
                                              public int compare(Anno a1, Anno a2) {
                                                  return a1.anno.compareTo(a2.anno);
                                              }
                                          });
                                           Intent intent = new Intent(MainActivity.this, StoricoActivity.class);
                                           startActivity(intent);
                                       }
                                   }

                                   @Override
                                   public void onCancelled(DatabaseError databaseError) {

                                   }
                               });

                           }
                        });
                        logout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                new AlertDialog.Builder(MainActivity.this).setMessage("Sei sicuro di voler uscire?")

                                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int whichButton) {
                                                AuthUI.getInstance()
                                                        .signOut(MainActivity.this)
                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                Intent intent= new Intent(MainActivity.this,LoginActivity.class);
                                                                startActivity(intent);
                                                            }
                                                        });

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
                    }
                }

                public void onBackPressed() {
                    new AlertDialog.Builder(MainActivity.this).setMessage("Sei sicuro di voler uscire?")

                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    AuthUI.getInstance()
                                            .signOut(MainActivity.this)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                public void onComplete(@NonNull Task<Void> task) {

                                                }
                                            });
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
                    return;
                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                }
            });

        }
}