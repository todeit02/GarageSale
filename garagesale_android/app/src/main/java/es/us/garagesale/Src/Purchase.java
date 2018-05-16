package es.us.garagesale.Src;

import com.orm.SugarRecord;

/**
 * Created by mariaventura on 7/3/18.
 */

/*cambiar String a date*/

public class Purchase extends SugarRecord<Purchase>{
    private String buy_time;
    private int offer_id;
    private String buyer_username;
    private int price;
    private String paymentMethod;
    private boolean hasContactedSeller;

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public boolean isHasContactedSeller() {
        return hasContactedSeller;
    }

    public void setHasContactedSeller(boolean hasContactedSeller) {
        this.hasContactedSeller = hasContactedSeller;
    }

    public int getOffer_id() {
        return offer_id;
    }

    public void setOffer_id(int offer_id) {
        this.offer_id = offer_id;
    }

    public String getBuyer_username() {
        return buyer_username;
    }

    public void setBuyer_username(String buyer_username) {
        this.buyer_username = buyer_username;
    }

    public String getBuy_time() {
        return buy_time;
    }

    public void setBuy_time(String buy_time) {
        this.buy_time = buy_time;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Purchase(){

    }

    public Purchase(String buy_time, int offer_id, String buyer_username, int price) {
        this.buy_time = buy_time;
        this.offer_id = offer_id;
        this.buyer_username = buyer_username;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Purchase{" +
                "buy_time='" + buy_time + '\'' +
                ", offer_id=" + offer_id +
                ", buyer_username='" + buyer_username + '\'' +
                ", price=" + price +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", hasContactedSeller=" + hasContactedSeller +
                '}';
    }
}
