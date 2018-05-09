package es.us.garagesale.DataAccess;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import es.us.garagesale.R;
import es.us.garagesale.Src.Card;
import es.us.garagesale.Src.CustomRequest;
import es.us.garagesale.Src.Interested;
import es.us.garagesale.Src.Offer;
import es.us.garagesale.Src.Person;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

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

    public static void loadUsernameOffers(String username, Activity callingActivity, final IUsernameOffersConsumer callback){
        VolleySingleton.
                getInstance(callingActivity).
                addToRequestQueue(
                        new JsonObjectRequest(
                                Request.Method.GET,
                                Constantes.GET_ALL_USERNAME_OFFERS+"?seller_username="+username,
                                (String)null,
                                new Response.Listener<JSONObject>() {

                                    @Override
                                    public void onResponse(JSONObject response) {
                                        processUsernameOffersResponse(response, callback);
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

    public static void loadInterested(int id,Activity callingActivity, final IInterestedConsumer callback) {
        VolleySingleton.
                getInstance(callingActivity).
                addToRequestQueue(
                        new JsonObjectRequest(
                                Request.Method.GET,
                                Constantes.GET_OFFER_INTERESTED+"?offer_id="+id,
                                (String)null,
                                new Response.Listener<JSONObject>() {

                                    @Override
                                    public void onResponse(JSONObject response) {
                                        processInterestedResponse(response, callback);
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

    private static void processInterestedResponse(JSONObject response, final IInterestedConsumer callback) {
        try {
            String state = response.getString("estado");
            System.out.println("State: " + state);

            switch (state) {
                case successResponse:
                    JSONArray offersResponse = response.getJSONArray("interested");
                    System.out.println("Message: " + offersResponse.toString());

                    Gson gson = new Gson();
                    Interested[] interested = gson.fromJson(offersResponse.toString(), Interested[].class);
                    callback.consume(interested);
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

    public static void loadPersonInfo(String user, Activity callingActivity, final IPersonConsumer callback) {
        VolleySingleton.
                getInstance(callingActivity).
                addToRequestQueue(
                        new JsonObjectRequest(
                                Request.Method.GET,
                                Constantes.GET_PERSON_BY_USERNAME+"?username="+user,
                                (String)null,
                                new Response.Listener<JSONObject>() {

                                    @Override
                                    public void onResponse(JSONObject response) {
                                        processPersonResponse(response, callback);
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

    private static void processUsernameOffersResponse(JSONObject response, final IUsernameOffersConsumer callback) {
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
                    JSONObject offerResponse = response.getJSONObject("offer");

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

    private static void processPersonResponse(JSONObject response, final IPersonConsumer callback) {
        try {
            String state = response.getString("estado");
            System.out.println("State: " + state);

            switch (state) {
                case successResponse:
                    JSONObject personResponse = response.getJSONObject("person");

                    System.out.println("Message: " + personResponse.toString());

                    Gson gson = new Gson();
                    Person person = gson.fromJson(personResponse.toString(), Person.class);
                    callback.consume(person);
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

    public static void createOffer(Offer creatingOffer)
    {

    }

    public static void editOffer(int id, final Activity callingActivity){
        String url = Constantes.UPDATE_OFFER;
        Map<String, String> params = new HashMap<String, String>();
        params.put("sold", String.valueOf(1));
        params.put("id", String.valueOf(id));

        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                    Log.d("Response: ", response.toString());


            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError response) {
                Log.d("Response: ", response.toString());
            }
        });
        VolleySingleton.getInstance(callingActivity).addToRequestQueue(jsObjRequest);
    }



    public static void editOfferViejo(int id, final Activity callingActivity){

        HashMap<String, String> map = new HashMap<>();

        map.put("sold", String.valueOf(1));
        map.put("id", String.valueOf(id));

        // Crear nuevo objeto Json basado en el mapa
        JSONObject jobject = new JSONObject(map);



        // Actualizar datos en el servidor
        VolleySingleton.getInstance(callingActivity).addToRequestQueue(
                new JsonObjectRequest(
                        Request.Method.POST,
                        Constantes.UPDATE_OFFER,
                        jobject,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                processEditOffer(response, callingActivity);
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

    private static void processEditOffer(JSONObject response, Activity callingActivity){
        try {
            // Obtener estado
            String estado = response.getString("estado");
            // Obtener mensaje
            String mensaje = response.getString("mensaje");

            switch (estado) {
                case "1":
                    // Mostrar mensaje
                    Toast.makeText(
                            callingActivity,
                            mensaje,
                            Toast.LENGTH_LONG).show();
                    // Enviar código de éxito
                    callingActivity.setResult(Activity.RESULT_OK);
                    // Terminar actividad
                    callingActivity.finish();
                    break;

                case "2":
                    // Mostrar mensaje
                    Toast.makeText(
                            callingActivity,
                            mensaje,
                            Toast.LENGTH_LONG).show();
                    // Enviar código de falla
                    callingActivity.setResult(Activity.RESULT_CANCELED);
                    // Terminar actividad
                    callingActivity.finish();
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public static void saveInterested(Interested newInterested, Activity callingActivity){


            HashMap<String, String> map = new HashMap<>();// Mapeo previo

            map.put("username", newInterested.getUsername());
            map.put("offer_id", String.valueOf(newInterested.getOfferId()));
            map.put("price", String.valueOf(newInterested.getPrice()));

            // Crear nuevo objeto Json basado en el mapa
            JSONObject jobject = new JSONObject(map);

            // Actualizar datos en el servidor
            VolleySingleton.getInstance(callingActivity).
                    addToRequestQueue(
                            new JsonObjectRequest(
                                    Request.Method.POST,
                                    Constantes.INSERT_OFFER_INTERESTED,
                                    jobject,
                                    new Response.Listener<JSONObject>() {
                                        @Override
                                        public void onResponse(JSONObject response) {
                                            // Procesar la respuesta del servidor
                                            processInsertInterested(response);
                                        }
                                    },
                                    new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
                                            Log.d(methodName, "Error Volley: " + error.getMessage());
                                        }
                                    }

                    ) {
                        @Override
                        public Map<String, String> getHeaders() {
                            Map<String, String> headers = new HashMap<String, String>();
                            headers.put("Content-Type", "application/json; charset=utf-8");
                            headers.put("Accept", "application/json");
                            return headers;
                        }

                        @Override
                        public String getBodyContentType() {
                            return "application/json; charset=utf-8" + getParamsEncoding();
                        }
                    }
            );
    }

    public static void processInsertInterested(JSONObject response){
        try {
            String state = response.getString("estado");
            System.out.println("State: " + state);

            switch (state) {
                case successResponse:
                    JSONObject interestedResponse = response.getJSONObject("interested");

                    System.out.println("Message: " + interestedResponse.toString());

                    Gson gson = new Gson();
                    Interested interested = gson.fromJson(interestedResponse.toString(), Interested.class);
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

    public static void createPerson(Person creatingPerson, Activity callingActivity, final ISignupResponseConsumer callback)
    {
        HashMap<String, String> map = new HashMap<>();

        map.put("username", creatingPerson.getUsername());
        map.put("password", creatingPerson.getPassword());
        map.put("realName", creatingPerson.getRealName());
        map.put("email", creatingPerson.getEmail());
        map.put("birthDate", creatingPerson.getBirthDate("yyyy-MM-dd"));
        map.put("nationality", creatingPerson.getNationality());
        map.put( "reputation", String.valueOf(creatingPerson.getReputation()) );

        Card creatingCard = creatingPerson.getPersonalCard();
        map.put( "cardNum", String.valueOf(creatingCard.getCardNum()) );
        map.put("expDate", creatingCard.getExpDate("yyyy-MM-dd"));
        map.put( "ccv", String.valueOf(creatingCard.getCcv()) );
        map.put("bank", creatingCard.getBank());

        // Crear nuevo objeto Json basado en el mapa
        JSONObject jobject = new JSONObject(map);

        // Actualizar datos en el servidor
        VolleySingleton.getInstance(callingActivity).
                addToRequestQueue(
                        new JsonObjectRequest(
                                Request.Method.POST,
                                Constantes.INSERT_PERSON,
                                jobject,
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        // Procesar la respuesta del servidor
                                        processSignupResponse(response, callback);
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
                                        Log.d(methodName, "Error Volley: " + error.getMessage());
                                        processSignupResponse(null, callback);
                                    }
                                }

                        ) {
                            @Override
                            public Map<String, String> getHeaders() {
                                Map<String, String> headers = new HashMap<String, String>();
                                headers.put("Content-Type", "application/json; charset=utf-8");
                                headers.put("Accept", "application/json");
                                return headers;
                            }

                            @Override
                            public String getBodyContentType() {
                                return "application/json; charset=utf-8" + getParamsEncoding();
                            }
                        }
                );
    }

    private static void processSignupResponse(JSONObject response, final ISignupResponseConsumer callback)
    {
        if(response == null)
        {
            callback.consume(false, false);
        }

        try
        {
            String state = response.getString("estado");
            System.out.println("State: " + state);
            boolean isUsernameAlreadyTaken = false;

            switch (state)
            {
                case successResponse:
                    isUsernameAlreadyTaken = response.getBoolean("isUsernameAlreadyTaken");
                    callback.consume(true, isUsernameAlreadyTaken);
                    break;
                case failResponse:
                    String failMessage = response.getString("mensaje");
                    System.out.println(failMessage);
                    isUsernameAlreadyTaken = response.getBoolean("isUsernameAlreadyTaken");
                    callback.consume(false, isUsernameAlreadyTaken);
                    break;
            }
            System.out.println("Message: " + isUsernameAlreadyTaken);
        }
        catch (JSONException e) { e.printStackTrace(); }
    }

    private DatabaseManager(){}
}