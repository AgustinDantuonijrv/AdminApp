package com.example.adminapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;


public class Videostodelete extends Fragment implements AdapterVideos.OnItemListener, AdapterView.OnItemSelectedListener {
    public DatabaseReference Videos;
    public Spinner spinner;
    public SearchView sw;
    public RecyclerView rw;
    public Button bdelete;
    ArrayList<PojoVideos> list, listfiltered;
    AdapterVideos adapterVideos;

    public Videostodelete() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_videostodelete, container, false);
        try {

            Videos = FirebaseDatabase.getInstance().getReference("Videos");


            rw = rootview.findViewById(R.id.recyclerdevideos);
            rw.setLayoutManager(new LinearLayoutManager(getContext()));
            list = new ArrayList<PojoVideos>();

            listfiltered = new ArrayList<PojoVideos>();
            sw = rootview.findViewById(R.id.searchid);

            spinner = rootview.findViewById(R.id.spinner);
            spinner.setSelection(1);
            spinner.setOnItemSelectedListener(this);


            Videos.child(spinner.getSelectedItem().toString()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    list.clear();
                    listfiltered.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        PojoVideos p = dataSnapshot.getValue(PojoVideos.class);
                        listfiltered.add(p);
                        list.add(p);
                    }
                    initrecycler();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            if (sw != null) {

                sw.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        search(newText);
                        return true;
                    }
                });
            } else {
                Toast.makeText(getActivity().getApplicationContext(), "seleccione una categoría", Toast.LENGTH_SHORT).show();

            }

        } catch (Exception e) {
            Toast.makeText(getContext().getApplicationContext(), "errorfatal:" + e, Toast.LENGTH_SHORT).show();
        }
        return rootview;
    }

    public void search(final String text) {
        Videos.child(spinner.getSelectedItem().toString()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                listfiltered.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    PojoVideos p = dataSnapshot.getValue(PojoVideos.class);
                    if (p.getTitulo().contains(text)) {
                        listfiltered.add(p);
                    }
                }
                initrecycler();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void initrecycler() {
        adapterVideos = new AdapterVideos(getActivity(), listfiltered, this);
        rw.setAdapter(adapterVideos);
        adapterVideos.notifyDataSetChanged();
    }

    public String getTitulospinner() {
        return spinner.getSelectedItem().toString();
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(getActivity().getApplicationContext(), VerVideo.class);
        intent.putExtra("video", listfiltered.get(position));
        intent.putExtra("categoria", spinner.getSelectedItem().toString());
        startActivity(intent);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Videos.child(spinner.getSelectedItem().toString()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                listfiltered.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    PojoVideos p = dataSnapshot.getValue(PojoVideos.class);
                    listfiltered.add(p);
                    list.add(p);
                }
                initrecycler();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }
}