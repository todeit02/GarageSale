package es.us.garagesale.Activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.*;

import es.us.garagesale.DataAccess.DatabaseManager;
import es.us.garagesale.DataAccess.IOffersConsumer;
import es.us.garagesale.Src.Offer;

import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import es.us.garagesale.R;


public class OfferListActivity extends Activity
{
    private LinearLayout linearLayout = null;
    private LayoutInflater linearLayoutInflater = null;
    private FloatingActionButton buttonAddOffer = null;
    private ImageButton btnPersonalArea;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_list);

        linearLayout = (LinearLayout)findViewById(R.id.offer_list_content);
        linearLayoutInflater = LayoutInflater.from(this);
        buttonAddOffer = findViewById(R.id.fab_add_offer);
        btnPersonalArea = findViewById(R.id.imgbtn_personal_area);

        DatabaseManager.loadOffers(this, new IOffersConsumer() {
            @Override
            public void consume(Offer[] offers) {
                createOfferViews(offers);
            }
        });

        buttonAddOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent createOfferActivityIntent = new Intent(getApplicationContext(), OfferCreationActivity.class);
                startActivity(createOfferActivityIntent);
            }
        });

        btnPersonalArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewProfileActivity = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(viewProfileActivity);
            }
        });

        SharedPreferences sharedPreferences = this.getSharedPreferences("login", MODE_PRIVATE);
        Log.d(this.getClass().getSimpleName(), sharedPreferences.getString("username", null));
    }

    private void createOfferViews(Offer[] creatingOffers)
    {
        int idOffset = 1;
        int length = 0;

        for(final Offer creatingOffer : creatingOffers)
        {

            if(creatingOffer.isValid()) {

                length++;

                View inflatedOffer = linearLayoutInflater.inflate(R.layout.offer_list_item, null);
                inflatedOffer.setId(R.layout.offer_list_item + idOffset);

                TextView inflatedOfferItemTitle = inflatedOffer.findViewById(R.id.tv_offer_item_title);
                inflatedOfferItemTitle.setId(R.id.tv_offer_item_title + idOffset * (100 + 1));

                TextView inflatedOfferItemPrice = inflatedOffer.findViewById(R.id.tv_offer_item_price);
                inflatedOfferItemPrice.setId(R.id.tv_offer_item_price + idOffset * (10000 + 1));

                inflatedOfferItemTitle.setText(creatingOffer.getName());
                inflatedOfferItemPrice.setText("Precio original: " + creatingOffer.getPrice() + getString(R.string.currency));

                inflatedOffer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent createOfferDetailActivityIntent = new Intent(getApplicationContext(), OfferDetailActivity.class);
                        Bundle extras = new Bundle();
                        extras.putInt("id",creatingOffer.getId());
                        extras.putString("username",creatingOffer.getSellerUsername());
                        createOfferDetailActivityIntent.putExtras(extras);
                        startActivity(createOfferDetailActivityIntent);
                    }
                });

                linearLayout.addView(inflatedOffer);
                idOffset++;
            }
        }

        if(length==0 || creatingOffers.length==0){
            //Mostrar mensaje :"No hay ofertas disponibles"

            View inflatedOffer = linearLayoutInflater.inflate(R.layout.offer_list_empty, null);

            TextView inflatedOfferItemTitle = inflatedOffer.findViewById(R.id.tv_offer_item_title);
            inflatedOfferItemTitle.setText("No hay ofertas disponibles");

            linearLayout.addView(inflatedOffer);
        }
    }
}