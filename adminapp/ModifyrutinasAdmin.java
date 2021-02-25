package com.example.adminapp;

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

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class ModifyrutinasAdmin extends AppCompatActivity {

    public DatabaseReference Fisico;
    public EditText editejercicio,esitpesomaximo,editrepeticiones,editseries;
    public Spinner spinndia;
    public TextView seeobservaciones, ultimospesos, nombrdusuario;
    public String dia, ejercicio, pesomaximo, repeticiones, series, NombreCompleto;
    public Button botonconfirmar;
    public boolean bol,bol1;
    public String index, indexx;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifyrutinas_admin);

        initViews();

        NombreCompleto = getIntent().getStringExtra("nombrefull");
        nombrdusuario.setText(NombreCompleto);
        Toast.makeText(ModifyrutinasAdmin.this, "Recuerde seleccionar el dia correcto", Toast.LENGTH_SHORT).show();

        // this name is not being passed yet
        pojoselectrutine info = getIntent().getParcelableExtra("inforutinas");
        // we have to do the same thing but with the pojo class that belongs to this version of the rutine class
        Fisico = FirebaseDatabase.getInstance().getReference("Fisico");

        // asign all the string values
        assert info != null;
        dia = info.getDia();
        //Toast.makeText(ModifyrutinasAdmin.this, "dia:" + dia, Toast.LENGTH_SHORT).show();
        //setspinner(spinndia,dia);
        ejercicio= info.getEjercicio();
        setText(editejercicio,ejercicio);
        indexx = info.getIndex();
        pesomaximo =  info.getPesomaximo();
        setText(esitpesomaximo,pesomaximo);
        repeticiones = info.getRepeticiones();
        setText(editrepeticiones,repeticiones);
        series = info.getSeries();
        setText(editseries,series);



       // esto no va a ser determinado asi sino que cargamos directamente los datos del parceable
       // y conectamos con la base de datos cuando clickeamos el boton de listo

        botonconfirmar.setOnClickListener(new View.OnClickListener() {
        //vamos a probar consiguiendo el texto directamente y no usando el metodo getdata
            @Override
            public void onClick(View view) { // el dia tiene que ser un spinner para evitar el posible error y de paso es mas comodo para cris
                if (indexx != null) {
                    Fisico.child(NombreCompleto).child("rutinas3").child(indexx).child("dia").setValue(spinndia.getSelectedItem().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            // Toast.makeText(ModifyrutinasAdmin.this, "Datos modificados correctamente", Toast.LENGTH_SHORT).show();
                        }
                    });
                    Fisico.child(NombreCompleto).child("rutinas3").child(indexx).child("ejercicio").setValue(editejercicio.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            // Toast.makeText(ModifyrutinasAdmin.this, "Datos modificados correctamente", Toast.LENGTH_SHORT).show();
                        }
                    });
                    Fisico.child(NombreCompleto).child("rutinas3").child(indexx).child("pesomaximo").setValue(esitpesomaximo.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            // Toast.makeText(ModifyrutinasAdmin.this, "Datos modificados correctamente", Toast.LENGTH_SHORT).show();
                        }
                    });
                    Fisico.child(NombreCompleto).child("rutinas3").child(indexx).child("repeticiones").setValue(editrepeticiones.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            // Toast.makeText(ModifyrutinasAdmin.this, "Datos modificados correctamente", Toast.LENGTH_SHORT).show();
                        }
                    });
                    Fisico.child(NombreCompleto).child("rutinas3").child(indexx).child("series").setValue(editseries.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(ModifyrutinasAdmin.this, "Datos modificados correctamente", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                  //  Toast.makeText(ModifyrutinasAdmin.this, "index" + indexx, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void initViews() {
    spinndia = (Spinner) findViewById(R.id.diaedit);
    editejercicio = (EditText) findViewById(R.id.nombreejercicioedit);
    esitpesomaximo = (EditText) findViewById(R.id.pesomaximoedit);
    editrepeticiones = (EditText) findViewById(R.id.repeticionesedit);
    editseries = (EditText) findViewById(R.id.seriesedit);
    botonconfirmar = (Button) findViewById(R.id.crearnuevarutina);
    nombrdusuario = (TextView) findViewById(R.id.nombredelusuariomuestra);
    }

    public void setText(EditText t, String data) {
       t.setText(data);
    }
    public void setspinner(Spinner s,String data) { // we want the spinner to display the current day of the rutine
       // sabemos la cantidad de veces que itera tiene 5 entries
        int i = 1;
        s.setSelection(i);
        while (!s.getSelectedItem().toString().equals(data)) { //i vale 0 esta repitiendo el 0
            i = i + 1;
           }
        if (s.getSelectedItem().toString().equals(data)){
            s.setSelection(i);
        }
        }
    public String getdata(EditText t) {
        return t.getText().toString();
    }
}
