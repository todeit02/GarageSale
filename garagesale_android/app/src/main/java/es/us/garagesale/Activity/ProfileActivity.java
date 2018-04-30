package es.us.garagesale.Activity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import es.us.garagesale.DataAccess.DatabaseManager;
import es.us.garagesale.DataAccess.IOfferConsumer;
import es.us.garagesale.DataAccess.IPersonConsumer;
import es.us.garagesale.R;
import es.us.garagesale.Src.Offer;
import es.us.garagesale.Src.Person;

/**
 * Created by mariaventura on 29/4/18.
 */

public class ProfileActivity extends Activity {

    ImageButton sales, purchases, messages, personalArea;
    TextView name, username, birthDate, email, reputation, nationality, card;
    String actualUser;
    Person fromDB;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_area);

        getButtons();
        getTextViews();

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
                Intent viewSalesActivity = new Intent(getApplicationContext(), SalesActivity.class);
                startActivity(viewSalesActivity);
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
                displayPersonalInfo(fromDB);
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

    public void displayPersonalInfo(Person actualPerson){
        name.setText(actualPerson.getName());
        username.setText(actualPerson.getUsername());
        birthDate.setText(actualPerson.getBirthDate());
        email.setText(actualPerson.getEmail());
        reputation.setText(String.valueOf(actualPerson.getReputation()));
        nationality.setText(actualPerson.getNationality());
        card.setText(actualPerson.getPersonalCard().toString());
    }

    public void getButtons(){
        sales = findViewById(R.id.imgbtn_sales);
        purchases = findViewById(R.id.imgbtn_purchases);
        messages = findViewById(R.id.imgbtn_messages);
        personalArea = findViewById(R.id.imgbtn_personal);
    }

    public void getTextViews(){
        name = findViewById(R.id.tv_nameFill);
        username = findViewById(R.id.tv_usernameFill);
        birthDate = findViewById(R.id.tv_birthDateFill);
        email = findViewById(R.id.tv_emailFill);
        reputation = findViewById(R.id.tv_reputationFill);
        nationality = findViewById(R.id.tv_nameFill);
        card= findViewById(R.id.tv_cardFill);
    }

}