package com.example.pokeloot_android.adaptadores;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.pokeloot_android.R;
import com.example.pokeloot_android.modelos.Carta;

import java.util.ArrayList;

public class GridCartasAdaptador extends BaseAdapter {

    private Context context;

    private ArrayList<Carta> cartas;

    private LayoutInflater inflater;

    public GridCartasAdaptador(Context context, ArrayList<Carta> cartas) {
        this.context = context;
        this.cartas = cartas;
    }


    @Override
    public int getCount() {
        return cartas.size();
    }

    @Override
    public Object getItem(int i) {
        return cartas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return cartas.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (inflater == null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (view == null){
            view = inflater.inflate(R.layout.grid_item_cartas, null);
        }

        ViewHolderGridCarta viewHolder = (ViewHolderGridCarta) view.getTag();
        if (viewHolder == null) {
            viewHolder = new ViewHolderGridCarta(view);
            view.setTag(viewHolder);
        }

        viewHolder.update(cartas.get(i));

        return view;
    }

    public class ViewHolderGridCarta{
        private TextView tvCartaNome;
        private ImageView imgCarta;

        public ViewHolderGridCarta(View view) {
            tvCartaNome = view.findViewById(R.id.tvGridCartaNome);
            imgCarta = view.findViewById(R.id.imgGridCarta);
        }

        public void update(Carta carta) {
            byte[] decodedString = Base64.decode(carta.getImagem(), Base64.DEFAULT);
            Bitmap decodedImage = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

            tvCartaNome.setText(carta.getNome());
            imgCarta.setImageBitmap(decodedImage);
        }
    }
}
