package com.example.mariaventura.pruebaframe;

/**
 * Created by mariaventura on 7/3/18.
 */


/*cambiar String a date*/

public abstract class Person {
    private String user;
    private String password;
    private String name;
    private String email;
    private String birthDate;
    private String nationality;
    private Card personalCard;


    public Card getPersonalCard(){
        return this.personalCard;
    }

    public void setPersonalCard(Card personalCard){
        this.personalCard = personalCard;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName(){
        return this.name;
    }

    public void setNombre(String name){
        this.name= name;
    }

    public String getPassword(){
        return this.password;
    }
    public void setPassword(String password){
        this.password = password;
    }

    public String getUser(){
        return this.user;
    }

    public void setUsuario(String user){
        this.user= user;
    }
    public String getBirthDate(){
        return this.birthDate;
    }

    public void setBirthDate(String birthDate){
        this.birthDate = birthDate;
    }

    public String getNationality(){
        return this.nationality;
    }
    public void setNationality(String nationality){
        this.nationality = nationality;
    }

    public Person(){
        this.name = "";
        this.birthDate = "";
        this.user = "";
        this.nationality = "";
        this.password ="";
        this.email = "";
        this.personalCard = null;
    }

    public Person(String user, String password, String name, String email, String birthDate, String nationality, Card personalCard) {
        this.user = user;
        this.password = password;
        this.name = name;
        this.email = email;
        this.birthDate = birthDate;
        this.nationality = nationality;
        this.personalCard = personalCard;
    }

    @Override
    public String toString() {
        return "Person: " +
                "user=" + user + '\'' +
                ", password=" + password + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", birthDate='" + birthDate + '\'' +
                ", nationality='" + nationality + '\'' +
                ", personalCard=" + personalCard;
    }

    //Redefinir el equals

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;

        if (user != null ? !user.equals(person.user) : person.user != null) return false;

        if (email != null ? !email.equals(person.email) : person.email != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = user != null ? user.hashCode() : 0;
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (birthDate != null ? birthDate.hashCode() : 0);
        result = 31 * result + (nationality != null ? nationality.hashCode() : 0);
        result = 31 * result + (personalCard != null ? personalCard.hashCode() : 0);
        return result;
    }
}
