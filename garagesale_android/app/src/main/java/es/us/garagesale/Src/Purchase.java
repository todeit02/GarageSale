package es.us.garagesale.Src;

/**
 * Created by mariaventura on 7/3/18.
 */

public class Purchase
{
    private String buyTime;
    private int offerId;
    private String buyerUsername;
    private float price;
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
    public void setHasContactedSeller(boolean hasContactedSeller) { this.hasContactedSeller = hasContactedSeller; }

    public int getOfferId() { return offerId; }
    public void setOfferId(int offerId) {
        this.offerId = offerId;
    }

    public String getBuyerUsername() {
        return buyerUsername;
    }
    public void setBuyerUsername(String buyerUsername) {
        this.buyerUsername = buyerUsername;
    }

    public String getBuyTime() {
        return buyTime;
    }
    public void setBuyTime(String buyTime) {
        this.buyTime = buyTime;
    }

    public float getPrice() {
        return price;
    }
    public void setPrice(float price) {
        this.price = price;
    }

    public Purchase(){}

    public Purchase(String buyTime, int offerId, String buyerUsername, float price)
    {
        this.buyTime = buyTime;
        this.offerId = offerId;
        this.buyerUsername = buyerUsername;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Purchase{" +
                "buyTime='" + buyTime + '\'' +
                ", offerId=" + offerId +
                ", buyerUsername='" + buyerUsername + '\'' +
                ", price=" + price +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", hasContactedSeller=" + hasContactedSeller +
                '}';
    }
}
