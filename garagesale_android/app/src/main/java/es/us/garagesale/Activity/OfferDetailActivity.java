package es.us.garagesale.Activity;

import android.app.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

import es.us.garagesale.DataAccess.DatabaseManager;
import es.us.garagesale.DataAccess.IInterestedConsumer;
import es.us.garagesale.DataAccess.IOfferConsumer;
import es.us.garagesale.DataAccess.ISuccessConsumer;
import es.us.garagesale.DataAccess.IUserRankingConsumer;
import es.us.garagesale.DataAccess.PhotoDownloader;
import es.us.garagesale.R;
import es.us.garagesale.Src.Interested;
import es.us.garagesale.Src.Offer;
import es.us.garagesale.Src.OfferTool;

import static android.util.TypedValue.COMPLEX_UNIT_SP;


public class OfferDetailActivity extends Activity {

    private int selectedOfferId;
    private TextView title, detail, state, remainingTime, currentPrice, currentOffers, createInterested, location, googleMaps, seller, reputationText;
    private ConstraintLayout showLocation;
    private View map;
    private LinearLayout locationLayout;
    private Offer offer;
    private String actualUser;
    private String offerUser;
    private LinearLayout photoSection;
    private ImageView photoView;
    private Button previousPhotoButton, nextPhotoButton;

