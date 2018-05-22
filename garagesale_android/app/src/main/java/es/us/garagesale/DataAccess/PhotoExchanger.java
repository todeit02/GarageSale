package es.us.garagesale.DataAccess;

import android.app.Activity;

public abstract class PhotoExchanger
{
    protected Activity callingActivity = null;
    protected int offerId = 0;
    protected ISuccessConsumer onFinishConsumer = null;
}