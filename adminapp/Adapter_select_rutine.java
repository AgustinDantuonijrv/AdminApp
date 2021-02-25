package com.example.adminapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class Adapter_select_rutine extends RecyclerView.Adapter<Adapter_select_rutine.ViewHolder> implements View.OnClickListener {

    Context context;
    ArrayList<pojoselectrutine> datapojo;
    private OnRutineListener monRutineListener;

    public Adapter_select_rutine(Context c, ArrayList<pojoselectrutine> p, OnRutineListener onRutineListener)
    {
        this.context =c;
        this.datapojo = p;
        this.monRutineListener = onRutineListener;
    }

    @NonNull
    @Override
    public Adapter_select_rutine.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Adapter_select_rutine.ViewHolder(LayoutInflater.from(context).inflate(R.layout.selectcardview, parent, false),monRutineListener);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.dia2.setText(datapojo.get(position).getDia());
        holder.ejercicios2.setText(datapojo.get(position).getEjercicio());
        holder.pesomaximo2.setText(datapojo.get(position).getPesomaximo());
        holder.repeticiones2.setText(datapojo.get(position).getRepeticiones());
        holder.series2.setText(datapojo.get(position).getSeries());

    }

    @Override
   public int getItemCount(){
        return datapojo.size();
    }

    @Override
    public void onClick(View view) {

    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView dia2,ejercicios2,series2,repeticiones2,pesomaximo2;
        OnRutineListener onRutineListener;

        public ViewHolder(@NonNull View itemView, OnRutineListener onRutineListener) {
            super(itemView);

            dia2 = (TextView) itemView.findViewById(R.id.dia2);
            ejercicios2 = (TextView) itemView.findViewById(R.id.ejercicios2);
            pesomaximo2 = (TextView) itemView.findViewById(R.id.pesomaximo2);
            repeticiones2 = (TextView) itemView.findViewById(R.id.repeticiones2);
            series2 = (TextView) itemView.findViewById(R.id.series2);

            this.onRutineListener = onRutineListener;
            itemView.setOnClickListener(this);

        }
        @Override
        public void onClick(View view) {
            onRutineListener.onRutineClick(getAdapterPosition());
        }
    }

    public interface OnRutineListener{
        void onRutineClick(int position);
    }
}
