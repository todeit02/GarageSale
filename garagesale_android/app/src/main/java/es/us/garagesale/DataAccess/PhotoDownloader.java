package es.us.garagesale.DataAccess;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class PhotoDownloader extends PhotoExchanger
{
    private ArrayList<Bitmap> photosBuffer = null;


    public void download(final Activity callingActivity, int offerId, ArrayList<Bitmap> photosBuffer, final ISuccessConsumer onDownloadFinishedConsumer)
    {
        this.callingActivity = callingActivity;
        this.photosBuffer = photosBuffer;
        this.offerId = offerId;
        this.onFinishConsumer = onDownloadFinishedConsumer;

        requestPhotos();
    }


    private void requestPhotos()
    {
        VolleySingleton.
                getInstance(callingActivity).
                addToRequestQueue(
                        new JsonObjectRequest(
                                Request.Method.GET,
                                Constantes.GET_ALL_PHOTOS + "?offerId=" + offerId,
                                (String)null,
                                new Response.Listener<JSONObject>() {

                                    @Override
                                    public void onResponse(JSONObject response)
                                    {
                                        try
                                        {
                                            String state = response.getString("estado");
                                            switch (state)
                                            {
                                                case Constantes.SUCCESS_RESPONSE:
                                                    JSONArray photoContentsJson = response.getJSONArray("photos");
                                                    Gson gson = new Gson();
                                                    String[] photoContents = gson.fromJson(photoContentsJson.toString(), String[].class);

                                                    onServerResponse(true, photoContents);
                                                    break;

                                                case Constantes.FAIL_RESPONSE:
                                                    String failMessage = response.getString("mensaje");
                                                    System.out.println(failMessage);

                                                    onServerResponse(false, null);
                                                    break;
                                            }
                                        }
                                        catch (JSONException e)
                                        {
                                            e.printStackTrace();
                                        }
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        onServerResponse(false, null);
                                    }
                                }
                        )
                );
    }


    private void onServerResponse(boolean wasDownloadSuccessful, String[] photoContents)
    {
        if(!wasDownloadSuccessful || photoContents == null || photoContents.length == 0)
        {
            onFinishConsumer.consume(false);
            return;
        }

        for(String deserializingPhotoContent : photoContents)
        {
            byte[] photoBytes = Base64.decode(deserializingPhotoContent, Base64.DEFAULT);
            Bitmap deserializedPhoto = BitmapFactory.decodeByteArray(photoBytes, 0, photoBytes.length);

            if(deserializedPhoto == null)
            {
                onFinishConsumer.consume(false);
                return;
            }

            photosBuffer.add(deserializedPhoto);
        }

        onFinishConsumer.consume(true);
    }
}
