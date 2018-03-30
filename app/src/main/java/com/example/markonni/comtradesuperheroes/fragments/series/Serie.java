package com.example.markonni.comtradesuperheroes.fragments.series;

import android.os.Parcel;
import android.os.Parcelable;

public class Serie implements Parcelable {

    private String image;

    private int serieId;

    private String title;

    private String description;

    private String SerieDetailImage;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getSerieId() {
        return serieId;
    }

    public void setSerieId(int serieId) {
        this.serieId = serieId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSerieDetailImage() {
        return SerieDetailImage;
    }

    public void setSerieDetailImage(String serieDetailImage) {
        SerieDetailImage = serieDetailImage;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.image);
        dest.writeInt(this.serieId);
        dest.writeString(this.title);
        dest.writeString(this.description);
        dest.writeString(this.SerieDetailImage);
    }

    public Serie() {
    }

    protected Serie(Parcel in) {
        this.image = in.readString();
        this.serieId = in.readInt();
        this.title = in.readString();
        this.description = in.readString();
        this.SerieDetailImage = in.readString();
    }

    public static final Parcelable.Creator<Serie> CREATOR = new Parcelable.Creator<Serie>() {
        @Override
        public Serie createFromParcel(Parcel source) {
            return new Serie(source);
        }

        @Override
        public Serie[] newArray(int size) {
            return new Serie[size];
        }
    };
}
