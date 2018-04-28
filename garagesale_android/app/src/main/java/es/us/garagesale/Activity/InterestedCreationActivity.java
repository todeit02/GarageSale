package es.us.garagesale.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.TextView;

import es.us.garagesale.R;


/**
 * Created by mariaventura on 28/4/18.
 */

public class InterestedCreationActivity extends Activity {

    TextView offeredPrice;
    ConstraintLayout cancelBtn, submitBtn;
    int selectedOfferId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_interested);

        cancelBtn = findViewById(R.id.cl_btn_back);
        submitBtn = findViewById(R.id.cl_btn_publish);
        offeredPrice = findViewById(R.id.tv_offer_item);

        selectedOfferId = getIntent().getIntExtra("id", 0);

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backToOfferDetailActivity = new Intent(getApplicationContext(), OfferDetailActivity.class);
                backToOfferDetailActivity.putExtra("id", selectedOfferId);
                startActivity(backToOfferDetailActivity);
            }
        });

       /* submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backToOfferDetailActivity = new Intent(getApplicationContext(), OfferDetailActivity.class);
                backToOfferDetailActivity.putExtra("id", selectedOfferId);
                startActivity(backToOfferDetailActivity);
            }
        });
  */

    }


}
