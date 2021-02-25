package com.example.adminapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Objects;

import dagger.Binds;

public class AdapterCopyRutine extends RecyclerView.Adapter<AdapterCopyRutine.ViewHolder> implements View.OnClickListener {
    Context context;
    ArrayList<pojocopyrutine> datapojocopyrutine;
    private CopyRutine onItemListener;

    public static int cont1 = 0;
    public static int indspecial;

    public AdapterCopyRutine(Context c, ArrayList<pojocopyrutine> p, CopyRutine onItemListener, CopyRutine onRoundListener, CopyRutine onEditListener) {
        context = c;
        datapojocopyrutine = p;
        this.onItemListener = onItemListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.activity_copyrutine, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final AdapterCopyRutine.ViewHolder holder, final int position) {

        holder.Comentarios.setText(datapojocopyrutine.get(position).getComentarios());
        holder.ejercicio.setText(datapojocopyrutine.get(position).getEjercicio());
        holder.pesomaximo.setText(datapojocopyrutine.get(position).getPesomaximo());
        holder.repeticiones.setText(datapojocopyrutine.get(position).getRepeticiones());
        holder.series.setText(datapojocopyrutine.get(position).getSeries());

    }


    @Override
    public int getItemCount() {
        if (datapojocopyrutine.size() == 0) {
            return 1;
        }
        return datapojocopyrutine.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (datapojocopyrutine.size() == 0) {
            return 0;
        }
        return 1;
    }

    @Override
    public void onClick(View view) {

    }

//this is an intent to see if we can get the buttons inside the card view clickeable

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView ejercicio, pesomaximo, repeticiones, series; //repeticiones y series tienen que ser solo uno
        TextView Comentarios;

        OnItemListener onItemListener;


        public ViewHolder(View itemview) {
            super(itemview);

            Comentarios = (TextView) itemview.findViewById(R.id.valorcomentarios);
            ejercicio = (TextView) itemview.findViewById(R.id.nombres);
            pesomaximo = (TextView) itemview.findViewById(R.id.pesomaximo); //tienen que ser dos textviews
            repeticiones = (TextView) itemview.findViewById(R.id.repeticiones);
            series = (TextView) itemview.findViewById(R.id.series);

            itemview.setOnClickListener(this);


            // this still needs work but theres is a great tutorial open in chrome
            //textos y botones
        }

        public interface OnItemListener {
            void onItemClick(int position);
        }

        @Override
        public void onClick(View view) {
        }

    }
}
