package es.us.garagesale.Activity;

import android.app.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import es.us.garagesale.DataAccess.DatabaseManager;
import es.us.garagesale.DataAccess.IInterestedConsumer;
import es.us.garagesale.DataAccess.IOfferConsumer;
import es.us.garagesale.R;
import es.us.garagesale.Src.Interested;
import es.us.garagesale.Src.Offer;



public class OfferDetailActivity extends Activity {

    private int selectedOfferId;
    TextView title, detail, state, remainingTime, currentPrice, currentOffers, createInterested;
    private Offer offer;
    String actualUser;
    String offerUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_details);

        title = findViewById(R.id.tvOfferDetailsOfferTitle);
        detail = findViewById(R.id.tv_offer_details_description);
        state =  findViewById(R.id.tvOfferDetailsOfferUsedState);
        remainingTime = findViewById(R.id.tv_offer_details_remaining_time);
        currentPrice = findViewById(R.id.tv_offer_details_current_price);
        currentOffers = findViewById(R.id.tv_offer_details_offer_count);
        createInterested = findViewById(R.id.tv_btn_bid_label);
        createInterested.setText("Propón un precio!");


        SharedPreferences sharedPreferences = this.getSharedPreferences("login", MODE_PRIVATE);
        actualUser = sharedPreferences.getString("username", null);

        offerUser = getIntent().getStringExtra("username");
        selectedOfferId = getIntent().getIntExtra("id", 0);

        DatabaseManager.loadOffer(selectedOfferId, this, new IOfferConsumer() {
            @Override
            public void consume(Offer receivedOffer) {
                displayOffer(receivedOffer);
            }
        });
    }


    private void displayOffer(final Offer received){
        title.setText(received.getName());
        detail.setText(received.getDescription()+" \n"+ "Precio original: "+ getString(R.string.currency) +received.getPrice());
        state.setText(received.getState());
        remainingTime.setText(received.calculateRemainingTime()+" horas");
        getMaxOffer(received);
        createInterested.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isTheUsersOffer()){
                    Intent createInterestedActivityIntent = new Intent(getApplicationContext(), InterestedCreationActivity.class);
                    Bundle extras = new Bundle();
                    extras.putInt("id", selectedOfferId);
                    extras.putString("name",received.getName());
                    createInterestedActivityIntent.putExtras(extras);
                    startActivity(createInterestedActivityIntent);
                }
                else{
                    createInterested.setText("No es posible autotarifar");
                }
            }
        });

    }

    private boolean isTheUsersOffer(){
        boolean areEqual;
        if(actualUser.equals(offerUser)) {
            areEqual = true;
        }else{
            areEqual = false;
        }
        return areEqual;
    }

    private void getMaxOffer(Offer received){
        DatabaseManager.loadInterested(received.getId(),this, new IInterestedConsumer() {
            @Override
            public void consume(Interested[] interested) {
                currentOffers.setText("Hay " + interested.length + " ofertas");
                if(interested.length>0){
                    currentPrice.setText(getMaxInterested(interested)+ getString(R.string.currency)+ " es la oferta mas alta");
                }
                else{
                    currentPrice.setText("Nadie ha ofertado aun");
                }
            }
        });
    }

    private int getMaxInterested(Interested[] interested){
        int max=0;
        for(Interested i : interested)
        {
            if(i.getPrice()>max){
                max= i.getPrice();
            }
        }
        return max;
    }

}


