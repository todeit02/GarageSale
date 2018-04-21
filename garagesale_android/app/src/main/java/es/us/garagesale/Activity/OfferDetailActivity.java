package es.us.garagesale.Activity;

import android.app.Activity;

import android.os.Bundle;
import android.widget.TextView;

import es.us.garagesale.DataAccess.DatabaseManager;
import es.us.garagesale.DataAccess.IOfferConsumer;
import es.us.garagesale.R;
import es.us.garagesale.Src.Offer;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class OfferDetailActivity extends Activity {

    private int selectedOfferId;
    TextView title;
    TextView detail;
    TextView state;
    TextView remainingTime;
    private Offer offer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_details);

        title = findViewById(R.id.tvOfferDetailsOfferUsedState);
        detail = findViewById(R.id.tv_offer_details_description);
        state =  findViewById(R.id.tvOfferDetailsOfferUsedState);
        remainingTime = findViewById(R.id.tv_offer_details_remaining_time);

        selectedOfferId = getIntent().getIntExtra("id", 0);

        DatabaseManager.loadOffer(selectedOfferId, this, new IOfferConsumer() {
            @Override
            public void consume(Offer receivedOffer) {
                displayOffer(receivedOffer);
            }
        });
    }


    private void displayOffer(Offer received){
        title.setText(received.getName());
        detail.setText(received.getDescription());
       // calculateRemainingTime(received); ACA SE ROMPE POR EL TIMESTAMP
    }

    private void calculateRemainingTime(Offer received){
        String str = received.getTimestamp().toString();
        String[] splitStr = str.trim().split("\\s+");
        String date = splitStr[0];
        String aux[] = splitStr[1].split(":");
        String hour = aux[0];


        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        System.out.println(dateFormat.format(cal));

        //ACA TENGO QUE HACER EL CALCULO DE HORAS RESTANTES

    }

}


