package com.example.marketapp;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.marketapp.adapter.AdapterProdutosList;
import com.example.marketapp.dbHelper.ConnectionSQLite;
import com.example.marketapp.models.Produto;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.zxing.integration.android.IntentIntegrator;

import java.util.ArrayList;

public class FavFragment extends Fragment {

    ConnectionSQLite conexaoSQLite;

    AdapterProdutosList adapter;

    ListView lv_produtos;

    @SuppressLint("Range")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fav, container, false);

        lv_produtos = (ListView) view.findViewById(R.id.lvProdutos);

        conexaoSQLite = new ConnectionSQLite(view.getContext());

        selectSelecionados();

        return view;

    }

    @SuppressLint("Range")
    private void selectSelecionados() {

        SQLiteDatabase db = conexaoSQLite.getReadableDatabase();
        Cursor resultado = db.rawQuery("SELECT * from produtos_selecionados", null);

        ArrayList<Produto> listaProdutos = new ArrayList<Produto>();

        if (resultado.moveToFirst()) {

            while(resultado.moveToNext()) {

                Produto produtoTemporario = new Produto();
                produtoTemporario.setNome(resultado.getString(resultado.getColumnIndex("nome")));
                produtoTemporario.setPreco(resultado.getDouble(resultado.getColumnIndex("preco")));
                produtoTemporario.setFoto(resultado.getString(resultado.getColumnIndex("foto")));

                listaProdutos.add(produtoTemporario);

            }

            alert("Número de items: " + listaProdutos.size());

            AdapterProdutosList adapter = new AdapterProdutosList(getContext(), listaProdutos);
            lv_produtos.setAdapter(adapter);

        }
    }

    private void alert(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }


}
