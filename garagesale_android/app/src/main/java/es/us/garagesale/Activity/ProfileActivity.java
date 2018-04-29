package es.us.garagesale.Activity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import es.us.garagesale.R;

/**
 * Created by mariaventura on 29/4/18.
 */

public class ProfileActivity extends Activity {

    ImageButton sales, purchases, messages, personalArea;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_area);

        sales = findViewById(R.id.imgbtn_sales);
        purchases = findViewById(R.id.imgbtn_purchases);
        messages = findViewById(R.id.imgbtn_messages);
        personalArea = findViewById(R.id.imgbtn_personal);
       // personalArea.setBackgroundResource(R.drawable.button_group_middle_selected_background);

        sales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setBackgroundResource(R.drawable.button_group_left_selected_background);
                unSet(1);
            }
        });

        purchases.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setBackgroundResource(R.drawable.button_group_middle_selected_background);
                unSet(2);
            }
        });

        personalArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setBackgroundResource(R.drawable.button_group_middle_selected_background);
                unSet(3);
            }
        });

        messages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setBackgroundResource(R.drawable.button_group_right_selected_background);
                unSet(4);
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

}