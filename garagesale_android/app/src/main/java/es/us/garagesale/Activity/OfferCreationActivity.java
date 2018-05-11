package es.us.garagesale.Activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.FileProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;

import es.us.garagesale.DataAccess.DatabaseManager;
import es.us.garagesale.DataAccess.IIdConsumer;
import es.us.garagesale.DataAccess.ISuccessConsumer;
import es.us.garagesale.DataAccess.PhotoUploader;
import es.us.garagesale.R;
import es.us.garagesale.Src.ButtonGroupAppearanceManager;
import es.us.garagesale.Src.Offer;
import es.us.garagesale.Src.OfferCondition;
import es.us.garagesale.Src.OfferTool;
import es.us.garagesale.Src.TextLengthLimiter;

import static android.util.TypedValue.COMPLEX_UNIT_SP;

/**
 * Created by Tobias on 18/04/2018.
 */

public class OfferCreationActivity extends Activity
{
    private static final int PHOTO_INTENT_REQUEST_CODE = 1;
    private static final int PLACE_PICKER_INTENT_REQUEST_CODE = 2;

    private LinearLayout segmentsContainer = null;
    private EditText titleEdit = null;
    private ArrayList<Button> conditionButtons = new ArrayList<>();
    private EditText tagsEdit = null;
    private EditText descriptionEdit = null;
    private LinearLayout photoLocationSegment = null;
    private View addFirstPhotoButton = null;
    private View addLocationButton = null;
    private EditText priceEdit = null;
    private Button shortDurationButton = null;
    private Button mediumDurationButton = null;
    private Button longDurationButton = null;
    private ArrayList<Button> durationButtons = new ArrayList<>();
    private ConstraintLayout publishButton = null;

