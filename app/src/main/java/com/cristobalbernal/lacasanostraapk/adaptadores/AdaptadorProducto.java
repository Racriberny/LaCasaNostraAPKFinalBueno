package com.cristobalbernal.lacasanostraapk.adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cristobalbernal.lacasanostraapk.R;
import com.cristobalbernal.lacasanostraapk.Utils.EncodingImg;
import com.cristobalbernal.lacasanostraapk.modelos.Producto;

import java.util.List;

public class AdaptadorProducto extends RecyclerView.Adapter<AdaptadorProducto.ViewHolder> {
    private final List<Producto> productos;

    public AdaptadorProducto(List<Producto> productos){
        this.productos = productos;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem_producto, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Producto producto = productos.get(position);
        holder.bindFrase(producto);
    }

    @Override
    public int getItemCount() {
        return productos.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView imageView;
        private final TextView productoTv;
        private final TextView ingredientes;
        private final TextView kcal;
        private final TextView precio;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView3);
            productoTv = itemView.findViewById(R.id.nombreDelProducto);
            ingredientes = itemView.findViewById(R.id.ingredientesProductos);
            kcal = itemView.findViewById(R.id.kcal);
            precio = itemView.findViewById(R.id.precioProducoto);

        }

        public void bindFrase(Producto producto) {
            imageView.setImageBitmap(EncodingImg.decode(producto.getUrl_imagen()));
            productoTv.setText(producto.getNombre());
            ingredientes.setText(producto.getIngredientes());
            kcal.setText(producto.getCalorias());
            precio.setText(producto.getPrecio());
        }
    }
}
