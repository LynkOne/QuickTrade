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

public class BuscarProductosUsuario extends AppCompatActivity {
    private Spinner usuarios;
    private RecyclerView recyclerProductos;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private AdapterBuscarProductosCategoria adapterBuscarCat;
    private RecyclerView.LayoutManager rVLM;
    private ArrayList<Usuario> arrusuarios;
    private ArrayList<Productos> productos;
    private ArrayList<String> IDs_prods, IDs_usu;
    private DatabaseReference dbr_prod, dbr_usu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_productos_usuario);
        recyclerProductos=(RecyclerView) this.findViewById(R.id.recycler_buscarProdUsu);
        usuarios=(Spinner) this.findViewById(R.id.spinnerUsuBusc);

        dbr_prod=FirebaseDatabase.getInstance().getReference("productos");
        Log.d("hectorr", dbr_prod.toString());
        dbr_usu=FirebaseDatabase.getInstance().getReference("usuarios");
        Log.d("hectorr", dbr_usu.toString());
        mAuth=FirebaseAuth.getInstance();
        user=mAuth.getCurrentUser();
        productos=new ArrayList<>();
        arrusuarios=new ArrayList<>();
        IDs_prods=new ArrayList<>();
        IDs_usu=new ArrayList<>();
        rVLM=new LinearLayoutManager(getApplicationContext());
        recyclerProductos.setLayoutManager(rVLM);
        adapterBuscarCat=new AdapterBuscarProductosCategoria(productos, IDs_prods, BuscarProductosUsuario.this);
        recyclerProductos.setAdapter(adapterBuscarCat);

        dbr_usu.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arrusuarios.clear();
                IDs_usu.clear();
                for (DataSnapshot ds:dataSnapshot.getChildren()) {
                    Usuario auxusu = ds.getValue(Usuario.class);
                    arrusuarios.add(auxusu);
                    IDs_usu.add(ds.getKey());
                    Log.d("hectorr", auxusu.toString());
                }
                ArrayAdapter<Usuario> adapterUsuarios= new ArrayAdapter<Usuario>(BuscarProductosUsuario.this,android.R.layout.simple_spinner_item, arrusuarios);
                adapterUsuarios.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                usuarios.setAdapter(adapterUsuarios);

                usuarios.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {
                        dbr_prod.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                productos.clear();
                                IDs_prods.clear();
                                for (DataSnapshot ds:dataSnapshot.getChildren()) {
                                    Productos aux = ds.getValue(Productos.class);
                                    if(aux.getCreador().compareTo(IDs_usu.get(position))==0){
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

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
