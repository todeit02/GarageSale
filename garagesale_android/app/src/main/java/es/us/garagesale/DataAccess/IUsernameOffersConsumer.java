package es.us.garagesale.DataAccess;

import es.us.garagesale.Src.Offer;

/**
 * Created by mariaventura on 30/4/18.
 */

public interface IUsernameOffersConsumer {
    void consume(Offer[] offers);
}
