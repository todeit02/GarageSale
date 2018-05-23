package es.us.garagesale.Src;

/**
 * Created by mariaventura on 25/4/18.
 */

public class Interested {
   String username;
   int offer_id;
   float price;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getOfferId() {
        return offer_id;
    }

    public void setOfferId(int offerId) {
        this.offer_id = offerId;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Interested(String username, int offerId, float price) {
        this.username = username;
        this.offer_id = offerId;
        this.price = price;
    }

    public Interested(){

    }


    @Override
    public String toString() {
        return "Interested{" +
                "username='" + username + '\'' +
                ", offer_id=" + offer_id +
                ", price=" + price +
                '}';
    }
}
