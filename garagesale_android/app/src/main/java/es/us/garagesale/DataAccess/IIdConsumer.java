package es.us.garagesale.DataAccess;

/**
 * Created by Tobias on 10/05/2018.
 */

public interface IIdConsumer
{
    void consume(boolean wasCreationSuccessful, int id);
}