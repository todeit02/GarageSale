package es.us.garagesale.DataAccess;

import android.app.Activity;
import android.graphics.Bitmap;

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

public class PhotoUploader
{
    private final static int JPEG_QUALITY = 95;

    private Activity uploadingActivity = null;
    private int uploadingIndex = 0;
    private ArrayList<Bitmap> photos = null;
    private int offerId = 0;
    private ISuccessConsumer afterUploadResponseConsumer = null;

    private boolean wasUploadSuccessful = false;


    public void upload(final Activity callingActivity, ArrayList<Bitmap> photos, int offerId, final ISuccessConsumer onUploadFinishedConsumer)
    {
        wasUploadSuccessful = true;

        this.uploadingActivity = callingActivity;
        this.photos = photos;
        this.offerId = offerId;
        this.afterUploadResponseConsumer = onUploadFinishedConsumer;

        uploadLoop();
    }


    private void uploadLoop()
    {
        uploadPhoto(uploadingActivity, offerId, new ISuccessConsumer()
        {
            @Override
            public void consume(boolean wasSuccessful)
            {
                uploadPhoto(uploadingActivity, offerId, new ISuccessConsumer() {
                    @Override
                    public void consume(boolean wasSuccessful) {
                        if(!wasSuccessful)
                        {
                            wasUploadSuccessful = false;
                            afterUploadResponseConsumer.consume(wasUploadSuccessful);
                            return;
                        }
                        uploadLoop();
                    }
                });
            }
        });
    }


    private void uploadPhoto(Activity callingActivity, int offerId, final ISuccessConsumer onPhotoUploadConsumer)
    {
        if(uploadingIndex >= photos.size())
        {
            afterUploadResponseConsumer.consume(wasUploadSuccessful);
            return;
        }

        Bitmap uploadingPhoto = photos.get(uploadingIndex);

        ByteArrayOutputStream photoDataStream = new ByteArrayOutputStream();
        uploadingPhoto.compress(Bitmap.CompressFormat.JPEG, JPEG_QUALITY, photoDataStream);

        HashMap<String, String> map = new HashMap<>();

        map.put("offerId", Integer.toString(offerId));
        map.put("image", photoDataStream.toString());

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
                                        System.out.println("Photo uploaded.");
                                        onPhotoUploadConsumer.consume(true);
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        System.out.println("Error on upload.");
                                        onPhotoUploadConsumer.consume(false);
                                    }
                                }
                        )
                );
    }
}
