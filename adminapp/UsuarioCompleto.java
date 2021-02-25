package com.example.adminapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class UsuarioCompleto extends AppCompatActivity  {

    Profile infopojo;

    public String uridephoto;

    public int borro;

    public DatabaseReference users, fisico, usernames;

    public Button estadisticasbut,deleteuser;

    public ImageView imagencargadadeusuario;

    public String username, nombres,dni,faixa,mimbrodesde,profesor,fechadenacimiento,emaild;

    public TextView usernamet,nombrest,dnit,faixat,miembrodesdet,profesort,fechadenacimientot,emailt;

    public Switch yogaswitch, judoswitch, jiujitsuswitch;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usercompleto);

        users = FirebaseDatabase.getInstance().getReference("users");
        infopojo = getIntent().getParcelableExtra("usuario");

        //Now get all the strings and call

        fisico = FirebaseDatabase.getInstance().getReference("Fisico");
        usernames =FirebaseDatabase.getInstance().getReference("usernames");

        initviews();
        //Falta conseguir  los estados booleanos para setear los switch

        nombres = infopojo.getNombre();
        emaild = infopojo.getEmail();
        dni = infopojo.getDni();
        faixa = infopojo.getFaixa();
        mimbrodesde = infopojo.getMiembrodesde();
        profesor = infopojo.getProfesor();
        fechadenacimiento = infopojo.getNacimiento();
     //  uridephoto = infopojo.getImagenUri();

        getusername();

       users.child(username).child("ImagenUri").addListenerForSingleValueEvent(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
              uridephoto = snapshot.getValue(String.class);
               Glide.with(UsuarioCompleto.this).load(uridephoto).centerCrop().into(imagencargadadeusuario);
           }
           @Override
           public void onCancelled(@NonNull DatabaseError error) {
               Toast.makeText(UsuarioCompleto.this, "NO se pudo colocar la imagen de la forma esperada", Toast.LENGTH_SHORT).show();
           }
       });

try {

    users.child(username).child("permisojudo").addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            if (snapshot.getValue(Boolean.class) != null) {
                if (snapshot.getValue(Boolean.class)) {
                    judoswitch.setChecked(true);
                } else {
                    judoswitch.setChecked(false);
                }
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {
            Toast.makeText(UsuarioCompleto.this, "No se cargaron correctamente los permisos del usuario", Toast.LENGTH_SHORT).show();
        }
    });
    users.child(username).child("permisojiujitsu").addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            if (snapshot.getValue(Boolean.class) != null) {
                if (snapshot.getValue(Boolean.class)) {
                    jiujitsuswitch.setChecked(true);
                } else {
                    jiujitsuswitch.setChecked(false);
                }
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {
            Toast.makeText(UsuarioCompleto.this, "No se cargaron correctamente los permisos del usuario", Toast.LENGTH_SHORT).show();
        }
    });
    users.child(username).child("permisoyoga").addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            if (snapshot.getValue(Boolean.class) != null) {
                if (snapshot.getValue(Boolean.class)) {
                    yogaswitch.setChecked(true);
                } else {
                    yogaswitch.setChecked(false);
                }
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {
            Toast.makeText(UsuarioCompleto.this, "No se cargaron correctamente los permisos del usuario", Toast.LENGTH_SHORT).show();
        }
    });
}catch (Exception e){
    Toast.makeText(UsuarioCompleto.this, "Los permisos de este usuario pueden haberse cargado de forma erronea", Toast.LENGTH_SHORT).show();
}

       settext();
//no hay que usar el nombre hay que usar el username
      judoswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
          @Override
          public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
              users.child(username).child("permisojudo").setValue(b);
          }
      });
      yogaswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
          @Override
          public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
              users.child(username).child("permisoyoga").setValue(b);
          }
      });
      jiujitsuswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
          @Override
          public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
              users.child(username).child("permisojiujitsu").setValue(b);
          }
      });

      estadisticasbut.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              toestadistica();
          }
      });

      deleteuser.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
           safedelete();
                          }
                      });
                  }

    public void initviews() {

        nombrest = (TextView) findViewById(R.id.valornombre);
        dnit = (TextView) findViewById(R.id.valordni);
        faixat = (TextView) findViewById(R.id.valorfaixa);
        miembrodesdet = (TextView) findViewById(R.id.valormiembrodesde);
        profesort = (TextView) findViewById(R.id.valorprofesor);
        fechadenacimientot = (TextView) findViewById(R.id.valorfecha);
        emailt = (TextView) findViewById(R.id.valoremail);
        estadisticasbut = (Button) findViewById(R.id.estadisticasbutton);
        yogaswitch = (Switch) findViewById(R.id.switchyoga);
        jiujitsuswitch = (Switch) findViewById(R.id.switchjiu);
        judoswitch = (Switch) findViewById(R.id.switchjudo);
        imagencargadadeusuario = (ImageView) findViewById(R.id.userphoto);
        deleteuser = (Button) findViewById(R.id.deleteuser);
    }

    public void settext() {
        nombrest.setText(nombres);
        dnit.setText(dni);
        faixat.setText(faixa);
        emailt.setText(emaild);
        miembrodesdet.setText(mimbrodesde);
        profesort.setText(profesor);
        fechadenacimientot.setText(fechadenacimiento);

       //hay que conseguir nuevamente el uri desde la base de datos
    }

    public void getusername() {
        //GETTING USERNAME FROM EMAIL
        //also have to check if the user has a point in front of the @
        int pos = emaild.indexOf("@");
        int pos2 = emaild.indexOf("."); //grabs the first character position so even if the user has multiple points hes/shes username will be the string thats before the first point of the hole string
        username = emaild.substring(0,pos);
        if (username.contains(".")){
            username = emaild.substring(0,pos2);
        }
      //  Toast.makeText(UsuarioCompleto.this, "obtenemos: " + username, Toast.LENGTH_SHORT).show();
    }

    public void toestadistica () { //change Estadistica to Copyrutine
        Intent intent = new Intent(UsuarioCompleto.this, CopyRutine.class);
        intent.putExtra("nombrecompleto", nombres);
        startActivity(intent);
    }

    public void safedelete() {
       borro = 0;

        /*usernames.addValueEventListener(new ValueEventListener() { //checks for the user existence avoiding posible errors
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot: snapshot.getChildren()){

                    if (dataSnapshot.getValue(String.class).equals(nombres)){
                        usernames.child(nombres).removeValue(); // no sense cause if this would work then we could use that one line
                        usernames.child("contadorusuarios").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                try {
                                    int val = snapshot.getValue(int.class);
                                    usernames.child("contadorusuarios").setValue(val - 1);
                                    usernames.child(nombres).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            borro = borro + 1;
                                        }
                                    });
                                } catch (Exception e){
                                    Toast.makeText(UsuarioCompleto.this, "No se pudo borrar el usuario", Toast.LENGTH_SHORT).show();
                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        });
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        */

        fisico.child(nombres).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
             borro = borro + 1;
            }
        });
        users.child(username).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                borro = borro + 1;
                Toast.makeText(UsuarioCompleto.this, "Se eliminó la información asociada al usuario", Toast.LENGTH_SHORT).show();
            }
        });

        if (borro == 2) {
            Toast.makeText(UsuarioCompleto.this, "El usuario se borró correctamente", Toast.LENGTH_SHORT).show();
        }
    }
}
