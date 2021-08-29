package com.example.tareadistribuidas;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class VisualizarMapa extends AppCompatActivity {
    ImageView imageView,imgBandera;
    TextView resultado, txtResul, txtCodigo;
    Button button, btnJSON;
    FirebaseVisionImage image;
    RequestQueue requestQueue;
    String Ruta = "http://www.geognos.com/api/en/countries/info/";
    String URL_Banderas = "http://www.geognos.com/api/en/countries/flag/";
    String All_Ruta="http://www.geognos.com/api/en/countries/info/all.json";
    String Strjson="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizar_mapa);
        requestQueue = Volley.newRequestQueue(this);
        imageView = findViewById(R.id.imgFoto);

        resultado = findViewById(R.id.txtResultado);
        button = findViewById(R.id.button);
        txtResul = findViewById(R.id.txtResul);
        txtCodigo = findViewById(R.id.txtCod);
        imgBandera=findViewById(R.id.imgBandera);
    }

    public void irMapa(View view){
        Intent intent=new Intent(VisualizarMapa.this, Mapa.class);
        Bundle b=new Bundle();
        b.putString("json",Strjson);
        intent.putExtras(b);
        startActivity(intent);

    }



    public void seleccionarImagen(View view) {
        Intent intent = new Intent();
        intent.setType("imagen/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Seleccionar una imagen"), 1);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            imageView.setImageURI(data.getData());
            try {
                image = FirebaseVisionImage.fromFilePath(getApplicationContext(), data.getData());
                FirebaseVisionTextRecognizer textRecognizer = FirebaseVision.getInstance().getOnDeviceTextRecognizer();
                textRecognizer.processImage(image).addOnSuccessListener(new OnSuccessListener<FirebaseVisionText>() {
                    @Override
                    public void onSuccess(FirebaseVisionText result) {
                        resultado.setText(result.getText());
                        jsonCompleto(result.getText().toString());
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void requestJSON(String cod_Pais) {
        JsonObjectRequest json = new JsonObjectRequest(Request.Method.GET,
                Ruta + cod_Pais + ".json", null,
                new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Strjson=response.getJSONObject("Results").getJSONObject("GeoRectangle").toString();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        txtResul.setText(response.toString());
                        Glide.with(VisualizarMapa.this).load(URL_Banderas+cod_Pais+".png").centerCrop().into(imgBandera);
                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("Error: "+error.getMessage());
            }
        });
        requestQueue.add(json);
    }

    private void jsonCompleto(String pais) {
        JsonObjectRequest json = new JsonObjectRequest(Request.Method.GET,
                All_Ruta, null,
                new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonCampos=response.getJSONObject("Results").names();
                            JSONArray jsonArray=response.getJSONObject("Results").toJSONArray(jsonCampos);
                            JSONObject jsonObject;
                            for (int i=0;i<jsonCampos.length();i++){
                                jsonObject=jsonArray.getJSONObject(i);
                                if(pais.toUpperCase().equals(jsonObject.getString("Name").toUpperCase())){

                                    txtCodigo.setText(jsonCampos.getString(i));
                                    requestJSON(jsonCampos.getString(i));
                                    break;
                                }
                            }
                        } catch (JSONException e) {
                            System.out.println(e.getMessage());
                        }

                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("Error: "+error.getMessage());
            }
        });
        requestQueue.add(json);
    }


}