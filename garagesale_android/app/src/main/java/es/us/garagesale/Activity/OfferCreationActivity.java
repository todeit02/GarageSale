package es.us.garagesale.Activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import es.us.garagesale.DataAccess.DatabaseManager;
import es.us.garagesale.R;
import es.us.garagesale.Src.ButtonGroupAppearanceManager;
import es.us.garagesale.Src.Offer;
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
    private EditText priceEdit = null;
    private Button shortDurationButton = null;
    private Button mediumDurationButton = null;
    private Button longDurationButton = null;
    private ArrayList<Button> durationButtons = new ArrayList<>();
    private ConstraintLayout publishButton = null;

    private Offer workingOffer = new Offer();


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_offer);

        Intent leadingIntent = getIntent();
        String[] offerTags = leadingIntent.getStringArrayExtra("selected_filter_tags");

        findViewReferences();

        //prepareEditTextLimits();
        prepareConditionButtons();
        //fillFilterTags(offerTags);
        prepareDurationButtons();
        preparePublishButton();
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
        priceEdit = findViewById(R.id.et_price);

        shortDurationButton = findViewById(R.id.btn_3_days);
        mediumDurationButton = findViewById(R.id.btn_7_days);
        longDurationButton = findViewById(R.id.btn_14_days);
        durationButtons.add(shortDurationButton);
        durationButtons.add(mediumDurationButton);
        durationButtons.add(longDurationButton);

        publishButton = findViewById(R.id.cl_btn_publish);
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


    private void prepareConditionButtons()
    {
        final ButtonGroupAppearanceManager conditionButtonsAppearanceManager = new ButtonGroupAppearanceManager(conditionButtons, this);
        View.OnClickListener conditionClickListener = new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                conditionButtonsAppearanceManager.onClick(v);

                CharSequence selectedConditionText = ((Button) v).getText();
                Offer.Condition selectedCondition = getConditionFromCharSequence(selectedConditionText);

                workingOffer.setCondition(selectedCondition);
            }
        };

        for(Button listenerSettingButton : conditionButtons)
        {
            listenerSettingButton.setOnClickListener(conditionClickListener);
        }
        // need to preselect value
        conditionClickListener.onClick(conditionButtons.get(0));
    }


    private void fillFilterTags(String[] preselectedTags)
    {
        StringBuilder tagsChainBuilder = new StringBuilder();

        for(String appendingTag : preselectedTags)
        {
            if(tagsChainBuilder.length() > 0) tagsChainBuilder.append(" ");
            tagsChainBuilder.append(appendingTag);
        }

        tagsEdit.setText(tagsChainBuilder.toString());
    }


    private void prepareDurationButtons()
    {
        shortDurationButton.setText(getString(R.string.offer_duration, Offer.durationsDays.get(Offer.Duration.SHORT)));
        mediumDurationButton.setText(getString(R.string.offer_duration, Offer.durationsDays.get(Offer.Duration.MEDIUM)));
        longDurationButton.setText(getString(R.string.offer_duration, Offer.durationsDays.get(Offer.Duration.LONG)));

        final ButtonGroupAppearanceManager durationButtonsAppearanceManager = new ButtonGroupAppearanceManager(durationButtons, this);
        View.OnClickListener durationClickListener = new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                durationButtonsAppearanceManager.onClick(v);

                CharSequence selectedDurationText = ((Button) v).getText();
                Offer.Duration selectedDuration = getDurationFromCharSequence(selectedDurationText);
                int durationDays = Offer.durationsDays.get(selectedDuration);

                workingOffer.setDurationDays(durationDays);
            }
        };

        for(Button listenerSettingButton : durationButtons)
        {
            listenerSettingButton.setOnClickListener(durationClickListener);
        }
        // need to preselect value
        durationClickListener.onClick(shortDurationButton);
    }


    private void preparePublishButton()
    {
        publishButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) { tryPublishOffer(); }
        });
    }


    private void tryPublishOffer()
    {
        ArrayList<View> invalidUserInputViews = validateFreeUserInputs();

        if(invalidUserInputViews.size() == 0)
        {
            DatabaseManager.createOffer(workingOffer);
            finish();
        }
        else
        {
            highlightInvalidInputViews(invalidUserInputViews);
        }
    }


    private ArrayList<View> validateFreeUserInputs()
    {
        ArrayList<View> invalidValueContainers = new ArrayList<>();

        if(titleEdit.getText().length() < getResources().getInteger(R.integer.min_offer_title_length))
        {
            invalidValueContainers.add(titleEdit);
        }
        if(tagsEdit.getText().length() < getResources().getInteger(R.integer.min_offer_tags_length))
        {
            invalidValueContainers.add(tagsEdit);
        }
        if(descriptionEdit.getText().length() < getResources().getInteger(R.integer.min_offer_description_length))
        {
            invalidValueContainers.add(descriptionEdit);
        }
        if(priceEdit.getText().length() < getResources().getInteger(R.integer.min_offer_price_length))
        {
            invalidValueContainers.add(priceEdit);
        }

        return invalidValueContainers;
    }


    private void highlightInvalidInputViews(ArrayList<View> invalidViews)
    {
        for(View invalidHighlightingView : invalidViews)
        {
            Drawable invalidInputFrame = getResources().getDrawable(R.drawable.invalid_input_view_frame);
            invalidHighlightingView.setBackgroundDrawable(invalidInputFrame);
        }
    }


    private Offer.Duration getDurationFromCharSequence(CharSequence productDurationText)
    {
        if(productDurationText.equals( getString(R.string.offer_duration, Offer.durationsDays.get(Offer.Duration.SHORT)) )) return Offer.Duration.SHORT;
        if(productDurationText.equals( getString(R.string.offer_duration, Offer.durationsDays.get(Offer.Duration.MEDIUM)) )) return Offer.Duration.MEDIUM;
        if(productDurationText.equals( getString(R.string.offer_duration, Offer.durationsDays.get(Offer.Duration.LONG)) )) return Offer.Duration.LONG;
        return Offer.Duration.INVALID;
    }


    private CharSequence getCharSequenceFromCondition(Offer.Condition productCondition)
    {
        switch(productCondition)
        {
            case NEW:           return getResources().getText(R.string.offer_condition_new);
            case NEARLY_NEW:    return getResources().getText(R.string.offer_condition_nearly_new);
            case USED:          return getResources().getText(R.string.offer_condition_used);
            case DEFECTIVE:     return getResources().getText(R.string.offer_condition_defective);
            default:            return "";
        }
    }


    private Offer.Condition getConditionFromCharSequence(CharSequence productConditionText)
    {
        if(productConditionText.equals( getResources().getText(R.string.offer_condition_new) )) return Offer.Condition.NEW;
        if(productConditionText.equals( getResources().getText(R.string.offer_condition_nearly_new) )) return Offer.Condition.NEARLY_NEW;
        if(productConditionText.equals( getResources().getText(R.string.offer_condition_used) )) return Offer.Condition.USED;
        if(productConditionText.equals( getResources().getText(R.string.offer_condition_defective) )) return Offer.Condition.DEFECTIVE;
        return Offer.Condition.INVALID;
    }
}
