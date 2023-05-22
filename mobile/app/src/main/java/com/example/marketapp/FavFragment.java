package com.example.marketapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.marketapp.adapter.AdapterProdutosList;
import com.example.marketapp.dbHelper.ConnectionSQLite;
import com.example.marketapp.models.Produto;
import com.example.marketapp.observerpattern.IObserver;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.zxing.integration.android.IntentIntegrator;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class FavFragment extends Fragment implements IObserver {

    ConnectionSQLite conexaoSQLite;

    AdapterProdutosList adapter;

    ListView lvProdutos;
    TextView tvTotal;

    Intent i;

    @SuppressLint("Range")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fav, container, false);

        i = new Intent(getActivity(), ProdutoActivity.class);

        lvProdutos = (ListView) view.findViewById(R.id.lvProdutos);
        tvTotal = (TextView) view.findViewById(R.id.tvTotal);

        conexaoSQLite = ConnectionSQLite.getInstance(getActivity().getApplication());

        conexaoSQLite.addObserver((IObserver) this);

        tvTotal.setText("Total: R$" + conexaoSQLite.getTotalPreco());

        selectSelecionados();

        lvProdutos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Produto produto = (Produto) adapter.getItem(position);
                i.putExtra("nome", produto.getNome());
                i.putExtra("preco", produto.getPreco());
                i.putExtra("codigo", produto.getCodigo());
                i.putExtra("foto", produto.getFoto());

                startActivity(i);
            }
        });

        return view;

    }

    @SuppressLint("Range")
    private void selectSelecionados() {

        SQLiteDatabase db = conexaoSQLite.getReadableDatabase();
        Cursor resultado = db.rawQuery("SELECT * from produtos_selecionados", null);

        ArrayList<Produto> listaProdutos = conexaoSQLite.selectProdutos();

        adapter = new AdapterProdutosList(getContext(), listaProdutos);
        lvProdutos.setAdapter(adapter);

    }

    private void alert(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void update() {

        tvTotal.setText("Total: R$" + conexaoSQLite.getTotalPreco());
        selectSelecionados();

    }
}
