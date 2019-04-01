package com.flaiserapps.quicktrade;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class BuscarProductosCategoria extends AppCompatActivity {
    private Spinner categoria;
    private RecyclerView recyclerProductos;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private AdapterBuscarProductosCategoria adapterBuscarCat;
    private RecyclerView.LayoutManager rVLM;
    private ArrayList<Productos> productos;
    private ArrayList<String> IDs;
    private DatabaseReference dbr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_productos_categoria);
        recyclerProductos=(RecyclerView) this.findViewById(R.id.recycler_buscarProdCat);
        categoria=(Spinner) this.findViewById(R.id.spinnerCatBusc);
        String[] arraySpinner=getResources().getStringArray(R.array.categorias);
        ArrayAdapter<String> adapterCategorias= new ArrayAdapter<String>(BuscarProductosCategoria.this,android.R.layout.simple_spinner_item, arraySpinner);
        adapterCategorias.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categoria.setAdapter(adapterCategorias);
        dbr=FirebaseDatabase.getInstance().getReference("productos");
        Log.d("hectorr", dbr.toString());
        mAuth=FirebaseAuth.getInstance();
        user=mAuth.getCurrentUser();
        productos=new ArrayList<>();
        IDs=new ArrayList<>();
        rVLM=new LinearLayoutManager(getApplicationContext());
        recyclerProductos.setLayoutManager(rVLM);
        adapterBuscarCat=new AdapterBuscarProductosCategoria(productos, IDs, BuscarProductosCategoria.this);
        recyclerProductos.setAdapter(adapterBuscarCat);

        categoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                dbr.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        productos.clear();
                        IDs.clear();

                        for (DataSnapshot ds:dataSnapshot.getChildren()) {
                            //Log.d("hectorr", ds.getKey());
                            Productos aux = ds.getValue(Productos.class);
                            if (aux.getCategoria().compareTo(categoria.getSelectedItem().toString())==0){
                                productos.add(aux);
                                IDs.add(ds.getKey());
                            }
                        }

                        Log.d("hectorr", ""+productos.size());

                        adapterBuscarCat.setIDs(IDs);
                        adapterBuscarCat.setProductos(productos);
                        adapterBuscarCat.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
