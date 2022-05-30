package com.example.lostandfoundappfinal;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lostandfoundappfinal.data.DatabaseHelper;

public class MainActivity extends AppCompatActivity {
    public static DatabaseHelper databaseHelper = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseHelper = new DatabaseHelper(getApplicationContext());

        Button newAdvert = findViewById(R.id.newAdvert);
        newAdvert.setOnClickListener(view -> createNewAdvert());

        Button lostAndFound = findViewById(R.id.lostAndFoundButton);
        lostAndFound.setOnClickListener(view -> showLostAndFound());

        Button map = findViewById(R.id.mapButton);
        map.setOnClickListener(view -> showOnMap());
    }

    public void createNewAdvert() {
        Intent intent = new Intent(this, NewAdvert.class);
        startActivity(intent);
    }

    public void showLostAndFound() {
        Intent intent = new Intent(this, LostAndFoundItems.class);
        startActivity(intent);
    }

    public void showOnMap() {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }
}