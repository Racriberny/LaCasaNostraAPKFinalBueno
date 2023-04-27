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

public class AdaptadorListaUsuarios extends RecyclerView.Adapter<AdaptadorListaUsuarios.ViewHolder>{

    private final List<Vista> vistas;

    public AdaptadorListaUsuarios(List<Vista> vistaList){
        this.vistas = vistaList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem_lista_reservas, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Vista vista = vistas.get(position);
        holder.bindProducto(vista);
    }

    @Override
    public int getItemCount() {
        return vistas.size();
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

        public void bindProducto(Vista vista) {
            cantidad.setText(vista.getCantidad());
            hora.setText(vista.getHora());
            fecha.setText(vista.getFecha());
        }

    }


}
