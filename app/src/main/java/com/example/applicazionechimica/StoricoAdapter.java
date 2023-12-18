package com.example.applicazionechimica;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class StoricoAdapter extends BaseAdapter {
    @Override
    public int getCount() {
        return MainActivity.listaStorico.size();
    }

    @Override
    public Object getItem(int i) {
        return MainActivity.listaStorico.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if(view == null){
            LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
            view = layoutInflater.inflate(R.layout.anno_layout, viewGroup, false);
        }
        TextView anno = view.findViewById(R.id.txtAnno);
        anno.setText(MainActivity.listaStorico.get(i).anno);

        return view;
    }
}