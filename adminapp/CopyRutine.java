package com.example.adminapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ToolbarWidgetWrapper;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

//the spinner and the button from the bar needs to be managed in here
public class CopyRutine extends AppCompatActivity implements View.OnClickListener {
    public CardView rutinecard;
    public String Nombre;
    public TextView novisible;
    public DatabaseReference Fisico;
    public String ejercicio, series, repeticiones, pesomaximo;
    public ImageButton cb;
    public Button addrutinasbutton;
    public String selectedd;
    public pojocopyrutine object;
    public Boolean valid = true, valid2 = true;
    public ImageView box;
    public Button editarej;

    public Spinner sp;
    public androidx.appcompat.widget.Toolbar tb;
    public RecyclerView rv;
    ArrayList<pojocopyrutine> list;
    ArrayList<pojocopyrutine> listfiltered;
    AdapterCopyRutine adapterCopyRutine;

    public int cont1 = 0, cont2 = 0, cont3 = 0, cont4 = 0, cont5 = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_copyrutine);
        initViews();

        addrutinasbutton.setOnClickListener(this);
        editarej.setOnClickListener(this);

        rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        list = new ArrayList<pojocopyrutine>();
        listfiltered = new ArrayList<pojocopyrutine>();

        Nombre = getIntent().getStringExtra("nombrecompleto");

        Fisico = FirebaseDatabase.getInstance().getReference("Fisico");

        // sp.setOnItemSelectedListener(this);

        try {
            Fisico.child(Nombre).child("rutinas3").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    try {
                        list.clear(); //the data that need to be filter is the one set in the list object
                        listfiltered.clear();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            //here is the part to get only the data wich has the specific days we want and the adapter gett all the data
                            pojocopyrutine pjc = dataSnapshot.getValue(pojocopyrutine.class);
                            if (pjc.getDia().equals(getday())) {
                                //it goes from one object by one so one list has all the items and the other only the ones that we wanted
                                //we only put the data filtered in the list
                                listfiltered.add(pjc); //puede que aca este consiguiendo all
                            }

                            list.add(pjc);

                            if (listfiltered.size() == 0) {
                                valid2 = false;
                            } else {
                                valid2 = true;
                            }
                        }
                        if (valid2) {
                            novisible.setText("");
                            //the box and question mark gone and rv on
                            rv.setVisibility(View.VISIBLE);
                            box.setVisibility(View.GONE);
                            initRecycler();
                        } else {
                            novisible.setText("No hay rutinas en " + sp.getSelectedItem().toString());

                            box.setVisibility(View.VISIBLE);
                            rv.setVisibility(View.GONE);
                            // Toast.makeText(getApplicationContext(), "No hay actividades en el día seleccionado", Toast.LENGTH_SHORT).show();
                        }
                        initRecycler();
                    } catch (Exception e) {

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }

            });
        } catch (Exception e) {
            Toast.makeText(this, "error" + e, Toast.LENGTH_SHORT).show();
        }
//may be null pointer exception on the list data if this wasnt setted yet
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                  // Toast.makeText(getApplicationContext(), "Entré al nuevo", Toast.LENGTH_SHORT).show();

                    selectedd = getday();
                  //  Toast.makeText(getApplicationContext(), selectedd, Toast.LENGTH_SHORT).show();
                    //updaterecycler();
                    //Before it was a singlevalue event listener
                    Fisico.child(Nombre).child("rutinas3").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            list.clear(); //the data that need to be filter is the one set in the list object
                            listfiltered.clear();
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                //here is the part to get only the data wich has the specific days we want and the adapter gett all the data
                                pojocopyrutine pjc = dataSnapshot.getValue(pojocopyrutine.class);
                                assert pjc != null;
                                try {
                                    if (pjc.getDia().equals(getday())) {
                                        //it goes from one object by one so one list has all the items and the other only the ones that we wanted
                                        //we only put the data filtered in the list
                                        listfiltered.add(pjc); //puede que aca este consiguiendo all
                                    }
                                }catch (Exception e){

                                }
                                list.add(pjc);

                                if (listfiltered.size() == 0) {
                                    valid = false;
                                } else {
                                    valid = true;
                                }
                            }
                            if (valid) {
                                novisible.setText("");
                                box.setVisibility(View.GONE);
                                rv.setVisibility(View.VISIBLE);
                                initRecycler();
                            } else {
                                novisible.setText("No hay rutinas en " + sp.getSelectedItem().toString());
                                rv.setVisibility(View.GONE);
                                box.setVisibility(View.VISIBLE);
                                // Toast.makeText(getApplicationContext(), "No hay actividades en el día seleccionado", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(getApplicationContext(), "Algo salió mal", Toast.LENGTH_SHORT).show();
                        }
                    });

                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Error" + e, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        //the list add does not need to be in one line it can be one by one for this particular place
    }
    //we have to change that and use one single get that fill the pojo class fields

    public void initRecycler() { //we have to use the listfiltered but for esting proposes we ill put list
        adapterCopyRutine = new AdapterCopyRutine(this, listfiltered, this, this, this);
        rv.setAdapter(adapterCopyRutine);
        adapterCopyRutine.notifyDataSetChanged();
        //list.clear();
        //listfiltered.clear();
    }

    public void initViews() {
        rutinecard = (CardView) findViewById(R.id.rutinecard);
        rv = (RecyclerView) findViewById(R.id.recyclercardsview);
        sp = (Spinner) findViewById(R.id.spinner);
        novisible = (TextView) findViewById(R.id.novisible);
        box = (ImageView) findViewById(R.id.emptybox);
        editarej = (Button) findViewById(R.id.editarejercicio);
        addrutinasbutton = (Button) findViewById(R.id.plusrutinebutton);
        // add the two last buttons
        //tb =  findViewById(R.id.toolbar);
    }

    //this cuold be an interface
    public String getday() {
        return sp.getSelectedItem().toString(); //we get the day that we want for the filtering in the holder viewing
    }

    public void toRecords() {
        Intent intent = new Intent(CopyRutine.this, Records.class);
        startActivity(intent);
    }

    //cuando se abre la actividad y cuando se cambia el spinner tamben debe colocarse todos en 0
//hoy dejo hecho el agregar y el editar lo pongo en un boton
//tambien hago el listo y me queda solucionar el que solo hay una cuenta

    //if want to add morw button use switch case and id to cast to the proper object using the function thats wanted
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.plusrutinebutton:
                Intent intent = new Intent(CopyRutine.this, addrutinas.class);
                intent.putExtra("fullname", Nombre);
                startActivity(intent);
                break;
            case R.id.editarejercicio:
                Intent intent1 = new Intent(CopyRutine.this, Select_rutine.class);
                intent1.putExtra("nombrefull", Nombre);

                startActivity(intent1);

                //vamos a tener que modificar como manejamos el tema de la modificacion y tener todos los ejercicios mostrados y ahi tocar para que nos lleve a modificarlos
        }
        //here we have to add a swithc case managing clicks by id
        //toRecords();
    }

//we have to get the data the same in listfiltered always and list has to be changing

}
