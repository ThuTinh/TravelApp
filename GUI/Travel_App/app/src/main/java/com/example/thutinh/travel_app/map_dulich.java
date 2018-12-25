package com.example.thutinh.travel_app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.maps.GoogleMap;

public class map_dulich extends AppCompatActivity {
    GoogleMap googleMap;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_dulich);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
}
