package com.example.markonni.comtradesuperheroes.superhero;

import android.os.Parcel;
import android.os.Parcelable;

public class Superhero implements Parcelable {

    private String superheroName;
    private String description;
    private String image;
    private String imageSuperheroDetails;
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

    public String getImageSuperheroDetails() {
        return imageSuperheroDetails;
    }

    public void setImageSuperheroDetails(String imageSuperheroDetails) {
        this.imageSuperheroDetails = imageSuperheroDetails;
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

    public Superhero() {
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
        dest.writeString(this.imageSuperheroDetails);
        dest.writeInt(this.superheroId);
    }

    protected Superhero(Parcel in) {
        this.superheroName = in.readString();
        this.description = in.readString();
        this.image = in.readString();
        this.imageSuperheroDetails = in.readString();
        this.superheroId = in.readInt();
    }

    public static final Creator<Superhero> CREATOR = new Creator<Superhero>() {
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

