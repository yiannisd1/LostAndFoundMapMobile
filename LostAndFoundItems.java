package com.example.lostandfoundappfinal;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lostandfoundappfinal.model.LostAndFound;

import java.util.ArrayList;

public class LostAndFoundItems extends AppCompatActivity {
    LinearLayout lostFoundListLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lost_and_found_items);

        lostFoundListLayout = findViewById(R.id.lostFoundListLayout);
        showLostFound();
    }

    @Override
    protected void onResume() {
        super.onResume();
        lostFoundListLayout.removeAllViews();
        showLostFound();
    }

    @SuppressLint("SetTextI18n")
    private void showLostFound(){
        ArrayList<LostAndFound> lostAndFoundArrayList = MainActivity.databaseHelper.fetchAllLostAndFound();
        for (int i = 0; i < lostAndFoundArrayList.size(); i++){
            final LostAndFound lostAndFound = lostAndFoundArrayList.get(i);
            TextView textView = new TextView(this);
            textView.setBackgroundResource(R.drawable.border);
            textView.setPadding(16, 8, 0, 8);
            textView.setHeight(100);
            textView.setTextSize(18);
            textView.setText((lostAndFound.getTypeOfAdvert() + " " + lostAndFound.getName()));
            textView.setTag(lostAndFound.getId());

            textView.setOnClickListener(view -> {
                Intent passData = new Intent(LostAndFoundItems.this, DisplayInfo.class);
                passData.putExtra("id", lostAndFound.getId());
                passData.putExtra("typeOfAdvert", lostAndFound.getTypeOfAdvert());
                passData.putExtra("name", lostAndFound.getName());
                passData.putExtra("phone", lostAndFound.getPhone());
                passData.putExtra("description", lostAndFound.getDescription());
                passData.putExtra("date", lostAndFound.getDate());
                passData.putExtra("location", lostAndFound.getLocation());
                startActivity(passData);
            });
            lostFoundListLayout.addView(textView);
        }
    }
}