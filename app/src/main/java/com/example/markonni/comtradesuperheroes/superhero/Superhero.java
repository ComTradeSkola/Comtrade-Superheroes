package com.example.markonni.comtradesuperheroes.superhero;

public class Superhero {

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
}

