package com.example.mariaventura.pruebaframe.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.mariaventura.pruebaframe.Activity.InsertActivity;
import com.example.mariaventura.pruebaframe.Activity.OfferAdapter;
import com.example.mariaventura.pruebaframe.DataAccess.Constantes;
import com.example.mariaventura.pruebaframe.DataAccess.VolleySingleton;
import com.example.mariaventura.pruebaframe.R;
import com.example.mariaventura.pruebaframe.Src.Offer;
import com.google.gson.Gson;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;


/**
 * Fragmento principal que contiene la lista de las metas
 */
public class MainFragment extends Fragment {

    /*
    Etiqueta de depuracion
     */
    private static final String TAG = MainFragment.class.getSimpleName();

    /*
    Adaptador del recycler view
     */
    private OfferAdapter adapter;

    /*
    Instancia global del recycler view
     */
    private RecyclerView lista;

    /*
    instancia global del administrador
     */
    private RecyclerView.LayoutManager lManager;

    /*
    Instancia global del FAB
     */
    com.melnykov.fab.FloatingActionButton fab;
    private Gson gson = new Gson();

    public MainFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_main, container, false);

        lista = (RecyclerView) v.findViewById(R.id.reciclador);
        lista.setHasFixedSize(true);

        // Usar un administrador para LinearLayout
        lManager = new LinearLayoutManager(getActivity());
        lista.setLayoutManager(lManager);

        // Cargar datos en el adaptador
        loadAdapter();

        // Obtener instancia del FAB
        fab = (com.melnykov.fab.FloatingActionButton) v.findViewById(R.id.fab);

        // Asignar escucha al FAB
        fab.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Iniciar actividad de inserción
                        getActivity().startActivityForResult(
                                new Intent(getActivity(), InsertActivity.class), 3);
                    }
                }
        );

        return v;
    }

    /**
     * Carga el adaptador con las ofertas obtenidas
     * en la respuesta
     */
    public void loadAdapter() {
        // Petición GET
        VolleySingleton.
                getInstance(getActivity()).
                addToRequestQueue(
                        new JsonObjectRequest(
                                Request.Method.GET,
                                Constantes.GET,
                                (String)null,
                                new Response.Listener<JSONObject>() {

                                    @Override
                                    public void onResponse(JSONObject response) {
                                        // Procesar la respuesta Json
                                        processResponse(response);
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Log.d(TAG, "Error Volley: " + error.getMessage());
                                    }
                                }

                        )
                );
    }

    /**
     * Interpreta los resultados de la respuesta y así
     * realizar las operaciones correspondientes
     *
     * @param response Objeto Json con la respuesta
     */
    private void processResponse(JSONObject response) {
        try {
            // Obtener atributo "estado"
            String estado = response.getString("estado");

            switch (estado) {
                case "1": // EXITO
                    // Obtener array "offers" Json
                    JSONArray mensaje = response.getJSONArray("offers");
                    // Parsear con Gson
                    Offer[] offers = gson.fromJson(mensaje.toString(), Offer[].class);
                    // Inicializar adaptador
                    adapter = new OfferAdapter(Arrays.asList(offers), getActivity());
                    // Setear adaptador a la lista
                    lista.setAdapter(adapter);
                    break;
                case "2": // FALLIDO
                    String mensaje2 = response.getString("mensaje");
                    Toast.makeText(
                            getActivity(),
                            mensaje2,
                            Toast.LENGTH_LONG).show();
                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


}