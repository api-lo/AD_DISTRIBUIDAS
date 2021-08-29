package com.example.tareadistribuidas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.tareadistribuidas.model.User;

import de.hdodenhof.circleimageview.CircleImageView;

public class ActivityUsuario extends AppCompatActivity {
    TextView txtUser;
    CircleImageView profile_image;
    User user;
    Button btnOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario);
        Bundle bundle = this.getIntent().getExtras();
        user=(User)bundle.getSerializable("usuario");
        System.out.println("imagen "+user.getImage());
        txtUser=findViewById(R.id.txtUser);
        profile_image=findViewById(R.id.profile_image);
        txtUser.setText(user.getUsername());
        Glide.with(ActivityUsuario.this)
                .load(user.getImage())
                .centerCrop()
                .into(profile_image);
    }
    public void verUsuarios(View view){
        Intent intent=new Intent(ActivityUsuario.this, ListaUsuarios.class);
        startActivity(intent);
    }
    public  void perfil(View view){
        Intent intent=new Intent(ActivityUsuario.this, EditarPerfil.class);
        startActivity(intent);
    }
    public  void mapa(View view){
        Intent intent=new Intent(ActivityUsuario.this, VisualizarMapa.class);
        startActivity(intent);
    }
}