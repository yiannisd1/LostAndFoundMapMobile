package com.example.lostandfoundappfinal;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.lostandfoundappfinal.model.LostAndFound;

@SuppressWarnings("ALL")
public class NewAdvert extends AppCompatActivity {

    RadioGroup radioGroup;
    RadioButton radioButton;
    TextView textView;

    EditText textLocation;

    LocationManager locationManager;
    LocationListener locationListener;
    Button getDirectionsButton;

    String current_location;
    String typeOfAdvert;
    String name;
    String phone;
    String description;
    String date;
    String location;
    Double latitude;
    Double longitude;


    @Override
    public void onRequestPermissionsResult(int requestCode,@NonNull String[]permissions,@NonNull int[]grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_advert);


        RadioButton radio_lost = findViewById(R.id.radio_lost);
        EditText textName = findViewById(R.id.textName);
        EditText textPhone = findViewById(R.id.textPhone);
        EditText textDescription = findViewById(R.id.textDescription);
        EditText textDate = findViewById(R.id.textDate);
        textLocation = findViewById(R.id.textLocation);


        radioGroup = findViewById(R.id.radioGroup);
        textView = findViewById(R.id.post_type_selected);

        textLocation.setOnClickListener(view -> {
            Intent placeIntent = new Intent(NewAdvert.this, PlaceActivity.class);
            startActivityForResult(placeIntent, 3);
        });


        Button buttonSave = findViewById(R.id.saveButton);
        buttonSave.setOnClickListener(view -> {
            int postTypeId = radioGroup.getCheckedRadioButtonId();

            radioButton = findViewById(postTypeId);


            if (radio_lost.isChecked()){
                typeOfAdvert = "lost";
            }
            else
            {
                typeOfAdvert = "found";
            }

            name = textName.getText().toString();
            phone = textPhone.getText().toString();
            description = textDescription.getText().toString();
            date = textDate.getText().toString();
            location = textLocation.getText().toString();

            LostAndFound lostAndFound = new LostAndFound(null, typeOfAdvert, name, phone, description, date, location, latitude, longitude);

            boolean addData = MainActivity.databaseHelper.insertLostAndFound(lostAndFound);

            if (addData)
            {

                Toast.makeText(NewAdvert.this, "Successful Entry Log", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(NewAdvert.this, "Unsuccessful Entry Log", Toast.LENGTH_SHORT).show();
            }

            Intent saveIntent = new Intent( NewAdvert.this, LostAndFoundItems.class);
            startActivity(saveIntent);

        });

        getDirectionsButton = findViewById(R.id.getLocationButton);
        getDirectionsButton.setOnClickListener(view -> textLocation.setText(current_location));


        locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        locationListener = location -> current_location = location.toString();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,0, locationListener);
    }

    public void checkPostType(View v) {
        int postTypeId = radioGroup.getCheckedRadioButtonId();

        radioButton = findViewById(postTypeId);

        Toast.makeText(this, "Post Type Selected:" + radioButton.getText(), Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 3)
        {
            if (resultCode == RESULT_OK && data != null)
            {
                latitude = data.getDoubleExtra("latitude", 0);
                longitude = data.getDoubleExtra("longitude", 0);
                String name = data.getStringExtra("name");
                textLocation.setText(name);
            }
        }


    }

}