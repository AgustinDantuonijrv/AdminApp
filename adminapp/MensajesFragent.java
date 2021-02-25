package com.example.adminapp;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.crashlytics.internal.common.Utils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MensajesFragent extends Fragment {

    public DatabaseReference Mensajeria, users, usernamesreference;

    public int contusuarios;

    ArrayList<Profile> list;

    public String mensajestring, destinatariostring, gruposeleccionado;
    public String index; //vamos a utilizar para que cada mensaje sea el siguiente al actual

    public MensajesFragent() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        users = FirebaseDatabase.getInstance().getReference("users");
        usernamesreference = FirebaseDatabase.getInstance().getReference("usernames");
        Mensajeria = FirebaseDatabase.getInstance().getReference("Mensajerias");

         View rootview = inflater.inflate(R.layout.fragment_mensajes_fragent, container, false);

        Button EnviarMensaje = (Button) rootview.findViewById(R.id.enviarmensaje);
        Button Reset = (Button) rootview.findViewById(R.id.reset);
        list = new ArrayList<Profile>();

        final Spinner destspinner = (Spinner) rootview.findViewById(R.id.spinnerdestinatario);
        final EditText mensaje = (EditText) rootview.findViewById(R.id.mensajeedit);
       // final EditText destinatario = (EditText) rootview.findViewById(R.id.destinatarioedit);
        final Spinner grupo = (Spinner) rootview.findViewById(R.id.spineredit2);

 try {
    Mensajeria.child("index").addValueEventListener(new ValueEventListener() {
         @Override
         public void onDataChange(@NonNull DataSnapshot snapshot) {
             //here we will manage all the data set to the arraylist for the recycler view
             index = snapshot.getValue(String.class);
         }
         @Override
         public void onCancelled(@NonNull DatabaseError error) {
         }
     });
   //por ahi va a haber que modificar aca y pnerle que consiga la ruta de la forma que esta hecho en el ejemplo

   //aca a users y nos quedamos con el objeto entero y despues agarramos todos los nombres para cararlos en el
    users.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            try {
                //puedo hacer una lista y sacar la estructura usernames y asi tener armado desde users y eliminar solo a user
                final List<String> usernamesList = new ArrayList<String>();
                  for (DataSnapshot usersnapshot: snapshot.getChildren()) {
                      Profile p = usersnapshot.getValue(Profile.class);
                      String identificadorusuario = p.getEmail();

                      int pos = identificadorusuario.indexOf("@");
                      int pos2 = identificadorusuario.indexOf(".");
                      String username = identificadorusuario.substring(0,pos);
                      if (username.contains(".")){
                          username = identificadorusuario.substring(0,pos2);
                      }

                         if (identificadorusuario!= null){
                             usernamesList.add(username);
                         }
                }
                final ArrayAdapter<String> spinnerArrayadapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.support_simple_spinner_dropdown_item,usernamesList);
                spinnerArrayadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                destspinner.setAdapter(spinnerArrayadapter);

            } catch (Exception exception) {
                Toast.makeText(getActivity().getApplicationContext(), "forerrro:" + exception, Toast.LENGTH_SHORT).show();
            }
        }
        @Override
        public void onCancelled(@NonNull DatabaseError error) {
            //donothing
        }
    });
} catch (Exception ex) {
    Toast.makeText(getActivity().getApplicationContext(), "loqueandapasando:" + ex, Toast.LENGTH_SHORT).show();
}

        EnviarMensaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //get all the data in the onclick call and the logic of private or public gets resolved in the add function

                try {
                    mensajestring = mensaje.getText().toString();
                    destinatariostring = destspinner.getSelectedItem().toString();
                    gruposeleccionado = grupo.getSelectedItem().toString();
                    add();
                }  catch(Exception e) {

                    Toast.makeText(getActivity().getApplicationContext(), "Error al clickear" + e, Toast.LENGTH_SHORT).show();

                }
            }
        });

        Reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mensaje.setText("");
               // destinatario.setText("");
            }
        });

        return rootview;
    }

    private void add() { //Modificar el destinatario

        if (gruposeleccionado.contains("Privado")){


           users.child(destinatariostring).child("Mensaje").setValue(mensajestring);

           Toast.makeText(getActivity().getApplicationContext(), "Se envio el mensaje correctamente a :" + destinatariostring, Toast.LENGTH_SHORT).show();

        } else {
            index = index + 1; //aca estaba hecho con el string value y por eso da nulo
            //hacer mensaje general para grupo especifico
            Mensajeria.child(index).child("Mensaje").setValue(mensajestring);
            Mensajeria.child(index).child("Grupo").setValue(gruposeleccionado);
            Mensajeria.child(index).child("index").setValue(index);
        }

    }
}
