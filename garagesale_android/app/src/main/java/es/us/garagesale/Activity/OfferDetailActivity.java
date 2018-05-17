package es.us.garagesale.Activity;

import android.app.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

import es.us.garagesale.DataAccess.DatabaseManager;
import es.us.garagesale.DataAccess.IInterestedConsumer;
import es.us.garagesale.DataAccess.IOfferConsumer;
import es.us.garagesale.DataAccess.IUserRankingConsumer;
import es.us.garagesale.R;
import es.us.garagesale.Src.Interested;
import es.us.garagesale.Src.Offer;
import es.us.garagesale.Src.OfferTool;

import static android.util.TypedValue.COMPLEX_UNIT_SP;


public class OfferDetailActivity extends Activity {

    private int selectedOfferId;
    TextView title, detail, state, remainingTime, currentPrice, currentOffers, createInterested, location, googleMaps, seller, reputationText;
    ConstraintLayout showLocation;
    View map;
    LinearLayout locationLayout;
    private Offer offer;
    String actualUser;
    String offerUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_details);

        title = findViewById(R.id.tvOfferDetailsOfferTitle);
        reputationText = findViewById(R.id.tvReputation);
        seller= findViewById(R.id.tvSeller);
        googleMaps = findViewById(R.id.tv_btn_show_map);
        map = findViewById(R.id.imgv_btn_show_map);
        detail = findViewById(R.id.tv_offer_details_description);
        state =  findViewById(R.id.tvOfferDetailsOfferUsedState);
        remainingTime = findViewById(R.id.tv_offer_details_remaining_time);
        currentPrice = findViewById(R.id.tv_offer_details_current_price);
        location = findViewById(R.id.tv_offer_details_location);
        locationLayout = findViewById(R.id.ll_offer_location);
        currentOffers = findViewById(R.id.tv_offer_details_offer_count);
        showLocation = findViewById(R.id.cl_btn_show_map);
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


    private void displayOffer(final Offer received)
    {
        title.setText(received.getName());
        seller.setText("Vendedor: "+received.getSellerUsername());
        final RatingBar reputation = findViewById(R.id.ratingBarDetail);
        reputation.setEnabled(false);
        DatabaseManager.getUserRanking(actualUser, OfferDetailActivity.this, new IUserRankingConsumer() {
            @Override
            public void consume(float receivedRanking) {
                float totalRanking = receivedRanking;
                reputation.setRating(totalRanking);
                reputationText.setText("Reputacion: ");

            }
        });
        detail.setText(received.getDescription()+" \n"+ "Precio original: "+ getString(R.string.currency) +received.getPrice());
        CharSequence condition = OfferTool.getCharSequenceFromCondition(received.getCondition(), this);
        state.setText(condition);
        remainingTime.setText(received.calculateRemainingTime()+" horas");
        currentOffers.setText("Nadie ha ofertado aun");
        currentPrice.setText("0"+getString(R.string.currency)+" es la oferta mas alta");
        getMaxOffer(received);
        if (received.getCoordinates().latitude!=0.0&&received.getCoordinates().longitude!=0.0){
            showLocation.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    openGoogleMaps(received);
                }
            });

            CharSequence placeName = received.getCityName();
            LatLng coordinates = received.getCoordinates();
            CharSequence locationText = getString(R.string.offer_location, placeName.toString(), coordinates.toString());
            location.setText(locationText);
            location.setTextSize(COMPLEX_UNIT_SP, 12);
        }else{
            showLocation.setBackgroundResource(0);
            location.setVisibility(View.INVISIBLE);
            map.setVisibility(View.INVISIBLE);
            googleMaps.setVisibility(View.INVISIBLE);
        }
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

    private void openGoogleMaps(Offer receivedOffer){
        LatLng coordinates = receivedOffer.getCoordinates();
        double lat = coordinates.latitude;
        double lng = coordinates.longitude;
        String label = "Ubicacion del producto";
        String uriBegin = "geo:" + lat + "," + lng;
        String query = lat + "," + lng + "(" + label + ")";
        String encodedQuery = Uri.encode(query);
        String uriString = uriBegin + "?q=" + encodedQuery + "&z=16";
        Uri uri = Uri.parse(uriString);
        Intent mapIntent = new Intent(android.content.Intent.ACTION_VIEW, uri);
        startActivity(mapIntent);
    }


}


