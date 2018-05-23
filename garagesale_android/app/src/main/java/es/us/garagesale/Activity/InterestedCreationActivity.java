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
import android.widget.Toast;

import java.util.ArrayList;
import es.us.garagesale.DataAccess.DatabaseManager;
import es.us.garagesale.DataAccess.ISuccessConsumer;
import es.us.garagesale.R;
import es.us.garagesale.Src.Interested;


/**
 * Created by mariaventura on 28/4/18.
 */

public class InterestedCreationActivity extends Activity
{
    public class ResultCode
    {
        public static final int BID_COMPLETED = 1;
        public static final int BID_ABORTED = 2;
    }

    TextView title;
    EditText offeredPrice;
    ConstraintLayout cancelBtn, submitBtn, priceLayout;
    int selectedOfferId;
    String actualUser, offerName;


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
        actualUser = sharedPreferences.getString("username", null);

        selectedOfferId = getIntent().getIntExtra("id", 0);
        offerName = getIntent().getStringExtra("name");
        title.setText("Cuánto deseas ofertar por "+offerName+ "?");

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(ResultCode.BID_ABORTED);
                finish();
            }
        });



       submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<View> invalidUserInputViews = validateFreeUserInputs();

                if (invalidUserInputViews.size() == 0) {
                    Interested newInterested =  new Interested();
                    newInterested.setPrice(Float.parseFloat(offeredPrice.getText().toString()));
                    newInterested.setUsername(actualUser);
                    newInterested.setOfferId(selectedOfferId);
                    DatabaseManager.saveInterested(newInterested, InterestedCreationActivity.this, new ISuccessConsumer() {
                        @Override
                        public void consume(boolean wasSuccessful) {
                            onInterestedSaveResponse(wasSuccessful);
                        }
                    });
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

    private ArrayList<View> validateFreeUserInputs()
    {
        ArrayList<View> invalidValueContainers = new ArrayList<>();

        if((offeredPrice.getText().toString().trim().length()==0))
        {
            invalidValueContainers.add(priceLayout);
        }
        return invalidValueContainers;
    }

    private void onInterestedSaveResponse(boolean wasSuccessful)
    {
        if(wasSuccessful)
        {
            setResult(ResultCode.BID_COMPLETED);
            finish();
        }
        else
        {
            Toast.makeText(getBaseContext(), getString(R.string.connection_problem), Toast.LENGTH_LONG).show();
        }
    }
}
