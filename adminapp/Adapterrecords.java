package com.example.adminapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapterrecords extends RecyclerView.Adapter<Adapterrecords.ViewHolder> implements View.OnClickListener {

    //public class AdapterRecyclerRecords extends RecyclerView.Adapter<AdapterRecyclerRecords.ViewHolder> implements View.OnClickListener {};

    Context context;
    ArrayList<pojorecords> datapojorecords;
    private OnItemListener onItemListener;


    public Adapterrecords(Context c, ArrayList<pojorecords> p, Records onItemListener)
    {
        context = c;
        datapojorecords = p;
        this.onItemListener = onItemListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.cardviewrecords,parent,false),onItemListener);
        //CardView need to be changed for
    }

    @Override
    public void onBindViewHolder(@NonNull Adapterrecords.ViewHolder holder, int position) {
        holder.ejercicio.setText(datapojorecords.get(position).getEjercicio());
        holder.repeticiones.setText(datapojorecords.get(position).getRepeticiones());
        holder.pesomaximo.setText(datapojorecords.get(position).getPesomaximo());
        holder.objetivo.setText(datapojorecords.get(position).getObjetivo());
    }

    @Override
    public int getItemCount() {
        return datapojorecords.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener
    {
        //Declare textviees,edittext etc
        TextView ejercicio,repeticiones,pesomaximo,objetivo;
        Adapterrecords.OnItemListener onItemListener;

        public ViewHolder(View itemView, OnItemListener onItemListener) {
            super(itemView);

//initialize the things that were mentioned before

            ejercicio = (TextView) itemView.findViewById(R.id.valorejrecords);
            pesomaximo = (TextView) itemView.findViewById(R.id.valorpesomaximo);
            repeticiones = (TextView) itemView.findViewById(R.id.valorrepeticionesrecords);
            objetivo = (TextView) itemView.findViewById(R.id.valoobjetivo);
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

    public interface OnItemListener{
        void onItemClick(int position);
    }

    }

