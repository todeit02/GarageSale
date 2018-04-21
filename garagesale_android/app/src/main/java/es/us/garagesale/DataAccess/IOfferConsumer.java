package es.us.garagesale.DataAccess;

import es.us.garagesale.Src.Offer;

/**
 * Created by Meri.
 */

public interface IOfferConsumer
{
    void consume(Offer receivedOffer);
}