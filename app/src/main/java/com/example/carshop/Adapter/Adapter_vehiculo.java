package com.example.carshop.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.carshop.Clases.Vehiculo;
import com.example.carshop.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Adapter_vehiculo extends RecyclerView.Adapter<Adapter_vehiculo.ViewHolderVehiculo> implements View.OnClickListener  {

    ArrayList<Vehiculo> listVehiculo;
    private View.OnClickListener listener;
    private Context context;

    public Adapter_vehiculo (ArrayList<Vehiculo> listVehiculo, Context context) {
        this.listVehiculo = listVehiculo;
        this.context = context;
    }

    @NonNull
    @Override
    public Adapter_vehiculo.ViewHolderVehiculo onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_vehiculo, null, false);
        view.setOnClickListener(this);
        view.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
        return new Adapter_vehiculo.ViewHolderVehiculo(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderVehiculo holder, int position) {
        holder.modelo.setText(listVehiculo.get(position).getModelo());
        holder.precio.setText("$ " + String.valueOf(listVehiculo.get(position).getPrecio()));
        holder.asientos.setText(String.valueOf(listVehiculo.get(position).getAsientos()));
        holder.estado.setText(listVehiculo.get(position).getEstado());
        holder.fecha.setText((CharSequence) listVehiculo.get(position).getFecha_publicacion());
        holder.categoria.setText(listVehiculo.get(position).getCategoria());
        holder.tipo.setText(listVehiculo.get(position).getTipo());
        holder.cantidad.setText(listVehiculo.get(position).getCantidad());
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

        TextView modelo, precio, asientos, estado, fecha, categoria, tipo, cantidad;

        public ViewHolderVehiculo(@NonNull View itemView) {
            super(itemView);
            modelo = itemView.findViewById(R.id.txtModelo);
            precio = itemView.findViewById(R.id.txtPrecio);
            asientos = itemView.findViewById(R.id.txtAsientos);
            estado = itemView.findViewById(R.id.txtEstado);
            fecha = itemView.findViewById(R.id.txtFecha);
            categoria = itemView.findViewById(R.id.txtCateVehiculo);
            tipo = itemView.findViewById(R.id.txtTipo);
            cantidad = itemView.findViewById(R.id.txtCantidad);
        }
    }
}
