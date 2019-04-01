package com.flaiserapps.quicktrade;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdapterEditarProductos extends RecyclerView.Adapter<AdapterEditarProductos.ProductoViewHolder>{

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

    //Clase ProductoViewHolder
    public static class ProductoViewHolder extends RecyclerView.ViewHolder{
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
    public AdapterEditarProductos(ArrayList<Productos> productos, ArrayList<String> IDs, Context contexto) {
        //Constructor del adapter, recibe arraylist de productos
        Log.d("hectorr", "Constructor llamado AdapterEditarProductos");
        this.productos = productos;
        this.contexto=contexto;
        this.IDs=IDs;
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
    public void onBindViewHolder(@NonNull final ProductoViewHolder productoViewHolder, int i) {
        //Java code..
        final int pos=i;
        Productos prodaux=productos.get(i);
        productoViewHolder.descprod.setText(prodaux.getDescripcion());
        productoViewHolder.nombreprod.setText(prodaux.getNombre());
        productoViewHolder.precioprod.setText(prodaux.getPrecio());
        productoViewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertBuilder=new AlertDialog.Builder(contexto);
                alertBuilder.setTitle("¿Desea eliminar el producto?");
                alertBuilder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Eliminar el producto seleccionado
                        DatabaseReference dbr= FirebaseDatabase.getInstance().getReference("productos").child(IDs.get(pos));
                        Log.d("hecotrr", "Database Reference: "+dbr.toString());
                        dbr.removeValue();
                        IDs.remove(pos);
                        productos.remove(pos);
                        notifyItemRemoved(pos);
                        notifyItemRangeChanged(pos, getItemCount());
                        dialog.dismiss();
                    }
                });
                alertBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    }
                });
                alertBuilder.show();
            }
        });
        productoViewHolder.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Guardar cambios
                DatabaseReference dbr= FirebaseDatabase.getInstance().getReference("productos").child(IDs.get(pos));
                Log.d("hecotrr", "Database Reference: "+dbr.toString());
                final Productos prodaux=new Productos();
                prodaux.setNombre(productoViewHolder.nombreprod.getText().toString());
                prodaux.setPrecio(productoViewHolder.precioprod.getText().toString());
                prodaux.setDescripcion(productoViewHolder.descprod.getText().toString());
                dbr.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        dataSnapshot.getRef().child("nombre").setValue(prodaux.getNombre());
                        dataSnapshot.getRef().child("descripcion").setValue(prodaux.getDescripcion());
                        dataSnapshot.getRef().child("precio").setValue(prodaux.getPrecio());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

    }

    @Override
    public int getItemCount() {
        Log.d("hectorr", "Get item count: "+productos.size());
        //Tiene que devolver el numero de productos del arraylist para indicarselo al recyclerview
        return productos.size();
    }
}
