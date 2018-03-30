package com.example.mariaventura.pruebaframe.Fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.mariaventura.pruebaframe.DataAccess.Constantes;
import com.example.mariaventura.pruebaframe.DataAccess.VolleySingleton;
import com.example.mariaventura.pruebaframe.R;
import com.google.gson.Gson;
import com.example.mariaventura.pruebaframe.Src.Offer;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * Fragmento con formulario para actualizar la meta
 */
public class UpdateFragment extends Fragment {
    /*
    Etiqueta de depuración
     */
    private static final String TAG = UpdateFragment.class.getSimpleName();

    /*
    Etiqueta de valor extra para modo edición
     */
    private static final String EXTRA_ID = "CODE";

    /*
    Controles
    */
    private EditText name_input;
    private EditText precio_input;
    private EditText descripcion_input;
    private TextView fechaEjemplo_text;
    private Spinner filtro_spinner;

    /*
    Valor del argumento extra
     */
    private String code;

    /**
     * Es la oferta obtenida como respuesta de la petición HTTP
     */
    private Offer originalOffer;

    /**
     * Instancia Gson para el parsing Json
     */
    private Gson gson = new Gson();


    public UpdateFragment() {
    }

    /**
     * Crea un nuevo fragmento basado en un argumento
     *
     * @param extra Argumento de entrada
     * @return Nuevo fragmento
     */
    public static Fragment createInstance(String extra) {
        UpdateFragment detailFragment = new UpdateFragment();
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_ID, extra);
        detailFragment.setArguments(bundle);
        return detailFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflando layout del fragmento
        View v = inflater.inflate(R.layout.fragment_update, container, false);

        // Obtención de instancias controles
        name_input = (EditText) v.findViewById(R.id.name_input);
        precio_input = (EditText) v.findViewById(R.id.precio_input);
        descripcion_input = (EditText) v.findViewById(R.id.descripcion_input);
        fechaEjemplo_text = (TextView) v.findViewById(R.id.fechaEjemplo_text);
        filtro_spinner = (Spinner) v.findViewById(R.id.filtro_spinner);


