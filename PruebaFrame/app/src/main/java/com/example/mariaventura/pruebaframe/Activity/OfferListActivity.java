package com.example.mariaventura.pruebaframe.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.*;

import com.example.mariaventura.pruebaframe.DataAccess.DatabaseManager;
import com.example.mariaventura.pruebaframe.DataAccess.IOffersConsumer;
import com.example.mariaventura.pruebaframe.R;
import com.example.mariaventura.pruebaframe.Src.Offer;

import android.widget.LinearLayout;
import android.widget.TextView;


public class OfferListActivity extends AppCompatActivity
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
    }

    private void createOfferViews(Offer[] creatingOffers)
    {
        int idOffset = 1;

        for(Offer creatingOffer : creatingOffers)
        {
            View inflatedOffer = linearLayoutInflater.inflate(R.layout.offer_list_item, null);
            inflatedOffer.setId(R.layout.offer_list_item + idOffset);

            TextView inflatedPostOfferItemTitle = inflatedOffer.findViewById(R.id.tv_offer_item_title);
            inflatedPostOfferItemTitle.setId(R.id.tv_offer_item_title + idOffset*(100 + 1));

            TextView inflatedPostOfferItemPrice = inflatedOffer.findViewById(R.id.tv_offer_item_price);
            inflatedPostOfferItemPrice.setId(R.id.tv_offer_item_price + idOffset*(10000 + 1));

            inflatedPostOfferItemTitle.setText(creatingOffer.getName());
            inflatedPostOfferItemPrice.setText("" + creatingOffer.getPrice() + getString(R.string.currency));

            linearLayout.addView(inflatedOffer);
            idOffset++;
        }
    }

    private void createDummyItems()
    {
        for (int i = 0; i < 16; i++) {
            View inflatedPost = linearLayoutInflater.inflate(R.layout.offer_list_item, null);
            inflatedPost.setId(R.layout.offer_list_item + i + 1);

            TextView inflatedPostOfferItemTitle = inflatedPost.findViewById(R.id.tv_offer_item_title);
            inflatedPostOfferItemTitle.setId(R.id.tv_offer_item_title + i + 1);

            LinearLayout inflatedPostOfferItemTexts = inflatedPost.findViewById(R.id.ll_offer_item_texts);
            inflatedPostOfferItemTexts.setId(R.id.ll_offer_item_texts + i + 101);

            TextView inflatedPostOfferItemPrice = inflatedPost.findViewById(R.id.tv_offer_item_price);
            inflatedPostOfferItemPrice.setId(R.id.tv_offer_item_price + i + 10001);

            inflatedPostOfferItemTitle.setText("Título de la oferta " + (i + 1));
            inflatedPostOfferItemPrice.setText("" + (13.07 * (i + 1)) + getString(R.string.currency));

            linearLayout.addView(inflatedPost);
        }
    }
}