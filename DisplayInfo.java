package com.example.lostandfoundappfinal;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DisplayInfo extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_info);

        textView = findViewById(R.id.textView);

        Intent intent = getIntent();
        Integer id = intent.getIntExtra("id", 0);
        String typeOfAdvert = intent.getStringExtra("typeOfAdvert");
        String name = intent.getStringExtra("name");
        String phone = intent.getStringExtra("phone");
        String description = intent.getStringExtra("description");
        String date = intent.getStringExtra("date");
        String location = intent.getStringExtra("location");


        String info = "Type: " + typeOfAdvert + "\nItem Name: " + name + "\nPhone Number: " + phone +
                "\nDescription: " + description + "\nDate: " + date + "\nLocation: " + location;

        textView.setText(info);
        Button remove = findViewById(R.id.removeButton);


        remove.setOnClickListener(v -> {
            MainActivity.databaseHelper.deleteData(id);
            finish();
        });
    }
}