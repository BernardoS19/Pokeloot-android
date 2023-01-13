package com.example.pokeloot_android.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pokeloot_android.R;

public class GridCartasAdaptador extends BaseAdapter {

    private Context context;
    private String[] cartaNome;
    private int[] imagem;

    private LayoutInflater inflater;

    public GridCartasAdaptador(Context context, String[] cartaNome, int[] imagem)
    {
        this.context = context;
        this.imagem = imagem;
        this.cartaNome = cartaNome;
    }


    @Override
    public int getCount() {
        return cartaNome.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (inflater == null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (view == null){
            view = inflater.inflate(R.layout.grid_item_cartas, null);
        }

        ImageView imageView = view.findViewById(R.id.imgCarta);
        TextView textView = view.findViewById(R.id.tvCartaNome);

        imageView.setImageResource(imagem[i]);
        textView.setText(cartaNome[i]);

        return view;
    }
}
