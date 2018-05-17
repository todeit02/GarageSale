package es.us.garagesale.DataAccess;

import android.app.Activity;
import android.graphics.Bitmap;
import android.util.Base64;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Tobias on 11/05/2018.
 */

public class PhotoUploader extends PhotoExchanger
{
    private final static int JPEG_QUALITY = 95;

    private int uploadingIndex = 0;
    private ArrayList<Bitmap> photos = null;

    private boolean wasUploadSuccessful = false;


    public void upload(final Activity callingActivity, ArrayList<Bitmap> photos, int offerId, final ISuccessConsumer onUploadFinishedConsumer)
    {
        wasUploadSuccessful = true;

        this.callingActivity = callingActivity;
        this.photos = photos;
        this.offerId = offerId;
        this.onFinishConsumer = onUploadFinishedConsumer;

        uploadLoop();
    }


    private void uploadLoop()
    {
        uploadPhoto(new ISuccessConsumer()
        {
            @Override
            public void consume(boolean wasSuccessful)
            {
                uploadPhoto(new ISuccessConsumer() {
                    @Override
                    public void consume(boolean wasSuccessful) {
                        if(!wasSuccessful)
                        {
                            wasUploadSuccessful = false;
                            onFinishConsumer.consume(wasUploadSuccessful);
                            return;
                        }
                        uploadLoop();
                    }
                });
            }
        });
    }


    private void uploadPhoto(final ISuccessConsumer onPhotoUploadConsumer)
    {
        if(uploadingIndex >= photos.size())
        {
            onFinishConsumer.consume(wasUploadSuccessful);
            return;
        }

        Bitmap uploadingPhoto = photos.get(uploadingIndex);

        ByteArrayOutputStream photoDataStream = new ByteArrayOutputStream();
        uploadingPhoto.compress(Bitmap.CompressFormat.JPEG, JPEG_QUALITY, photoDataStream);

        HashMap<String, String> map = new HashMap<>();

        map.put("offerId", Integer.toString(offerId));

        byte[] imageData = photoDataStream.toByteArray();
        String encodedImage = Base64.encodeToString(imageData, Base64.DEFAULT);
        map.put("image", encodedImage);

        JSONObject json = new JSONObject(map);

        VolleySingleton.
                getInstance(callingActivity).
                addToRequestQueue(
                        new JsonObjectRequest(
                                Request.Method.POST,
                                Constantes.UPLOAD_PHOTO,
                                json,
                                new Response.Listener<JSONObject>() {

                                    @Override
                                    public void onResponse(JSONObject response) {
                                        onPhotoUploadConsumer.consume(true);
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        onPhotoUploadConsumer.consume(false);
                                    }
                                }
                        )
                );

        uploadingIndex++;
    }
}
