package es.us.garagesale.Activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.*;

import es.us.garagesale.DataAccess.DatabaseManager;
import es.us.garagesale.DataAccess.IOffersConsumer;
import es.us.garagesale.DataAccess.ISuccessConsumer;
import es.us.garagesale.DataAccess.PhotoDownloader;
import es.us.garagesale.Src.Offer;

import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

import es.us.garagesale.R;


public class OfferListActivity extends Activity
{
    class RequestCode
    {
        public static final int CREATE_OFFER = 1;
    }

    private LinearLayout linearLayout = null;
    private LayoutInflater linearLayoutInflater = null;
    private FloatingActionButton buttonAddOffer = null;
    private ImageButton btnPersonalArea, btnSearchTag;
    private TextView noResults = null;
    private SearchView search = null;
    private ArrayList<Offer> allOffers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_list);

        linearLayout = (LinearLayout)findViewById(R.id.offer_list_content);
        linearLayoutInflater = LayoutInflater.from(this);
        buttonAddOffer = findViewById(R.id.fab_add_offer);
        btnPersonalArea = findViewById(R.id.imgbtn_personal_area);
        noResults = findViewById(R.id.tv_no_results);
        search = findViewById(R.id.search_view);
        search.setQueryHint("Qué estás buscando?");
        btnSearchTag = findViewById(R.id.imgbtn_filter);
        //Crea evento al clickear la cruz del searchview
        int searchCloseButtonId = search.getContext().getResources()
                .getIdentifier("android:id/search_close_btn", null, null);
        ImageView closeButton = (ImageView) this.search.findViewById(searchCloseButtonId);

        search.setVisibility(View.GONE);

        DatabaseManager.loadOffers(this, new IOffersConsumer() {
            @Override
            public void consume(Offer[] offers) {
                ArrayList<Offer> activeOffers = Offer.removeInactiveOffers(offers);
                displayOffers(activeOffers);
                allOffers = activeOffers;
            }
        });

        buttonAddOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent createOfferActivityIntent = new Intent(getApplicationContext(), OfferCreationActivity.class);
                startActivityForResult(createOfferActivityIntent, RequestCode.CREATE_OFFER);
            }
        });

        btnPersonalArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewProfileActivity = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(viewProfileActivity);
            }
        });

        btnSearchTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search.setVisibility(View.VISIBLE);
            }
        });

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                DatabaseManager.loadFilteredOffers(query, query, OfferListActivity.this, new IOffersConsumer() {
                    @Override
                    public void consume(Offer[] offers)
                    {
                        ArrayList<Offer> activeFilteredOffers = Offer.removeInactiveOffers(offers);
                        displayOffers(activeFilteredOffers);
                    }
                });
                return false;
            }

            //BORRAR
            @Override
            public boolean onQueryTextChange(String newText) {

                return false;
            }
        });

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search.setQuery("", false);
                search.clearFocus();
                search.setVisibility(View.GONE);
                displayOffers(allOffers);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(requestCode == RequestCode.CREATE_OFFER)
        {
            if(resultCode == OfferCreationActivity.ResultCode.CREATION_COMPLETED) restartActivity();
        }
    }


    private void inflateOffer(final Offer inflatingOffer)
    {
        View inflatedOffer = linearLayoutInflater.inflate(R.layout.offer_list_item, null);

        TextView inflatedOfferItemTitle = inflatedOffer.findViewById(R.id.tv_offer_item_title);
        inflatedOfferItemTitle.setText(inflatingOffer.getName());

        TextView inflatedOfferItemPrice = inflatedOffer.findViewById(R.id.tv_offer_item_price);
        inflatedOfferItemPrice.setText("Precio original: " + inflatingOffer.getPrice() + getString(R.string.currency));

        if(inflatingOffer.hasPhotos())
        {
            ImageView inflatedOfferPhotoView = inflatedOffer.findViewById(R.id.imgv_offer_item_photo);
            inflatedOfferPhotoView.setImageBitmap(inflatingOffer.getPhotos().get(0));
        }

        inflatedOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent createOfferDetailActivityIntent = new Intent(getApplicationContext(), OfferDetailActivity.class);
                Bundle extras = new Bundle();
                extras.putInt("id",inflatingOffer.getId());
                extras.putString("username",inflatingOffer.getSellerUsername());
                createOfferDetailActivityIntent.putExtras(extras);
                startActivity(createOfferDetailActivityIntent);
            }
        });

        linearLayout.addView(inflatedOffer);
    }


    private void displayOffers(ArrayList<Offer> photolessOffers)
    {
        linearLayout.removeAllViews();

        if(photolessOffers.size() == 0)
        {
            showNoOffersMessage();
            return;
        }

        noResults.setVisibility(View.GONE);

        for(final Offer photoAddingOffer : photolessOffers)
        {
            if(photoAddingOffer.hasPhotos()) continue;

            final ArrayList<Bitmap> photosBuffer = new ArrayList<>();
            PhotoDownloader photoDownloader = new PhotoDownloader();
            photoDownloader.download(this, photoAddingOffer.getId(), true, photosBuffer, new ISuccessConsumer() {
                @Override
                public void consume(boolean wasSuccessful)
                {
                    if(wasSuccessful)
                    {
                        photoAddingOffer.setPhotos(photosBuffer);
                    }
                    inflateOffer(photoAddingOffer);
                }
            });
        }
    }


    private void showNoOffersMessage()
    {
        noResults.setVisibility(View.VISIBLE);

        View inflatedOffer = linearLayoutInflater.inflate(R.layout.offer_list_empty, null);

        TextView inflatedOfferItemTitle = inflatedOffer.findViewById(R.id.tv_offer_item_title);
        inflatedOfferItemTitle.setText("No hay ofertas disponibles");

        linearLayout.addView(inflatedOffer);
    }


    private void restartActivity()
    {
        finish();
        startActivity(getIntent());
    }
}