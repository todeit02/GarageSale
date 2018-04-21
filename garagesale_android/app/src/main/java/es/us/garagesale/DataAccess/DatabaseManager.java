package es.us.garagesale.DataAccess;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import es.us.garagesale.R;
import es.us.garagesale.Src.Offer;
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
Activity aux;

    public static void loadOffers(Activity callingActivity, final IOffersConsumer callback) {
        VolleySingleton.
                getInstance(callingActivity).
                addToRequestQueue(
                        new JsonObjectRequest(
                                Request.Method.GET,
                                Constantes.GET_ALL_OFFERS,
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

    public static void loadOffer(int id, Activity callingActivity, final IOfferConsumer callback) {
        VolleySingleton.
                getInstance(callingActivity).
                addToRequestQueue(
                        new JsonObjectRequest(
                                Request.Method.GET,
                                Constantes.GET_OFFER_BY_ID+"?id="+id,
                                (String)null,
                                new Response.Listener<JSONObject>() {

                                    @Override
                                    public void onResponse(JSONObject response) {
                                        processOfferResponse(response, callback);
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
                    JSONArray offersResponse = response.getJSONArray("offers");
                    System.out.println("Message: " + offersResponse.toString());

                    Gson gson = new Gson();
                    Offer[] offers = gson.fromJson(offersResponse.toString(), Offer[].class);
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


    private static void processOfferResponse(JSONObject response, final IOfferConsumer callback) {
        try {
            String state = response.getString("estado");
            System.out.println("State: " + state);

            switch (state) {
                case successResponse:
                    JSONObject offerResponse = response.getJSONObject("offer"); //ACA TIENE CODIGO HTML EN VEZ DE JSON

                    System.out.println("Message: " + offerResponse.toString());

                    Gson gson = new Gson();
                    Offer offer = gson.fromJson(offerResponse.toString(), Offer.class);
                    callback.consume(offer);
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

    public static void checkLoginCredentials(Activity callingActivity, final String username, final String password, final ILoginResponseConsumer callback)
    {
        VolleySingleton.
                getInstance(callingActivity).
                addToRequestQueue(
                        new JsonObjectRequest(
                                Request.Method.GET,
                                String.format(Constantes.GET_LOGIN_VALID + "?username=\"%1$s\"&password=\"%2$s\"", username, password),
                                (String)null,
                                new Response.Listener<JSONObject>() {

                                    @Override
                                    public void onResponse(JSONObject response) {
                                        processLoginResponse(response, username, password, callback);
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

    private static void processLoginResponse(JSONObject response, final String username, final String password, final ILoginResponseConsumer callback) {
        try {
            String state = response.getString("estado");
            System.out.println("State: " + state);

            switch (state) {
                case successResponse:
                    boolean isLoginValid = response.getBoolean("isLoginValid");
                    System.out.println("Message: " + isLoginValid);
                    callback.consume(isLoginValid, username, password);
                    break;
                case failResponse:
                    String failMessage = response.getString("mensaje");
                    System.out.println(failMessage);
                    callback.consume(false, username, password);
                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private DatabaseManager(){};
}