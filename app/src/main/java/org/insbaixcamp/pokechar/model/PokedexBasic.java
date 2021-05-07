package org.insbaixcamp.pokechar.model;

public class PokedexBasic {

    private int id;
    private String name;
    private String urlData;
    private String urlImage;

    public PokedexBasic(int id, String name, String urlData, String urlImage) {
        this.id = id;
        this.name = name;
        this.urlData = urlData;
        this.urlImage = urlImage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrlData() {
        return urlData;
    }

    public void setUrlData(String urlData) {
        this.urlData = urlData;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }
}
