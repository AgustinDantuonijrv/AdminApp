package com.example.adminapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterVideos extends RecyclerView.Adapter<AdapterVideos.ViewHolder> implements View.OnClickListener {


    Context context;
    ArrayList<PojoVideos> datapojo;
    private OnItemListener onItemListener;

    public AdapterVideos(Context c, ArrayList<PojoVideos> p, Videostodelete onItemListener) {
        context = c;
        datapojo = p;
        this.onItemListener = onItemListener;
    }

    @NonNull
    @Override
    public AdapterVideos.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AdapterVideos.ViewHolder(LayoutInflater.from(context).inflate(R.layout.cardviewvideos, parent, false), (OnItemListener) onItemListener);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.Titulo.setText(datapojo.get(position).getTitulo());
    }

    @Override
    public int getItemCount() {
        return datapojo.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        //Declare textviees,edittext etc
        TextView Titulo;
        AdapterVideos.OnItemListener onItemListener;

        public ViewHolder(View itemView, AdapterVideos.OnItemListener onItemListener) {
            super(itemView);

//initialize the things that were mentioned before
            Titulo = (TextView) itemView.findViewById(R.id.resultadostitulo);
            this.onItemListener = onItemListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onItemListener.onItemClick(getAdapterPosition());
        }
    }

    @Override
    public void onClick(View view) {

    }

    public interface OnItemListener {
        void onItemClick(int position);
    }
}
