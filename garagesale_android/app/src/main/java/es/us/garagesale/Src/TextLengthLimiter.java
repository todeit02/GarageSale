package es.us.garagesale.Src;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import es.us.garagesale.R;


/*
    Instances of this class can listen to any EditText.
    It will show the user how many characters remain to be entered ("user text")
    by appending the indicator stated in the remaining_characters_count string resource.
    If they try to enter too long text the current change will be prevented.
 */

public class TextLengthLimiter implements TextWatcher
{

    Context appContext = null;
    EditText watchingEdit = null;
    int maxTextCharacterCount = 0;
    CharSequence textBeforeChange = null;
    boolean currentChangeIsTooLong = false;
    boolean changeIsCausedByThis = false; // prevents infinite loop

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after)
    {
        if(changeIsCausedByThis) return;

        int pendingLengthChange = after - count;
        int remainingUserCharacterCount = maxTextCharacterCount - extractUserText(s).length() - pendingLengthChange;

        if (remainingUserCharacterCount < 0)
        {
            currentChangeIsTooLong = true;
            textBeforeChange = s;
        }
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count)
    {
        if(changeIsCausedByThis)
        {
            changeIsCausedByThis = false;
            return;
        }

        changeIsCausedByThis = true;

        if(currentChangeIsTooLong)
        {
            watchingEdit.setText(textBeforeChange);
            return;
        }

        String userText = extractUserText(s);
        watchingEdit.setText(userText + " " + getRemainingCharactersIndicator(userText));
    }

    @Override
    public void afterTextChanged(Editable s) { }

    public TextLengthLimiter(EditText watchingEdit, int maxTextCharacterCount, Context appContext)
    {
        this.watchingEdit = watchingEdit;
        this.maxTextCharacterCount = maxTextCharacterCount;
        this.appContext = appContext;
    }


    private String getRemainingCharactersIndicator(CharSequence entireText)
    {
        int userTextLength = extractUserText(entireText).length();
        int remainingLength = maxTextCharacterCount - userTextLength;
        return appContext.getString(R.string.remaining_characters_count, remainingLength);
    }

    private String extractUserText(CharSequence entireText)
    {
        String[] splitsAroundIndicator = String.valueOf(entireText).split(getIndicatorRegex());
        return splitsAroundIndicator[0];
    }

    private String getIndicatorRegex()
    {
        int dummyCharacterCount = 0;
        String plainIndicator = appContext.getString(R.string.remaining_characters_count, dummyCharacterCount);
        String plainIndicatorRegex = " " + plainIndicator.replace(String.valueOf(dummyCharacterCount), "[0-9]+");

        return plainIndicatorRegex;
    }
}