package com.example.marketapp.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.marketapp.models.Produto;
import com.example.marketapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterProdutosList extends BaseAdapter {

    private Context context;
    private ArrayList<Produto> produtosList;

    public AdapterProdutosList(Context context, ArrayList<Produto>  produtosList) {

        this.context = context;
        this.produtosList = produtosList;

    }

    @Override
    public int getCount() {

        return this.produtosList.size();

    }

    @Override
    public Object getItem(int position) {

        return this.produtosList.get(position);

    }

    @Override
    public long getItemId(int position) {

        return position;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = View.inflate(this.context, R.layout.layout_produto, null);

        v.setBackgroundColor(Color.parseColor("#d3d3d3"));

        ImageView img_produto = (ImageView) v.findViewById(R.id.img_produto);
        TextView tv_nome_produto = (TextView) v.findViewById(R.id.txt_nome_produto);
        TextView tv_preco_produto = (TextView) v.findViewById(R.id.txt_preco_produto);

        Picasso.with(v.getContext())
                .load("http://192.168.15.152:8080/" + produtosList.get(position).getFoto())
                .resize(500, 500)
                .into(img_produto);

        String preco = "R$" + produtosList.get(position).getPreco();

        tv_nome_produto.setText(produtosList.get(position).getNome());
        tv_preco_produto.setText(preco);

        return v;

    }
}