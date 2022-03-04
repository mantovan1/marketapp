package com.example.marketapp;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.marketapp.dbHelper.ConnectionSQLite;
import com.example.marketapp.models.Produto;
import com.squareup.picasso.Picasso;

public class ProdutoActivity extends AppCompatActivity {

    boolean fav = false;
    ConnectionSQLite connectionSQLite;
    Produto p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produto);

        ImageView ivFotoProduto = (ImageView) findViewById(R.id.ivFotoProduto);
        TextView txtNomeProduto = (TextView) findViewById(R.id.tvNomeProduto);
        ImageButton ibFav = (ImageButton) findViewById(R.id.ibFav);
        connectionSQLite = new ConnectionSQLite(getBaseContext());

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String nome_empresa = extras.getString("nome_empresa");
            double preco = extras.getDouble("preco");
            String foto_perfil = extras.getString("foto_perfil");

            Produto p = new Produto();
            p.setNome(nome_empresa);
            p.setPreco(preco);
            p.setFoto(foto_perfil);

            txtNomeProduto.setText(nome_empresa);

            Picasso
            .with(this)
            .load("http://192.168.15.152:8080/" + foto_perfil)
            .into(ivFotoProduto);

        }

        ibFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fav == true) {
                    fav = false;
                    ibFav.setBackgroundResource(R.drawable.coracao);
                } else {
                    fav = true;
                    ibFav.setBackgroundResource(R.drawable.coracaocheio);



                    //connectionSQLite.addProduct(p);

                    /*if(dataChecked) {
                        Toast.makeText(getBaseContext(), "sucesso", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getBaseContext(), "sucesso", Toast.LENGTH_SHORT).show();
                    }*/
                }
            }
        });

    }

}
