package com.example.adminapp;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.storage.StorageReference;

public class Profile implements Parcelable {


    private String Dni;
    private String ImagenUri;
    private String Miembrodesde;
    private String Nacimiento;
    private String Nombre;
    private String Wapp;
    private String email;
    private String faixa;
    private String profesor;


public Profile(){

}

public Profile(String Dni, String ImagenUri, String Miembrodesde, String Nacimiento, String Wapp, String Nombre, String email, String faixa, String profesor)
{
    this.Dni = Dni;
    this.ImagenUri = ImagenUri;
    this.Miembrodesde = Miembrodesde;
    this.Nacimiento = Nacimiento;
    this.Nombre = Nombre;
    this.Wapp = Wapp;
    this.email = email;
    this.faixa = faixa;
    this.profesor = profesor;

}

    protected Profile(Parcel in) {
        Dni = in.readString();
        ImagenUri = in.readString();
        Miembrodesde = in.readString();
        Nacimiento = in.readString();
        Nombre = in.readString();
        Wapp = in.readString();
        email = in.readString();
        faixa = in.readString();
        profesor = in.readString();
    }

    public static final Creator<Profile> CREATOR = new Creator<Profile>() {
        @Override
        public Profile createFromParcel(Parcel in) {
            return new Profile(in);
        }

        @Override
        public Profile[] newArray(int size) {
            return new Profile[size];
        }
    };

    public String getDni () {
        return Dni;
    }
    public String getImagenUri() {
        return ImagenUri;
    }
    public String getMiembrodesde () {
        return Miembrodesde;
    }
    public String getNacimiento () {
        return Nacimiento;
    }
    public String getNombre() {
    return Nombre;
    }
    public String getWapp() {
        return Wapp;
    }
    public String getEmail () {
        return email;
    }

    public String getFaixa () {
        return faixa;
    }

    public String getProfesor () {
        return profesor;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(Dni);
        parcel.writeString(ImagenUri);
        parcel.writeString(Miembrodesde);
        parcel.writeString(Nacimiento);
        parcel.writeString(Nombre);
        parcel.writeString(Wapp);
        parcel.writeString(email);
        parcel.writeString(faixa);
        parcel.writeString(profesor);
    }
}


