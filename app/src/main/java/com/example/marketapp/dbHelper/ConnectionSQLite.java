package com.example.marketapp.dbHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.marketapp.models.Produto;

public class ConnectionSQLite extends SQLiteOpenHelper {

    private static final int VERSION_DB = 1;
    private static final String NAME_DB = "Market";
    SQLiteDatabase db;

    public ConnectionSQLite(Context context) {

        super(context, NAME_DB, null, VERSION_DB);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String sqlProductTable = "CREATE TABLE IF NOT EXISTS produtos_selecionados" + "(" + "nome TEXT" + "," + "preco DOUBLE" + "," + "foto TEXT" + ")";

        sqLiteDatabase.execSQL(sqlProductTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS produtos_selecionados");
        onCreate(sqLiteDatabase);

    }

    public void addProduct(Produto produtoSelecionado) {

        db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("nome", produtoSelecionado.getNome());
        values.put("preco", produtoSelecionado.getPreco());
        values.put("foto", produtoSelecionado.getFoto());

        long result = db.insert("produtos_selecionados", null, values);

        db.close();

    }

    public void delProduto() {

        db = this.getWritableDatabase();
        db.delete("produtos_selecionados", null, null);

    }

}
