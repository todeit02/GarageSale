package es.us.garagesale.Src;

import android.app.Activity;
import android.widget.Button;

import java.util.ArrayList;

import es.us.garagesale.R;

public class DurationButtonGroup extends ButtonGroup
{
    private Button shortDurationButton;
    private Button mediumDurationButton;
    private Button longDurationButton;


    public DurationButtonGroup(Activity activity)
    {
        super(activity);
        setTexts();
    }


    public int getSelectedDurationDays()
    {
        CharSequence selectedDurationText = super.getSelectedButton().getText();
        Offer.Duration selectedDuration = OfferTool.getDurationFromCharSequence(selectedDurationText, super.getActivity());
        int selectedDurationDays = Offer.durationsDays.get(selectedDuration);
        return selectedDurationDays;
    }


    @Override
    protected void findViewReferences()
    {
        Activity activity = super.getActivity();
        shortDurationButton = activity.findViewById(R.id.btn_3_days);
        mediumDurationButton = activity.findViewById(R.id.btn_7_days);
        longDurationButton = activity.findViewById(R.id.btn_14_days);
    }


    @Override
    protected void addButtonsToList()
    {
        ArrayList<Button> buttonList = super.getButtons();
        buttonList.add(shortDurationButton);
        buttonList.add(mediumDurationButton);
        buttonList.add(longDurationButton);
    }


    private void setTexts()
    {
        Activity activity = super.getActivity();
        shortDurationButton.setText(activity.getString(R.string.offer_duration, Offer.durationsDays.get(Offer.Duration.SHORT)));
        mediumDurationButton.setText(activity.getString(R.string.offer_duration, Offer.durationsDays.get(Offer.Duration.MEDIUM)));
        longDurationButton.setText(activity.getString(R.string.offer_duration, Offer.durationsDays.get(Offer.Duration.LONG)));
    }
}
