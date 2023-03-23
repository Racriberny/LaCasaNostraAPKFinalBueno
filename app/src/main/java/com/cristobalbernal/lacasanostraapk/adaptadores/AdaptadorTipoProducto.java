package com.cristobalbernal.lacasanostraapk.adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cristobalbernal.lacasanostraapk.R;
import com.cristobalbernal.lacasanostraapk.interfaces.IProductoComida;
import com.cristobalbernal.lacasanostraapk.modelos.Producto;

import java.util.List;

public class AdaptadorTipoProducto extends RecyclerView.Adapter<AdaptadorTipoProducto.ViewHolder> {

    private final List<Producto> productos;
    private final IProductoComida listener;


    public AdaptadorTipoProducto(List<Producto> productos,IProductoComida listener){
        this.productos = productos;
        this.listener = listener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem_tipo_producto, parent, false);
        return new ViewHolder(itemView,listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Producto producto = productos.get(position);
        holder.bindProducto(producto);
    }

    @Override
    public int getItemCount() {
        return productos.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        //private final ImageView imageView;
        private final TextView productoTv;
        private final TextView ingredientesTv;
        private final IProductoComida listener;

        public ViewHolder(@NonNull View itemView, IProductoComida listener) {
            super(itemView);
            //this.imageView = itemView.findViewById(R.id.ivPhoto);
            this.productoTv = itemView.findViewById(R.id.nombreProducto);
            this.ingredientesTv = itemView.findViewById(R.id.ingredientes);
            this.listener = listener;
            itemView.setOnClickListener(this);
        }

        public void bindProducto(Producto producto) {
            //imageView.setImageBitmap(EncodingImg.Decode(producto.getUrl_imagen()));
            productoTv.setText(producto.getNombre());
            ingredientesTv.setText(producto.getIngredientes());
        }

        @Override
        public void onClick(View v) {
            if (listener !=null){
                listener.onProductoSeleccionada(getAdapterPosition());
            }
        }
    }
}
