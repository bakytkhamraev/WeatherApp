package com.example.weatherapp.data;

public class Book {

    private String Title;
    private String Price;
    private int Image;

    public Book() {
    }

    public Book(String title, String price, int image) {
        Title = title;
        Price = price;
        Image = image;
    }


    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public int getImage() {
        return Image;
    }

    public void setImage(int image) {
        Image = image;
    }
}
