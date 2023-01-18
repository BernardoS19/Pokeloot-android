package com.example.pokeloot_android.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.pokeloot_android.R;
import com.example.pokeloot_android.modelos.Baralho;

import java.util.ArrayList;

public class ListaBaralhoAdaptador extends BaseAdapter {

    private Context context;
    private ArrayList<Baralho> baralhos;
    private LayoutInflater inflater;

    public ListaBaralhoAdaptador(Context context, ArrayList<Baralho> baralhos) {
        this.context = context;
        this.baralhos = baralhos;
    }

    @Override
    public int getCount() {
        return baralhos.size();
    }

    @Override
    public Object getItem(int i) {
        return baralhos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return baralhos.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if (inflater == null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (view == null) {
            view = inflater.inflate(R.layout.list_row_baralho, null);
        }

        ViewHolderListaBaralhos viewHolder = (ViewHolderListaBaralhos) view.getTag();
        if (viewHolder == null) {
            viewHolder = new ListaBaralhoAdaptador.ViewHolderListaBaralhos(view);
            view.setTag(viewHolder);
        }

        viewHolder.update(baralhos.get(i));

        return view;
    }

    private class ViewHolderListaBaralhos {
        private TextView tvNomeBaralho;

        public ViewHolderListaBaralhos(View view) {
            tvNomeBaralho = view.findViewById(R.id.tvBaralhoNome);
        }

        public void update(Baralho baralho) {
            tvNomeBaralho.setText(baralho.getNome());
        }
    }

}
