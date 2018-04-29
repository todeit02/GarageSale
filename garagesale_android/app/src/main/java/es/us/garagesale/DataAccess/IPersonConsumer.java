package es.us.garagesale.DataAccess;

import es.us.garagesale.Src.Person;

/**
 * Created by mariaventura on 29/4/18.
 */

public interface IPersonConsumer {
    void consume(Person recievedPerson);
}
