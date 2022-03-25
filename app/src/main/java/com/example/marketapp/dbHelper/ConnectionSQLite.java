package com.example.marketapp.dbHelper;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.marketapp.MainActivity;
import com.example.marketapp.models.Produto;
import com.example.marketapp.observerpattern.IObserver;
import com.example.marketapp.observerpattern.ISubject;

import java.util.ArrayList;

public class ConnectionSQLite extends SQLiteOpenHelper implements ISubject {

    private static final int VERSION_DB = 1;
    private static final String NAME_DB = "Market";

    public ArrayList <IObserver> observers = new ArrayList <IObserver> ();

    SQLiteDatabase db;

    //Singleton Pattern
    private static ConnectionSQLite mInstance = null;

    public static ConnectionSQLite getInstance(Context context){
        if(mInstance == null) mInstance = new ConnectionSQLite(context.getApplicationContext());
        return mInstance;
    }//func

    private ConnectionSQLite(Context context) {

        super(context, NAME_DB, null, VERSION_DB);

    }

    //

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String sqlProductTable = "CREATE TABLE IF NOT EXISTS produtos_selecionados" + "(" + "nome TEXT" + "," + "preco REAL" + "," + "codigo TEXT unique" + "," +  "foto TEXT" + ")";

        sqLiteDatabase.execSQL(sqlProductTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS produtos_selecionados");
        onCreate(sqLiteDatabase);

    }

    //

    public boolean addProduct(Produto produtoSelecionado) {

        db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("nome", produtoSelecionado.getNome());
        values.put("preco", produtoSelecionado.getPreco());
        values.put("codigo", produtoSelecionado.getCodigo());
        values.put("foto", produtoSelecionado.getFoto());

        long result = db.insert("produtos_selecionados", null, values);

        notifyObserver();

        return result != -1;

    }

    public void delProduto(Produto produtoSelecionado) {

        db = this.getWritableDatabase();
        db.delete("produtos_selecionados", "codigo=?", new String[]{produtoSelecionado.getCodigo()});

        notifyObserver();

    }

    @SuppressLint("Range")
    public ArrayList<Produto> selectProdutos() {
        db = getReadableDatabase();
        Cursor resultado = db.rawQuery("SELECT * from produtos_selecionados", null);

        ArrayList<Produto> listaProdutos = new ArrayList<Produto>();

        if (resultado.moveToFirst()) {

            do {

                Produto produto = new Produto();

                produto.setNome(resultado.getString(resultado.getColumnIndex("nome")));
                produto.setPreco(resultado.getDouble(resultado.getColumnIndex("preco")));
                produto.setCodigo(resultado.getString(resultado.getColumnIndex("codigo")));
                produto.setFoto(resultado.getString(resultado.getColumnIndex("foto")));

                listaProdutos.add(produto);

            } while (resultado.moveToNext());
        }

        return listaProdutos;
    }

    public boolean isInTheFavoriteList(Produto produto) {
        db = getReadableDatabase();
        Cursor resultado = db.rawQuery("SELECT * from produtos_selecionados where codigo = ?", new String[] {produto.getCodigo()}, null);

        return resultado.moveToFirst();
    }

    //Observer Pattern

    @Override
    public void addObserver(IObserver i) {
        observers.add(i);
    }

    @Override
    public void removeObserver(IObserver i) {
        observers.remove(i);
    }

    @Override
    public void notifyObserver() {
        for (IObserver observer : observers) {
            observer.update();
        }
    }
}
