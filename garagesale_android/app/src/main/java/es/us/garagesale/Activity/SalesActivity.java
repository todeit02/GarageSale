package es.us.garagesale.Activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import es.us.garagesale.DataAccess.DatabaseManager;
import es.us.garagesale.DataAccess.IInterestedConsumer;
import es.us.garagesale.DataAccess.IOffersConsumer;
import es.us.garagesale.DataAccess.IUsernameOffersConsumer;
import es.us.garagesale.R;
import es.us.garagesale.Src.Interested;
import es.us.garagesale.Src.Offer;

/**
 * Created by mariaventura on 30/4/18.
 */

public class SalesActivity extends Activity {

    private LinearLayout linearLayout = null;
    private LayoutInflater linearLayoutInflater = null;
    String actualUser;
    int length;
    int idOffset;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_area);

        linearLayout = (LinearLayout)findViewById(R.id.ll_content_scroll);
        linearLayoutInflater = LayoutInflater.from(this);
        linearLayout.removeAllViews();

        SharedPreferences sharedPreferences = this.getSharedPreferences("login", MODE_PRIVATE);
        actualUser = sharedPreferences.getString("username", null);

        DatabaseManager.loadUsernameOffers(actualUser, this, new IUsernameOffersConsumer() {
            @Override
            public void consume(Offer[] offers) {
                getUsernameOffersInterested(offers);
            }
        });
    }

    public void getUsernameOffersInterested(Offer[] offers){

        ArrayList<Offer> valid = getValidOffers(offers);
         length = 0;
         idOffset = 1;

            for(final Offer offer : offers)
            {

                    length++;

                    final View inflatedOffer = linearLayoutInflater.inflate(R.layout.sale_list_item_, null);
                    inflatedOffer.setId(R.layout.sale_list_item_ + idOffset);

                    TextView generalTitle = inflatedOffer.findViewById(R.id.tv_offer_title);
                    generalTitle.setText("oferta nro. " + offer.getId());

                    TextView title = inflatedOffer.findViewById(R.id.tv_title);
                    title.setId(R.id.tv_title + idOffset * (100 + 1));
                    title.setText(offer.getName());


                    TextView originalPrice = inflatedOffer.findViewById(R.id.tv_original_price);
                    originalPrice.setId(R.id.tv_original_price + idOffset * (1000 + 1));
                    originalPrice.setText(String.valueOf(offer.getPrice())+getString(R.string.currency));

                    TextView remainingTime = inflatedOffer.findViewById(R.id.tv_remaining_time);
                    remainingTime.setId(R.id.tv_remaining_time + idOffset * (100000 + 1));

                    DatabaseManager.loadInterested(offer.getId(), this, new IInterestedConsumer() {
                        @Override
                        public void consume(Interested[] interested) {
                            TextView actualPrice = inflatedOffer.findViewById(R.id.tv_actual_price);
                            actualPrice.setId(R.id.tv_actual_price + idOffset * (10000 + 1));

                            TextView buyerCandidate = inflatedOffer.findViewById(R.id.tv_candidate_buyer);
                            buyerCandidate.setId(R.id.tv_candidate_buyer + idOffset * (1000000 + 1));

                            if (interested.length > 0) {
                                actualPrice.setText(getMaxPriceAndUser(interested).get("max") + getString(R.string.currency) + " es la oferta mas alta");
                                buyerCandidate.setText(getMaxPriceAndUser(interested).get("user") + " ha hecho la oferta mas alta");
                            } else {
                                actualPrice.setText("Nadie ha ofertado aun");
                            }
                        }
                    });

                    ConstraintLayout btnAccept = inflatedOffer.findViewById(R.id.cl_btn_bid);
                    btnAccept.setId(R.id.cl_btn_bid + idOffset * (10000000 + 1));

                    TextView lblAccept = inflatedOffer.findViewById(R.id.tv_btn_bid_label);
                if(!valid.contains(offer)) {
                    btnAccept.setBackgroundResource(R.drawable.border_rounded_background_error);
                    lblAccept.setText("Esta oferta no esta más disponible.");
                }else {
                    lblAccept.setText("Aceptar precio");
                    btnAccept.setBackgroundResource(R.drawable.border_rounded_background);
                }

                    linearLayout.addView(inflatedOffer);
                    idOffset++;

            }
    }

    public ArrayList<Offer> getValidOffers(Offer[] offersToFilter)
    {
        ArrayList<Offer> valid = new ArrayList();
        for(final Offer offer : offersToFilter)
        {
            if(offer.isValid()){
                valid.add(offer);
            }
        }
        return valid;
    }

    private Map<String, String> getMaxPriceAndUser(Interested[] interested){
        Map<String, String> ret = new HashMap<String, String>();
        int max=0;
        String user="";
        for(Interested i : interested)
        {
            if(i.getPrice()>max){
                max= i.getPrice();
                user = i.getUsername();
            }
        }
        ret.put("maxPrice", String.valueOf(max));
        ret.put("maxUser", user);
        return ret;
    }
}

