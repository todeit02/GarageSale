package com.example.mariaventura.pruebaframe.Src;


import java.sql.Timestamp;
import java.util.ArrayList;

/**
 * Created by mariaventura on 7/3/18.
 */

/*cambiar String a timestamp*/
public class Offer {

    private String name;
    private String description;
    private float price;
    private ArrayList<String> tags;
    private Timestamp timestamp;
    private boolean sold; //pensarlo, porque si esta en la ArrayLista de purchases esta vendida
    private Seller seller;
    private ArrayList<Person> interested;
    private int id;
    private ArrayList<String> photoPaths;
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

    public float getPrice() {
        return price;
    }
    public void setPrice(float price) {
        this.price = price;
    }

    public ArrayList<String> getTags() {
        return tags;
    }
    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isSold() {
        return sold;
    }
    public void setSold(boolean sold) {
        this.sold = sold;
    }

    public Seller getSeller() { return seller; }
    public void setSeller(Seller seller) { this.seller = seller; }

    public ArrayList<Person> getInterested() {
        return interested;
    }
    public void setInterested(ArrayList<Person> interested) {
        this.interested = interested;
    }

    public int getId() {
        return id;
    }

    public Offer(String name, String description, float price, ArrayList<String> tags, Timestamp timestamp, boolean sold, Seller seller, ArrayList<Person> interested) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.tags = tags;
        this.timestamp = timestamp;
        this.sold = sold;
        this.seller = seller;
        this.interested = interested;
        // ID is set by database
    }

    public Offer() {
        this.name = "";
        this.description = "";
        this.price = 0;
        this.tags = new ArrayList<String>();
        this.timestamp = null;
        this.sold = false;
        this.seller = null;
        this.interested = new ArrayList<Person>();
        // ID is set by database
    }

    @Override
    public String toString() {
        return "Offer: " +
                "name=" + name + '\'' +
                ", description=" + description + '\'' +
                ", price=" + price +
                ", tags=" + tags +
                ", timestamp='" + timestamp + '\'' +
                ", sold=" + sold +
                ", seller=" + seller +
                ", interested=" + interested;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Offer offer = (Offer) o;

        return id == offer.id;
    }

}
