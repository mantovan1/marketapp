package com.example.marketapp.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.marketapp.helper.BaseURL;
import com.example.marketapp.models.Produto;
import com.example.marketapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterProdutosList extends BaseAdapter {

    private Context context;
    private ArrayList <Produto> listaProdutos;

    public AdapterProdutosList(Context context, ArrayList<Produto> listaProdutos) {

        this.context = context;
        this.listaProdutos = listaProdutos;

    }

    @Override
    public int getCount() {

        return this.listaProdutos.size();

    }

    @Override
    public Object getItem(int position) {

        return this.listaProdutos.get(position);

    }

    @Override
    public long getItemId(int position) {

        return position;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = View.inflate(this.context, R.layout.layout_produto, null);

        v.setBackgroundColor(Color.parseColor("#d3d3d3"));

        ImageView ivProduto = (ImageView) v.findViewById(R.id.img_produto);
        TextView tvNomeProduto = (TextView) v.findViewById(R.id.txt_nome_produto);
        TextView tvPrecoProduto = (TextView) v.findViewById(R.id.txt_preco_produto);

        Picasso.with(v.getContext())
                .load(BaseURL.BASE_URL + listaProdutos.get(position).getFoto())
                .resize(500, 500)
                .into(ivProduto);

        String preco = "R$" + listaProdutos.get(position).getPreco();

        tvNomeProduto.setText(listaProdutos.get(position).getNome());
        tvPrecoProduto.setText(preco);

        return v;

    }
}