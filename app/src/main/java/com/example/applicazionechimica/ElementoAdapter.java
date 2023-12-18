package com.example.applicazionechimica;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ElementoAdapter extends BaseAdapter {
    @Override
    public int getCount() {
        return MainActivity.listaElementi.size();
    }

    @Override
    public Object getItem(int i) {
        return MainActivity.listaElementi.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if(view == null){
            LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
            view = layoutInflater.inflate(R.layout.elemento_layout, viewGroup, false);
        }
        TextView armadio = view.findViewById(R.id.textArmadio);
        TextView nome = view.findViewById(R.id.textNome);
        TextView quantita = view.findViewById(R.id.textQuantita);
        TextView numero = view.findViewById(R.id.textNum);
        TextView scadenza = view.findViewById(R.id.textScadenza);

        armadio.setText("Armadio: "+(MainActivity.listaElementi.get(i).armadio));
        nome.setText((MainActivity.listaElementi.get(i).nome));
        quantita.setText("Qtà: "+(MainActivity.listaElementi.get(i).quantita)+" "+(MainActivity.listaElementi.get(i).unitaMisura));
        numero.setText(MainActivity.listaElementi.get(i).numero);
        scadenza.setText("");

        return view;
    }
}
