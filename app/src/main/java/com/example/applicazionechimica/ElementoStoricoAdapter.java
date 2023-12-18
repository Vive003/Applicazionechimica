package com.example.applicazionechimica;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ElementoStoricoAdapter extends BaseAdapter {

    int index;

    public ElementoStoricoAdapter(int index){
        this.index = index;
    }

    @Override
    public int getCount() {
        return MainActivity.listaStorico.get(index).listaElementoStorico.size();
    }

    @Override
    public Object getItem(int i) {
        return MainActivity.listaStorico.get(index).listaElementoStorico.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if(view == null){
            LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
            view = layoutInflater.inflate(R.layout.elemento_storico_layout, viewGroup, false);
        }

        TextView nome= view.findViewById(R.id.textNome);
        nome.setText(MainActivity.listaStorico.get(index).listaElementoStorico.get(i).nome);
        TextView num = view.findViewById(R.id.textNum);
        num.setText(MainActivity.listaStorico.get(index).listaElementoStorico.get(i).numero);
        TextView qta = view.findViewById(R.id.textQuantita);
        qta.setText("Quantità: "+ MainActivity.listaStorico.get(index).listaElementoStorico.get(i).qta+" "+MainActivity.listaStorico.get(index).listaElementoStorico.get(i).unita);


        return view;
    }
}
