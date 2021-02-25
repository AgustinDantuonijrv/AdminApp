package com.example.adminapp;

import android.os.Parcel;
import android.os.Parcelable;

public class PojoVideos implements Parcelable {

    private String titulo;
    private String Uri;

    public PojoVideos(){
    }

    public PojoVideos(String titulo, String Uri){
        this.titulo = titulo;
        this.Uri = Uri;
    }

    protected PojoVideos(Parcel in) {
        titulo = in.readString();
        Uri = in.readString();
    }

    public static final Creator<PojoVideos> CREATOR = new Creator<PojoVideos>() {
        @Override
        public PojoVideos createFromParcel(Parcel in) {
            return new PojoVideos(in);
        }

        @Override
        public PojoVideos[] newArray(int size) {
            return new PojoVideos[size];
        }
    };

    public String getTitulo(){
        return titulo;
    }
    public String getUri(){
        return Uri;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(titulo);
        parcel.writeString(Uri);
    }
}
