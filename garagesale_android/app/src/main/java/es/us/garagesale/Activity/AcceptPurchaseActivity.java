package es.us.garagesale.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import es.us.garagesale.DataAccess.DatabaseManager;
import es.us.garagesale.DataAccess.IInterestedConsumer;
import es.us.garagesale.DataAccess.IOfferConsumer;
import es.us.garagesale.R;
import es.us.garagesale.Src.Interested;
import es.us.garagesale.Src.Offer;

/**
 * Created by mariaventura on 2/5/18.
 */

public class AcceptPurchaseActivity extends Activity {

    private int selectedOfferId;
    private Offer toPurchase;
    private String maxUsername;
    private int maxPrice;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept_purchase);

        ConstraintLayout btnAccept = findViewById(R.id.cl_btn_accept);

        selectedOfferId = getIntent().getIntExtra("id", 0);

        DatabaseManager.loadInterested(selectedOfferId, this, new IInterestedConsumer() {
            @Override
            public void consume(Interested[] interested) {
                loadListView(interested);

                TextView confirmationMssg = findViewById(R.id.tv_confirmation_message);
                TextView actualPrice = findViewById(R.id.tv_price);
                maxPrice = Integer.valueOf(getMaxPriceAndUser(interested).get("maxPrice"));
                actualPrice.setText(maxPrice + getString(R.string.currency));
                maxUsername = getMaxPriceAndUser(interested).get("maxUser");
                confirmationMssg.setText("La oferta mas alta ha sido la de "+ maxUsername);

            }
        });


        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertPurchase();
                sendMessageToBuyer(selectedOfferId); //DO STH
                changeOfferToSold(selectedOfferId);

                final AlertDialog alertDialog = new AlertDialog.Builder(AcceptPurchaseActivity.this).create();
                alertDialog.setTitle("Venta exitosa");
                alertDialog.setMessage("El comprador sera notificado.");
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

                TimerTask task = new TimerTask() {

                    @Override
                    public void run() {
                        Intent profileActivity = new Intent(getApplicationContext(), ProfileActivity.class);
                        startActivity(profileActivity);
                        finishscreen();
                    }
                };
                Timer t = new Timer();
                t.schedule(task, 3000);
            }
        });

    }

    private void finishscreen() {
        this.finish();
    }

    private void changeOfferToSold(int id){
        DatabaseManager.editOffer(id, AcceptPurchaseActivity.this);
    }

    private void insertPurchase(){
        DatabaseManager.insertPurchase(selectedOfferId, maxPrice, maxUsername, AcceptPurchaseActivity.this);
    }

    private void sendMessageToBuyer(int offerId){

    }

    private void loadListView(Interested[] interested){
        ArrayList<String> usernames = new ArrayList<>();
        ArrayList<String> prices = new ArrayList<>();
        for(Interested i : interested)
        {
            usernames.add(i.getUsername());
            prices.add(String.valueOf(i.getPrice())+getString(R.string.currency));
        }

        ArrayAdapter arrayAdapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1, usernames);
        ListView myList = (ListView) findViewById(R.id.list_view_interested);
        myList.setAdapter(arrayAdapter);

        ArrayAdapter arrayAdapter2 = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1, prices);
        ListView myList2 = (ListView) findViewById(R.id.list_view_prices);
        myList2.setAdapter(arrayAdapter2);

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
