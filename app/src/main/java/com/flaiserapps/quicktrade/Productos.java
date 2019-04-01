package com.flaiserapps.quicktrade;

import android.os.Parcel;
import android.os.Parcelable;

public class Productos implements Parcelable {
    private String categoria;
    private String creador;
    private String descripcion;
    private String nombre;
    private String precio;

    public Productos() {
    }

    public Productos(String categoria, String creador, String descripcion, String nombre, String precio) {
        this.categoria = categoria;
        this.creador = creador;
        this.descripcion = descripcion;
        this.nombre = nombre;
        this.precio = precio;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getCreador() {
        return creador;
    }

    public void setCreador(String creador) {
        this.creador = creador;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    protected Productos(Parcel in) {
        categoria = in.readString();
        creador = in.readString();
        descripcion = in.readString();
        nombre = in.readString();
        precio = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(categoria);
        dest.writeString(creador);
        dest.writeString(descripcion);
        dest.writeString(nombre);
        dest.writeString(precio);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Productos> CREATOR = new Parcelable.Creator<Productos>() {
        @Override
        public Productos createFromParcel(Parcel in) {
            return new Productos(in);
        }

        @Override
        public Productos[] newArray(int size) {
            return new Productos[size];
        }
    };
}
