package es.us.garagesale.DataAccess;

import es.us.garagesale.Src.Ranking;

/**
 * Created by mariaventura on 12/5/18.
 */

public interface IRankingConsumer {
    void consume(Ranking receivedRanking);
}
