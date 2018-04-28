package es.us.garagesale.Src;


import android.graphics.Bitmap;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Map;

/**
 * Created by mariaventura on 7/3/18.
 */

/*cambiar String a startTime*/
public class Offer
{
    public enum Condition
    {
        NEW,
        NEARLY_NEW,
        USED,
        DEFECTIVE,

        INVALID
    }

    public enum Duration
    {
        SHORT,
        MEDIUM,
        LONG,

        INVALID
    }

    public static final Map<Duration, Integer> durationsDays = new EnumMap<>(Duration.class);

    private String name;
    private Condition condition;
    private String description;
    private float price;
    private ArrayList<String> tags;
    private Timestamp startTime;
    private int durationDays;
    private boolean sold; //pensarlo, porque si esta en la ArrayLista de purchases esta vendida
    private Seller seller;
    private ArrayList<Person> interested;
    private int id;
    private ArrayList<Bitmap> photos;
    //poner galeria de fotos

    static
    {
        initializeDurationMap();
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public Condition getCondition() {
        return condition;
    }
    public void setCondition(Condition condition) {
        this.condition = condition;
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

    public Timestamp getStartTime() {
        return startTime;
    }
    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public int getDurationDays() { return durationDays; }
    public void setDurationDays(int durationDays) { this.durationDays = durationDays; }

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


    public ArrayList<Bitmap> getPhotos() { return photos; }

    public void addPhoto(Bitmap addingPhoto)
    {
        photos.add(addingPhoto);
    }

    public void deletePhoto(Bitmap deletingPhoto)
    {
        photos.remove(deletingPhoto);
    }

    public void deletePhotoLast()
    {
        int lastPhotoIndex = photos.size() - 1;
        if(lastPhotoIndex < 0) return;

        photos.remove(lastPhotoIndex);
    }

    public boolean hasPhotos()
    {
        return (photos.size() > 0);
    }

    public int getId() {
        return id;
    }

    public Offer(String name, String description, float price, ArrayList<String> tags, Timestamp startTime, boolean sold, Seller seller, ArrayList<Person> interested) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.tags = tags;
        this.startTime = startTime;
        this.sold = sold;
        this.seller = seller;
        this.interested = interested;
        this.photos = new ArrayList<>();
        // ID is set by database
    }

    public Offer() {
        this.name = "";
        this.description = "";
        this.price = 0;
        this.tags = new ArrayList<String>();
        this.startTime = null;
        this.sold = false;
        this.seller = null;
        this.interested = new ArrayList<Person>();
        this.photos = new ArrayList<>();
        // ID is set by database
    }

    @Override
    public String toString() {
        return "Offer: " +
                "name=" + name + '\'' +
                ", description=" + description + '\'' +
                ", price=" + price +
                ", tags=" + tags +
                ", startTime='" + startTime + '\'' +
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

    private static void initializeDurationMap()
    {
        durationsDays.put(Duration.SHORT, 3);
        durationsDays.put(Duration.MEDIUM, 7);
        durationsDays.put(Duration.LONG, 14);
    }
}
