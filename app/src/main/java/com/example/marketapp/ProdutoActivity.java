package com.example.marketapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.marketapp.adapter.AdapterProdutosList;
import com.example.marketapp.dbHelper.ConnectionSQLite;
import com.example.marketapp.models.Produto;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProdutoActivity extends AppCompatActivity {

    ConnectionSQLite connectionSQLite;

    Produto produto;

    boolean fav = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produto);

        ImageView ivFotoProduto = (ImageView) findViewById(R.id.ivFotoProduto);
        TextView txtNomeProduto = (TextView) findViewById(R.id.tvNomeProduto);
        ImageButton ibFav = (ImageButton) findViewById(R.id.ibFav);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String nome_empresa = extras.getString("nome");
            Double preco = extras.getDouble("preco");
            String codigo = extras.getString("codigo");
            String foto_perfil = extras.getString("foto");

            connectionSQLite = ConnectionSQLite.getInstance(getApplication());

            produto = new Produto();
            produto.setNome(nome_empresa);
            produto.setPreco(preco);
            produto.setCodigo(codigo);
            produto.setFoto(foto_perfil);

            txtNomeProduto.setText(nome_empresa);

            if(connectionSQLite.isInTheFavoriteList(produto)) {
                fav = true;
                ibFav.setBackgroundResource(R.drawable.coracaocheio);
            }

            Picasso
            .with(this)
            .load("http://192.168.15.152:8080/" + foto_perfil)
            .into(ivFotoProduto);

        }

        ibFav.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("Range")
            @Override
            public void onClick(View v) {
                if(fav == true) {
                    fav = false;
                    connectionSQLite.delProduto(produto);
                    ibFav.setBackgroundResource(R.drawable.coracao);
                } else {
                    fav = true;
                    ibFav.setBackgroundResource(R.drawable.coracaocheio);

                    boolean result = connectionSQLite.addProduct(produto);

                    if(result) {
                        alert("Favoritado");
                    }
                }
            }
        });

    }

    private void alert(String msg) {
        Toast.makeText(getBaseContext(), msg, Toast.LENGTH_SHORT).show();
    }

}
