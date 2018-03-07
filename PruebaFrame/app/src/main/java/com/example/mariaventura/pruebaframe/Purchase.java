package com.example.mariaventura.pruebaframe;

/**
 * Created by mariaventura on 7/3/18.
 */

/*cambiar String a date*/

public class Purchase {
    private Post post;
    private String date;
    private Person buyer;
    private int totalPrice;
    private int code;

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Person getBuyer() {
        return buyer;
    }

    public void setBuyer(Person buyer) {
        this.buyer = buyer;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Purchase(Post post, String date, Person buyer, int totalPrice, int code) {
        this.post = post;
        this.date = date;
        this.buyer = buyer;
        this.totalPrice = totalPrice;
        this.code = code;
    }

    public Purchase(){
        this.post = null;
        this.date = "";
        this.buyer = null;
        this.totalPrice = 0;
        this.code=0;
    }

    @Override
    public String toString() {
        return "Purchase: " +
                "post=" + post +
                ", date='" + date + '\'' +
                ", buyer=" + buyer +
                ", totalPrice=" + totalPrice + "price" + totalPrice;
    }
}
