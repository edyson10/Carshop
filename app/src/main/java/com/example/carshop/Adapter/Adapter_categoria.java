package com.example.carshop.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.carshop.Clases.Categorias;
import com.example.carshop.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Adapter_categoria extends RecyclerView.Adapter<Adapter_categoria.ViewHolderCategoria> implements View.OnClickListener {

    ArrayList<Categorias> listCategoria;
    private View.OnClickListener listener;
    private Context context;

    public Adapter_categoria (ArrayList<Categorias> listCategoria, Context context) {
        this.listCategoria = listCategoria;
        this.context = context;
    }

    @NonNull
    @Override
    public Adapter_categoria.ViewHolderCategoria onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_categoria, null, false);
        view.setOnClickListener(this);
        return new Adapter_categoria.ViewHolderCategoria(view);
    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderCategoria holder, int position) {
        holder.foto.setImageResource(listCategoria.get(position).getFoto());
        holder.nombre.setText(listCategoria.get(position).getNombre());
    }

    @Override
    public int getItemCount() {
        return listCategoria.size();
    }

    @Override
    public void onClick(View view) {
        if (this.listener != null) {
            listener.onClick(view);
        }
    }

    public class ViewHolderCategoria extends RecyclerView.ViewHolder {

        TextView nombre;
        ImageView foto;

        public ViewHolderCategoria(@NonNull View itemView) {
            super(itemView);
            foto = itemView.findViewById(R.id.imagenCategoria);
            nombre = itemView.findViewById(R.id.txtCategoria);
        }
    }
}
