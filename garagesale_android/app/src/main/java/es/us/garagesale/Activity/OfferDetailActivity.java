package es.us.garagesale.Activity;

import android.app.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import es.us.garagesale.DataAccess.Constantes;
import es.us.garagesale.DataAccess.DatabaseManager;
import es.us.garagesale.DataAccess.IOfferConsumer;
import es.us.garagesale.DataAccess.IOffersConsumer;
import es.us.garagesale.DataAccess.VolleySingleton;
import es.us.garagesale.R;
import es.us.garagesale.Src.Offer;


public class OfferDetailActivity extends Activity {

    private int selectedOfferId;
    TextView title;
    private Offer offer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_details);

        title = findViewById(R.id.tvOfferDetailsOfferUsedState);
        selectedOfferId = getIntent().getIntExtra("id", 0);

        //cargarDatos(selectedOfferId, this);

        DatabaseManager.loadOffer(selectedOfferId, this, new IOfferConsumer() {
            @Override
            public void consume(Offer receivedOffer) {
                displayOffer(receivedOffer);
            }
        });
    }


    private void displayOffer(Offer received){
        title.setText(received.getName());
    }

    private void procesarRespuesta1(JSONObject response, final Activity callingActivity) {

        try {
            // Obtener atributo "mensaje"
            String mensaje = response.getString("estado");

            switch (mensaje) {
                case "1":
                    // Obtener objeto "meta"
                    JSONObject object = response.getJSONObject("meta");

                    Gson gson = new Gson();
                    //Parsear objeto
                    Offer meta = gson.fromJson(object.toString(), Offer.class);
                    displayOffer(meta);
                    // Asignar color del fondo


                    break;

                case "2":
                    String mensaje2 = response.getString("mensaje");
                    Toast.makeText(
                            callingActivity,
                            mensaje2,
                            Toast.LENGTH_LONG).show();
                    break;

                case "3":
                    String mensaje3 = response.getString("mensaje");
                    Toast.makeText(
                            callingActivity,
                            mensaje3,
                            Toast.LENGTH_LONG).show();
                    break;
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public  void cargarDatos(int id, final Activity callingActivity) {

        // Añadir parámetro a la URL del web service
        String newURL = Constantes.GET_OFFER_BY_ID + "?id=" + id;

        // Realizar petición GET_BY_ID
        VolleySingleton.getInstance(callingActivity).addToRequestQueue(
                new JsonObjectRequest(
                        Request.Method.GET,
                        newURL,
                        (String)null,
                        new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                // Procesar respuesta Json
                                procesarRespuesta1(response, callingActivity); //NUNCA ENTRA ACA
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d("", "Error Volley: " + error.getMessage());
                            }
                        }
                )
        );
    }

}


