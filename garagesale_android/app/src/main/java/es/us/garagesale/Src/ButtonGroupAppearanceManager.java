package es.us.garagesale.Src;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import es.us.garagesale.R;

/**
 * Created by Tobias on 25/04/2018.
 */

public class ButtonGroupAppearanceManager implements View.OnClickListener
{
    private ArrayList<Button> groupButtons = null;
    private Context appContext;

    @Override
    public void onClick(View v) {
        for(int i = 0; i < groupButtons.size(); i++)
        {
            Button appearanceChangingButton = groupButtons.get(i);
            boolean isCurrentButtonActive = (appearanceChangingButton == v);
            int unselectedDrawableId = 0;
            int selectedDrawableId = 0;

            if(i == 0)
            {
                unselectedDrawableId = R.drawable.button_group_left_unselected_background;
                selectedDrawableId = R.drawable.button_group_left_selected_background;
            }
            else if(i == groupButtons.size() - 1)
            {
                unselectedDrawableId = R.drawable.button_group_right_unselected_background;
                selectedDrawableId = R.drawable.button_group_right_selected_background;
            }
            else
            {
                unselectedDrawableId = R.drawable.button_group_middle_unselected_background;
                selectedDrawableId = R.drawable.button_group_middle_selected_background;
            }

            Drawable settingBackground = appContext.getResources().getDrawable(isCurrentButtonActive ? selectedDrawableId : unselectedDrawableId);
            appearanceChangingButton.setBackgroundDrawable(settingBackground);
        }
    }

    public ButtonGroupAppearanceManager(ArrayList<Button> groupButtons, Context appContext)
    {
        this.groupButtons = groupButtons;
        this.appContext = appContext;
    }
}