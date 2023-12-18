package com.example.applicazionechimica;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ActionOnlyNavDirections;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class AggiungiNuoviReagenti extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide(); //nasconde barra
        Intent intent= new Intent();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aggiungi_nuovi_reagenti);
        EditText numero= findViewById(R.id.aggiungiNumero);
        EditText nome= findViewById(R.id.aggiungiNome);
        Spinner armadio= findViewById(R.id.aggiungiArmadio);
        EditText quantitaInizio= findViewById(R.id.aggiungiQtaInizio);
        Spinner unitaMisura= findViewById(R.id.aggiungiUnitaMisura);
        Button conferma= findViewById(R.id.bottoneAggiungi);
        FloatingActionButton home= findViewById(R.id.home);
        conferma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean controllo=true;
                for (int i = 0; i < MainActivity.listaElementi.size(); i++) {
                    if(MainActivity.listaElementi.get(i).numero.equals(numero.getText().toString()) || MainActivity.listaElementi.get(i).nome.equals(nome.getText().toString()))    {
                        controllo=false;
                    }
                }
                if(Double.parseDouble(quantitaInizio.getText().toString())<0)   {
                    Toast.makeText(AggiungiNuoviReagenti.this, "Non è stato possibile aggiungere il reagente. Non può inserire una quantità negativa ", Toast.LENGTH_LONG).show();
                }
                else if(!controllo)  {
                    Toast.makeText(AggiungiNuoviReagenti.this, "Non è stato possibile aggiungere il reagente perchè è già presente ", Toast.LENGTH_LONG).show();
                }
                else if(nome.getText().toString().equals("") || numero.getText().toString().equals("")  || quantitaInizio.getText().toString().equals("")) {
                    String dato="";
                    if(nome.getText().toString().equals(""))    {
                        dato="nome ";
                    }
                    if(numero.getText().toString().equals(""))  {
                        dato=dato +" numero ";
                    }

                    if(quantitaInizio.getText().toString().equals(""))    {
                        dato=dato + "quantita inizio ";
                    }
                    intent.putExtra("datomancante", dato);
                    setResult(0, intent);
                    Toast.makeText(AggiungiNuoviReagenti.this, "Non è stato possibile aggiungere il reagente. Dato mancante: " + dato, Toast.LENGTH_LONG).show();

                }
                else    {
                    Elemento elemento = new Elemento(armadio.getSelectedItem().toString(), nome.getText().toString(), 0, unitaMisura.getSelectedItem().toString(), numero.getText().toString(),Double.parseDouble(quantitaInizio.getText().toString()));
                    System.out.println(elemento.toString());
                    aggiungiElemento(elemento);
                    MainActivity.listaElementi.add(elemento);
                    setResult(Activity.RESULT_OK, intent);
                    Toast.makeText(AggiungiNuoviReagenti.this, "Il reagente è stata aggiunto con successo!", Toast.LENGTH_SHORT).show();

                }
                finish();
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1= new Intent(AggiungiNuoviReagenti.this, MainActivity.class);
                startActivity(intent1);
            }
        });
    }
    public static void aggiungiElemento(Elemento elemento) {
        FirebaseDatabase database= FirebaseDatabase.getInstance();
        DatabaseReference reference= database.getReference("/catalogo");
        String chiave = reference.push().getKey();
        reference.child("/"+(chiave)).updateChildren(elemento.toHashMap());
    }
}