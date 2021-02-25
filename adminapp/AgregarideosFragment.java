package com.example.adminapp;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;


public class AgregarideosFragment extends Fragment {

    //Declarations

    static int  PreqCode = 1;
    static int REQUESCODE = 1;

    private FirebaseStorage storage;
    public boolean videouploaded;
    public boolean result;
    public String stringuri;
    public DatabaseReference videos;
    private ProgressDialog progressDialog;
    public String tipodevideostring;
    public Uri filepath;
    public Integer cont;
    public TextView uri;
    public EditText titulo;
    public ImageView imagesure;
    public DatabaseReference users;
    public StorageReference mStorageReference;
    public String titulos = "No se selecciono un titulo";
    public Button subirvideo;
    public TextView textosure;
    public Spinner tipodevideo;
    public TextView Urltextomuestra;
    public TextView location;
    public Button confirmar;
    public TextView ubicacion;


    Uri pickedVideoUri ;

    public AgregarideosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       videos = FirebaseDatabase.getInstance().getReference("Videos");

       storage = FirebaseStorage.getInstance();
       mStorageReference = storage.getReference();


       View rootview = inflater.inflate(R.layout.fragment_agregarideos, container, false);

       progressDialog = new ProgressDialog(getContext());

        titulo = (EditText) rootview.findViewById(R.id.titulovideo);
        location = (TextView) rootview.findViewById(R.id.rutauri);
        Urltextomuestra = (TextView) rootview.findViewById(R.id.Urimostrar);
        subirvideo = (Button) rootview.findViewById(R.id.agregarvideo);
        tipodevideo = (Spinner) rootview.findViewById(R.id.tipodevideo);
        imagesure = (ImageView) rootview.findViewById(R.id.imagevideo);
        imagesure.setVisibility(View.GONE);
        textosure = (TextView) rootview.findViewById(R.id.textoseguro);
        textosure.setVisibility(View.GONE);
        confirmar = (Button) rootview.findViewById(R.id.confirmarvideo);
        confirmar.setVisibility(View.GONE);

        tipodevideostring = tipodevideo.getSelectedItem().toString();
        //get the cont and modified when video is added

        videos.child("cont").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                 cont = snapshot.getValue(Integer.class);
                 //es el valor de la cantidad de videos ya subidos //hay que aumentarla a uno y usarla para darle el nombere al video
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
try {
    subirvideo.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            // clearall(location);
             clearall(Urltextomuestra);
             reversevisibilities();
            }
    });
    confirmar.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            uploadvideo();
        }
    });

} catch (Exception subirvideoexception){
    Toast.makeText(getActivity().getApplicationContext(), "error al subir video", Toast.LENGTH_SHORT).show();
}
        final Button seleccionarboton = (Button) rootview.findViewById(R.id.seleccionarvideo);
        seleccionarboton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (titulo.getText().toString().equals("")) {
                    Toast.makeText(getActivity().getApplicationContext(), "Tenes que darle un título", Toast.LENGTH_SHORT).show();
                } else {
                    checkAndRequestForPermission(view);
                }
            }
        });

       return rootview;
    }
    //TODAVIA FALTA PARA QUE TOME EL VALOR DE LA ACTIVIDAD
    private void addvideo() {

        videos.child(tipodevideostring).child("video"+cont).child("Uri").setValue(stringuri).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    videos.child("cont").setValue(cont);
                    videos.child(tipodevideostring).child("video"+cont).child("titulo").setValue(titulos);
                    Toast.makeText(getActivity().getApplicationContext(), "El video se cargo correctamente", Toast.LENGTH_SHORT).show();
                } else {
                    String messeage = task.getException().toString();
                    Toast.makeText(getActivity().getApplicationContext(), "Error:" + messeage, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    private void checkAndRequestForPermission(View view){

        //TODO: open gallery and wait for user to pick a Video !
        if (ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)) {
                Toast.makeText(getActivity().getApplicationContext(), "Por Favor Acepte el requerimiento para poder agregar una foto", Toast.LENGTH_SHORT).show();
            } else {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        PreqCode);
            }
        } else {
            openGallery();
        }
    }
    private void openGallery(){

        //TODO: open gallery intent and wait for user to pick a Video !

        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("video/*");
        startActivityForResult(galleryIntent,REQUESCODE);
    }
    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode, data);

        if (resultCode == RESULT_OK && requestCode == REQUESCODE && data != null){
            //se eligio exitosamente una imagen
            //guardamos referencia en uri

            pickedVideoUri = data.getData();
            stringuri = pickedVideoUri.toString(); // i need the real deal

            filepath = pickedVideoUri;

            Uri bitmap = null;

                bitmap = MediaStore
                                .Video
                                .Media
                                .getContentUri(
                                        String.valueOf(pickedVideoUri));
                //we have to change the visibility
            titulos = titulo.getText().toString();
            Changevisivilities();
        }
    }

    public void Changevisivilities(){

        confirmar.setVisibility(View.VISIBLE);
        textosure.setVisibility(View.VISIBLE);
        imagesure.setVisibility(View.VISIBLE);
        titulo.setVisibility(View.GONE);
        tipodevideo.setVisibility(View.GONE);
    }
    public void reversevisibilities(){
        confirmar.setVisibility(View.GONE);
        textosure.setVisibility(View.GONE);
        imagesure.setVisibility(View.GONE);
        titulo.setVisibility(View.VISIBLE);
        tipodevideo.setVisibility(View.VISIBLE);
    }

    public void clearall(TextView t){
        titulo.setText("Titulo");
        t.setText("Url");
    }

    public void uploadvideo() {

        Toast.makeText(getActivity().getApplicationContext(), "Cargando video", Toast.LENGTH_SHORT).show();

        final String randomkey = UUID.randomUUID().toString();
        final StorageReference riversRef = mStorageReference.child("videos/" + randomkey);
        videouploaded = false;
        // the selected item is the class or folder that was selecte dby the admin in the spinner of the app UI

            riversRef.putFile(pickedVideoUri)
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                            progressDialog.setMessage("Subiendo el video:" + (int) progress + "" + "%");
                            progressDialog.show();
                            if (progress == 100.0) {
                                progressDialog.dismiss();
                            }
                        }
                    })
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            riversRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    stringuri = uri.toString(); // so that i can use the video uri
                                    if (tipodevideostring != null) {
                                        videouploaded = true;
                                            //conseguimos el texto (Modularizado)
                                            tipodevideostring = tipodevideo.getSelectedItem().toString();
                                            cont = cont + 1;
                                            location.setText("Videos/" + "/" + titulos);
                                            Urltextomuestra.setText(stringuri);
                                            addvideo();
                                        } else {
                                            Toast.makeText(getActivity().getApplicationContext(), "El video que quiere subir no se pudo cargar correctamente", Toast.LENGTH_SHORT).show();
                                        }
                                }
                            });
                        }
                    });
    }
}
