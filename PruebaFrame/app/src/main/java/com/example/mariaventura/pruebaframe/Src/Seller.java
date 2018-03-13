package com.example.mariaventura.pruebaframe.Src;

import com.example.mariaventura.pruebaframe.Src.Person;
import com.example.mariaventura.pruebaframe.Src.Post;
import com.example.mariaventura.pruebaframe.Src.Purchase;
import com.orm.SugarRecord;

import java.util.ArrayList;

/**
 * Created by mariaventura on 7/3/18.
 */
/*los sellers pueden ser buyers tambien*/
public class Seller extends Person{

    private  String adress;
    private int reputation; //cuando un buyer compra algo de un seller, se le habilita la opcion de valorar la reputacion de ESTE seller
    private ArrayList<Post> posts;
    private int earnedMoney;
    private ArrayList<Purchase> sales;
    private int cellphone;

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public int getReputation() {
        return reputation;
    }

    public void setReputation(int reputation) {
        this.reputation = reputation;
    }

    public ArrayList<Post> getPosts() {
        return posts;
    }

    public void setPosts(ArrayList<Post> posts) {
        this.posts = posts;
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

    public Seller(String adress, int reputation, ArrayList<Post> posts, int earnedMoney, ArrayList<Purchase> sales, int cellphone) {
        this.adress = adress;
        this.reputation = reputation;
        this.posts = posts;
        this.earnedMoney = earnedMoney;
        this.sales = sales;
        this.cellphone = cellphone;
    }

    public  Seller(){

    }


}
