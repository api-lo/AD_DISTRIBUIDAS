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

public class MenuOpcione extends AppCompatActivity {
    TextView txtUser;
    CircleImageView profile_image;
    User user;
    Button btnOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_opcione);
        Bundle bundle = this.getIntent().getExtras();
        user=(User)bundle.getSerializable("usuario");
        System.out.println("imagen "+user.getImage());
        txtUser=findViewById(R.id.txtUser);
        profile_image=findViewById(R.id.profile_image);
        txtUser.setText(user.getUsername());
        Glide.with(MenuOpcione.this)
                .load(user.getImage())
                .centerCrop()
                .into(profile_image);

    }
    public void opcionPerfil(View view){
        Intent intent=new Intent(MenuOpcione.this, EditarPerfil.class);
        startActivity(intent);
    }

    public void opcionMapa(View view){
        Intent intent=new Intent(MenuOpcione.this, VisualizarMapa.class);
        startActivity(intent);
    }






}