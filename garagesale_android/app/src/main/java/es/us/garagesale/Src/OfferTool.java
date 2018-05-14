package es.us.garagesale.Src;

import android.content.Context;

import es.us.garagesale.R;

/**
 * Created by Tobias on 10/05/2018.
 */

public class OfferTool
{
    public static Offer.Duration getDurationFromCharSequence(CharSequence productDurationText, Context appContext)
    {
        if(productDurationText.equals( appContext.getString(R.string.offer_duration, 3) )) return Offer.Duration.SHORT;
        if(productDurationText.equals( appContext.getString(R.string.offer_duration, 7) )) return Offer.Duration.MEDIUM;
        if(productDurationText.equals( appContext.getString(R.string.offer_duration, 14) )) return Offer.Duration.LONG;
        return Offer.Duration.INVALID;
    }


    public static CharSequence getCharSequenceFromCondition(OfferCondition productCondition, Context appContext)
    {
        switch(productCondition)
        {
            case NEW:           return appContext.getText(R.string.offer_condition_new);
            case NEARLY_NEW:    return appContext.getText(R.string.offer_condition_nearly_new);
            case USED:          return appContext.getText(R.string.offer_condition_used);
            case DEFECTIVE:     return appContext.getText(R.string.offer_condition_defective);
            default:            return "";
        }
    }


    public static OfferCondition getConditionFromCharSequence(CharSequence productConditionText, Context appContext)
    {
        if(productConditionText.equals( appContext.getText(R.string.offer_condition_new) )) return OfferCondition.NEW;
        if(productConditionText.equals( appContext.getText(R.string.offer_condition_nearly_new) )) return OfferCondition.NEARLY_NEW;
        if(productConditionText.equals( appContext.getText(R.string.offer_condition_used) )) return OfferCondition.USED;
        if(productConditionText.equals( appContext.getText(R.string.offer_condition_defective) )) return OfferCondition.DEFECTIVE;
        return null;
    }

    private OfferTool() {}
}
