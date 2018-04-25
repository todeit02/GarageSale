package es.us.garagesale.DataAccess;

import es.us.garagesale.Src.Interested;


/**
 * Created by mariaventura on 25/4/18.
 */

public interface IInterestedConsumer
{
    void consume(Interested[] interested);
}