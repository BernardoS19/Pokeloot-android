package com.example.pokeloot_android.vistas;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pokeloot_android.R;
import com.example.pokeloot_android.modelos.Carta;
import com.example.pokeloot_android.modelos.SingletonCartas;

public class DetalhesCartaActivity extends AppCompatActivity {

    private ImageView imgCarta;
    private TextView tvNomeCarta, tvTipoCarta, tvElementoCarta, tvColecaoCarta, tvDescricaoCarta;
    private Carta carta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_carta);

        imgCarta = findViewById(R.id.imgDetalhesCarta);
        tvNomeCarta = findViewById(R.id.tvDetalhesCartaNome);
        tvTipoCarta = findViewById(R.id.tvDetalhesCartaTipo);
        tvElementoCarta = findViewById(R.id.tvDetalhesCartaElemento);
        tvColecaoCarta = findViewById(R.id.tvDetalhesCartaColecao);
        tvDescricaoCarta = findViewById(R.id.tvDetalhesCartaDescricao);

        int id = getIntent().getIntExtra("carta_id", 0);
        carta = SingletonCartas.getInstance(getApplicationContext()).getCarta(id);

        if (carta != null) {
            byte[] decodedString = Base64.decode(carta.getImagem(), Base64.DEFAULT);
            Bitmap decodedImage = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

            setTitle(carta.getNome());
            imgCarta.setImageBitmap(decodedImage);
            tvNomeCarta.setText(carta.getNome());
            tvTipoCarta.setText(carta.getTipo());
            tvElementoCarta.setText(carta.getElemento());
            tvColecaoCarta.setText(carta.getColecao());
            tvDescricaoCarta.setText(carta.getDescricao());
        }

    }
}