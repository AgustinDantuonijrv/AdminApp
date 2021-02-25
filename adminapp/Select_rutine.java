package com.example.adminapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Select_rutine extends AppCompatActivity implements Adapter_select_rutine.OnRutineListener {

    public CardView cardselect;
    public RecyclerView recyclerView;
    public DatabaseReference Fisico;
    public TextView displayname;
    ArrayList<pojoselectrutine> list;
    Adapter_select_rutine adapter_select_rutine;
    public String nombre;

    public  Select_rutine () {
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.premodificarrutinas);

        nombre = getIntent().getStringExtra("nombrefull");
        Fisico = FirebaseDatabase.getInstance().getReference("Fisico");

        initviews();

        displayname.setText(nombre);

        recyclerView.setLayoutManager(new LinearLayoutManager(Select_rutine.this));
        list = new ArrayList<pojoselectrutine>();

        try {
            Fisico.child(nombre).child("rutinas3").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                 try {
                     list.clear();
                     for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                         pojoselectrutine pojoselectrutine = dataSnapshot.getValue(pojoselectrutine.class);
                         list.add(pojoselectrutine);
                     }
                     initRecycler();
                 }catch (Exception e){
                     Toast.makeText(Select_rutine.this, "Error al cargar", Toast.LENGTH_SHORT).show();
                 }

                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });

        } catch (Exception e){

        }
    }

    public void initviews(){
        recyclerView = (RecyclerView) findViewById(R.id.recyclerutinasedit);
        cardselect = (CardView) findViewById(R.id.rutinecard);
        displayname = (TextView) findViewById(R.id.username);
    }

    public void initRecycler(){ //we have to use the listfiltered but for esting proposes we ill put list
        adapter_select_rutine = new Adapter_select_rutine(this, list,this);
        recyclerView.setAdapter(adapter_select_rutine);
        adapter_select_rutine.notifyDataSetChanged();
    }

    @Override
    public void onRutineClick(int position) {
        try {
            Intent intent = new Intent(Select_rutine.this, ModifyrutinasAdmin.class);
            intent.putExtra("nombrefull", nombre);
            intent.putExtra("inforutinas", list.get(position));
            startActivity(intent);
        } catch (Exception e){
            Toast.makeText(Select_rutine.this, "error:" + e, Toast.LENGTH_SHORT).show();
        }
    }
}
