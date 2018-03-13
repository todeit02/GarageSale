package com.example.mariaventura.pruebaframe.Src;

import com.orm.SugarRecord;

import java.util.ArrayList;

/**
 * Created by mariaventura on 7/3/18.
 */

/*cambiar String a date*/
public class Post extends SugarRecord<Post> {

    private String name;
    private String description;
    private int price;
    private ArrayList<String> filters;
    private String date;
    private boolean sold; //pensarlo, porque si esta en la lista de purchases esta vendida
    private Seller seller;
    private ArrayList<Person> interested;
    private int code;
    //poner galeria de fotos

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public ArrayList<String> getFilters() {
        return filters;
    }

    public void setFilters(ArrayList<String> filters) {
        this.filters = filters;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isSold() {
        return sold;
    }

    public void setSold(boolean sold) {
        this.sold = sold;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    public ArrayList<Person> getInterested() {
        return interested;
    }

    public void setInterested(ArrayList<Person> interested) {
        this.interested = interested;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Post(String name, String description, int price, ArrayList<String> filters, String date, boolean sold, Seller seller, ArrayList<Person> interested, int code) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.filters = filters;
        this.date = date;
        this.sold = sold;
        this.seller = seller;
        this.interested = interested;
        this.code= code;
    }

    public Post() {
        this.name = "";
        this.description = "";
        this.price = 0;
        this.filters = new ArrayList<String>();
        this.date = "";
        this.sold = false;
        this.seller = null;
        this.interested = new ArrayList<Person>();
        this.code= 0;
    }

    @Override
    public String toString() {
        return "Post: " +
                "name=" + name + '\'' +
                ", description=" + description + '\'' +
                ", price=" + price +
                ", filters=" + filters +
                ", date='" + date + '\'' +
                ", sold=" + sold +
                ", seller=" + seller +
                ", interested=" + interested;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Post post = (Post) o;

        return code == post.code;
    }

}