    private Bitmap showingPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_details);

        title = findViewById(R.id.tvOfferDetailsOfferTitle);
        reputationText = findViewById(R.id.tvReputation);
        photoSection = findViewById(R.id.ll_offer_details_photos);
        photoView = findViewById(R.id.imgv_offer_photo);
        previousPhotoButton = findViewById(R.id.btn_offer_details_photos_prev);
        nextPhotoButton = findViewById(R.id.btn_offer_details_photos_next);
        seller = findViewById(R.id.tvSeller);
        googleMaps = findViewById(R.id.tv_btn_show_map);
        map = findViewById(R.id.imgv_btn_show_map);
        detail = findViewById(R.id.tv_offer_details_description);
        state =  findViewById(R.id.tvOfferDetailsOfferUsedState);
        remainingTime = findViewById(R.id.tv_offer_details_remaining_time);
        currentPrice = findViewById(R.id.tv_offer_details_current_price);
        location = findViewById(R.id.tv_offer_details_location);
        locationLayout = findViewById(R.id.ll_offer_location);
        currentOffers = findViewById(R.id.tv_offer_details_offer_count);
        showLocation = findViewById(R.id.cl_btn_show_map);
        createInterested = findViewById(R.id.tv_btn_bid_label);
        createInterested.setText("Propón un precio!");

        preparePreviousPhotoButton();
        prepareNextPhotoButton();

        SharedPreferences sharedPreferences = this.getSharedPreferences("login", MODE_PRIVATE);
        actualUser = sharedPreferences.getString("username", null);

        offerUser = getIntent().getStringExtra("username");
        selectedOfferId = getIntent().getIntExtra("id", 0);

        DatabaseManager.loadOffer(selectedOfferId, this, new IOfferConsumer() {
            @Override
            public void consume(Offer receivedOffer) {
                offer = receivedOffer;
                displayOffer();
            }
        });
    }


    private void displayOffer()
    {
        final ArrayList<Bitmap> photosBuffer = new ArrayList<>();
        PhotoDownloader photoDownloader = new PhotoDownloader();
        photoDownloader.download(this, offer.getId(), false, photosBuffer, new ISuccessConsumer() {
            @Override
            public void consume(boolean wasSuccessful) {
                if(wasSuccessful)
                {
                    freezePhotoViewSize();
                    offer.setPhotos(photosBuffer);
                    showPhoto(offer.getPhotos().get(0));
                }
                else
                {
                    ((ViewGroup)photoSection.getParent()).removeView(photoSection);
                }
            }
        });

        title.setText(offer.getName());
        seller.setText("Vendedor: "+offer.getSellerUsername());
        final RatingBar reputation = findViewById(R.id.ratingBarDetail);
        reputation.setEnabled(false);
        DatabaseManager.getUserRanking(actualUser, OfferDetailActivity.this, new IUserRankingConsumer() {
            @Override
            public void consume(float receivedRanking) {
                float totalRanking = receivedRanking;
                reputation.setRating(totalRanking);
                reputationText.setText("Reputacion: ");

            }
        });
        detail.setText(offer.getDescription()+" \n"+ "Precio original: "+ getString(R.string.currency) +offer.getPrice());
        CharSequence condition = OfferTool.getCharSequenceFromCondition(offer.getCondition(), this);
        state.setText(condition);
        remainingTime.setText(offer.calculateRemainingTime()+" horas");
        currentOffers.setText("Nadie ha ofertado aun");
        currentPrice.setText("0"+getString(R.string.currency)+" es la oferta mas alta");
        getMaxOffer(offer);
        if (offer.getCoordinates().latitude!=0.0&&offer.getCoordinates().longitude!=0.0){
            showLocation.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    openGoogleMaps(offer);
                }
            });

            CharSequence placeName = offer.getCityName();
            LatLng coordinates = offer.getCoordinates();
            CharSequence locationText = getString(R.string.offer_location, placeName.toString(), coordinates.toString());
            location.setText(locationText);
            location.setTextSize(COMPLEX_UNIT_SP, 12);
        }else{
            showLocation.setBackgroundResource(0);
            location.setVisibility(View.INVISIBLE);
            map.setVisibility(View.INVISIBLE);
            googleMaps.setVisibility(View.INVISIBLE);
        }
        createInterested.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isTheUsersOffer()){
                    Intent createInterestedActivityIntent = new Intent(getApplicationContext(), InterestedCreationActivity.class);
                    Bundle extras = new Bundle();
                    extras.putInt("id", selectedOfferId);
                    extras.putString("name",offer.getName());
                    createInterestedActivityIntent.putExtras(extras);
                    startActivity(createInterestedActivityIntent);
                }
                else{
                    createInterested.setText("No es posible autotarifar");
                }
            }
        });

    }

    private boolean isTheUsersOffer(){
        boolean areEqual;
        if(actualUser.equals(offerUser)) {
            areEqual = true;
        }else{
            areEqual = false;
        }
        return areEqual;
    }

    private void getMaxOffer(Offer offer){
        DatabaseManager.loadInterested(offer.getId(),this, new IInterestedConsumer() {
            @Override
            public void consume(Interested[] interested) {
                currentOffers.setText("Hay " + interested.length + " ofertas");
                if(interested.length>0){
                    currentPrice.setText(getMaxInterested(interested)+ getString(R.string.currency)+ " es la oferta mas alta");
                }
                else{
                    currentPrice.setText("Nadie ha ofertado aun");
                }
            }
        });
    }

    private int getMaxInterested(Interested[] interested){
        int max=0;
        for(Interested i : interested)
        {
            if(i.getPrice()>max){
                max= i.getPrice();
            }
        }
        return max;
    }

    private void openGoogleMaps(Offer receivedOffer){
        LatLng coordinates = receivedOffer.getCoordinates();
        double lat = coordinates.latitude;
        double lng = coordinates.longitude;
        String label = "Ubicacion del producto";
        String uriBegin = "geo:" + lat + "," + lng;
        String query = lat + "," + lng + "(" + label + ")";
        String encodedQuery = Uri.encode(query);
        String uriString = uriBegin + "?q=" + encodedQuery + "&z=16";
        Uri uri = Uri.parse(uriString);
        Intent mapIntent = new Intent(android.content.Intent.ACTION_VIEW, uri);
        startActivity(mapIntent);
    }

    
    private void preparePreviousPhotoButton()
    {
        previousPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(!offer.hasPhotos()) return;

                Bitmap settingPhoto = offer.getNeighbourPhoto(showingPhoto, -1);
                if(settingPhoto != null)
                {
                    showPhoto(settingPhoto);
                }
            }
        });
    }
    
    
    private void prepareNextPhotoButton()
    {
        nextPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(!offer.hasPhotos()) return;

                Bitmap settingPhoto = offer.getNeighbourPhoto(showingPhoto, 1);
                if(settingPhoto != null)
                {
                    showPhoto(settingPhoto);
                }
            }
        });
    }


    private void freezePhotoViewSize()
    {
        int currentWidth = photoView.getWidth();
        int currentHeight = photoView.getHeight();
        LinearLayout.LayoutParams fixParams = new LinearLayout.LayoutParams(currentWidth, currentHeight);

        photoView.setLayoutParams(fixParams);
    }


    private void showPhoto(Bitmap photo)
    {
        showingPhoto = photo;
        photoView.setImageBitmap(photo);
    }
}