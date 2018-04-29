package es.us.garagesale.Src;

/**
 * Created by mariaventura on 25/4/18.
 */

public class Interested {
   String username;
   int offer_id;
   int price;

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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Interested(String username, int offerId, int price) {
        this.username = username;
        this.offer_id = offerId;
        this.price = price;
    }
    public Interested(){

    }
}
