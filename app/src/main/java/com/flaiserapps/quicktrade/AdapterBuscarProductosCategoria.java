package com.flaiserapps.quicktrade;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.ArrayList;

public class AdapterBuscarProductosCategoria extends RecyclerView.Adapter<AdapterBuscarProductosCategoria.ProductoViewHolder> {

    private ArrayList<Productos> productos;
    private ArrayList<String> IDs;
    private Context contexto;

    //Inicio getters y setters
    public ArrayList<Productos> getProductos() {
        return productos;
    }

    public void setProductos(ArrayList<Productos> productos) {
        this.productos = productos;
    }

    public ArrayList<String> getIDs() {
        return IDs;
    }

    public void setIDs(ArrayList<String> IDs) {
        this.IDs = IDs;
    }

    //Fin getters y setters


    //Inicio de la clase ProductoViewHolder
    public static class ProductoViewHolder extends RecyclerView.ViewHolder {
        ImageButton delete, save;
        EditText nombreprod, descprod, precioprod;

        public ProductoViewHolder(@NonNull View itemView) {
            super(itemView);
            delete=(ImageButton) itemView.findViewById(R.id.deleteprod);
            save=(ImageButton) itemView.findViewById(R.id.updateprod);
            nombreprod=(EditText) itemView.findViewById(R.id.mod_nombreprod);
            descprod=(EditText) itemView.findViewById(R.id.mod_descprod);
            precioprod=(EditText) itemView.findViewById(R.id.mod_precioprod);
        }
    }
    //Fin de la clase ProductoViewHolder

    //Constructor de la clase
    public AdapterBuscarProductosCategoria(ArrayList<Productos> productos, ArrayList<String> IDs, Context contexto) {
        this.productos = productos;
        this.IDs = IDs;
        this.contexto = contexto;
    }
    //Fin constructor de la clase


    @NonNull
    @Override
    public ProductoViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //Al inicializar cada tarjeta
        View v=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.producto_viewholder, viewGroup, false);
        return new ProductoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductoViewHolder productoViewHolder, int i) {
        //Java...
        final int pos=i;
        Productos prodaux=productos.get(i);
        productoViewHolder.descprod.setText(prodaux.getDescripcion());
        productoViewHolder.nombreprod.setText(prodaux.getNombre());
        productoViewHolder.precioprod.setText(prodaux.getPrecio());
        productoViewHolder.save.setVisibility(View.INVISIBLE);
        productoViewHolder.delete.setVisibility(View.INVISIBLE);


    }

    @Override
    public int getItemCount() {
        //Tiene que devolver el numero de productos del arraylist para indicarselo al recyclerview
        Log.d("hectorr", "Get item count: "+productos.size());
        return productos.size();
    }
}
