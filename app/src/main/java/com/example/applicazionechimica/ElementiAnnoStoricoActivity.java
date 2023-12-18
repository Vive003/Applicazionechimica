package com.example.applicazionechimica;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static android.Manifest.permission.ACCESS_NOTIFICATION_POLICY;
import static android.Manifest.permission.INTERNET;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class ElementiAnnoStoricoActivity extends AppCompatActivity {

    int pageHeight = 1120;
    int pagewidth = 792;
    private static final int PERMISSION_REQUEST_CODE = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_elementi_anno_storico);
        int i=getIntent().getExtras().getInt("indice");
        ElementoStoricoAdapter elementoStoricoAdapter= new ElementoStoricoAdapter(i);
        ListView listView= findViewById(R.id.listaElementiAnno);
        listView.setAdapter(elementoStoricoAdapter);
        TextView anno= findViewById(R.id.textAnno);
        anno.setText(MainActivity.listaStorico.get(i).anno);
        FloatingActionButton home= findViewById(R.id.home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1= new Intent(ElementiAnnoStoricoActivity.this, MainActivity.class);
                startActivity(intent1);
            }
        });

    }
}