package com.example.adminapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

//we ll have to declare finals and see where to put everything to work like in a appcompat activity

public class VerusuariosFragment extends Fragment implements Adapterrecycler.OnUserListener {

    public RecyclerView recyclerView;
    DatabaseReference users;
    ArrayList<Profile> list;
    Adapterrecycler adapterrecycler;
    public String nombre;

    public VerusuariosFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        users = FirebaseDatabase.getInstance().getReference("users");
        //We have to take the messeage of and put it in other wat and then modify so that keeps working

      View rootview = inflater.inflate(R.layout.fragment_verusuarios, container, false);

      recyclerView = (RecyclerView) rootview.findViewById(R.id.recyclerusuarios);
      recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
      list = new ArrayList<Profile>();

      users.addValueEventListener(new ValueEventListener() {
          @Override
          public void onDataChange(@NonNull DataSnapshot snapshot) {
              list.clear();
           for (DataSnapshot dataSnapshot: snapshot.getChildren()){
              Profile p = dataSnapshot.getValue(Profile.class);
 //puede que haya que hacer un poco mas para especificar bien que key pertenece a cada variable del objeto profile
               list.add(p); //creamos un objeto de tipo perfil que espera todos los parametros string y lo seteamos al adapter
           }
           initRecycler();
          }

          @Override
          public void onCancelled(@NonNull DatabaseError error) {
              Toast.makeText(getActivity().getApplicationContext(), "Algo salio mal", Toast.LENGTH_SHORT).show();
          }
      });

      return rootview;
    }

    private void initRecycler() {
        adapterrecycler = new Adapterrecycler(getActivity(),list,this);
        recyclerView.setAdapter(adapterrecycler);
        adapterrecycler.notifyDataSetChanged();
    }

    @Override
    public void onUserClick(int position) {
        Intent intent = new Intent(getActivity().getApplicationContext(), UsuarioCompleto.class);
        intent.putExtra("usuario", list.get(position));

        startActivity(intent);
    }
}
