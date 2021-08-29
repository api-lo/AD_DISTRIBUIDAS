package com.example.tareadistribuidas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ActivityUsuario extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario);
    }
    public void verUsuarios(View view){
        Intent intent=new Intent(ActivityUsuario.this, ListaUsuarios.class);
        startActivity(intent);
    }
}