package es.us.garagesale.Src;

import android.app.Activity;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;

import es.us.garagesale.R;

public class ConditionButtonGroup extends ButtonGroup
{
    public ConditionButtonGroup(Activity activity)
    {
        super(activity);
    }


    public OfferCondition getSelectedCondition()
    {
        CharSequence selectedConditionText = super.getSelectedButton().getText();
        OfferCondition selectedCondition = OfferTool.getConditionFromCharSequence(selectedConditionText, super.getActivity());
        return selectedCondition;
    }


    @Override
    protected void findViewReferences() { }


    @Override
    protected void addButtonsToList()
    {
        ArrayList<Button> buttonList = super.getButtons();

        ViewGroup conditionSelectors = super.getActivity().findViewById(R.id.ll_condition_selection);
        for(int i = 0; i < conditionSelectors.getChildCount(); i++)
        {
            buttonList.add( (Button)conditionSelectors.getChildAt(i) );
        }
    }
}
