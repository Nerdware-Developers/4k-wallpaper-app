package com.nerdware.wall;

public class categories {
    private final String name;
    private final int Image;

    public categories(String name, int image) {
        this.name = name;
        Image = image;
    }

    public String getName() {
        return name;
    }

    public int getImage() {
        return Image;
    }

    public boolean getImageUrl() {
        return false;
    }
}
