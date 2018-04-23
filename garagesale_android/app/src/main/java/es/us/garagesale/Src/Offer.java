package es.us.garagesale.Src;


import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by mariaventura on 7/3/18.
 */

/*cambiar String a timestamp*/
public class Offer {

    private String name;
    private String description;
    private float price;
    private ArrayList<String> tags;
    private String publishDate;
    private String endTime;
    private boolean sold; //pensarlo, porque si esta en la ArrayLista de purchases esta vendida
    private Seller seller;
    private ArrayList<Person> interested;
    private int id;
    private String state;
    private int activePeriod;
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

    public String getPublishDate() {
        return publishDate;
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

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTimestamp() {
        return publishDate;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public void setTimestamp(String publishDate) {
        this.publishDate = publishDate;
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

    public Offer(String name, String description, float price, ArrayList<String> tags, String timestamp, boolean sold, Seller seller, ArrayList<Person> interested) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.tags = tags;
        this.publishDate = timestamp;
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
        this.publishDate = null;
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
                ", timestamp='" + publishDate + '\'' +
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



    //hacer funcion para filtrar las ofertas validas.

    public int calculateRemainingTime(){
        Offer received = this;
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date offerDate = null;
        try {
            offerDate = format.parse(received.getTimestamp());
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

    public boolean isValid(){
        int hours = calculateRemainingTime();
        if(hours<0) return false;
        return true;
    }

}
