package com.example.tareadistribuidas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.tareadistribuidas.model.ListaUser;
import com.example.tareadistribuidas.model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ListaUsuarios extends AppCompatActivity {
    List<ListaUser>  list=new ArrayList<>();
    RequestQueue requestQueue;
    RecyclerView recyclerView;
    ListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_usuarios);
        requestQueue = Volley.newRequestQueue(this);
        jsonArrayRequest();
    }
    private void inicializarelementos(List<ListaUser> list02 ) {
        ListAdapter lista=new ListAdapter(list02, this);
        RecyclerView recyclerView=findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(lista);
    }

   private void jsonArrayRequest() {
       String url="https://api.jsonbin.io/b/612aef1ac5159b35ae05cf10";
        JsonArrayRequest json = new JsonArrayRequest(Request.Method.GET, url, null,
                new com.android.volley.Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        list = new ArrayList<>();
                        int size = response.length();
                        for (int i = 0; i < size; i++) {
                            try {
                                JSONObject objeto = new JSONObject(response.get(i).toString());
                                String username = objeto.getString("username");
                                String tipo = objeto.getString("tipo");
                                ListaUser elemento = new ListaUser(username,tipo);
                                list.add(elemento);
                            } catch (JSONException e) {
                                e.printStackTrace();
                                System.out.println(e.getMessage());
                            }
                        }
                        System.out.println(list.get(0).getUsername());
                        inicializarelementos(list);
                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        requestQueue.add(json);
    }

}