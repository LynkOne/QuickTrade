package com.flaiserapps.quicktrade;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class BuscarProductosNombre extends AppCompatActivity {

    private Button buscar;
    private EditText nombreProd;
    private RecyclerView recyclerProductos;
    private DatabaseReference dbr;
    private ArrayList<Productos> productos;
    private ArrayList<String> IDs_prods;
    private AdapterBuscarProductosCategoria adapterBuscarCat;
    private RecyclerView.LayoutManager rVLM;
    private FirebaseAuth mAuth;
    private FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_productos_nombre);
        recyclerProductos=(RecyclerView) this.findViewById(R.id.rv_buscar_prod_nombre);
        nombreProd=(EditText) this.findViewById(R.id.et_busca_prod_nombre);
        buscar=(Button) this.findViewById(R.id.bt_buscar_prod_nom);
        dbr=FirebaseDatabase.getInstance().getReference("productos");
        Log.d("hectorr", dbr.toString());
        mAuth=FirebaseAuth.getInstance();
        user=mAuth.getCurrentUser();
        productos=new ArrayList<>();
        IDs_prods=new ArrayList<>();
        rVLM=new LinearLayoutManager(getApplicationContext());
        recyclerProductos.setLayoutManager(rVLM);
        adapterBuscarCat=new AdapterBuscarProductosCategoria(productos, IDs_prods, BuscarProductosNombre.this);
        recyclerProductos.setAdapter(adapterBuscarCat);

        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productos.clear();
                IDs_prods.clear();
                dbr.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot ds:dataSnapshot.getChildren()) {
                            Productos aux = ds.getValue(Productos.class);
                            if(aux.getNombre().contains(nombreProd.getText())){
                                productos.add(aux);
                                IDs_prods.add(ds.getKey());
                            }
                        }
                        Log.d("hectorr", ""+productos.size());

                        adapterBuscarCat.setIDs(IDs_prods);
                        adapterBuscarCat.setProductos(productos);
                        adapterBuscarCat.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

    }
}
