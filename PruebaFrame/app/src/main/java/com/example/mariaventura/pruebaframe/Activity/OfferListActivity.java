package com.example.mariaventura.pruebaframe.Activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.*;

import com.example.mariaventura.pruebaframe.R;

import android.widget.LinearLayout;
import android.widget.TextView;

public class OfferListActivity extends Activity {

    private LinearLayout linearLayout = null;
    private LayoutInflater linearLayoutInflater = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_list);

        linearLayout = (LinearLayout)findViewById(R.id.offerListContent);
        linearLayoutInflater = LayoutInflater.from(this);

        createDummyItems();
    }

    private void createDummyItems()
    {
        for (int i = 0; i < 16; i++) {
            View inflatedPost = linearLayoutInflater.inflate(R.layout.offer_list_item, null);
            inflatedPost.setId(R.layout.offer_list_item + i + 1);

            LinearLayout.LayoutParams layoutParams;
            layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            inflatedPost.setLayoutParams(layoutParams);

            inflatedPost.setBackgroundColor(Color.RED);

            TextView inflatedPostOfferItemTitle = inflatedPost.findViewById(R.id.tv_offer_item_title);
            inflatedPostOfferItemTitle.setId(R.id.tv_offer_item_title + i + 1);

            LinearLayout inflatedPostOfferItemTexts = inflatedPost.findViewById(R.id.ll_offer_item_texts);
            inflatedPostOfferItemTexts.setId(R.id.ll_offer_item_texts + i + 101);

            TextView inflatedPostOfferItemPrice = inflatedPost.findViewById(R.id.tv_offer_item_price);
            inflatedPostOfferItemPrice.setId(R.id.tv_offer_item_price + i + 10001);

            inflatedPostOfferItemTitle.setText("Título de la oferta " + (i + 1));
            inflatedPostOfferItemTexts.setBackgroundColor(Color.YELLOW);
            inflatedPostOfferItemPrice.setText("" + (13.07 * (i + 1)) + "@string/currency");

            linearLayout.addView(inflatedPost);
        }
    }
}