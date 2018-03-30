package com.example.mariaventura.pruebaframe.DataAccess;

import com.example.mariaventura.pruebaframe.Src.Card;
import com.example.mariaventura.pruebaframe.Src.Person;
import com.orm.SugarRecord;

/**
 * Created by mariaventura on 13/3/18.


public class PersonHandler {
    public void AddPerson(Person toAdd){
        Person newPerson= new Person(toAdd.getUser(), toAdd.getPassword() , toAdd.getName() , toAdd.getEmail(), toAdd.getBirthDate() , toAdd.getNationality() ,toAdd.getPersonalCard());
        newPerson.save();
    }

   // public boolean PersonAlreadyExists(Person toSearch){
     //   Person toLook = Person.findById(Person.class, toSearch.getUser());
       // if(
    //}
}*/
