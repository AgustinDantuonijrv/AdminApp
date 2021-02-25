package com.example.adminapp;

import android.os.Parcel;
import android.os.Parcelable;

public class pojoselectrutine implements Parcelable {

    private String dia;
    private String ejercicio;
    private String index;
    private String pesomaximo;
    private String repeticiones;
    private String repeticiones1;
    private String repeticiones2;
    private String repeticiones3;
    private String repeticiones4;
    private String repeticiones5;
    private String series;

    public pojoselectrutine(){ }

    public pojoselectrutine(String dia,String ejercicio, String index, String pesomaximo, String repeticiones,String repeticiones1,String repeticiones2,String repeticiones3,String repeticiones4,String repeticiones5, String series){

        this.dia = dia;
        this.ejercicio = ejercicio;
        this.index = index;
        this.pesomaximo = pesomaximo;
        this.repeticiones = repeticiones;
        this.repeticiones1 = repeticiones1;
        this.repeticiones2 = repeticiones2;
        this.repeticiones3 = repeticiones3;
        this.repeticiones4 = repeticiones4;
        this.repeticiones5 = repeticiones5;
        this.series = series;

    }

    protected pojoselectrutine(Parcel in) {
        dia = in.readString();
        ejercicio = in.readString();
        index = in.readString();
        pesomaximo = in.readString();
        repeticiones = in.readString();
        repeticiones1 = in.readString();
        repeticiones2 = in.readString();
        repeticiones3 = in.readString();
        repeticiones4 = in.readString();
        repeticiones5 = in.readString();
        series = in.readString();
    }

    public static final Creator<pojoselectrutine> CREATOR = new Creator<pojoselectrutine>() {
        @Override
        public pojoselectrutine createFromParcel(Parcel in) {
            return new pojoselectrutine(in);
        }

        @Override
        public pojoselectrutine[] newArray(int size) {
            return new pojoselectrutine[size];
        }
    };

    public String getDia(){
        return dia;
    }
    public String getEjercicio() {
        return ejercicio;
    }

    public String getIndex() {
        return index;
    }

    public String getPesomaximo() {
        return pesomaximo;
    }

    public String getRepeticiones() {
        return repeticiones;
    }

    public String getRepeticiones1(){
        return repeticiones1;
    }

    public String getRepeticiones2(){
        return repeticiones2;
    }

    public String getRepeticiones3(){
        return repeticiones3;
    }

    public String getRepeticiones4(){
        return repeticiones4;
    }

    public String getRepeticiones5(){
        return repeticiones5;
    }

    public String getSeries(){
        return series;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(dia);
        parcel.writeString(ejercicio);
        parcel.writeString(index);
        parcel.writeString(pesomaximo);
        parcel.writeString(repeticiones);
        parcel.writeString(repeticiones1);
        parcel.writeString(repeticiones2);
        parcel.writeString(repeticiones3);
        parcel.writeString(repeticiones4);
        parcel.writeString(repeticiones5);
        parcel.writeString(series);
    }
}
