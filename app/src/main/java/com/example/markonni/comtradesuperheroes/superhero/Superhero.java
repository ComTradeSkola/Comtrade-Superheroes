package com.example.markonni.comtradesuperheroes.superhero;

import android.os.Parcel;
import android.os.Parcelable;

public class Superhero implements Parcelable {

    private String superheroName;
    private String description;
    private String image;
    private int superheroId;

    public String getSuperheroName() {
        return superheroName;
    }

    public void setSuperheroName(String superheroName) {
        this.superheroName = superheroName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getSuperheroId() {
        return superheroId;
    }

    public void setSuperheroId(int superheroId) {
        this.superheroId = superheroId;
    }

    @Override
    public String toString() {
        return "Superhero{" +
                "superheroName='" + superheroName + '\'' +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                ", id='" + superheroId + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.superheroName);
        dest.writeString(this.description);
        dest.writeString(this.image);
        dest.writeInt(this.superheroId);
    }

    public Superhero() {
    }

    protected Superhero(Parcel in) {
        this.superheroName = in.readString();
        this.description = in.readString();
        this.image = in.readString();
        this.superheroId = in.readInt();
    }

    public static final Parcelable.Creator<Superhero> CREATOR = new Parcelable.Creator<Superhero>() {
        @Override
        public Superhero createFromParcel(Parcel source) {
            return new Superhero(source);
        }

        @Override
        public Superhero[] newArray(int size) {
            return new Superhero[size];
        }
    };
}

