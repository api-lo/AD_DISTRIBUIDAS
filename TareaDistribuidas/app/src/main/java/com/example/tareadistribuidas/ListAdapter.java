package com.example.tareadistribuidas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.tareadistribuidas.model.ListaUser;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder>{

    private List<ListaUser> listausuarios;
    private Context context;

    public ListAdapter(List<ListaUser> listaelementos, Context context) {
        this.listausuarios = listaelementos;
        this.context = context;
    }


    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListAdapter.ViewHolder holder, int position) {
        holder.txtNombrerevista.setText(listausuarios.get(position).getUsername());
        holder.txtfecha.setText(listausuarios.get(position).getTipo());
    }

    @Override
    public int getItemCount() {
        return listausuarios.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView imgfoto;
        private TextView txtNombrerevista;
        private TextView txtfecha;
        private TextView txtVol;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNombrerevista = itemView.findViewById(R.id.txtNombrerevista);
            txtfecha = itemView.findViewById(R.id.txtfecha);


        }
    }



}

