package es.us.garagesale.Src;

import com.orm.SugarRecord;

/**
 * Created by mariaventura on 7/3/18.
 */

/*cambiar String a date*/

public class Purchase extends SugarRecord<Purchase>{
    private int state;
    private int offer_id;
    private String buyer_username;


    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
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

    public Purchase(int state, int offer_id, String buyer_username) {
        this.state = state;
        this.offer_id = offer_id;
        this.buyer_username = buyer_username;
    }

    @Override
    public String toString() {
        return "Purchase{" +
                "state=" + state +
                ", offer_id=" + offer_id +
                ", buyer_username='" + buyer_username + '\'' +
                '}';
    }

    public Purchase(){

    }
}
