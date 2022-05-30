package com.example.lostandfoundappfinal;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.util.Arrays;

public class PlaceActivity extends AppCompatActivity {

    private static final String TAG = "Running";
    Double latitude;
    Double longitude;
    String name;
    Button placeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);

        placeButton = findViewById(R.id.placeButton);

        placeButton.setOnClickListener(view -> {
            Intent doneIntent = new Intent(PlaceActivity.this, NewAdvert.class);
            doneIntent.putExtra("latitude", latitude);
            doneIntent.putExtra("longitude", longitude);
            doneIntent.putExtra("name", name);

            setResult(RESULT_OK, doneIntent);
            finish();
        });
        // Initialize the SDK
        Places.initialize(getApplicationContext(),getString(R.string.Place_API));

        // Create a new PlacesClient instance
        PlacesClient placesClient = Places.createClient(this);

        // Initialize the Autocomplete Support Fragment.
        AutocompleteSupportFragment autocompleteFragment=(AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);
        // Specify the types of place data to return.
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.LAT_LNG,Place.Field.NAME));
        // Setup a PlaceSelectionListener to handle the response.
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener(){
            @Override
            public void onPlaceSelected(@NonNull Place place){

                latitude = place.getLatLng().latitude;
                longitude = place.getLatLng().longitude;
                name = place.getName();
            }
            @Override
            public void onError(@NonNull Status status){
                // TODO:Handle the error.
                Log.i(TAG,"An error occurred: " + status);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}