    private Offer workingOffer = new Offer();
    private String currentPhotoPath = null;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_offer);

        Intent leadingIntent = getIntent();
        String[] offerTags = leadingIntent.getStringArrayExtra("selected_filter_tags");

        findViewReferences();

        //prepareEditTextLimits();
        prepareConditionButtons();
        //fillFilterTags(offerTags);
        prepareAddPhotoButton(addFirstPhotoButton);
        prepareAddLocationButton(addLocationButton);
        prepareDurationButtons();
        preparePublishButton();

        SharedPreferences sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
        String loginUsername = sharedPreferences.getString("username", "");
        workingOffer.setSellerUsername(loginUsername);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PHOTO_INTENT_REQUEST_CODE && resultCode == RESULT_OK)
        {
            handlePhotoResult(data);
        }
        else if (requestCode == PLACE_PICKER_INTENT_REQUEST_CODE && resultCode == RESULT_OK)
        {
            handleLocationResult(data);
        }
    }


    private void findViewReferences()
    {
        titleEdit = findViewById(R.id.et_title);

        ViewGroup conditionSelectors = findViewById(R.id.ll_condition_selection);
        for(int i = 0; i < conditionSelectors.getChildCount(); i++)
        {
            conditionButtons.add( (Button)conditionSelectors.getChildAt(i) );
        }

        segmentsContainer = findViewById(R.id.ll_segments_container);
        tagsEdit = findViewById(R.id.et_tags);
        descriptionEdit = findViewById(R.id.et_description);
        photoLocationSegment = findViewById(R.id.ll_photo_location);
        addFirstPhotoButton = findViewById(R.id.cl_btn_add_photo);
        addLocationButton = findViewById(R.id.cl_btn_add_location);
        priceEdit = findViewById(R.id.et_price);

        shortDurationButton = findViewById(R.id.btn_3_days);
        mediumDurationButton = findViewById(R.id.btn_7_days);
        longDurationButton = findViewById(R.id.btn_14_days);
        durationButtons.add(shortDurationButton);
        durationButtons.add(mediumDurationButton);
        durationButtons.add(longDurationButton);

        publishButton = findViewById(R.id.cl_btn_publish);
    }


    private void prepareEditTextLimits()
    {
        int maxTitleCharacters = getResources().getInteger(R.integer.max_offer_title_length);
        int maxTagsCharacters = getResources().getInteger(R.integer.max_offer_tags_length);
        int maxDescriptionCharacters = getResources().getInteger(R.integer.max_offer_description_length);

        titleEdit.addTextChangedListener(new TextLengthLimiter(titleEdit, maxTitleCharacters, this));
        tagsEdit.addTextChangedListener(new TextLengthLimiter(tagsEdit, maxTagsCharacters, this));
        descriptionEdit.addTextChangedListener(new TextLengthLimiter(descriptionEdit, maxDescriptionCharacters, this));
    }


    private void prepareConditionButtons()
    {
        final ButtonGroupAppearanceManager conditionButtonsAppearanceManager = new ButtonGroupAppearanceManager(conditionButtons, this);
        View.OnClickListener conditionClickListener = new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                conditionButtonsAppearanceManager.onClick(v);

                CharSequence selectedConditionText = ((Button) v).getText();
                OfferCondition selectedCondition = OfferTool.getConditionFromCharSequence(selectedConditionText, OfferCreationActivity.this);

                workingOffer.setCondition(selectedCondition);
            }
        };

        for(Button listenerSettingButton : conditionButtons)
        {
            listenerSettingButton.setOnClickListener(conditionClickListener);
        }
        // need to preselect value
        conditionClickListener.onClick(conditionButtons.get(0));
    }


    private void fillFilterTags(String[] preselectedTags)
    {
        StringBuilder tagsChainBuilder = new StringBuilder();

        for(String appendingTag : preselectedTags)
        {
            if(tagsChainBuilder.length() > 0) tagsChainBuilder.append(" ");
            tagsChainBuilder.append(appendingTag);
        }

        tagsEdit.setText(tagsChainBuilder.toString());
    }


    private void prepareAddPhotoButton(View addPhotoButton)
    {
        addPhotoButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                handleAddPhotoButtonClick();
            }
        });
    }


    private void prepareAddLocationButton(View addLocationButton)
    {
        addLocationButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                handleAddLocationButtonClick();
            }
        });

        Place selectedLocation = workingOffer.getLocation();
        if(selectedLocation != null) updateLocationView();
    }


    private void prepareDurationButtons()
    {
        shortDurationButton.setText(getString(R.string.offer_duration, Offer.durationsDays.get(Offer.Duration.SHORT)));
        mediumDurationButton.setText(getString(R.string.offer_duration, Offer.durationsDays.get(Offer.Duration.MEDIUM)));
        longDurationButton.setText(getString(R.string.offer_duration, Offer.durationsDays.get(Offer.Duration.LONG)));

        final ButtonGroupAppearanceManager durationButtonsAppearanceManager = new ButtonGroupAppearanceManager(durationButtons, this);
        View.OnClickListener durationClickListener = new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                durationButtonsAppearanceManager.onClick(v);

                CharSequence selectedDurationText = ((Button) v).getText();
                Offer.Duration selectedDuration = OfferTool.getDurationFromCharSequence(selectedDurationText, OfferCreationActivity.this);
                int durationDays = Offer.durationsDays.get(selectedDuration);

                workingOffer.setDurationDays(durationDays);
            }
        };

        for(Button listenerSettingButton : durationButtons)
        {
            listenerSettingButton.setOnClickListener(durationClickListener);
        }
        // need to preselect value
        durationClickListener.onClick(shortDurationButton);
    }


    private void preparePublishButton()
    {
        publishButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) { tryPublishOffer(); }
        });
    }


    private void prepareDeletePhotoButton()
    {
        View deletePhotoButton = findViewById(R.id.cl_btn_delete_photo);
        deletePhotoButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                deleteLastPhoto();
            }
        });
    }


    private void handleAddPhotoButtonClick()
    {
        Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        boolean cameraAppAvailable = (takePhotoIntent.resolveActivity(getPackageManager()) != null);
        if(!cameraAppAvailable) return;

        File photoFile = null;
        try
        {
            photoFile = createEmptyImageFile();
        }
        catch(IOException e){}

        if(photoFile == null) return;

        Uri photoUri = FileProvider.getUriForFile(this, "es.us.garagesale.fileprovider", photoFile);
        takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
        startActivityForResult(takePhotoIntent, PHOTO_INTENT_REQUEST_CODE);
    }


    private void handlePhotoResult(Intent resultIntent)
    {
        addPhotoToGallery();

        if(workingOffer.hasPhotos())
        {
            appendPhotoToScrollingContainer();
        }
        else
        {
            exchangeSegmentToTakenPhotoNoLocation();
        }

        Bitmap fullsizePhoto = getCurrentPhoto();
        workingOffer.addPhoto(fullsizePhoto);
    }


    private void handleAddLocationButtonClick()
    {
        PlacePicker.IntentBuilder pickerIntentBuilder = new PlacePicker.IntentBuilder();
        try
        {
            Intent pickerIntent = pickerIntentBuilder.build(this);
            startActivityForResult(pickerIntent, PLACE_PICKER_INTENT_REQUEST_CODE);
        }
        catch (Exception e) {}
    }


    private void handleLocationResult(Intent locationSelectionIntent)
    {
        Place selectedPlace = PlacePicker.getPlace(this, locationSelectionIntent);
        workingOffer.setLocation(selectedPlace);

        /*
        View addLocationButton = findViewById(R.id.cl_btn_add_location);
        photoLocationSegment.removeView(addLocationButton);

        LayoutInflater locationTextInflater = LayoutInflater.from(this);
        TextView locationTextView = (TextView) locationTextInflater.inflate(R.layout.create_offer_tv_chosen_location, null);
        photoLocationSegment.addView(locationTextView);
        */

        updateLocationView();
    }


    private void updateLocationView()
    {
        CharSequence placeName = workingOffer.getLocation().getName();
        LatLng coordinates = workingOffer.getLocation().getLatLng();

        TextView locationTextView = findViewById(R.id.tv_btn_add_location);
        CharSequence locationText = getString(R.string.offer_location, placeName.toString(), coordinates.toString());
        locationTextView.setText(locationText);
        locationTextView.setTextSize(COMPLEX_UNIT_SP, 12);
    }


    private void addPhotoToGallery() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(currentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }


    private void setViewToFirstTakenImage()
    {
        LinearLayout photosContainer = findViewById(R.id.ll_photos_container);
        ImageView dummyPhotoView = (ImageView) photosContainer.getChildAt(0);
        Bitmap takenResizedPhoto = getCurrentPhotoResized(dummyPhotoView.getWidth(), dummyPhotoView.getHeight());
        dummyPhotoView.setImageBitmap(takenResizedPhoto);
    }


    private void exchangeSegmentToTakenPhotoNoLocation()
    {
        LayoutInflater layoutInflater = LayoutInflater.from(this);

        View inflatedPhotoLocationSection = layoutInflater.inflate(R.layout.create_offer_photo_taken_section, null);

        final int photoLocationSegmentIndex = segmentsContainer.indexOfChild(photoLocationSegment);
        segmentsContainer.removeViewAt(photoLocationSegmentIndex);
        segmentsContainer.addView(inflatedPhotoLocationSection, photoLocationSegmentIndex);

        View appendFurtherPhotoButton = findViewById(R.id.cl_btn_add_photo);
        prepareAddPhotoButton(appendFurtherPhotoButton);
        prepareDeletePhotoButton();
        addLocationButton = findViewById(R.id.cl_btn_add_location);
        prepareAddLocationButton(addLocationButton);

        segmentsContainer.post(new Runnable() {
            @Override
            public void run() {
                setViewToFirstTakenImage();
                lockPhotoScrollSize();
            }
        });
    }

    private void lockPhotoScrollSize()
    {
        HorizontalScrollView photoScroll = findViewById(R.id.hsv_photos_scroll);
        int currentWidth = photoScroll.getWidth();
        int currentHeight = photoScroll.getHeight();
        LinearLayout.LayoutParams fixParams = new LinearLayout.LayoutParams(currentWidth, currentHeight);

        photoScroll.setLayoutParams(fixParams);
    }

    private void appendPhotoToScrollingContainer()
    {
        LinearLayout photosContainer = findViewById(R.id.ll_photos_container);
        LayoutInflater imageViewInflater = LayoutInflater.from(this);
        ImageView photoView = (ImageView) imageViewInflater.inflate(R.layout.create_offer_photo_view, null);
        photosContainer.addView(photoView);

        int presentPhotoWidth = photosContainer.getChildAt(0).getWidth();
        int presentPhotoHeight = photosContainer.getChildAt(0).getHeight();
        Bitmap resizedTakenPhoto = getCurrentPhotoResized(presentPhotoWidth, presentPhotoHeight);
        photoView.setImageBitmap(resizedTakenPhoto);
    }

    private void deleteLastPhoto()
    {
        LinearLayout photosContainer = findViewById(R.id.ll_photos_container);
        int lastPhotoIndex = photosContainer.getChildCount() - 1;
        if(lastPhotoIndex >= 0)
        {
            photosContainer.removeViewAt(lastPhotoIndex);
        }

        workingOffer.deletePhotoLast();
    }

    private File createEmptyImageFile() throws IOException
    {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDirectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File emptyPhoto = File.createTempFile(imageFileName, ".jpg", storageDirectory);

        currentPhotoPath = emptyPhoto.getAbsolutePath();

        return emptyPhoto;
    }

    private BitmapFactory.Options allocatePhotoMemory()
    {
        BitmapFactory.Options allocationOnlyOptions = new BitmapFactory.Options();
        allocationOnlyOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(currentPhotoPath, allocationOnlyOptions);

        return allocationOnlyOptions;
    }

    private Bitmap getCurrentPhoto()
    {
        BitmapFactory.Options allocationResultOptions = allocatePhotoMemory();
        BitmapFactory.Options decodingOptions = allocationResultOptions;

        decodingOptions.inJustDecodeBounds = false;
        decodingOptions.inSampleSize = 1; // no rescaling
        decodingOptions.inPurgeable = true;

        Bitmap photo = BitmapFactory.decodeFile(currentPhotoPath, decodingOptions);
        return photo;
    }

    private Bitmap getCurrentPhotoResized(int targetWidthPx, int targetHeightPx)
    {
        BitmapFactory.Options allocationResultOptions = allocatePhotoMemory();

        int photoWidthPx = allocationResultOptions.outWidth;
        int photoHeightPx = allocationResultOptions.outHeight;

        int scaleFactor = Math.min(photoWidthPx/targetWidthPx, photoHeightPx/targetHeightPx);

        BitmapFactory.Options decodingOptions = allocationResultOptions;

        decodingOptions.inJustDecodeBounds = false;
        decodingOptions.inSampleSize = scaleFactor;
        decodingOptions.inPurgeable = true;

        Bitmap photo = BitmapFactory.decodeFile(currentPhotoPath, decodingOptions);
        return photo;
    }


    private void tryPublishOffer()
    {
        ArrayList<View> invalidUserInputViews = validateFreeUserInputs();

        if(invalidUserInputViews.size() == 0)
        {
            workingOffer.setName(titleEdit.getText().toString());
            ArrayList<String> tags = getTagsFromUI();
            workingOffer.setTags(tags);
            workingOffer.setDescription(descriptionEdit.getText().toString());
            workingOffer.setPrice( Float.parseFloat(priceEdit.getText().toString()) );

            DatabaseManager.createOffer(this, workingOffer, new IIdConsumer() {
                @Override
                public void consume(boolean wasCreationSuccessful, int id)
                {
                    onOfferUploadResponse(wasCreationSuccessful, id);
                }
            });
        }
        else
        {
            highlightInvalidInputViews(invalidUserInputViews);
        }
    }

    private void onOfferUploadResponse(boolean wasOfferUploadSuccessful, int id)
    {
        if (wasOfferUploadSuccessful)
        {
            PhotoUploader photoUploader = new PhotoUploader();
            photoUploader.upload(this, workingOffer.getPhotos(), id, new ISuccessConsumer() {
                @Override
                public void consume(boolean wasPhotosUploadSuccessful)
                {
                    onPhotosUploadResponse(wasPhotosUploadSuccessful);
                }
            });
        }
        else
        {
            Toast.makeText(getBaseContext(), getString(R.string.connection_problem), Toast.LENGTH_LONG).show();
        }
    }


    private ArrayList<String> getTagsFromUI()
    {
        return new ArrayList<String>(); // dummy
    }


    private void onPhotosUploadResponse(boolean wasSuccessful)
    {
        if(wasSuccessful) finish();
        else Toast.makeText(getBaseContext(), getString(R.string.connection_problem), Toast.LENGTH_LONG).show();
    }


    private ArrayList<View> validateFreeUserInputs()
    {
        ArrayList<View> invalidValueContainers = new ArrayList<>();

        if(titleEdit.getText().length() < getResources().getInteger(R.integer.min_offer_title_length))
        {
            invalidValueContainers.add(titleEdit);
        }
        if(tagsEdit.getText().length() < getResources().getInteger(R.integer.min_offer_tags_length))
        {
            invalidValueContainers.add(tagsEdit);
        }
        if(descriptionEdit.getText().length() < getResources().getInteger(R.integer.min_offer_description_length))
        {
            invalidValueContainers.add(descriptionEdit);
        }
        if(priceEdit.getText().length() < getResources().getInteger(R.integer.min_offer_price_length))
        {
            invalidValueContainers.add(priceEdit);
        }

        return invalidValueContainers;
    }


    private void highlightInvalidInputViews(ArrayList<View> invalidViews)
    {
        for(View invalidHighlightingView : invalidViews)
        {
            Drawable invalidInputFrame = getResources().getDrawable(R.drawable.invalid_input_view_frame);
            invalidHighlightingView.setBackgroundDrawable(invalidInputFrame);
        }
    }
}
