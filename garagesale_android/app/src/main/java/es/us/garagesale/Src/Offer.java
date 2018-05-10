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
    private OfferCondition condition;
    private String description;
    private float price;
    private ArrayList<String> tags;
    private String startTime;
    private int sold; //pensarlo, porque si esta en la ArrayLista de purchases esta vendida
    private int id;
    private int durationDays;
    private int activePeriod;
    private ArrayList<Bitmap> photos;
    private Place location;

    static
    {
        initializeDurationMap();
    }

    public static Map<Duration, Integer> getDurationsDays() {
        return durationsDays;
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

    public OfferCondition getCondition() {
        return condition;
    }
    public void setCondition(OfferCondition condition) {
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

    public int getDurationDays() {
        return durationDays;
    }

    public void setDurationDays(int durationDays) {
        this.durationDays = durationDays;
    }

    public ArrayList<String> getTags() {
        return tags;
    }
    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
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

    public int getSold() {
        return sold;
    }
    public void setSold(int sold) {
        this.sold = sold;
    }


    public ArrayList<Bitmap> getPhotos() { return photos; }

    public void setPhotos(ArrayList<Bitmap> photos) {
        this.photos = photos;
    }

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

    public Offer(String name, String seller_username, OfferCondition condition, String description, float price, ArrayList<String> tags, String startTime, int sold, int id, int durationDays, int activePeriod, ArrayList<Bitmap> photos, Place location)
    {
        this.name = name;
        this.seller_username = seller_username;
        this.condition = condition;
        this.description = description;
        this.price = price;
        this.tags = tags;
        this.startTime = startTime;
        this.sold = sold;
        this.id = id;
        this.durationDays = durationDays;
        this.activePeriod = activePeriod;
        this.photos = photos;
        this.location = location;
    }

    public Offer() {
    }

    @Override
    public String toString() {
        return "Offer{" +
                "name='" + name + '\'' +
                ", seller_username='" + seller_username + '\'' +
                ", condition=" + condition.getNumericValue() +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", tags=" + tags +
                ", startTime='" + startTime + '\'' +
                ", sold=" + sold +
                ", id=" + id +
                ", activePeriod=" + activePeriod +
                ", photos=" + photos +
                ", location=" + location +
                '}';
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