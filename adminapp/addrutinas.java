package com.example.adminapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

public class addrutinas extends AppCompatActivity {

    public String nombre_del_ussuario;
    public Button agregar;
    public EditText  nombreejercicioedit, pesomaximoedit, repeticionesedit, seriesedit;
    public Spinner diasedit;
    public String dias, ejercicio, pesomaximo, repeticiones, series;
    public TextView showuser;
    public DatabaseReference Fisico;
    public int Contador_de_rutinas;
    public int index;
    public boolean readytoserver;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addrutinas);

        initviews();
        Fisico = FirebaseDatabase.getInstance().getReference("Fisico");

        nombre_del_ussuario = getIntent().getStringExtra("fullname");

        if (nombre_del_ussuario != null) {
            showuser.setText(nombre_del_ussuario);
        }
        //getthe contrutinas // este contador lo vamos a hacer general afuera total se puede seguir igual la dependencia (hay que acordarse de setearlo cuando se crea
        // un usuario al igual que el recycler vacio)

        Fisico.child(nombre_del_ussuario).child("contadoresrutinas").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                    index = snapshot.getValue(Integer.class);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
         Toast.makeText(addrutinas.this, "Algo salio mal", Toast.LENGTH_SHORT).show();
            }
        });

        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getdata(); // if data not null call the server and send the new data
                if (readytoserver){
                    sendtoserver();
                }
            }
        });
    }

    public void initviews(){
        agregar = (Button) findViewById(R.id.crearnuevarutina);
        diasedit = (Spinner) findViewById(R.id.diaedit);
        nombreejercicioedit = (EditText) findViewById(R.id.nombreejercicioedit);
        pesomaximoedit = (EditText) findViewById(R.id.pesomaximoedit);
        repeticionesedit = (EditText) findViewById(R.id.repeticionesedit);
        seriesedit = (EditText) findViewById(R.id.seriesedit);
        showuser = (TextView) findViewById(R.id.nombredelusuariomuestra);
    }

    public boolean validatingdata2server(){
        return  nombreejercicioedit.getText().toString() != null && pesomaximoedit.getText().toString() != null && repeticionesedit.getText().toString() != null && seriesedit.getText().toString() != null;
    }

    public boolean getdata() {

        readytoserver = false;
//this has to be diferent cause we need the data for seperate ways
        readytoserver = validatingdata2server();
        //we could use an specific function here to see if data is valid to send to server
        //boolean value nedde to know if we can send to server

        dias = diasedit.getSelectedItem().toString();
        ejercicio = nombreejercicioedit.getText().toString();
        pesomaximo = pesomaximoedit.getText().toString();
        repeticiones = repeticionesedit.getText().toString();
        series = seriesedit.getText().toString();

        index = index + 1;// String.valueOf(Integer.parseInt(Contador_de_rutinas) + 1);


        if (!readytoserver) {
            Toast.makeText(addrutinas.this, "Asegurate de haber ingresado bien toda la información", Toast.LENGTH_SHORT).show();
        }
        return readytoserver;
    }

   public void sendtoserver() { //same thing but to the right direction
       try {
           Fisico.child(nombre_del_ussuario).child("rutinas3").child(String.valueOf(index)).child("Comentarios").setValue("");
           Fisico.child(nombre_del_ussuario).child("rutinas3").child(String.valueOf(index)).child("dia").setValue(dias);
           Fisico.child(nombre_del_ussuario).child("rutinas3").child(String.valueOf(index)).child("ejercicio").setValue(ejercicio);
           Fisico.child(nombre_del_ussuario).child("rutinas3").child(String.valueOf(index)).child("pesomaximo").setValue(pesomaximo);
           Fisico.child(nombre_del_ussuario).child("rutinas3").child(String.valueOf(index)).child("repeticiones").setValue(repeticiones);
           Fisico.child(nombre_del_ussuario).child("rutinas3").child(String.valueOf(index)).child("repeticiones1").setValue("0");
           Fisico.child(nombre_del_ussuario).child("rutinas3").child(String.valueOf(index)).child("repeticiones2").setValue("0");
           Fisico.child(nombre_del_ussuario).child("rutinas3").child(String.valueOf(index)).child("repeticiones3").setValue("0");
           Fisico.child(nombre_del_ussuario).child("rutinas3").child(String.valueOf(index)).child("repeticiones4").setValue("0");
           Fisico.child(nombre_del_ussuario).child("rutinas3").child(String.valueOf(index)).child("repeticiones5").setValue("0");
           Fisico.child(nombre_del_ussuario).child("rutinas3").child(String.valueOf(index)).child("index").setValue(String.valueOf(index));
           Fisico.child(nombre_del_ussuario).child("rutinas3").child(String.valueOf(index)).child("series").setValue(series);
           Fisico.child(nombre_del_ussuario).child("contadoresrutinas").setValue(index);

       } catch (Exception e) {
           Toast.makeText(addrutinas.this, "" + e, Toast.LENGTH_SHORT).show();
       }
   }
}
