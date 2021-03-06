package es.us.garagesale.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import es.us.garagesale.DataAccess.DatabaseManager;
import es.us.garagesale.DataAccess.IInterestedConsumer;
import es.us.garagesale.DataAccess.IOfferConsumer;
import es.us.garagesale.DataAccess.IPersonConsumer;
import es.us.garagesale.DataAccess.IRankingConsumer;
import es.us.garagesale.DataAccess.IUserRankingConsumer;
import es.us.garagesale.DataAccess.IUsernameOffersConsumer;
import es.us.garagesale.DataAccess.IUsernamePurchasesConsumer;
import es.us.garagesale.R;
import es.us.garagesale.Src.Interested;
import es.us.garagesale.Src.Offer;
import es.us.garagesale.Src.Person;
import es.us.garagesale.Src.Purchase;
import es.us.garagesale.Src.Ranking;

import static android.util.TypedValue.COMPLEX_UNIT_SP;

/**
 * Created by mariaventura on 29/4/18.
 */

public class ProfileActivity extends Activity{

    private static final int PLACE_PICKER_INTENT_REQUEST_CODE=2;

    ImageButton sales, purchases, messages, personalArea;
    TextView primaryTitle;
    String actualUser;
    String maxPrice;
    Person fromDB;
    int length;
    int idOffset;
    private LinearLayout linearLayout = null;
    private LayoutInflater linearLayoutInflater = null;
    private String maxUser;
    private float ranking;

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
                primaryTitle.setText("Mis ventas:");
                primaryTitle.setPaintFlags(primaryTitle.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
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
                primaryTitle.setText("Mis compras:");
                primaryTitle.setPaintFlags(primaryTitle.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                v.setBackgroundResource(R.drawable.button_group_middle_selected_background);
                unSet(2);
                linearLayout.removeAllViews();
                DatabaseManager.loadUsernamePurchases(actualUser, new ProfileActivity(), new IUsernamePurchasesConsumer() {
                    @Override
                    public void consume(Purchase[] purchases) {
                        displayUserPurchases(purchases);
                    }
                });
            }
        });

        personalArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                primaryTitle.setText("Información personal:");
                primaryTitle.setPaintFlags(primaryTitle.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
                v.setBackgroundResource(R.drawable.button_group_middle_selected_background);
                unSet(3);
                linearLayout.removeAllViews();
                displayPersonalInfo(fromDB);
            }
        });

        messages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                primaryTitle.setText("Mis mensajes");
                primaryTitle.setPaintFlags(primaryTitle.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);

                v.setBackgroundResource(R.drawable.button_group_right_selected_background);
                unSet(4);
                linearLayout.removeAllViews();
                DatabaseManager.loadUsernamePurchases(actualUser, new ProfileActivity(), new IUsernamePurchasesConsumer() {
                    @Override
                    public void consume(Purchase[] purchases) {
                        displayUserMessages(purchases);
                    }
                });
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

    private void displayUserPurchases(Purchase[] purchases) {
        primaryTitle.setText("Mis compras:");
        primaryTitle.setPaintFlags(primaryTitle.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        idOffset = 1;

        for (final Purchase purchase : purchases) {
            length++;

            final View inflatedOffer = linearLayoutInflater.inflate(R.layout.purchase_item_list, null);
            inflatedOffer.setId(R.layout.purchase_item_list + idOffset);

            TextView generalTitle = inflatedOffer.findViewById(R.id.tv_purchase_title);
            generalTitle.setText("Compra nro. " + purchase.getOfferId());


            DatabaseManager.loadOffer(purchase.getOfferId(), this, new IOfferConsumer() {
                @Override
                public void consume(final Offer receivedOffer) {
                    TextView showLocation = inflatedOffer.findViewById(R.id.tv_btn_show_map);
                    TextView locationTextView = inflatedOffer.findViewById(R.id.tv_offer_details_location);
                    if (receivedOffer.getCoordinates().latitude!=0.0&&receivedOffer.getCoordinates().longitude!=0.0){
                        showLocation.setOnClickListener(new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View v)
                            {
                                openGoogleMaps(receivedOffer);
                            }
                        });

                        CharSequence placeName = receivedOffer.getCityName();
                        LatLng coordinates = receivedOffer.getCoordinates();
                        CharSequence locationText = getString(R.string.offer_location, placeName.toString(), coordinates.toString());
                        locationTextView.setText(locationText);
                        locationTextView.setTextSize(COMPLEX_UNIT_SP, 12);
                    }else{
                        locationTextView.setVisibility(View.GONE);
                        ConstraintLayout cl= inflatedOffer.findViewById(R.id.cl_btn_show_map);
                        cl.setVisibility(View.GONE);
                    }

                    TextView title = inflatedOffer.findViewById(R.id.tv_title);
                    title.setId(R.id.tv_title + idOffset * (100 + 1));
                    title.setText(receivedOffer.getName());

                    TextView seller = inflatedOffer.findViewById(R.id.tv_seller);
                    seller.setId(R.id.tv_seller + idOffset * (100 + 1));
                    seller.setText("Vendedor: " +receivedOffer.getSellerUsername());

                    final Button submit = inflatedOffer.findViewById(R.id.btnSubmit);
                    final RatingBar ratingBar = inflatedOffer.findViewById(R.id.ratingBar);

                    final String sellerUsn = receivedOffer.getSellerUsername();


                    submit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ratingBar.setEnabled(false);
                            submit.setVisibility(View.GONE);
                            //Inserta el ranking del vendedor:
                            float rate = ratingBar.getRating();
                            DatabaseManager.insertRanking(sellerUsn, actualUser, rate, receivedOffer.getId(), ProfileActivity.this);

                        }
                    });

                    DatabaseManager.getRankingById(sellerUsn, actualUser, receivedOffer.getId(), ProfileActivity.this, new IRankingConsumer() {
                        @Override
                        public void consume(Ranking receivedRanking) {
                            ranking = receivedRanking.getValue();
                            ratingBar.setRating(ranking);
                            ratingBar.setEnabled(false);
                            submit.setVisibility(View.GONE);
                        }
                    });

                }
            });

            ConstraintLayout btnReport = inflatedOffer.findViewById(R.id.cl_btn_bid);
            btnReport.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //generar reporte
                    AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
                    builder.setTitle("Escribe tu reporte de defectos. Nos pondremos en contacto.");

                    // Set up the input
                    final EditText input = new EditText(ProfileActivity.this);
                    // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                    input.setInputType(InputType.TYPE_CLASS_TEXT);
                    builder.setView(input);

                    // Set up the buttons
                    builder.setPositiveButton("Enviar reporte", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String m_Text = input.getText().toString();
                            //se simula que le llega a alguien el reporte y hacen algo al respecto.
                        }
                    });
                    builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    builder.show();
                }
            });

            TextView purchaseTime = inflatedOffer.findViewById(R.id.tv_time);
            purchaseTime.setText("Fecha: " +purchase.getBuyTime());

            TextView actualPrice = inflatedOffer.findViewById(R.id.tv_actual_price);
            actualPrice.setId(R.id.tv_original_price + idOffset * (1000 + 1));
            actualPrice.setText(String.valueOf(purchase.getPrice()) + getString(R.string.currency));


            linearLayout.addView(inflatedOffer);
            idOffset++;

        }
    }

    private void displayUserMessages(Purchase[] purchases){
        primaryTitle.setText("Mis mensajes:");
        primaryTitle.setPaintFlags(primaryTitle.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        idOffset = 1;


        for (final Purchase purchase : purchases) {

            if(!purchase.isHasContactedSeller())
            {
                length++;

                final View inflatedOffer = linearLayoutInflater.inflate(R.layout.message_item_list, null);
                inflatedOffer.setId(R.layout.message_item_list + idOffset);

                TextView generalTitle = inflatedOffer.findViewById(R.id.tv_message_title);
                generalTitle.setText("Compra nro. " + purchase.getOfferId());

                final TextView title = inflatedOffer.findViewById(R.id.tv_title);
                title.setId(R.id.tv_title + idOffset * (100 + 1));

                final TextView seller = inflatedOffer.findViewById(R.id.tv_seller);
                seller.setId(R.id.tv_seller + idOffset * (100 + 1));

                final TextView phone = inflatedOffer.findViewById(R.id.tv_phone);
                phone.setId(R.id.tv_phone + idOffset * (100 + 1));

                DatabaseManager.loadOffer(purchase.getOfferId(), this, new IOfferConsumer() {
                    @Override
                    public void consume(final Offer receivedOffer) {
                        title.setText(receivedOffer.getName());
                        seller.setText("Vendedor: " +receivedOffer.getSellerUsername());
                        DatabaseManager.loadPersonInfo(receivedOffer.getSellerUsername(), ProfileActivity.this, new IPersonConsumer() {
                            @Override
                            public void consume(Person seller) {
                               phone.setText(seller.getPhone());
                            }
                        });
                    }
                });

                final RadioButton card = inflatedOffer.findViewById(R.id.radioButtonCard);
                card.setChecked(true);

                final RadioButton cash = inflatedOffer.findViewById(R.id.radioButtonCash);
                cash.setChecked(false);

                if(purchase.getPaymentMethod().equals("")){

                    card.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            card.setChecked(true);
                            cash.setChecked(false);

                        }
                    });

                    cash.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            cash.setChecked(true);
                            card.setChecked(false);

                        }
                    });
                }else{
                    if(purchase.getPaymentMethod().equals("card")){
                        cash.setVisibility(View.GONE);
                        card.setSelected(true);
                        card.setEnabled(false);
                        card.setText("Se ha cobrado el total en tu tarjeta");
                    }else{
                        card.setVisibility(View.GONE);
                        cash.setSelected(true);
                        cash.setEnabled(false);
                        cash.setText("Se te cobrara el importe en el momento");
                    }
                }



                ConstraintLayout call = inflatedOffer.findViewById(R.id.cl_btn_bid);
                call.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone.getText().toString(), null));
                        startActivity(intent);
                        String paymentMethod;
                        if(card.isChecked()){
                            paymentMethod = "card";
                            cash.setVisibility(View.GONE);
                            card.setSelected(true);
                            card.setEnabled(false);
                            card.setText("Se ha cobrado el total en tu tarjeta");
                        }else{
                            paymentMethod = "cash";
                            card.setVisibility(View.GONE);
                            cash.setEnabled(false);
                            cash.setSelected(true);
                            cash.setText("Se te cobrara el importe en el momento");
                        }
                        //UPDATE PURCHASE METHOD
                        DatabaseManager.editPurchase(purchase.getOfferId(), paymentMethod, 0, ProfileActivity.this);

                    }
                });

                Switch erase = inflatedOffer.findViewById(R.id.switchErase);
                erase.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked){
                            linearLayout.removeView(inflatedOffer);

                            //HACER UN UPDATE HAS CONTACTED
                        }
                    }
                });
                linearLayout.addView(inflatedOffer);
                idOffset++;

            }

        }
    }

    public void displayPersonalInfo(Person actualPerson){


        linearLayout.removeAllViews();

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

        final RatingBar reputation = inflatedInfo.findViewById(R.id.ratingBarProfile);
        reputation.setEnabled(false);
        DatabaseManager.getUserRanking(actualUser, ProfileActivity.this, new IUserRankingConsumer() {
            @Override
            public void consume(float receivedRanking) {
                float totalRanking = receivedRanking;
                reputation.setRating(totalRanking);

            }
        });

        TextView nationality = inflatedInfo.findViewById(R.id.tv_nationalityFill);
        nationality.setText(actualPerson.getNationality());

        TextView card= inflatedInfo.findViewById(R.id.tv_cardFill);
        card.setText(actualPerson.getCardId());

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

            LinearLayout report= inflatedOffer.findViewById(R.id.ll_report);
            title.setId(R.id.ll_report + idOffset * (100 + 1));
            report.setVisibility(View.GONE);

            TextView originalPrice = inflatedOffer.findViewById(R.id.tv_original_price);
            originalPrice.setId(R.id.tv_original_price + idOffset * (1000 + 1));
            originalPrice.setText("Precio original: "+String.valueOf(offer.getPrice())+getString(R.string.currency));

            TextView remainingTime = inflatedOffer.findViewById(R.id.tv_remaining_time);
            remainingTime.setId(R.id.tv_remaining_time + idOffset * (100000 + 1));
            remainingTime.setText("Quedan "+ offer.calculateRemainingTime()+" horas");

            final ConstraintLayout btnAccept = inflatedOffer.findViewById(R.id.cl_btn_bid);
            btnAccept.setId(R.id.cl_btn_bid + idOffset * (10000000 + 1));

          final TextView lblAccept = inflatedOffer.findViewById(R.id.tv_btn_bid_label);
           final ImageView img= inflatedOffer.findViewById(R.id.imgv_btn_title_offer);

            btnAccept.setBackgroundResource(R.drawable.border_rounded_background_error);
            img.setImageResource(R.mipmap.cancel);
            lblAccept.setText("Esta publicación no tiene ofertas.");


            DatabaseManager.loadInterested(offer.getId(), this, new IInterestedConsumer() {
                @Override
                public void consume(Interested[] interested) {
                    TextView actualPrice = inflatedOffer.findViewById(R.id.tv_actual_price);
                    actualPrice.setId(R.id.tv_actual_price + idOffset * (10000 + 1));

                    TextView buyerCandidate = inflatedOffer.findViewById(R.id.tv_candidate_buyer);
                    buyerCandidate.setId(R.id.tv_candidate_buyer + idOffset * (1000000 + 1));

                    maxPrice = getMaxPriceAndUser(interested).get("maxPrice");
                    actualPrice.setText(maxPrice + getString(R.string.currency) + " es la oferta mas alta");
                    maxUser = getMaxPriceAndUser(interested).get("maxUser");
                    buyerCandidate.setText(maxUser + " ha hecho la oferta mas alta");

                    if (interested.length > 0 && offer.getSold()==0) {

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
                }
            });


            ImageView delete = inflatedOffer.findViewById(R.id.imgv_btn_delete);
            delete.setImageResource(android.R.color.transparent);

            if(offer.getSold()==1){
                btnAccept.setBackgroundResource(R.drawable.border_rounded_background);
                img.setImageResource(R.mipmap.moneybag);
                lblAccept.setText("Has vendido este producto");
                remainingTime.setText("Quedan 0 horas");
                report.setVisibility(View.VISIBLE);
                report.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //generar reporte
                        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
                        builder.setTitle("Escribe tu reporte de defectos. Nos pondremos en contacto.");

                        final EditText input = new EditText(ProfileActivity.this);
                        input.setInputType(InputType.TYPE_CLASS_TEXT);
                        builder.setView(input);

                        builder.setPositiveButton("Enviar reporte", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String m_Text = input.getText().toString();
                                //se simula que le llega a alguien el reporte y hacen algo al respecto.
                            }
                        });
                        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                        builder.show();
                    }
                });
            }
            else if(!valid.contains(offer)) {
                btnAccept.setBackgroundResource(R.drawable.border_rounded_background_error);
                img.setImageResource(R.mipmap.cancel);
                lblAccept.setText("Esta oferta no esta más disponible.");
                delete.setImageResource(R.mipmap.delete);
                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DatabaseManager.deleteOffer(offer.getId(), ProfileActivity.this);
                        showDeleteMessage();

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

    public void showDeleteMessage(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder
                .setMessage("Estas seguro que quieres borrar este anuncio?")
                .setPositiveButton("Si",  new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        showResultMessage();
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

    private void showResultMessage(){
        final AlertDialog alertDialog = new AlertDialog.Builder(ProfileActivity.this).create();
        alertDialog.setTitle("Anuncio borrado");
        alertDialog.setMessage("Has borrado este anuncio");
        alertDialog.show();


        // Hide after some seconds
        final Handler handler  = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (alertDialog.isShowing()) {
                    alertDialog.dismiss();
                }
            }
        };

        alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                handler.removeCallbacks(runnable);
            }
        });

        handler.postDelayed(runnable, 2000);
        sales.performClick();

    }

    private ArrayList<Offer> getValidOffers(Offer[] offersToFilter)
    {
        ArrayList<Offer> valid = new ArrayList();
        for(final Offer offer : offersToFilter)
        {
            if(offer.isActive()){
                valid.add(offer);
            }
        }
        return valid;
    }

    private Map<String, String> getMaxPriceAndUser(Interested[] interested)
    {
        Map<String, String> ret = new HashMap<String, String>();
        float max = 0f;
        String user = "";

        for(Interested i : interested)
        {
            if(i.getPrice() > max)
            {
                max = i.getPrice();
                user = i.getUsername();
            }
        }
        ret.put("maxPrice", String.valueOf(max));
        ret.put("maxUser", user);
        return ret;
    }

private void openGoogleMaps(Offer receivedOffer){
    LatLng coordinates = receivedOffer.getCoordinates();
    double lat = coordinates.latitude;
    double lng = coordinates.longitude;
    String label = "Ven a buscar tu producto!";
    String uriBegin = "geo:" + lat + "," + lng;
    String query = lat + "," + lng + "(" + label + ")";
    String encodedQuery = Uri.encode(query);
    String uriString = uriBegin + "?q=" + encodedQuery + "&z=16";
    Uri uri = Uri.parse(uriString);
    Intent mapIntent = new Intent(android.content.Intent.ACTION_VIEW, uri);
    startActivity(mapIntent);
}



}