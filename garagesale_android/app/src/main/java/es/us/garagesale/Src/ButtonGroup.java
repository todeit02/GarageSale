package es.us.garagesale.Src;

import android.app.Activity;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public abstract class ButtonGroup
{
    private Activity activity = null;
    private ArrayList<Button> buttons = null;
    private ButtonGroupAppearanceManager appearanceManager = null;
    private Button selectedButton = null;



    protected ButtonGroup(Activity activity)
    {
        this.activity = activity;
        buttons = new ArrayList<>();

        findViewReferences();
        addButtonsToList();
        assignButtonsHandler();
    }

    protected Activity getActivity() { return activity; }

    protected ArrayList<Button> getButtons() { return buttons; }

    protected Button getSelectedButton() { return selectedButton; }


    protected abstract void findViewReferences();

    protected abstract void addButtonsToList();

    private void assignButtonsHandler()
    {
        appearanceManager = new ButtonGroupAppearanceManager(buttons, activity);

        View.OnClickListener durationClickListener = new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                appearanceManager.onClick(v);
                selectedButton = (Button) v;
            }
        };

        for(Button listenerSettingButton : buttons)
        {
            listenerSettingButton.setOnClickListener(durationClickListener);
        }

        // need to preselect value
        durationClickListener.onClick(buttons.get(0));
    }
}
