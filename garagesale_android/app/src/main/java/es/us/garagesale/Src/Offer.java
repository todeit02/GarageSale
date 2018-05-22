package es.us.garagesale.Src;


import android.graphics.Bitmap;

import com.google.android.gms.maps.model.LatLng;

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
    private int isSold;
    private int id;
    private int durationDays;
    private ArrayList<Bitmap> photos = new ArrayList<>();
    private LatLng coordinates;
    private String cityName;


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

    public ArrayList<String> getTags() {
        return tags;
    }
    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    public String getStartTime() {
        return startTime;
    }
    public void setStartTime(String startTime) { this.startTime = startTime; }

    public int getDurationDays() {
        return durationDays;
    }
    public void setDurationDays(int durationDays) {
        this.durationDays = durationDays;
    }

    public int getSold() {
        return isSold;
    }
    public void setSold(int isSold) {
        this.isSold = isSold;
    }


    public ArrayList<Bitmap> getPhotos() { return photos; }
    public Bitmap getNeighbourPhoto(Bitmap photo, int offset)
    {
        if(!this.hasPhotos()) return null;

        int currentPhotoIndex = this.getPhotos().indexOf(photo);
        int neighbourPhotoIndex = (currentPhotoIndex + offset);
        if((neighbourPhotoIndex >= 0) && (neighbourPhotoIndex < photos.size()))
        {
            return photos.get(neighbourPhotoIndex);
        }
        return null;
    }
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

    public LatLng getCoordinates() { return coordinates; }
    public void setCoordinates(LatLng coordinates) { this.coordinates = coordinates; }

    public String getCityName() { return cityName; }
    public void setCityName(String cityName) { this.cityName = cityName; }

    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }

    public Offer(String name,
                 String seller_username,
                 OfferCondition condition,
                 String description,
                 float price,
                 ArrayList<String> tags,
                 String startTime,
                 int isSold,
                 int id,
                 int durationDays,
                 ArrayList<Bitmap> photos,
                 LatLng coordinates,
                 String cityName)
    {
        this.name = name;
        this.seller_username = seller_username;
        this.condition = condition;
        this.description = description;
        this.price = price;
        this.tags = tags;
        this.startTime = startTime;
        this.isSold = isSold;
        this.id = id;
        this.durationDays = durationDays;
        this.photos = photos;
        this.coordinates = coordinates;
        this.cityName = cityName;
    }

    public Offer() {
    }

    @Override
    public String toString() {
        return "Offer{" +
                "name='" + name + '\'' +
                ", seller_username='" + seller_username + '\'' +
                ", condition=" + condition +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", tags=" + tags +
                ", startTime='" + startTime + '\'' +
                ", isSold=" + isSold +
                ", id=" + id +
                ", durationDays=" + durationDays +
                ", photos=" + photos +
                ", location=" + coordinates.latitude + ' ' + coordinates.longitude +
                ", cityName=" + cityName +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Offer offer = (Offer) o;

        return id == offer.id;
    }


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
        cal.add(Calendar.DATE, received.getDurationDays());
        Date updatedDate = cal.getTime();
        Date today = new Date();
        long secs = (updatedDate.getTime() - today.getTime()) / 1000;
        int hours = (int)secs / 3600;

        return hours < 0 ? 0 : hours;
    }

    public boolean isActive()
    {
        int hours = calculateRemainingTime();
        if(hours<=0 || this.isSold==1) return false;
        return true;
    }


    public static ArrayList<Offer> removeInactiveOffers(Offer[] allOffers)
    {
        ArrayList<Offer> activeOffers = new ArrayList<>();

        for(Offer possiblyActiveOffer : allOffers)
        {
            if(!possiblyActiveOffer.isActive()) continue;
            activeOffers.add(possiblyActiveOffer);
        }

        return activeOffers;
    }


    private static void initializeDurationMap()
    {
        durationsDays.put(Duration.SHORT, 3);
        durationsDays.put(Duration.MEDIUM, 7);
        durationsDays.put(Duration.LONG, 14);
    }
}