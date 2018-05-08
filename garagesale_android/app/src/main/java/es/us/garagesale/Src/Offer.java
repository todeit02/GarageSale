package es.us.garagesale.Src;


import android.graphics.Bitmap;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.maps.model.LatLng;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
    private String seller_username;
    private Condition condition;
    private String description;
    private float price;
    private ArrayList<String> tags;
    private String startTime;
    private int durationDays;
    private int sold; //pensarlo, porque si esta en la ArrayLista de purchases esta vendida
    private Seller seller;
    private ArrayList<Person> interested;
    private int id;
    private String state;
    private int activePeriod;
    private ArrayList<Bitmap> photos;
    private Place location;

    static
    {
        initializeDurationMap();
    }

    public String getSellerUsername() {
        return seller_username;
    }

    public void setSellerUsername(String sellerUsername) {
        this.seller_username = sellerUsername;
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

    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }

    public int getActivePeriod() {
        return activePeriod;
    }
    public void setActivePeriod(int activePeriod) {
        this.activePeriod = activePeriod;
    }

    public String getStartTime() {
        return startTime;
    }
    public void setStartTime(String startTime) { this.startTime = startTime; }

    public int getDurationDays() { return durationDays; }
    public void setDurationDays(int durationDays) { this.durationDays = durationDays; }

    public int isSold() {
        return sold;
    }
    public void setSold(int sold) {
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

    public Place getLocation() { return location; }
    public void setLocation(Place location) { this.location = location; }

    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }

    public Offer(String name, String description, float price, ArrayList<String> tags, String startTime, int sold, Seller seller, ArrayList<Person> interested) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.tags = tags;this.startTime = startTime;
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
        this.sold = 0;
        this.seller = null;
        this.interested = new ArrayList<Person>();
        this.photos = new ArrayList<>();
        this.location = null;
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
                ", seller_username=" + seller_username +
                ", interested=" + interested;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Offer offer = (Offer) o;

        return id == offer.id;
    }


    //hacer funcion para filtrar las ofertas validas.


    public int calculateRemainingTime()
    {
        Offer received = this;
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date offerDate = null;
        try {
            offerDate = format.parse(received.getStartTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(offerDate);
        cal.add(Calendar.DATE, received.getActivePeriod());
        Date updatedDate = cal.getTime();
        Date today = new Date();
        long secs = (updatedDate.getTime() - today.getTime()) / 1000;
        int hours = (int)secs / 3600;

        return hours < 0 ? 0 : hours;
    }

    public boolean isValid()
    {
        int hours = calculateRemainingTime();
        if(hours<=0) return false;
        return true;
    }

    private static void initializeDurationMap()
    {
        durationsDays.put(Duration.SHORT, 3);
        durationsDays.put(Duration.MEDIUM, 7);
        durationsDays.put(Duration.LONG, 14);
    }
}