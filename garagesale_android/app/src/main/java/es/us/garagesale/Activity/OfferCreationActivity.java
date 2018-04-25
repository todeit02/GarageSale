package es.us.garagesale.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

import es.us.garagesale.R;
import es.us.garagesale.Src.TextLengthLimiter;

/**
 * Created by Tobias on 18/04/2018.
 */

public class OfferCreationActivity extends Activity
{
    private EditText titleEdit = null;
    private ArrayList<Button> conditionButtons = new ArrayList<>();
    private EditText tagsEdit = null;
    private EditText descriptionEdit = null;
    private View addPhotoButton = null;
    private View addLocationButton = null;
    private Button shortDurationButton = null;
    private Button mediumDurationButton = null;
    private Button longDurationButton = null;
    private ArrayList<Button> durationButtons = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_offer);

        Intent leadingIntent = getIntent();
        String[] offerTags = leadingIntent.getStringArrayExtra("selected_filter_tags");

        findViewReferences();
        //prepareEditTextLimits();

        ButtonGroupAppearanceManager conditionButtonsManager = new ButtonGroupAppearanceManager(conditionButtons);
        ButtonGroupAppearanceManager durationButtonsManager = new ButtonGroupAppearanceManager(durationButtons);

        for(Button listenerSettingButton : conditionButtons) listenerSettingButton.setOnClickListener(conditionButtonsManager);
        for(Button listenerSettingButton : durationButtons) listenerSettingButton.setOnClickListener(durationButtonsManager);
    }

    private void findViewReferences()
    {
        titleEdit = findViewById(R.id.et_title);

        ViewGroup conditionSelectors = findViewById(R.id.ll_condition_selection);
        for(int i = 0; i < conditionSelectors.getChildCount(); i++)
        {
            conditionButtons.add( (Button)conditionSelectors.getChildAt(i) );
        }

        tagsEdit = findViewById(R.id.et_tags);
        descriptionEdit = findViewById(R.id.et_description);
        addPhotoButton = findViewById(R.id.cl_btn_add_photo);
        addLocationButton = findViewById(R.id.cl_btn_add_location);
        shortDurationButton = findViewById(R.id.btn_3_days);
        mediumDurationButton = findViewById(R.id.btn_7_days);
        longDurationButton = findViewById(R.id.btn_14_days);

        durationButtons.add(shortDurationButton);
        durationButtons.add(mediumDurationButton);
        durationButtons.add(longDurationButton);
    }

    private void prepareEditTextLimits()
    {
        int maxTitleCharacters = getResources().getInteger(R.integer.max_offer_title_length);
        int maxTagsCharacters = getResources().getInteger(R.integer.max_offer_tags_length);
        int maxDescriptionCharacters = getResources().getInteger(R.integer.max_offer_description_length);

        titleEdit.addTextChangedListener(new TextLengthLimiter(titleEdit, maxTitleCharacters, this));
        tagsEdit.addTextChangedListener(new TextLengthLimiter(tagsEdit, maxTagsCharacters, this));
        descriptionEdit.addTextChangedListener(new TextLengthLimiter(descriptionEdit, maxDescriptionCharacters, this));
    }

    private class ButtonGroupAppearanceManager implements View.OnClickListener
    {
        private ArrayList<Button> groupButtons = null;

        @Override
        public void onClick(View v) {
            for(Button appearanceChangingButton : groupButtons)
            {
                if(appearanceChangingButton == v)
                {
                    appearanceChangingButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_group_middle_selected_background));
                }
                else
                {
                    appearanceChangingButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_group_middle_unselected_background));
                }
            }
        }

        public ButtonGroupAppearanceManager(ArrayList<Button> groupButtons)
        {
            this.groupButtons = groupButtons;
        }
    }
}
