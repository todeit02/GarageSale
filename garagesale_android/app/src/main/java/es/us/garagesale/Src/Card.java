package es.us.garagesale.Src;

import com.orm.SugarRecord;

/**
 * Created by mariaventura on 7/3/18.
 */

/*cambiar String a date*/
public class Card extends SugarRecord<Card> {
    private int cardNum;
    private String expDate;
    private int ccv;
    private String bank;

    public int getCardNum(){
        return this.cardNum;
    }

    public void setCardNum(int cardNum){
        this.cardNum=cardNum;
    }

    public String getExpDate(){
        return this.expDate;
    }

    public void setExpDate(String expDate){
        this.expDate= expDate;
    }
    public int getCcv(){
        return this.ccv;
    }
    public void setCcv(int ccv){
        this.ccv = ccv;
    }
    public String getBank(){
        return this.bank;
    }
    public void setBank(String bank){
        this.bank = bank;
    }

    public Card(int cardNum, String expDate, int ccv, String bank) {
        this.cardNum = cardNum;
        this.expDate = expDate;
        this.ccv = ccv;
        this.bank = bank;
    }

    public Card(){
        this.cardNum = 0;
        this.expDate = "";
        this.ccv = 000;
        this.bank = "";
    }

    @Override
    public String toString() {
        return "Card: " +
                "cardNum=" + cardNum +
                ", expDate='" + expDate + '\'' +
                ", ccv=" + ccv +
                ", bank='" + bank;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Card card = (Card) o;

        if (cardNum != card.cardNum) return false;
        if (ccv != card.ccv) return false;
        if (expDate != null ? !expDate.equals(card.expDate) : card.expDate != null) return false;
        return bank != null ? bank.equals(card.bank) : card.bank == null;
    }

}
