package com.example.mariaventura.pruebaframe.Activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Debug;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.*;

import com.example.mariaventura.pruebaframe.DataAccess.DatabaseManager;
import com.example.mariaventura.pruebaframe.DataAccess.IOffersConsumer;
import com.example.mariaventura.pruebaframe.R;
import com.example.mariaventura.pruebaframe.Src.Offer;

import android.widget.LinearLayout;
import android.widget.TextView;


public class OfferListActivity extends Activity
{
    private LinearLayout linearLayout = null;
    private LayoutInflater linearLayoutInflater = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_list);

        linearLayout = (LinearLayout)findViewById(R.id.offerListContent);
        linearLayoutInflater = LayoutInflater.from(this);

        DatabaseManager.loadOffers(this, new IOffersConsumer() {
            @Override
            public void consume(Offer[] offers) {
                createOfferViews(offers);
            }
        });

        SharedPreferences sp1=this.getSharedPreferences("login", MODE_PRIVATE);

       Log.d(this.getClass().getSimpleName(), sp1.getString("username", null));
    }

    private void createOfferViews(Offer[] creatingOffers)
    {
        int idOffset = 1;

        for(Offer creatingOffer : creatingOffers)
        {
            View inflatedOffer = linearLayoutInflater.inflate(R.layout.offer_list_item, null);
            inflatedOffer.setId(R.layout.offer_list_item + idOffset);

            TextView inflatedOfferItemTitle = inflatedOffer.findViewById(R.id.tv_offer_item_title);
            inflatedOfferItemTitle.setId(R.id.tv_offer_item_title + idOffset*(100 + 1));

            TextView inflatedOfferItemPrice = inflatedOffer.findViewById(R.id.tv_offer_item_price);
            inflatedOfferItemPrice.setId(R.id.tv_offer_item_price + idOffset*(10000 + 1));

            inflatedOfferItemTitle.setText(creatingOffer.getName());
            inflatedOfferItemPrice.setText("" + creatingOffer.getPrice() + getString(R.string.currency));

            linearLayout.addView(inflatedOffer);
            idOffset++;
        }
    }
}