package com.flaiserapps.quicktrade;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CrearProductos extends AppCompatActivity {
    private Button ok, cancel;
    private Spinner categoria;
    private FirebaseAuth mAuth;
    private EditText nombreprod, descprod, precioprod;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_productos);
        ok=(Button)this.findViewById(R.id.creaprod_ok);
        cancel=(Button) this.findViewById(R.id.creaprod_cancel);
        categoria=(Spinner) this.findViewById(R.id.creaprod_cat);
        nombreprod=(EditText)this.findViewById(R.id.creaprod_nombre);
        descprod=(EditText) this.findViewById(R.id.creaprod_desc);
        precioprod=(EditText) this.findViewById(R.id.creaprod_precio);
        mAuth=FirebaseAuth.getInstance();
        String[] arraySpinner=getResources().getStringArray(R.array.categorias);
        ArrayAdapter<String> adapterCategorias= new ArrayAdapter<String>(CrearProductos.this,android.R.layout.simple_spinner_item, arraySpinner);
        adapterCategorias.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categoria.setAdapter(adapterCategorias);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nombreprod.getText().toString().isEmpty()){
                    nombreprod.setError("Introduce un nombre");
                    nombreprod.requestFocus();
                }else {
                    if (descprod.getText().toString().isEmpty()){
                        descprod.setError("Introduce una descripcion");
                        descprod.requestFocus();
                    }else{
                        if(precioprod.getText().toString().isEmpty()){
                            precioprod.setError("Introduce un precio");
                            precioprod.requestFocus();
                        }else{
                            crearProducto();
                        }
                    }
                }
            }
        });


    }
    public void crearProducto(){
        FirebaseUser user = mAuth.getCurrentUser();
        String uid=user.getUid();
        Productos prodaux=new Productos(categoria.getSelectedItem().toString(),uid,descprod.getText().toString(),nombreprod.getText().toString(),precioprod.getText().toString());
        //Apuntamos al nodo productos
        DatabaseReference dbr= FirebaseDatabase.getInstance().getReference("productos");
        Log.d("hecotrr", "Database Reference: "+dbr.toString());
        //Generar uid para productos (despues de database reference)
        String prodUid=dbr.push().getKey();
        //Si no existe lo crea, si existe lo machaca
        dbr.child(prodUid).setValue(prodaux);
        setResult(RESULT_OK);
        finish();
    }
}
