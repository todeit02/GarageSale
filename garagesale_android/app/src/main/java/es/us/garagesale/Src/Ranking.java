package es.us.garagesale.Src;

/**
 * Created by mariaventura on 12/5/18.
 */

public class Ranking {
    String seller_username;
    String buyer_username;
    float value;

    public Ranking(){

    }

    public String getSeller_username() {
        return seller_username;
    }

    public void setSeller_username(String seller_username) {
        this.seller_username = seller_username;
    }

    public String getBuyer_username() {
        return buyer_username;
    }

    public void setBuyer_username(String buyer_username) {
        this.buyer_username = buyer_username;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Ranking{" +
                "seller_username='" + seller_username + '\'' +
                ", buyer_username='" + buyer_username + '\'' +
                ", value=" + value +
                '}';
    }

    public Ranking(String seller_username, String buyer_username, float value) {
        this.seller_username = seller_username;
        this.buyer_username = buyer_username;
        this.value = value;
    }
}
