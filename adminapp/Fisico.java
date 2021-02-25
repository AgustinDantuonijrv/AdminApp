package com.example.adminapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Fisico extends AppCompatActivity {

    public String nombre;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fisico);
        if (getIntent().hasExtra("usuario")) {
        Profile usuario = getIntent().getParcelableExtra("usuario");
        nombre = usuario.getNombre();

        //Toast.makeText(this, nombre, Toast.LENGTH_SHORT).show();
    }

    }
}
