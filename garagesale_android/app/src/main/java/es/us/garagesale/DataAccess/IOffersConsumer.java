package es.us.garagesale.DataAccess;

import es.us.garagesale.Src.Offer;

/**
 * Created by Tobias on 25/03/2018.
 */

public interface IOffersConsumer
{
    void consume(Offer[] offers);
}