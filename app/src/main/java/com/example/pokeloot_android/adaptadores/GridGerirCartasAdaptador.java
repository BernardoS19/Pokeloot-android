package com.example.pokeloot_android.adaptadores;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pokeloot_android.R;
import com.example.pokeloot_android.modelos.BaralhoCarta;
import com.example.pokeloot_android.modelos.SingletonCartas;

import java.util.ArrayList;

public class GridGerirCartasAdaptador extends BaseAdapter {
    private Context context;

    private int baralhoId;

    private ArrayList<BaralhoCarta> cartas;

    private LayoutInflater inflater;

    public GridGerirCartasAdaptador(int baralhoId, Context context, ArrayList<BaralhoCarta> cartas) {
        this.baralhoId = baralhoId;
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
        if (view == null) {
            view = inflater.inflate(R.layout.grid_item_gerir_cartas, null);
        }

        ViewHolderGridGerirCarta viewHolder = (ViewHolderGridGerirCarta) view.getTag();
        if (viewHolder == null) {
            viewHolder = new ViewHolderGridGerirCarta(view);
            view.setTag(viewHolder);
        }

        viewHolder.update(cartas.get(i));

        Button btnAdicionar = (Button) view.findViewById(R.id.btnAdicionarCartaBaralho);
        Button btnRemover = (Button) view.findViewById(R.id.btnRemoverCartaBaralho);
        BaralhoCarta carta = viewHolder.carta;

        if (carta.getAdicionado() == 1) {
            btnAdicionar.setVisibility(View.GONE);
            btnRemover.setVisibility(View.VISIBLE);
        } else if (carta.getAdicionado() == 0) {
            btnRemover.setVisibility(View.GONE);
            btnAdicionar.setVisibility(View.VISIBLE);
        }

        btnAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnAdicionar.setVisibility(View.GONE);
                btnRemover.setVisibility(View.VISIBLE);
                SingletonCartas.getInstance(context).addCartaBaralhoAPI(baralhoId, carta.getId(), context);
            }
        });

        btnRemover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnRemover.setVisibility(View.GONE);
                btnAdicionar.setVisibility(View.VISIBLE);
                SingletonCartas.getInstance(context).removerCartaBaralhoAPI(baralhoId, carta.getId(), context);
            }
        });

        return view;
    }

    private class ViewHolderGridGerirCarta{
        private TextView tvCartaNome;
        private ImageView imgCarta;
        private BaralhoCarta carta;

        public ViewHolderGridGerirCarta(View view) {
            tvCartaNome = view.findViewById(R.id.tvGridGerirCartaNome);
            imgCarta = view.findViewById(R.id.imgGridGerirCarta);
        }

        public void update(BaralhoCarta carta) {
            byte[] decodedString = Base64.decode(carta.getImagem(), Base64.DEFAULT);
            Bitmap decodedImage = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            this.carta = carta;

            tvCartaNome.setText(carta.getNome());
            imgCarta.setImageBitmap(decodedImage);
        }
    }

}
