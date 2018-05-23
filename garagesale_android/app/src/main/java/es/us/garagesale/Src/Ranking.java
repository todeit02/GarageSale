package es.us.garagesale.Src;

/**
 * Created by mariaventura on 12/5/18.
 */

public class Ranking
{
    private String sellerUsername;
    private String buyerUsername;
    private float value;

    public String getSellerUsername() {
        return sellerUsername;
    }
    public void setSellerUsername(String seller_username) { this.sellerUsername = seller_username; }

    public String getBuyerUsername() {
        return buyerUsername;
    }
    public void setBuyerUsername(String buyer_username) {
        this.buyerUsername = buyer_username;
    }

    public float getValue() {
        return value;
    }
    public void setValue(float value) {
        this.value = value;
    }

    @Override
    public String toString()
    {
        return "Ranking{" +
                "sellerUsername='" + sellerUsername + '\'' +
                ", buyerUsername='" + buyerUsername + '\'' +
                ", value=" + value +
                '}';
    }

    public Ranking(){}

    public Ranking(String sellerUsername, String buyerUsername, float value)
    {
        this.sellerUsername = sellerUsername;
        this.buyerUsername = buyerUsername;
        this.value = value;
    }
}
