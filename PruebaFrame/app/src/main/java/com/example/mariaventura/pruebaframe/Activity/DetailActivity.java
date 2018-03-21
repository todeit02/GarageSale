package com.example.mariaventura.pruebaframe.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.mariaventura.pruebaframe.DataAccess.Constantes;
import com.example.mariaventura.pruebaframe.R;
import com.example.mariaventura.pruebaframe.Fragment.*;

/**
 * Esta actividad contiene un fragmento que muestra el detalle
 * de los posts.
 */
public class DetailActivity extends AppCompatActivity {
    /**
     * Instancia global del post a detallar
     */
    private String selectedPost;

    /**
     * Inicia una nueva instancia de la actividad
     *
     * @param activity Contexto desde donde se lanzará
     * @param code   Identificador del post a detallar
     */
    public static void launch(Activity activity, String code) {
        Intent intent = getLaunchIntent(activity, code);
        activity.startActivityForResult(intent, Constantes.CODIGO_DETALLE);
    }

    /**
     * Construye un Intent a partir del contexto y la actividad
     * de detalle.
     *
     * @param context Contexto donde se inicia
     * @param code  Identificador de la meta
     * @return Intent listo para usar
     */
    public static Intent getLaunchIntent(Context context, String code) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(Constantes.EXTRA_ID, code);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getSupportActionBar() != null) {
            // Dehabilitar titulo de la actividad
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            // Setear ícono "X" como Up button
            getSupportActionBar().setHomeAsUpIndicator(R.mipmap.close);
        }

        // Retener instancia
        if (getIntent().getStringExtra(Constantes.EXTRA_ID) != null)
            selectedPost = getIntent().getStringExtra(Constantes.EXTRA_ID);


        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, DetailFragment.createInstance(selectedPost), "DetailFragment")
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}


