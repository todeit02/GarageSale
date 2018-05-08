package es.us.garagesale.Src;

import com.orm.SugarRecord;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by mariaventura on 7/3/18.
 */

/*cambiar String a date*/
public class Card
{
    private String cardNum = "";
    private Date expDate;
    private int ccv;
    private String bank;

    public String getCardNum(){
        return cardNum;
    }
    public void setCardNum(String cardNum){
        this.cardNum = cardNum;
    }

    public Date getExpDate() { return expDate; }
    public String getExpDate(String formatPattern)
    {
        SimpleDateFormat birthDateFormat = new SimpleDateFormat(formatPattern, Locale.US);
        String birthDate = birthDateFormat.format(this.getExpDate());
        return birthDate;
    }
    public void setExpDate(Date expDate){
        this.expDate = expDate;
    }

    public int getCcv(){
        return ccv;
    }
    public void setCcv(int ccv){
        this.ccv = ccv;
    }

    public String getBank(){
        return bank;
    }
    public void setBank(String bank){
        this.bank = bank;
    }

    public Card(String cardNum, Date expDate, int ccv, String bank)
    {
        this.cardNum = cardNum;
        this.expDate = expDate;
        this.ccv = ccv;
        this.bank = bank;
    }

    public Card()
    {
        this.cardNum = "";
        this.expDate = null;
        this.ccv = 000;
        this.bank = "";
    }

    @Override
    public String toString()
    {
        return "Card: " +
                "cardNum=" + cardNum +
                ", expDate='" + expDate + '\'' +
                ", ccv=" + ccv +
                ", bank='" + bank;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Card card = (Card) o;

        if (!cardNum.equals(card.cardNum)) return false;
        if (ccv != card.ccv) return false;
        if (expDate != null ? !expDate.equals(card.expDate) : card.expDate != null) return false;
        return bank != null ? bank.equals(card.bank) : card.bank == null;
    }

}