        fechaEjemplo_text.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DialogFragment picker = new DatePickerFragment();
                        picker.show(getFragmentManager(), "datePicker");

                    }
                }
        );

        // Obtener valor extra
        code = getArguments().getString(EXTRA_ID);

        if (code != null) {
            cargarDatos();
        }

        return v;
    }

    /**
     * Obtiene los datos desde el servidor
     */
    private void cargarDatos() {
        // Añadiendo idMeta como parámetro a la URL
        String newURL = Constantes.GET_BY_ID + "?code=" + code;

        // Consultar el detalle de la meta
        VolleySingleton.getInstance(getActivity()).addToRequestQueue(
                new JsonObjectRequest(
                        Request.Method.GET,
                        newURL,
                        (String)null,
                        new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                // Procesa la respuesta GET_BY_ID
                                procesarRespuestaGet(response);
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
     * Procesa la respuesta de obtención obtenida desde el sevidor     *
     */
    private void procesarRespuestaGet(JSONObject response) {

        try {
            String estado = response.getString("estado");

            switch (estado) {
                case "1":
                    JSONObject meta = response.getJSONObject("meta");
                    // Guardar instancia
                    originalOffer = gson.fromJson(meta.toString(), Offer.class);
                    // Setear valores de la meta
                    cargarViews(originalOffer);
                    break;

                case "2":
                    String mensaje = response.getString("mensaje");
                    // Mostrar mensaje
                    Toast.makeText(
                            getActivity(),
                            mensaje,
                            Toast.LENGTH_LONG).show();
                    // Enviar código de falla
                    getActivity().setResult(Activity.RESULT_CANCELED);
                    // Terminar actividad
                    getActivity().finish();
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Carga los datos iniciales del formulario con los
     * atributos de un objeto {@link Offer}
     *
     * @param offer Instancia
     */
    private void cargarViews(Offer offer) {
        // Seteando valores de la respuesta
        name_input.setText(offer.getName());
        descripcion_input.setText(offer.getDescription());
        fechaEjemplo_text.setText(offer.getTimestamp().toString());


        // Obteniendo acceso a los array de strings para filtros
        String[] filtros = getResources().getStringArray(R.array.entradas_filtro);

        // Obteniendo la posición del spinner filtro
        int posicion_filtro = 0;
        for (int i = 0; i < filtros.length; i++) {
            if (filtros[i].compareTo(offer.getTags().get(0)) == 0) {
                posicion_filtro = i;
                break;
            }
        }

        // Setear selección del Spinner de categorías
        filtro_spinner.setSelection(posicion_filtro);

    }

    /**
     * Compara los datos actuales con aquellos que se obtuvieron
     * por primera vez en la respuesta HTTP
     *
     * @return true si los datos no han cambiado, de lo contrario false
     */
    public boolean validarCambios() {
        return originalOffer.equals(obtenederDatos());
    }

    /**
     * Retorna en una nueva meta creada a partir
     * de los datos del formulario actual
     *
     * @return Instancia {@link Offer}
     */
    private Offer obtenederDatos() {

        String name = name_input.getText().toString();
        String description = descripcion_input.getText().toString();

        Timestamp date = null;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
            Date parsedDate = dateFormat.parse(fechaEjemplo_text.getText().toString());
            date = new java.sql.Timestamp(parsedDate.getTime());
        }
        catch(Exception e) {};

        String filter = (String) filtro_spinner.getSelectedItem();
        String price = precio_input.getText().toString();

        return new Offer(name, description, Float.parseFloat(price), null, date, false,  null, null);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true); // Contribución a la AB
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:// CONFIRMAR
                if (!validarCambios())
                    saveOffer();
                else
                    // Terminar actividad, ya que no hay cambios
                    getActivity().finish();
                return true;

            case R.id.action_delete:// ELIMINAR
                //mostrarDialogo(R.string.dialog_delete_msg);
                break;

            case R.id.action_discard:// DESCARTAR
                if (!validarCambios()) {
                    //mostrarDialogo(R.string.dialog_discard_msg);
                } else
                    // Terminar actividad, ya que no hay cambios
                    getActivity().finish();
                break;

        }
        ;

        return super.onOptionsItemSelected(item);
    }

    /**
     * Guarda los cambios de una meta editada.
     * <p>
     * Si está en modo inserción, entonces crea una nueva
     * meta en la base de datos
     */
    private void saveOffer() {

        // Obtener valores actuales de los controles
        String name = name_input.getText().toString();
        String description = descripcion_input.getText().toString();
        String date = fechaEjemplo_text.getText().toString();
        String filter = (String) filtro_spinner.getSelectedItem();
        String price = precio_input.getText().toString();
        String code = String.valueOf(0);

        HashMap<String, String> map = new HashMap<>();// Mapeo previo

        map.put("code",code);
        map.put("name", name);
        map.put("descrition", description);
        map.put("date", date);
        map.put("price", price);
        map.put("filter", filter);

        // Crear nuevo objeto Json basado en el mapa
        JSONObject jobject = new JSONObject(map);

        // Depurando objeto Json...
        Log.d(TAG, jobject.toString());

        // Actualizar datos en el servidor
        VolleySingleton.getInstance(getActivity()).addToRequestQueue(
                new JsonObjectRequest(
                        Request.Method.POST,
                        Constantes.UPDATE,
                        jobject,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                procesarRespuestaActualizar(response);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d(TAG, "Error Volley: " + error.getMessage());
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

    /**
     * Procesa todos las tareas para eliminar
     * una meta en la aplicación. Este método solo se usa
     * en la edición
     */
    public void deleteOffer() {

        HashMap<String, String> map = new HashMap<>();// MAPEO

        map.put("code", code);// Identificador

        JSONObject jobject = new JSONObject(map);// Objeto Json

        // Eliminar datos en el servidor
        VolleySingleton.getInstance(getActivity()).addToRequestQueue(
                new JsonObjectRequest(
                        Request.Method.POST,
                        Constantes.DELETE,
                        jobject,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                // Procesar la respuesta
                                procesarRespuestaEliminar(response);

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d(TAG, "Error Volley: " + error.getMessage());
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

    /**
     * Procesa la respuesta de eliminación obtenida desde el sevidor
     */
    private void procesarRespuestaEliminar(JSONObject response) {

        try {
            // Obtener estado
            String estado = response.getString("estado");
            // Obtener mensaje
            String mensaje = response.getString("mensaje");

            switch (estado) {
                case "1":
                    // Mostrar mensaje
                    Toast.makeText(
                            getActivity(),
                            mensaje,
                            Toast.LENGTH_LONG).show();
                    // Enviar código de éxito
                    getActivity().setResult(203);
                    // Terminar actividad
                    getActivity().finish();
                    break;

                case "2":
                    // Mostrar mensaje
                    Toast.makeText(
                            getActivity(),
                            mensaje,
                            Toast.LENGTH_LONG).show();
                    // Enviar código de falla
                    getActivity().setResult(Activity.RESULT_CANCELED);
                    // Terminar actividad
                    getActivity().finish();
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    /**
     * Procesa la respuesta de actualización obtenida desde el sevidor
     */
    private void procesarRespuestaActualizar(JSONObject response) {

        try {
            // Obtener estado
            String estado = response.getString("estado");
            // Obtener mensaje
            String mensaje = response.getString("mensaje");

            switch (estado) {
                case "1":
                    // Mostrar mensaje
                    Toast.makeText(
                            getActivity(),
                            mensaje,
                            Toast.LENGTH_LONG).show();
                    // Enviar código de éxito
                    getActivity().setResult(Activity.RESULT_OK);
                    // Terminar actividad
                    getActivity().finish();
                    break;

                case "2":
                    // Mostrar mensaje
                    Toast.makeText(
                            getActivity(),
                            mensaje,
                            Toast.LENGTH_LONG).show();
                    // Enviar código de falla
                    getActivity().setResult(Activity.RESULT_CANCELED);
                    // Terminar actividad
                    getActivity().finish();
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    /**
     * Actualiza la fecha del campo fechaEjemplo_text
     *
     * @param ano Año
     * @param mes Mes
     * @param dia Día
     */
    public void actualizarFecha(int ano, int mes, int dia) {
        // Setear en el textview la fecha
        fechaEjemplo_text.setText(ano + "-" + (mes + 1) + "-" + dia);
    }

    /**
     * Muestra un diálogo de confirmación, cuyo mensaje esta
     * basado en el parámetro identificador de Strings
     *
     * @param id Parámetro
     */
    private void mostrarDialogo(int id) {
        DialogFragment dialogo = ConfirmDialogFragment.
                createInstance(
                        getResources().
                                getString(id));
        dialogo.show(getFragmentManager(), "ConfirmDialog");
    }

}
