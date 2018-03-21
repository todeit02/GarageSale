package com.example.mariaventura.pruebaframe.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.mariaventura.pruebaframe.DataAccess.Constantes;
import com.example.mariaventura.pruebaframe.DataAccess.VolleySingleton;
import com.example.mariaventura.pruebaframe.R;
import com.example.mariaventura.pruebaframe.Src.Person;
import com.example.mariaventura.pruebaframe.Src.Post;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailFragment extends Fragment {
    /*
    Etiqueta de valor extra
     */
    private static final String EXTRA_ID = "CODE";

    /**
     * Etiqueta de depuración
     */
    private static final String TAG = DetailFragment.class.getSimpleName();

    /*
    Instancias de Views
     */
    private ImageView cabecera;
    private TextView name;
    private TextView description;
    private TextView price;
    private TextView publishDate;
    private TextView seller;
    private ImageButton editButton;
    private String extra;
    private Gson gson = new Gson();

    public DetailFragment() {
    }

    public static DetailFragment createInstance(String code) {
        DetailFragment detailFragment = new DetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_ID, code);
        detailFragment.setArguments(bundle);
        return detailFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_description, container, false);

        // Obtención de views
        cabecera = (ImageView) v.findViewById(R.id.cabecera);
        name = (TextView) v.findViewById(R.id.name);
        description = (TextView) v.findViewById(R.id.descripcion);
        price = (TextView) v.findViewById(R.id.precio);
        publishDate = (TextView) v.findViewById(R.id.fecha);
        seller = (TextView) v.findViewById(R.id.vendedor);
        editButton = (ImageButton) v.findViewById(R.id.fab);

        // Setear escucha para el fab
        editButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Iniciar actividad de actualización
                        Intent i = new Intent(getActivity(), UpdateActivity.class);
                        i.putExtra(EXTRA_ID, extra);
                        getActivity().startActivityForResult(i, Constantes.CODIGO_ACTUALIZACION);
                    }
                }
        );

        // Obtener extra del intent de envío
        extra = getArguments().getString(EXTRA_ID);

        // Cargar datos desde el web service
        cargarDatos();

        return v;
    }

    /**
     * Obtiene los datos desde el servidor
     */
    public void cargarDatos() {

        // Añadir parámetro a la URL del web service
        String newURL = Constantes.GET_BY_ID + "?code=" + extra;

        // Realizar petición GET_BY_ID
        VolleySingleton.getInstance(getActivity()).addToRequestQueue(
                new JsonObjectRequest(
                        Request.Method.GET,
                        newURL,
                        (String)null,
                        new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                // Procesar respuesta Json
                                procesarRespuesta(response);
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
     * Procesa cada uno de los estados posibles de la
     * respuesta enviada desde el servidor
     *
     * @param response Objeto Json
     */
    private void procesarRespuesta(JSONObject response) {

        try {
            // Obtener atributo "mensaje"
            String mensaje = response.getString("estado");

            switch (mensaje) {
                case "1":
                    // Obtener objeto "post"
                    JSONObject object = response.getJSONObject("post");

                    //Parsear objeto
                    Post post = gson.fromJson(object.toString(), Post.class);

                   /* // Asignar color del fondo
                    switch (post.getFilters().get(0)) {
                        case "Salud":
                            cabecera.setBackgroundColor(getResources().getColor(R.color.saludColor));
                            break;
                        case "Finanzas":
                            cabecera.setBackgroundColor(getResources().getColor(R.color.finanzasColor));
                            break;
                        case "Espiritual":
                            cabecera.setBackgroundColor(getResources().getColor(R.color.espiritualColor));
                            break;
                        case "Profesional":
                            cabecera.setBackgroundColor(getResources().getColor(R.color.profesionalColor));
                            break;
                        case "Material":
                            cabecera.setBackgroundColor(getResources().getColor(R.color.materialColor));
                            break;
                    }
*/
                    // Seteando valores en los views
                    name.setText(post.getName());
                    description.setText(post.getDescription());
                    price.setText(post.getPrice());
                    publishDate.setText(post.getDate());
                    seller.setText(post.getSeller().getUser());

                    break;

                case "2":
                    String mensaje2 = response.getString("mensaje");
                    Toast.makeText(
                            getActivity(),
                            mensaje2,
                            Toast.LENGTH_LONG).show();
                    break;

                case "3":
                    String mensaje3 = response.getString("mensaje");
                    Toast.makeText(
                            getActivity(),
                            mensaje3,
                            Toast.LENGTH_LONG).show();
                    break;
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}