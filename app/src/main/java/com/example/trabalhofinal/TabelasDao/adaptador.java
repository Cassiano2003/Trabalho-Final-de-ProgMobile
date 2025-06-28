package com.example.trabalhofinal.TabelasDao;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class adaptador extends BaseAdapter {

    private Context context;
    private int[] lista;

    public adaptador(Context context, int[] lista){
        this.context = context;
        this.lista = lista;
    }

    @Override
    public int getCount() {
        return lista.length;
    }

    @Override
    public Object getItem(int i) {
        return lista[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ImageView iv = new ImageView(context);
        iv.setImageResource(lista[i]);
        iv.setLayoutParams(new ViewGroup.LayoutParams(250,250));
        iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
        iv.setPadding(5,5,5,5);
        return iv;
    }
}
