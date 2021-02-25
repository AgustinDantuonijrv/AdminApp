package com.example.adminapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class addrecords extends AppCompatActivity {

    public String nombre_de_usuario;
    public TextView showuser;
    public String conttotal;
    public Button senddata;
    public boolean readytoserver;
    public EditText ejercicio, objetivo, pesomaximo, repeticiones;
    public String ejercicios;
    public String objetivos;
    public String pesomaximos;
    public String repeticioness;
    public String index;
    public DatabaseReference Fisico;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addrecords);

        initviews();

        Fisico = FirebaseDatabase.getInstance().getReference("Fisico");
        //setting the user name from the beginini of the method

        nombre_de_usuario = getIntent().getStringExtra("fullname");
        Toast.makeText(addrecords.this, "nombre de ussuario obtenido" + nombre_de_usuario, Toast.LENGTH_SHORT).show();
        if (nombre_de_usuario != null) {
            showuser.setText(nombre_de_usuario);

            Fisico.child(nombre_de_usuario).child("records").child("contador").child("conttotal").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.getValue(String.class) != null) {
                        conttotal = snapshot.getValue(String.class);
                    } else {
                        conttotal = "0";
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(addrecords.this, "No se pudo conseguir toda la data deseada", Toast.LENGTH_SHORT).show();
                }
            });
        }

        senddata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getdata();
                if (readytoserver){
                    sendtoserver();
                }
            }
        });

    }


    private void initviews(){
        showuser = (TextView) findViewById(R.id.nombredelusuariomuestra);
        senddata = (Button) findViewById(R.id.crearnuevorecord);
        ejercicio = (EditText) findViewById(R.id.ejerciciomuestra);
        repeticiones = (EditText) findViewById(R.id.repeticionesmuestra);
        objetivo = (EditText) findViewById(R.id.objetivomuestra);
        pesomaximo = (EditText) findViewById(R.id.pesomaximomuestra);
    }
//we are not getting the value to sum 1 every time the onclicklistener runs
    public boolean getdata() {
        readytoserver = false;

        if (ejercicio.getText().toString() != null && repeticiones.getText().toString() != null && objetivo.getText().toString() != null && pesomaximo.getText().toString() != null) {
            readytoserver = true;

            ejercicios = ejercicio.getText().toString();
            repeticioness = repeticiones.getText().toString();
            objetivos = objetivo.getText().toString();
            pesomaximos = pesomaximo.getText().toString();
           int findex = (Integer.parseInt(conttotal) + 1);
           index = String.valueOf(findex);
        } else {
            Toast.makeText(addrecords.this, "Debe llenar toda la informacion" , Toast.LENGTH_SHORT).show();
        }
        return  readytoserver;
    }

    public void sendtoserver() {
        Fisico.child(nombre_de_usuario).child("records").child(index).child("ejercicio").setValue(ejercicios);
        Fisico.child(nombre_de_usuario).child("records").child(index).child("objetivo").setValue(objetivos);
        Fisico.child(nombre_de_usuario).child("records").child(index).child("pesomaximo").setValue(pesomaximos);
        Fisico.child(nombre_de_usuario).child("records").child(index).child("repeticiones").setValue(repeticioness);
        Fisico.child(nombre_de_usuario).child("records").child(index).child("index").setValue(index);
        Fisico.child(nombre_de_usuario).child("records").child("contador").child("conttotal").setValue(index);
    }
}
