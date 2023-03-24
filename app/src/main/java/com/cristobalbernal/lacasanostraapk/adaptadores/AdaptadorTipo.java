package com.cristobalbernal.lacasanostraapk.adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cristobalbernal.lacasanostraapk.R;
import com.cristobalbernal.lacasanostraapk.interfaces.ITipoComida;
import com.cristobalbernal.lacasanostraapk.modelos.Tipo;

import java.util.List;

public class AdaptadorTipo extends RecyclerView.Adapter<AdaptadorTipo.ViewHolder> {
    private final List<Tipo> tipos;
    private final ITipoComida listener;


    public AdaptadorTipo(List<Tipo> tipos, ITipoComida listener){
        this.tipos = tipos;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem_tipo, parent, false);
        return new ViewHolder(itemView,listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Tipo tipo = tipos.get(position);
        holder.bindCategoria(tipo);
    }

    @Override
    public int getItemCount() {
        return tipos.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private final TextView tipo;
        private final TextView descripcion;
        private final ITipoComida tipoListener;

        public ViewHolder(@NonNull View itemView, ITipoComida listener){
            super(itemView);
            this.tipo = itemView.findViewById(R.id.tipo);
            this.descripcion = itemView.findViewById(R.id.descripcion);
            this.tipoListener = listener;
            itemView.setOnClickListener(this);
        }

        public void bindCategoria(Tipo tipos) {
            tipo.setText(tipos.getNombre());
            descripcion.setText(tipos.getDescripcion());
        }

        @Override
        public void onClick(View v) {
            if (tipoListener !=null){
                tipoListener.onTipoSeleccionada(getAdapterPosition());
            }
        }
    }
}
