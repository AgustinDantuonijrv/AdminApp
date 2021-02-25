package com.example.adminapp;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static androidx.constraintlayout.widget.Constraints.TAG;


public class Adapterrecycler extends RecyclerView.Adapter<Adapterrecycler.ViewHolder> implements View.OnClickListener {
    Context context;
    ArrayList<Profile> profiles;
    private OnUserListener mOnUserListener;
    public DatabaseReference databaseReference;
   public StorageReference storageReference;

    public Adapterrecycler(Context c, ArrayList<Profile> p, OnUserListener onUserListener)
    {  //before the generation or declaration was different
        this.context = c;
        this.profiles = p;
        this.mOnUserListener = onUserListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.cardview, parent, false),mOnUserListener);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

            holder.Dni.setText(profiles.get(position).getDni());
            if (profiles.get(position).getImagenUri() != null) {
                Glide.with(context)
                        .load(profiles.get(position).getImagenUri())
                       // .load(profiles.get(position).getImageuser())
                        .centerCrop()
                        .into(holder.ImagenUri);
            } else {
                Glide.with(context)
                        .load("https://firebasestorage.googleapis.com/v0/b/dojoapp-a9d00.appspot.com/o/images%2Fimagen.png?alt=media&token=40c582e6-0d95-4074-ba58-4b05daecd174")
                        .centerCrop()
                        .into(holder.ImagenUri);
            }
            holder.MiembroDesde.setText(profiles.get(position).getMiembrodesde());
            holder.Nacimiento.setText(profiles.get(position).getNacimiento());
            holder.Nombre.setText(profiles.get(position).getNombre());
            holder.Wapp.setText(profiles.get(position).getWapp());
            holder.email.setText(profiles.get(position).getEmail());
            holder.faixa.setText(profiles.get(position).getFaixa());
            holder.profesor.setText(profiles.get(position).getProfesor());
    }

    @Override
    public int getItemCount() {
        return profiles.size();
    }

   public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView Nombre, Wapp, email, Dni, faixa, MiembroDesde, Nacimiento, profesor;
        ImageView ImagenUri;
        OnUserListener onUserListener;

      public ViewHolder(View itemView, OnUserListener onUserListener){
          super(itemView);

          Nombre = (TextView) itemView.findViewById(R.id.textonombre);
          Wapp = (TextView) itemView.findViewById(R.id.wappnumbershow);
          email = (TextView) itemView.findViewById(R.id.textoemail);
          ImagenUri = (ImageView) itemView.findViewById(R.id.userimage);
          Dni = (TextView) itemView.findViewById(R.id.textodni);
          faixa = (TextView) itemView.findViewById(R.id.faixa);
          MiembroDesde = (TextView) itemView.findViewById(R.id.afiliado);
          Nacimiento = (TextView) itemView.findViewById(R.id.textonacimiento);
          profesor = (TextView) itemView.findViewById(R.id.profesor);

          this.onUserListener = onUserListener;
          itemView.setOnClickListener(this);
      }

        @Override
        public void onClick(View view) {
          onUserListener.onUserClick(getAdapterPosition());
        }
    }

    @Override
    public void onClick(View view) {

    }

    public interface OnUserListener{
        void onUserClick(int position);
    }
}
