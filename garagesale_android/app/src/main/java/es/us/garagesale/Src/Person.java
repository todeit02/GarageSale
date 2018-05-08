package es.us.garagesale.Src;

import com.orm.SugarRecord;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Created by mariaventura on 7/3/18.
 */


/*cambiar String a date*/

public class Person extends SugarRecord<Person> {

    private String username;
    private String password;
    private String realName;
    private String email;
    private Date birthDate;
    private String nationality;
    private Card card;
    private int reputation;
    private ArrayList<Purchase> purchases;


    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public int getReputation() {
        return reputation;
    }
    public void setReputation(int reputation) {
        this.reputation = reputation;
    }

    public Card getPersonalCard(){
        return this.card;
    }
    public void setPersonalCard(Card personalCard){
        this.card = personalCard;
    }

    public String getRealName(){
        return this.realName;
    }
    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword(){
        return this.password;
    }
    public void setPassword(String password){
        this.password = password;
    }

    public Date getBirthDate(){
        return this.birthDate;
    }
    public String getBirthDate(String formatPattern)
    {
        SimpleDateFormat birthDateFormat = new SimpleDateFormat(formatPattern, Locale.US);
        String birthDate = birthDateFormat.format(this.getBirthDate());
        return birthDate;
    }
    public void setBirthDate(Date birthDate){
        this.birthDate = birthDate;
    }

    public String getNationality(){
        return this.nationality;
    }
    public void setNationality(String nationality){
        this.nationality = nationality;
    }

    public ArrayList<Purchase> getPurchases() {
        return purchases;
    }
    public void setPurchases(ArrayList<Purchase> purchases) {
        this.purchases = purchases;
    }

    public Person()
    {
        this.realName   = "";
        this.birthDate  = null;
        this.username   = "";
        this.nationality = "";
        this.password   = "";
        this.email      = "";
        this.card       = null;
        this.purchases  = null;
    }

    public Person(String user, String password, String realName, String email, Date birthDate, String nationality, Card personalCard)
    {
        this(user, password, realName, email, birthDate, nationality, personalCard, new ArrayList<Purchase>(), 0);
    }

    public Person(String user, String password, String realName, String email, Date birthDate, String nationality, Card personalCard, ArrayList<Purchase> purchases, int reputation)
    {
        this.username = user;
        this.password = password;
        this.realName = realName;
        this.email = email;
        this.birthDate = birthDate;
        this.nationality = nationality;
        this.card = personalCard;
        this.purchases = purchases;
        this.reputation = reputation;
    }

    @Override
    public String toString() {
        return "Person: " +
                "username=" + username + '\'' +
                ", password=" + password + '\'' +
                ", realName='" + realName + '\'' +
                ", email='" + email + '\'' +
                ", birthDate='" + birthDate + '\'' +
                ", nationality='" + nationality + '\'' +
                ", reputation='" + reputation + '\'' +
                ", personalCard=" + card;

    }

    //Redefinir el equals

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;

        if (username != null ? !username.equals(person.username) : person.username != null) return false;

        if (email != null ? !email.equals(person.email) : person.email != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = username != null ? username.hashCode() : 0;
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (realName != null ? realName.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (birthDate != null ? birthDate.hashCode() : 0);
        result = 31 * result + (nationality != null ? nationality.hashCode() : 0);
        result = 31 * result + (card != null ? card.hashCode() : 0);
        return result;
    }
}
