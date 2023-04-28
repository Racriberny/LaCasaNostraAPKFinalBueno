package com.cristobalbernal.lacasanostraapk.adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cristobalbernal.lacasanostraapk.R;
import com.cristobalbernal.lacasanostraapk.modelos.Reservas;
import com.cristobalbernal.lacasanostraapk.modelos.Vista;

import java.util.List;

public class AdaptadorReservasAntiguas extends RecyclerView.Adapter<AdaptadorReservasAntiguas.ViewHolder>{

    private final List<Reservas> reservas;

    public AdaptadorReservasAntiguas(List<Reservas> reservas) {
        this.reservas = reservas;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem_lista_reservas, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Reservas reservas1 = reservas.get(position);
        holder.bindProducto(reservas1);
    }

    @Override
    public int getItemCount() {
        return reservas.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{

        private final TextView cantidad;
        private final TextView hora;
        private final TextView fecha;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cantidad = itemView.findViewById(R.id.tViCantidad);
            hora = itemView.findViewById(R.id.tViHora);
            fecha = itemView.findViewById(R.id.tViFecha);

        }

        public void bindProducto(Reservas reservas1) {
            cantidad.setText(reservas1.getCantidad());
            hora.setText(reservas1.getHora());
            fecha.setText(reservas1.getFecha());
        }

    }
}
