package com.flaiserapps.quicktrade;

import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ModificarUsuario extends AppCompatActivity {

    private ImageButton save;
    private EditText nombre, apellidos, telefono;
    private DatabaseReference dbr;
    private FirebaseAuth mAuth;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_usuario);
        nombre=(EditText) this.findViewById(R.id.mod_nombreusu);
        apellidos=(EditText) this.findViewById(R.id.mod_apeliidos);
        telefono=(EditText) this.findViewById(R.id.mod_telefono);
        save=(ImageButton) this.findViewById(R.id.ib_moddatausu_save);

        dbr=FirebaseDatabase.getInstance().getReference("usuarios");
        mAuth=FirebaseAuth.getInstance();
        user=mAuth.getCurrentUser();

        dbr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds:dataSnapshot.getChildren()) {
                    if(ds.getKey().compareTo(user.getUid())==0){
                        Usuario aux=ds.getValue(Usuario.class);
                        Log.d("hectorr", "Key uid del usuario en Firebase encontrado: "+ds.getKey());
                        nombre.setText(aux.getNombre());
                        apellidos.setText(aux.getApellidos());
                        telefono.setText(aux.getTelefono());
                    }


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbr.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }
}
