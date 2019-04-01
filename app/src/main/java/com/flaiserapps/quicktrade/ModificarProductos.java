package com.flaiserapps.quicktrade;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ModificarProductos extends AppCompatActivity {

    private RecyclerView recycler_modprod;
    private DatabaseReference dbr;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private AdapterEditarProductos adapterModProd;
    private RecyclerView.LayoutManager rVLM;
    private ArrayList<Productos> productos;
    private ArrayList<String> IDs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_productos);
        recycler_modprod=(RecyclerView) this.findViewById(R.id.recycler_modprod);
        dbr=FirebaseDatabase.getInstance().getReference("productos");
        Log.d("hectorr", dbr.toString());
        mAuth=FirebaseAuth.getInstance();
        user=mAuth.getCurrentUser();
        productos=new ArrayList<>();
        IDs=new ArrayList<>();
        rVLM=new LinearLayoutManager(getApplicationContext());
        recycler_modprod.setLayoutManager(rVLM);
        adapterModProd=new AdapterEditarProductos(productos, IDs, ModificarProductos.this);
        recycler_modprod.setAdapter(adapterModProd);

        dbr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                productos.clear();
                IDs.clear();

                for (DataSnapshot ds:dataSnapshot.getChildren()) {
                    Log.d("hectorr", ds.getKey());
                    Productos aux = ds.getValue(Productos.class);
                    if (aux.getCreador().compareTo(user.getUid())==0){
                        productos.add(aux);
                        IDs.add(ds.getKey());
                    }
                }

                Log.d("hectorr", ""+productos.size());

                adapterModProd.setIDs(IDs);
                adapterModProd.setProductos(productos);
                adapterModProd.notifyDataSetChanged();
            }



            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
