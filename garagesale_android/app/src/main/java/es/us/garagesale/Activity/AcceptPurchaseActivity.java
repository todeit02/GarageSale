package es.us.garagesale.Activity;

import android.app.Activity;
import android.os.Bundle;

import es.us.garagesale.R;

/**
 * Created by mariaventura on 2/5/18.
 */

public class AcceptPurchaseActivity extends Activity {

    private int selectedOfferId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept_purchase);

        selectedOfferId = getIntent().getIntExtra("id", 0);
    }

}
