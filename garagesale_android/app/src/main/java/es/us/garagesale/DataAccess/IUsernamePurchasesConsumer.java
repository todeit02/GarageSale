package es.us.garagesale.DataAccess;

import es.us.garagesale.Src.Offer;
import es.us.garagesale.Src.Purchase;

/**
 * Created by mariaventura on 12/5/18.
 */

public interface IUsernamePurchasesConsumer {
    void consume(Purchase[] purchases);

}
