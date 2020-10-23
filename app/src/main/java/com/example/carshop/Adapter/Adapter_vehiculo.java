package com.example.carshop.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.carshop.Clases.Vehiculo;
import com.example.carshop.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Adapter_vehiculo extends RecyclerView.Adapter<Adapter_vehiculo.ViewHolderVehiculo> implements View.OnClickListener  {

    ArrayList<Vehiculo> listVehiculo;
    private View.OnClickListener listener;

    public Adapter_vehiculo (ArrayList<Vehiculo> listVehiculo) {
        this.listVehiculo = listVehiculo;
    }

    @NonNull
    @Override
    public Adapter_vehiculo.ViewHolderVehiculo onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_vehiculo, null, false);
        view.setOnClickListener(this);
        return new Adapter_vehiculo.ViewHolderVehiculo(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderVehiculo holder, int position) {
        holder.modelo.setText(listVehiculo.get(position).getModelo());
        holder.precio.setText(listVehiculo.get(position).getPrecio());
        holder.asientos.setText(listVehiculo.get(position).getAsientos());
        holder.estado.setText(listVehiculo.get(position).getEstado());
        holder.fecha.setText((CharSequence) listVehiculo.get(position).getFecha_publicacion());
        holder.categoria.setText(listVehiculo.get(position).getCategoria());
        holder.otros.setText(listVehiculo.get(position).getOtros());
    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        return listVehiculo.size();
    }

    @Override
    public void onClick(View view) {
        if (this.listener != null) {
            listener.onClick(view);
        }
    }

    public class ViewHolderVehiculo extends RecyclerView.ViewHolder {

        TextView modelo, precio, asientos, estado, fecha, otros, categoria;

        public ViewHolderVehiculo(@NonNull View itemView) {
            super(itemView);
            modelo = itemView.findViewById(R.id.txtModelo);
            precio = itemView.findViewById(R.id.txtPrecio);
            asientos = itemView.findViewById(R.id.txtAsientos);
            estado = itemView.findViewById(R.id.txtEstado);
            fecha = itemView.findViewById(R.id.txtFecha);
            categoria = itemView.findViewById(R.id.txtCateVehiculo);
            otros = itemView.findViewById(R.id.txtOtros);
        }
    }
}
