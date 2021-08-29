package com.example.tareadistribuidas;





import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.tareadistribuidas.model.SessionData;
import com.example.tareadistribuidas.model.User;

import org.apache.commons.codec.digest.DigestUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;


public class MainActivity extends AppCompatActivity {
    TextView txtUsuario,txtContrasenia;
    RequestQueue requestQueue;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtUsuario=findViewById(R.id.txtUsuario);
        txtContrasenia=findViewById(R.id.txtContrasenia);
        requestQueue = Volley.newRequestQueue(this);
    }
    public void btnRegistrarse(View view){
        Intent intent=new Intent(MainActivity.this, RegistrarUsuario.class);
        startActivity(intent);
    }
    public void logIn(View view) {
        txtUsuario = findViewById(R.id.txtUsuario);
        txtContrasenia = findViewById(R.id.txtContrasenia);

        String user = txtUsuario.getText().toString();

        String pass = txtContrasenia.getText().toString();

        //pass = DigestUtils.sha1Hex(pass);
        requestLogin(user, pass);

    }

    private void requestLogin(String user, String password) {
        String url = "https://aplicaciones.uteq.edu.ec/WenServisPA/webresources/generic/login";
        HashMap<String, String> hash = new HashMap<>();
        hash.put("username", user);
        hash.put("password", password);
        //JsonArrayRequest jsonRequest = new JsonArrayRequest(Request.Method.POST, url, createMyReqSuccessListener(), createMyReqErrorListener()) { protected Map<String, String> getParams() throws com.android.volley.AuthFailureError { Map<String, String> params = new HashMap<String, String>(); params.put("param1", num1); params.put("param2", num2); return params; }; };
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(hash), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    MainActivity.this.user = new User(response.getInt("idUsuario"), response.getString("firstName"),
                            response.getString("lastName"), response.getString("birthdate"), response.getString("gender"),
                            response.getString("phone"), response.getString("username"), response.getString("mail"),
                            response.getString("password"), response.getString("image"), response.getBoolean("state"));
                    if (MainActivity.this.user.getUsername().equalsIgnoreCase(user) || MainActivity.this.user.getMail().equalsIgnoreCase(user)) {
                        if (password.equals(MainActivity.this.user.getPassword())) {
                            SessionData.setUsuario(MainActivity.this.user);
                            Toast.makeText(MainActivity.this, "Welcome: " + MainActivity.this.user.getUsername(), Toast.LENGTH_LONG).show();
                            Intent intent1 = new Intent(MainActivity.this, MenuOpcione.class);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("usuario",MainActivity.this.user);
                            bundle.putSerializable("imagen",MainActivity.this.user);
                            intent1.putExtras(bundle);
                            startActivity(intent1);

                        } else {
                            Toast.makeText(MainActivity.this, "Password error", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(MainActivity.this, "No results for user", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError ex) {
                Toast.makeText(MainActivity.this, "Incorrect: \n" + ex.toString(), Toast.LENGTH_SHORT).show();

                System.out.println(ex.toString());
            }
        });
        requestQueue.add(jsonRequest);
    }


}