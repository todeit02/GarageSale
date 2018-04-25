package es.us.garagesale.Src;

/**
 * Created by mariaventura on 25/4/18.
 */

public class Interested {
   Person person;
   Offer offer;
   int price;

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Offer getOffer() {
        return offer;
    }

    public void setOffer(Offer offer) {
        this.offer = offer;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Interested(Person person, Offer offer, int price) {
        this.person = person;
        this.offer = offer;
        this.price = price;
    }
    public Interested(){

    }
}
