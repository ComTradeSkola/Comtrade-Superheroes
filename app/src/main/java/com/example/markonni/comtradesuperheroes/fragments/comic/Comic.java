package com.example.markonni.comtradesuperheroes.fragments.comic;

import android.os.Parcel;
import android.os.Parcelable;

public class Comic implements Parcelable {

    private String image;

    private String title;

    private String description;

    private String imageComicDetail;

    private int comicId;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getComicId() {
        return comicId;
    }

    public void setComicId(int comicId) {
        this.comicId = comicId;
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

    public String getImageComicDetail() {
        return imageComicDetail;
    }

    public void setImageComicDetail(String imageComicDetail) {
        this.imageComicDetail = imageComicDetail;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.image);
        dest.writeString(this.title);
        dest.writeString(this.description);
        dest.writeString(this.imageComicDetail);
        dest.writeInt(this.comicId);
    }

    public Comic() {
    }

    protected Comic(Parcel in) {
        this.image = in.readString();
        this.title = in.readString();
        this.description = in.readString();
        this.imageComicDetail = in.readString();
        this.comicId = in.readInt();
    }

    public static final Parcelable.Creator<Comic> CREATOR = new Parcelable.Creator<Comic>() {
        @Override
        public Comic createFromParcel(Parcel source) {
            return new Comic(source);
        }

        @Override
        public Comic[] newArray(int size) {
            return new Comic[size];
        }
    };
}
