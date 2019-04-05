package com.flaiserapps.quicktrade;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Home extends AppCompatActivity {
    private Button creaprod, modprod, modusu, buscprodcat, buscarprodusu, buscarprodnombre;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        creaprod=(Button) this.findViewById(R.id.crear_producto);
        modprod=(Button) this.findViewById(R.id.modificar_productos);
        modusu=(Button) this.findViewById(R.id.modificar_datos_usuario);
        buscprodcat=(Button) this.findViewById(R.id.buscar_productos_categoria);
        buscarprodusu=(Button) this.findViewById(R.id.buscar_productos_usuario);
        buscarprodnombre=(Button) this.findViewById(R.id.buscar_productos_nombre);

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
        buscprodcat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), BuscarProductosCategoria.class);
                startActivity(i);
            }
        });
        buscarprodusu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), BuscarProductosUsuario.class);
                startActivity(i);
            }
        });
        buscarprodnombre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), BuscarProductosNombre.class);
                startActivity(i);
            }
        });
    }
}
