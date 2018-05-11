package es.us.garagesale.DataAccess;

/**
 * Created by Tobias on 11/05/2018.
 */

public interface ISuccessConsumer {
    void consume(boolean wasSuccessful);
}
