package com.example.adminapp;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class VerVideo extends AppCompatActivity implements View.OnClickListener {
    public VideoView videoView;
    public Uri uri;
    public Button delete;
    public String titulo;
    public StorageReference mStorageReference;
    public StorageReference file2delete;
    private FirebaseStorage storage;
    public TextView tw;
    public boolean encontre;
    public DatabaseReference videos;
    public String categoria;
    public int cont;
    public PojoVideos video;

    public MediaPlayer.OnCompletionListener completionListener, completionListener2;

    //if the video does not work using the uri get from the position we can try to have an index and get the data from the database using the index value
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_video);

        categoria = getIntent().getStringExtra("categoria");
        video = getIntent().getParcelableExtra("video");
        uri = Uri.parse(video.getUri());
        titulo = video.getTitulo();
        initviews();

        videos = FirebaseDatabase.getInstance().getReference("Videos");

        storage = FirebaseStorage.getInstance();
        mStorageReference = storage.getReferenceFromUrl(video.getUri());

        //StorageReference desertRef = storageRef.child("images/desert.jpg");

        delete.setOnClickListener(this);

        final MediaController mediaController2 = new MediaController(this);
        mediaController2.setAnchorView(videoView);

        videoView.setMediaController(mediaController2);
        videoView.setEnabled(true);
        videoView.setVideoURI(uri);

        PlayVideo();
    }

    //WE AVE TO HAVE IN CONSIDERATION THAT THE POSITION GETS THE URI AND THE TITTLE TO SET IN THE TEXT AND THEN WE HAVE TO SUPPORT ALL THAT IS BASICALLY NEEDED IN A MEDIA PLAYER (SEE ANDROID DEVELOPERS OFICIAL DOCUMENTATION)
    private void initviews() {
        videoView = findViewById(R.id.videoView);
        tw = findViewById(R.id.titulodelvideo);
        delete = findViewById(R.id.delete);
    }

    private void delete() {
            encontre = false;
        if (categoria != null) {
            videos.child(categoria).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        PojoVideos p = dataSnapshot.getValue(PojoVideos.class);
                        if (p.getUri().equals(video.getUri())) {
                            dataSnapshot.getRef().removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(VerVideo.this, "Se pudo eliminar toda referencia al video", Toast.LENGTH_SHORT).show();
                                    encontre = true;
                                }
                            });
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
            if (encontre) {
                mStorageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(VerVideo.this, "El Video Se Eliminó correctamente", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Toast.makeText(VerVideo.this, "El Video No Pudo Ser Eliminado", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        } else {
            Toast.makeText(VerVideo.this, "NO ENTRE A LA PARTE SEGUNDA PORQUE ENCONTRE VALE FALSE", Toast.LENGTH_SHORT).show();
        }
    }
    private void PlayVideo() {

        videoView.setOnCompletionListener(completionListener);

        try {

            MediaController mediaController2 = new MediaController( VerVideo.this);
            mediaController2.setAnchorView(videoView);

            videoView.setMediaController(mediaController2);
            videoView.setVideoURI(uri);

            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

                public void onPrepared(MediaPlayer mediaPlayer) {
                    videoView.start();
                    tw.setText(titulo);
                }
            });

        } catch (Exception e) {

            System.out.println("Video Play Error :" + e.toString());
            finish();

        }
    }
    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.delete:
                delete();
                break;
        }
    }
}

