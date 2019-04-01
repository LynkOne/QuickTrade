package com.flaiserapps.quicktrade;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Home extends AppCompatActivity {
    private Button creaprod, modprod, modusu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        creaprod=(Button) this.findViewById(R.id.crear_producto);
        modprod=(Button) this.findViewById(R.id.modificar_productos);
        modusu=(Button) this.findViewById(R.id.modificar_datos_usuario);
        creaprod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent i = new Intent(getApplicationContext(), CrearProductos.class);
            startActivity(i);
            }
        });
        modprod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ModificarProductos.class);
                startActivity(i);
            }
        });
        modusu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),ModificarUsuario.class);
                startActivity(i);
            }
        });
    }
}
