package com.example.tareadistribuidas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.example.tareadistribuidas.model.ListaUser;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder>{

    private List<ListaUser> listausuarios;
    private Context context;
    private LayoutInflater myinflater;

    public ListAdapter(List<ListaUser> listausuarios, Context context) {
        this.myinflater=LayoutInflater.from(context);
        this.listausuarios = listausuarios;
        this.context = context;
    }
    @Override
    public int getItemCount() {
        return listausuarios.size();
    }

    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = myinflater.inflate(R.layout.lista,null);
        return new ListAdapter.ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(final ListAdapter.ViewHolder holder,final int position) {
        holder.bindData(listausuarios.get(position));
        //holder.txtNombrerevista.setText(listausuarios.get(position).getUsername());
        //holder.txtfecha.setText(listausuarios.get(position).getTipo());
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

         ImageView imgfoto;
         TextView txtNombrerevista,txtTipo;
        ViewHolder(View itemView) {
            super(itemView);
            txtNombrerevista = itemView.findViewById(R.id.txtNombrerevista);
            txtTipo = itemView.findViewById(R.id.txtfecha);
            imgfoto=itemView.findViewById(R.id.imgfoto);


        }
        void bindData(final ListaUser item)
        {
            Glide.with(context)
                    .load("https://res.cloudinary.com/durxpegdm/image/upload/v1627937691/1467646262_522853_1467646344_noticia_normal_gomxe6.jpg")
                    .centerCrop()
                    .into(imgfoto);

            txtNombrerevista.setText(item.getUsername());
            txtTipo.setText(item.getTipo());
        }
    }
}

