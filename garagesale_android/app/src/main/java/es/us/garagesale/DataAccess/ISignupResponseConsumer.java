package es.us.garagesale.DataAccess;

/**
 * Created by Tobias on 04/05/2018.
 */

public interface ISignupResponseConsumer
{
    void consume(boolean wasSignupSuccessful, boolean isUsernameAlreadyTaken);
}
