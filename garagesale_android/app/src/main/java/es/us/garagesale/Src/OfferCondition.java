package es.us.garagesale.Src;

/**
 * Created by Tobias on 10/05/2018.
 */

public enum OfferCondition
{
    NEW(0),
    NEARLY_NEW(1),
    USED(2),
    DEFECTIVE(3);

    private int numericValue = 0;

    public int getNumericValue()
    {
        return numericValue;
    }

    private OfferCondition(int numericValue)
    {
        this.numericValue = numericValue;
    }
}