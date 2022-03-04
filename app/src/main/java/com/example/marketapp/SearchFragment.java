package com.example.marketapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.marketapp.adapter.AdapterProdutosList;
import com.example.marketapp.dbHelper.ConnectionSQLite;
import com.example.marketapp.helper.BaseURL;
import com.example.marketapp.models.Produto;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchFragment extends Fragment {

    RequestQueue queue;
    Intent i;

    ArrayList<Produto> produtos = new ArrayList<Produto>();
    AdapterProdutosList adapter;

    EditText etSearch;
    FloatingActionButton fab;
    ListView lv_produtos;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        queue = Volley.newRequestQueue(getContext());
        i = new Intent(getActivity(), ProdutoActivity.class);

        EditText etSearch = (EditText) view.findViewById(R.id.etSearch);
        fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        lv_produtos = (ListView) view.findViewById(R.id.lvProdutos);

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                String search = etSearch.getText().toString().trim();

                if(search != "" & search != null){
                    JsonArrayRequest myjsonrequest = new JsonArrayRequest(
                            Request.Method.GET,
                            BaseURL.BASE_URL + "produtos/nome/" + search,
                            null,
                            new Response.Listener<JSONArray>() {
                                @Override
                                public void onResponse(JSONArray response) {
                                    produtos.clear();

                                    for (int i = 0; i < response.length(); i++) {
                                        try {
                                            int id = response.getJSONObject(i).getInt("id");
                                            String nome = response.getJSONObject(i).getString("nome");
                                            double preco = response.getJSONObject(i).getDouble("preco");
                                            String cod = response.getJSONObject(i).getString("cod");
                                            String foto = response.getJSONObject(i).getString("foto");

                                            Produto empresa = new Produto(id, nome, preco, cod, foto);

                                            produtos.add(empresa);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }

                                    adapter = new AdapterProdutosList(getContext(), produtos);
                                    lv_produtos.setAdapter(adapter);

                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    VolleyLog.e("Error: ", error.getMessage());
                                    //alert("erro");
                                }
                            }
                    );

                    queue.add(myjsonrequest);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scan();
            }
        });

        lv_produtos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Produto produto = (Produto) adapter.getItem(position);
                i.putExtra("nome_empresa", produto.getNome());
                i.putExtra("preco", produto.getPreco());
                i.putExtra("foto_perfil", produto.getFoto());
                startActivity(i);

            }
        });

        init();

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if (result != null) {
            if (result.getContents() != null) {
                JsonObjectRequest myjsonrequest = new JsonObjectRequest(
                        Request.Method.GET,
                        BaseURL.BASE_URL + "produtos/qrcode/" + result.getContents(),
                        null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    int id = response.getInt("id");
                                    String nome = response.getString("nome");
                                    double preco = response.getDouble("preco");
                                    String cod = response.getString("cod");
                                    String foto = response.getString("foto");

                                    Produto empresa = new Produto(id, nome, preco, cod, foto);

                                    produtos.clear();
                                    produtos.add(empresa);

                                    adapter = new AdapterProdutosList(getContext(), produtos);
                                    lv_produtos.setAdapter(adapter);

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                VolleyLog.e("Error: ", error.getMessage());
                                //alert("erro");
                            }
                        }
                );

                queue.add(myjsonrequest);

            } else {
                alert("Scan cancelado");
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void init() {
        JsonArrayRequest myjsonrequest = new JsonArrayRequest(
                Request.Method.GET,
                BaseURL.BASE_URL + "produtos/",
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        for (int i = 0; i < response.length(); i++) {
                            try {
                                int id = response.getJSONObject(i).getInt("id");
                                String nome = response.getJSONObject(i).getString("nome");
                                double preco = response.getJSONObject(i).getDouble("preco");
                                String cod = response.getJSONObject(i).getString("cod");
                                String foto = response.getJSONObject(i).getString("foto");

                                Produto empresa = new Produto(id, nome, preco, cod, foto);

                                produtos.add(empresa);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        adapter = new AdapterProdutosList(getContext(), produtos);
                        lv_produtos.setAdapter(adapter);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.e("Error: ", error.getMessage());
                        alert("erro");
                    }
                }
        );

        queue.add(myjsonrequest);
    }

    private void alert(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }


    public void scan() {
        IntentIntegrator integrator= IntentIntegrator.forSupportFragment(SearchFragment.this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);

        integrator.setPrompt("Câmera Scan");
        integrator.setCameraId(0);
        integrator.initiateScan();
    }

    public void editEvent() {

        return ;

    }
}
