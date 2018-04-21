package es.us.garagesale.Activity;

import android.app.Activity;

import android.os.Bundle;
import android.widget.TextView;

import es.us.garagesale.DataAccess.DatabaseManager;
import es.us.garagesale.DataAccess.IOfferConsumer;
import es.us.garagesale.R;
import es.us.garagesale.Src.Offer;


public class OfferDetailActivity extends Activity {

    private int selectedOfferId;
    TextView title;
    TextView detail;
    private Offer offer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_details);

        title = findViewById(R.id.tvOfferDetailsOfferUsedState);
        detail = findViewById(R.id.tv_offer_details_description);
        selectedOfferId = getIntent().getIntExtra("id", 0);

        DatabaseManager.loadOffer(selectedOfferId, this, new IOfferConsumer() {
            @Override
            public void consume(Offer receivedOffer) {
                displayOffer(receivedOffer);
            }
        });
    }


    private void displayOffer(Offer received){
        title.setText(received.getName());
        detail.setText(received.getDescription());
    }
   //FALTAN SETEAR MAS COSAS..

}


