package es.us.garagesale.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import es.us.garagesale.DataAccess.DatabaseManager;
import es.us.garagesale.DataAccess.IInterestedConsumer;
import es.us.garagesale.DataAccess.IPersonConsumer;
import es.us.garagesale.DataAccess.IUsernameOffersConsumer;
import es.us.garagesale.R;
import es.us.garagesale.Src.Interested;
import es.us.garagesale.Src.Offer;
import es.us.garagesale.Src.Person;

/**
 * Created by mariaventura on 29/4/18.
 */

public class ProfileActivity extends Activity {

    ImageButton sales, purchases, messages, personalArea;
    TextView primaryTitle;
    String actualUser;
    Person fromDB;
    int length;
    int idOffset;
    private LinearLayout linearLayout = null;
    private LayoutInflater linearLayoutInflater = null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_area);

        linearLayout = (LinearLayout)findViewById(R.id.ll_content_scroll_customizable);
        linearLayoutInflater = LayoutInflater.from(this);
        primaryTitle = findViewById(R.id.tv_title);

        getButtons();

        personalArea.setBackgroundResource(R.drawable.button_group_middle_selected_background);

        SharedPreferences sharedPreferences = this.getSharedPreferences("login", MODE_PRIVATE);
        actualUser = sharedPreferences.getString("username", null);

        DatabaseManager.loadPersonInfo(actualUser, this, new IPersonConsumer() {
            @Override
            public void consume(Person actualPerson) {
                fromDB = actualPerson;
                displayPersonalInfo(actualPerson);
            }
        });

        sales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setBackgroundResource(R.drawable.button_group_left_selected_background);
                unSet(1);
                linearLayout.removeAllViews();
                DatabaseManager.loadUsernameOffers(actualUser, new ProfileActivity(), new IUsernameOffersConsumer() {
                    @Override
                    public void consume(Offer[] offers) {
                        getUsernameOffersInterested(offers);
                    }
                });
            }
        });

        purchases.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setBackgroundResource(R.drawable.button_group_middle_selected_background);
                unSet(2);
                linearLayout.removeAllViews();
            }
        });

        personalArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setBackgroundResource(R.drawable.button_group_middle_selected_background);
                unSet(3);
                linearLayout.removeAllViews();
                displayPersonalInfo(fromDB);
            }
        });

        messages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setBackgroundResource(R.drawable.button_group_right_selected_background);
                unSet(4);
                linearLayout.removeAllViews();
            }
        });
    }

    public void unSet(int selectedButton){
        switch (selectedButton){
            case 1:
                messages.setBackgroundResource(R.drawable.button_group_right_unselected_background);
                purchases.setBackgroundResource(R.drawable.button_group_middle_unselected_background);
                personalArea.setBackgroundResource(R.drawable.button_group_middle_unselected_background);
                break;
            case 2:
                sales.setBackgroundResource(R.drawable.button_group_left_unselected_background);
                messages.setBackgroundResource(R.drawable.button_group_right_unselected_background);
                personalArea.setBackgroundResource(R.drawable.button_group_middle_unselected_background);
                break;
            case 3:
                sales.setBackgroundResource(R.drawable.button_group_left_unselected_background);
                messages.setBackgroundResource(R.drawable.button_group_right_unselected_background);
                purchases.setBackgroundResource(R.drawable.button_group_middle_unselected_background);
                break;
            case 4:
                purchases.setBackgroundResource(R.drawable.button_group_middle_unselected_background);
                sales.setBackgroundResource(R.drawable.button_group_left_unselected_background);
                personalArea.setBackgroundResource(R.drawable.button_group_middle_unselected_background);
                break;
        }
    }

    public void displayPersonalInfo(Person actualPerson){

        primaryTitle.setText("Información personal:");
        primaryTitle.setPaintFlags(primaryTitle.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);


        final View inflatedInfo = linearLayoutInflater.inflate(R.layout.personal_info_content, null);
        inflatedInfo.setId(R.layout.personal_info_content + idOffset);

        TextView name = inflatedInfo.findViewById(R.id.tv_nameFill);
        name.setText(actualPerson.getRealName());

        TextView username = inflatedInfo.findViewById(R.id.tv_usernameFill);
        username.setText(actualPerson.getUsername());

        TextView birthDate = inflatedInfo.findViewById(R.id.tv_birthDateFill);
        birthDate.setText(actualPerson.getBirthDate("dd/MM/yyyy"));

        TextView email = inflatedInfo.findViewById(R.id.tv_emailFill);
        email.setText(actualPerson.getEmail());

        TextView reputation = inflatedInfo.findViewById(R.id.tv_reputationFill);
        reputation.setText(String.valueOf(actualPerson.getReputation()));

        TextView nationality = inflatedInfo.findViewById(R.id.tv_nationalityFill);
        nationality.setText(actualPerson.getNationality());

        TextView card= inflatedInfo.findViewById(R.id.tv_cardFill);
        card.setText(actualPerson.getPersonalCard().getCardNum());

        linearLayout.addView(inflatedInfo);
    }

    public void getButtons(){
        sales = findViewById(R.id.imgbtn_sales);
        purchases = findViewById(R.id.imgbtn_purchases);
        messages = findViewById(R.id.imgbtn_messages);
        personalArea = findViewById(R.id.imgbtn_personal);
    }

    private void getUsernameOffersInterested(Offer[] offers){

        ArrayList<Offer> valid = getValidOffers(offers);
        length = 0;
        idOffset = 1;

        primaryTitle.setText("Mis ventas:");
        primaryTitle.setPaintFlags(primaryTitle.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);

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
            remainingTime.setText("Quedan "+ offer.calculateRemainingTime()+" horas");

            DatabaseManager.loadInterested(offer.getId(), this, new IInterestedConsumer() {
                @Override
                public void consume(Interested[] interested) {
                    TextView actualPrice = inflatedOffer.findViewById(R.id.tv_actual_price);
                    actualPrice.setId(R.id.tv_actual_price + idOffset * (10000 + 1));

                    TextView buyerCandidate = inflatedOffer.findViewById(R.id.tv_candidate_buyer);
                    buyerCandidate.setId(R.id.tv_candidate_buyer + idOffset * (1000000 + 1));

                    if (interested.length > 0) {
                        actualPrice.setText(getMaxPriceAndUser(interested).get("maxPrice") + getString(R.string.currency) + " es la oferta mas alta");
                        buyerCandidate.setText(getMaxPriceAndUser(interested).get("maxUser") + " ha hecho la oferta mas alta");
                    } else {
                        actualPrice.setText("Nadie ha ofertado aun");
                    }
                }
            });


            ConstraintLayout btnAccept = inflatedOffer.findViewById(R.id.cl_btn_bid);
            btnAccept.setId(R.id.cl_btn_bid + idOffset * (10000000 + 1));

            TextView lblAccept = inflatedOffer.findViewById(R.id.tv_btn_bid_label);
            ImageView img= inflatedOffer.findViewById(R.id.imgv_btn_title_offer);
            if(!valid.contains(offer)) {// O SI NADIE HA OFERTADO!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                btnAccept.setBackgroundResource(R.drawable.border_rounded_background_error);
                img.setImageResource(R.mipmap.cancel);
                lblAccept.setText("Esta oferta no esta más disponible.");
            }else {
                lblAccept.setText("Aceptar precio");
                btnAccept.setBackgroundResource(R.drawable.border_rounded_background);
                img.setImageResource(R.mipmap.success);
                btnAccept.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        confirmDialog(offer.getId());
                    }
                });
            }

            linearLayout.addView(inflatedOffer);
            idOffset++;

        }
    }

    private void confirmDialog(final int offerId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder
                .setMessage("Estas seguro que quieres aceptar este precio?")
                .setPositiveButton("Si",  new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Intent acceptPurchaseActivity = new Intent(getApplicationContext(), AcceptPurchaseActivity.class);
                        Bundle extras = new Bundle();
                        extras.putInt("id",offerId);
                        acceptPurchaseActivity.putExtras(extras);
                        startActivity(acceptPurchaseActivity);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,int id) {
                        dialog.cancel();
                    }
                })
                .show();
    }


    private ArrayList<Offer> getValidOffers(Offer[] offersToFilter)
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