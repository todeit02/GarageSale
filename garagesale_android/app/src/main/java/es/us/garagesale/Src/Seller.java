package es.us.garagesale.Src;

import java.util.ArrayList;

/**
 * Created by mariaventura on 7/3/18.
 */
/*los sellers pueden ser buyers tambien*/
public class Seller extends Person{

    private  String adress;
   // private int reputation; //cuando un buyer compra algo de un seller, se le habilita la opcion de valorar la reputacion de ESTE seller
    private ArrayList<Offer> offers;
    private int earnedMoney;
    private ArrayList<Purchase> sales;
    private int cellphone;

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

   /*public int getReputation() {
        return reputation;
    }*/

    /*public void setReputation(int reputation) {
        this.reputation = reputation;
    }*/

    public ArrayList<Offer> getOffers() {
        return offers;
    }

    public void setOffers(ArrayList<Offer> offers) {
        this.offers = offers;
    }

    public int getEarnedMoney() {
        return earnedMoney;
    }

    public void setEarnedMoney(int earnedMoney) {
        this.earnedMoney = earnedMoney;
    }

    public ArrayList<Purchase> getSales() {
        return sales;
    }

    public void setSales(ArrayList<Purchase> sales) {
        this.sales = sales;
    }

    public int getCellphone() {
        return cellphone;
    }

    public void setCellphone(int cellphone) {
        this.cellphone = cellphone;
    }

    public Seller(String adress, ArrayList<Offer> offers, int earnedMoney, ArrayList<Purchase> sales, int cellphone) {
        this.adress = adress;
        this.offers = offers;
        this.earnedMoney = earnedMoney;
        this.sales = sales;
        this.cellphone = cellphone;
    }

    public  Seller(){

    }


}
