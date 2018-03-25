package com.example.mariaventura.pruebaframe.DataAccess;

import android.app.Activity;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.mariaventura.pruebaframe.Src.Offer;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Tobias on 25/03/2018.
 */

public class DatabaseManager
{
    private static final String successResponse = "1";
    private static final String failResponse = "2";

    public static void loadOffers(Activity callingActivity, final IOffersConsumer callback) {
        VolleySingleton.
                getInstance(callingActivity).
                addToRequestQueue(
                        new JsonObjectRequest(
                                Request.Method.GET,
                                Constantes.GET,
                                (String)null,
                                new Response.Listener<JSONObject>() {

                                    @Override
                                    public void onResponse(JSONObject response) {
                                        processOffersResponse(response, callback);
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
                                        Log.d(methodName, "Error Volley: " + error.getMessage());
                                    }
                                }

                        )
                );
    }

    private static void processOffersResponse(JSONObject response, final IOffersConsumer callback) {
        try {
            String state = response.getString("estado");
            System.out.println("State: " + state);

            switch (state) {
                case successResponse:
                    JSONArray posts = response.getJSONArray("offers");
                    System.out.println("Message: " + posts.toString());

                    Gson gson = new Gson();
                    Offer[] offers = gson.fromJson(posts.toString(), Offer[].class);
                    callback.consume(offers);
                    break;
                case failResponse:
                    String failMessage = response.getString("mensaje");
                    System.out.println(failMessage);
                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private DatabaseManager(){};
}
