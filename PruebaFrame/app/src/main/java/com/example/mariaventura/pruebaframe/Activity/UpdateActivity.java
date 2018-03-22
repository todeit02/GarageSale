package com.example.mariaventura.pruebaframe.Activity;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;

import com.example.mariaventura.pruebaframe.DataAccess.Constantes;
import com.example.mariaventura.pruebaframe.Fragment.ConfirmDialogFragment;
import com.example.mariaventura.pruebaframe.Fragment.DatePickerFragment;
import com.example.mariaventura.pruebaframe.Fragment.UpdateFragment;
import com.example.mariaventura.pruebaframe.R;



public class UpdateActivity extends AppCompatActivity
        implements DatePickerFragment.OnDateSelectedListener,
        ConfirmDialogFragment.ConfirmDialogListener {



    /**
     * Etiqueta del valor extra del dialogo
     */
    private static final String EXTRA_NOMBRE = "NAME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String extra = getIntent().getStringExtra(Constantes.EXTRA_ID);

        if (getSupportActionBar() != null)
            getSupportActionBar().setHomeAsUpIndicator(R.mipmap.done);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, UpdateFragment.createInstance(extra), "UpdateFragment")
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_form, menu);
        return true;
    }

    @Override
    public void onDateSelected(int year, int month, int day) {
        UpdateFragment updateFragment = (UpdateFragment)
                getSupportFragmentManager().findFragmentByTag("UpdateFragment");

        if (updateFragment != null) {
            updateFragment.actualizarFecha(year, month, day);
        }
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        UpdateFragment fragment = (UpdateFragment)
                getSupportFragmentManager().findFragmentByTag("UpdateFragment");

        if (fragment != null) {
            String extra = dialog.getArguments().getString(EXTRA_NOMBRE);
            String msg = getResources().
                    getString(R.string.dialog_delete_msg);

            if (extra.compareTo(msg) == 0) {
                fragment.eliminarPost(); // Eliminar la tarea
            } else {
                finish();
            }

        }
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        UpdateFragment fragment = (UpdateFragment)
                getSupportFragmentManager().findFragmentByTag("UpdateFragment");

        if (fragment != null) {
            // Nada por el momento
        }
    }
}