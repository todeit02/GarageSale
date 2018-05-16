package es.us.garagesale.DataAccess;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.util.JsonReader;
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
import es.us.garagesale.Src.OfferCondition;
import es.us.garagesale.Src.Person;
import es.us.garagesale.Src.Purchase;
import es.us.garagesale.Src.Ranking;

import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceDetectionClient;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

    public static void loadOffers(final Activity callingActivity, final IOffersConsumer callback) {
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
                                        processOffersResponse(response, callingActivity, callback);
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

    public static void loadFilteredOffers(String query, String name, Activity callingActivity, final IOffersConsumer callback){
        VolleySingleton.
                getInstance(callingActivity).
                addToRequestQueue(
                        new JsonObjectRequest(
                                Request.Method.GET,
                                Constantes.GET_FILTERED_OFFERS+"?tag='"+query+"'"+"&"+"name='%"+name+"%'",
                                (String)null,
                                new Response.Listener<JSONObject>() {

                                    @Override
                                    public void onResponse(JSONObject response) {
                                        processFilteredOffersResponse(response, callback);
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

    private static void processFilteredOffersResponse(JSONObject response, final IOffersConsumer callback) {
        try {
            String state = response.getString("estado");
            System.out.println("State: " + state);

            switch (state) {
                case successResponse:
                    JSONArray purchasesResponse = response.getJSONArray("offers");
                    System.out.println("Message: " + purchasesResponse.toString());

                    Gson gson = new Gson();
                    Offer[] offers = gson.fromJson(purchasesResponse.toString(), Offer[].class);
                    callback.consume(offers);
                    break;
                case failResponse:
                    String failMessage = response.getString("mensaje");
                    System.out.println(failMessage);

                    //Para mostrar mensaje de error en la UI
                    Offer[] offers2 = new Offer[0];
                    callback.consume(offers2);
                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
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

    public static void loadUsernamePurchases(String username, Activity callingActivity, final IUsernamePurchasesConsumer callback){
        VolleySingleton.
                getInstance(callingActivity).
                addToRequestQueue(
                        new JsonObjectRequest(
                                Request.Method.GET,
                                Constantes.GET_ALL_USERNAME_PURCHASES+"?buyer_username="+username,
                                (String)null,
                                new Response.Listener<JSONObject>() {

                                    @Override
                                    public void onResponse(JSONObject response) {
                                        processUsernamePurchasesResponse(response, callback);
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

    private static void processUsernamePurchasesResponse(JSONObject response, final IUsernamePurchasesConsumer callback) {
        try {
            String state = response.getString("estado");
            System.out.println("State: " + state);

            switch (state) {
                case successResponse:
                    JSONArray purchasesResponse = response.getJSONArray("purchases");
                    System.out.println("Message: " + purchasesResponse.toString());

                    Gson gson = new Gson();
                    Purchase[] purchases = gson.fromJson(purchasesResponse.toString(), Purchase[].class);
                    callback.consume(purchases);
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

    public static void loadOffer(int id, final Activity callingActivity, final IOfferConsumer callback) {
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
                                        processOfferResponse(response, callingActivity, callback);
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

    public static void getRankingById(String sellerUsername, String buyerUsername, int idOffer, Activity callingActivity, final IRankingConsumer callback){
        VolleySingleton.
                getInstance(callingActivity).
                addToRequestQueue(
                        new JsonObjectRequest(
                                Request.Method.GET,
                                Constantes.GET_RANKING_BY_ID+"?seller_username='"+sellerUsername+"'"+"&"+"buyer_username='"+buyerUsername+"'"+"&offer_id="+idOffer,
                                (String)null,
                                new Response.Listener<JSONObject>() {

                                    @Override
                                    public void onResponse(JSONObject response) {
                                        processRankingResponse(response, callback);
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

    public static void getUserRanking(String sellerUsername, Activity callingActivity, final IUserRankingConsumer callback){
        VolleySingleton.
                getInstance(callingActivity).
                addToRequestQueue(
                        new JsonObjectRequest(
                                Request.Method.GET,
                                Constantes.GET_USER_RANKING+"?seller_username='"+sellerUsername+"'",
                                (String)null,
                                new Response.Listener<JSONObject>() {

                                    @Override
                                    public void onResponse(JSONObject response) {
                                        processUserRankingResponse(response, callback);
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

    private static void processOffersResponse(JSONObject response, Context callContext, final IOffersConsumer callback) {
        try {
            String state = response.getString("estado");
            System.out.println("State: " + state);

            switch (state) {
                case successResponse:
                    JSONArray offersResponse = response.getJSONArray("offers");
                    System.out.println("Message: " + offersResponse.toString());

                    Gson gson = createOfferDeserializationGson(callContext);
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
    
    
    private static Gson createOfferDeserializationGson(Context callContext)
    {
        GsonBuilder gsonBuilder = new GsonBuilder();
        JsonDeserializer<Offer> deserializer = createOfferDeserializer(callContext);
        gsonBuilder.registerTypeAdapter(Offer.class, deserializer);

        Gson gson = gsonBuilder.create();
        return gson;
    }


    private static JsonDeserializer<Offer> createOfferDeserializer(final Context callContext)
    {
        return new JsonDeserializer<Offer>() {
            @Override
            public Offer deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
            {
                JsonObject jsonObject = json.getAsJsonObject();

                String name = jsonObject.get("name").getAsString();
                String sellerUsername = jsonObject.get("seller_username").getAsString();

                int conditionNumeric = jsonObject.get("state").getAsInt();
                OfferCondition condition = OfferCondition.fromNumericValue(conditionNumeric);

                String description = jsonObject.get("description").getAsString();
                float price = jsonObject.get("price").getAsFloat();

                ArrayList<String> tags = new ArrayList<>();
                JsonArray tagsJson = jsonObject.getAsJsonArray("tags");
                if(tagsJson != null)
                {
                    for(int i = 0; i < tagsJson.size(); i++)
                    {
                        String tagString = tagsJson.get(i).getAsString();
                        tags.add(tagString);
                    }
                }

                String startTime = jsonObject.get("startTime").getAsString();
                int isSold = jsonObject.get("isSold").getAsInt();
                int id = jsonObject.get("id").getAsInt();
                int durationDays = jsonObject.get("durationDays").getAsInt();
                ArrayList<Bitmap> photos = new ArrayList<>();
                LatLng coordinates = deserializeOfferCoordinates(jsonObject);
                String cityName = getOfferCityFromCoordinates(coordinates, callContext);

                return new Offer(name, sellerUsername, condition, description, price, tags, startTime, isSold, id, durationDays, photos, coordinates, cityName);
            }
        };
    }


    private static LatLng deserializeOfferCoordinates(JsonObject jsonObject)
    {
        if(jsonObject.get("latitude").isJsonNull() || jsonObject.get("longitude").isJsonNull())
        {
            return null;
        }

        double latitude = jsonObject.get("latitude").getAsDouble();
        double longitude = jsonObject.get("longitude").getAsDouble();

        return new LatLng(latitude, longitude);
    }


    private static String getOfferCityFromCoordinates(LatLng coordinates, Context callContext)
    {
        String cityName = "";

        Geocoder geocoder = new Geocoder(callContext);
        try
        {
            Address nearestAddress = geocoder.getFromLocation(coordinates.latitude, coordinates.longitude, 1).get(0);
            cityName = nearestAddress.getLocality();
        }
        catch (Exception e) {}

        return cityName;
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


    private static void processOfferResponse(JSONObject response, Context callContext, final IOfferConsumer callback) {
        try {
            String state = response.getString("estado");
            System.out.println("State: " + state);

            switch (state) {
                case successResponse:
                    JSONObject offerResponse = response.getJSONObject("offer");

                    System.out.println("Message: " + offerResponse.toString());

                    Gson gson = createOfferDeserializationGson(callContext);
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

    private static void processRankingResponse(JSONObject response, final IRankingConsumer callback) {
        try {
            String state = response.getString("estado");
            System.out.println("State: " + state);

            switch (state) {
                case successResponse:
                    JSONObject rankingResponse = response.getJSONObject("ranking");

                    System.out.println("Message: " + rankingResponse.toString());

                    Gson gson = new Gson();
                    Ranking ranking = gson.fromJson(rankingResponse.toString(), Ranking.class);
                    callback.consume(ranking);
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

    private static void processUserRankingResponse(JSONObject response, final IUserRankingConsumer callback){
        try {
            String state = response.getString("estado");
            System.out.println("State: " + state);

            switch (state) {
                case successResponse:
                    int ranking= response.getInt("ranking");
                    callback.consume(ranking);
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

    public static void createOffer(Activity callingActivity, Offer creatingOffer, final IIdConsumer callback)
    {
        JSONObject offerJson = createOfferJson(creatingOffer);

        // Actualizar datos en el servidor
        VolleySingleton.getInstance(callingActivity).
                addToRequestQueue(
                        new JsonObjectRequest(
                                Request.Method.POST,
                                Constantes.INSERT_OFFER,
                                offerJson,
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        // Procesar la respuesta del servidor
                                        processCreateOfferResponse(response, callback);
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
                                        Log.d(methodName, "Error Volley: " + error.getMessage());
                                        processCreateOfferResponse(null, callback);
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


    private static JSONObject createOfferJson(Offer creatingOffer)
    {
        JSONObject jsonObject = new JSONObject();

        try
        {
            jsonObject.put("name", creatingOffer.getName());
            jsonObject.put("description", creatingOffer.getDescription());
            JSONArray tagsArray = new JSONArray();
            for(String addingTag : creatingOffer.getTags())
            {
                tagsArray.put(addingTag);
            }
            jsonObject.put("tags", tagsArray);
            jsonObject.put("price", Float.toString(creatingOffer.getPrice()) );
            jsonObject.put("seller_username", creatingOffer.getSellerUsername());

            String conditionNumberString = Integer.toString(creatingOffer.getCondition().getNumericValue());
            jsonObject.put("state", conditionNumberString);

            jsonObject.put("durationDays", Integer.toString(creatingOffer.getDurationDays()) );

            String latitudeString = "";
            String longitudeString = "";
            if(creatingOffer.getCoordinates() != null)
            {
                latitudeString = Double.toString(creatingOffer.getCoordinates().latitude);
                longitudeString = Double.toString(creatingOffer.getCoordinates().longitude);
            }
            jsonObject.put("latitude", latitudeString);
            jsonObject.put("longitude", longitudeString);
        }
        catch (Exception e) {}

        return jsonObject;
    }


    private static void processCreateOfferResponse(JSONObject response, final IIdConsumer callback)
    {
        final int errorId = -1;

        if(response == null)
        {
            callback.consume(false, errorId);
            return;
        }

        try
        {
            String state = response.getString("estado");
            System.out.println("State: " + state);
            boolean isUsernameAlreadyTaken = false;

            switch (state)
            {
                case successResponse:
                    int offerId = response.getInt("offerId");
                    callback.consume(true, offerId);
                    break;
                case failResponse:
                    String failMessage = response.getString("mensaje");
                    System.out.println(failMessage);
                    callback.consume(false, errorId);
                    break;
            }
            System.out.println("Message: " + isUsernameAlreadyTaken);
        }
        catch (JSONException e) { e.printStackTrace(); }
    }
    public static void editOffer(int id, final Activity callingActivity){
        String url = Constantes.UPDATE_OFFER;
        Map<String, String> params = new HashMap<String, String>();
        params.put("isSold", String.valueOf(1));
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

    public static void insertPurchase(int offerId, int price, String buyer, Activity callingActivity){

        HashMap<String, String> map = new HashMap<>();// Mapeo previo

        map.put("offer_id", String.valueOf(offerId));
        map.put("price", String.valueOf(price));
        map.put("buyer_username", buyer);
        map.put("paymentMethod" , "card");
        map.put("hasContactedSeller", String.valueOf(0));

        // Crear nuevo objeto Json basado en el mapa
        JSONObject jobject = new JSONObject(map);

        // Actualizar datos en el servidor
        VolleySingleton.getInstance(callingActivity).
                addToRequestQueue(
                        new JsonObjectRequest(
                                Request.Method.POST,
                                Constantes.INSERT_PURCHASE,
                                jobject,
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        // Procesar la respuesta del servidor
                                        processInsertPurchase(response);
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

    public static void processInsertPurchase(JSONObject response){
        try {
            String state = response.getString("estado");
            System.out.println("State: " + state);

            switch (state) {
                case successResponse:
                    JSONObject interestedResponse = response.getJSONObject("purchase");

                    System.out.println("Message: " + interestedResponse.toString());

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

    public static void insertRanking(String sellerUsername, String buyerUsername,  float rate, int idOffer, Activity callingActivity){
        HashMap<String, String> map = new HashMap<>();// Mapeo previo

        map.put("seller_username",sellerUsername);
        map.put("buyer_username",buyerUsername);
        map.put("value", String.valueOf(rate));
        map.put("offer_id", String.valueOf(idOffer));

        // Crear nuevo objeto Json basado en el mapa
        JSONObject jobject = new JSONObject(map);

        // Actualizar datos en el servidor
        VolleySingleton.getInstance(callingActivity).
                addToRequestQueue(
                        new JsonObjectRequest(
                                Request.Method.POST,
                                Constantes.INSERT_RANKING,
                                jobject,
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        // Procesar la respuesta del servidor
                                        processInsertRanking(response);
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

    public static void processInsertRanking(JSONObject response){
        try {
            String state = response.getString("estado");
            System.out.println("State: " + state);

            switch (state) {
                case successResponse:
                    JSONObject interestedResponse = response.getJSONObject("ranking");

                    System.out.println("Message: " + interestedResponse.toString());

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

    public static void deleteOffer(int offerId, final Activity callingActivity){


        HashMap<String, String> map = new HashMap<>();// MAPEO

        map.put("id", String.valueOf(offerId));// Identificador

        JSONObject jobject = new JSONObject(map);// Objeto Json

        // Eliminar datos en el servidor
        VolleySingleton.getInstance(callingActivity).
                addToRequestQueue(
                        new JsonObjectRequest(
                                Request.Method.POST,
                                Constantes.DELETE_OFFER,
                                jobject,
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        // Procesar la respuesta
                                        processDeleteOffer(response);

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

    public static void processDeleteOffer(JSONObject response) {
        try {
            String state = response.getString("estado");
            System.out.println("State: " + state);

            switch (state) {
                case successResponse:
                    JSONObject interestedResponse = response.getJSONObject("offer");

                    System.out.println("Message: " + interestedResponse.toString());

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
            return;
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