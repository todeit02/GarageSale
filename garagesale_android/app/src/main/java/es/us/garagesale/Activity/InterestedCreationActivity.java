package es.us.garagesale.Activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import es.us.garagesale.DataAccess.DatabaseManager;
import es.us.garagesale.R;
import es.us.garagesale.Src.Interested;


/**
 * Created by mariaventura on 28/4/18.
 */

public class InterestedCreationActivity extends Activity {

    TextView title;
    EditText offeredPrice;
    ConstraintLayout cancelBtn, submitBtn, priceLayout;
    int selectedOfferId;
    String usr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_interested);

        cancelBtn = findViewById(R.id.cl_btn_back);
        submitBtn = findViewById(R.id.cl_btn_publish);
        title = findViewById(R.id.tv_offer_item);
        offeredPrice = findViewById(R.id.et_price);
        priceLayout = findViewById(R.id.cl_offeredPrice);

        SharedPreferences sharedPreferences = this.getSharedPreferences("login", MODE_PRIVATE);
        usr=   sharedPreferences.getString("username", null);

        selectedOfferId = getIntent().getIntExtra("id", 0);

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backToOfferDetailActivity = new Intent(getApplicationContext(), OfferDetailActivity.class);
                backToOfferDetailActivity.putExtra("id", selectedOfferId);
                startActivity(backToOfferDetailActivity);
            }
        });



       submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<View> invalidUserInputViews = validateFreeUserInputs();

                if (invalidUserInputViews.size() == 0) {
                    Interested newInterested =  new Interested();
                    newInterested.setPrice(Integer.parseInt(offeredPrice.getText().toString()));
                    newInterested.setUsername(usr);
                    newInterested.setOfferId(selectedOfferId);
                    DatabaseManager.saveInterested(newInterested, InterestedCreationActivity.this);
                } else {
                    highlightInvalidInputViews(invalidUserInputViews);
                }
            }
       });

    }

    private void highlightInvalidInputViews(ArrayList<View> invalidViews)
    {
        for(View invalidHighlightingView : invalidViews)
        {
            Drawable invalidInputFrame = getResources().getDrawable(R.drawable.invalid_input_view_frame);
            invalidHighlightingView.setBackgroundDrawable(invalidInputFrame);
        }
    }

    private ArrayList<View> validateFreeUserInputs(){
        ArrayList<View> invalidValueContainers = new ArrayList<>();

        if((offeredPrice.getText().toString().trim().length()==0))
        {
            invalidValueContainers.add(priceLayout);
        }
        return invalidValueContainers;
    }


}
