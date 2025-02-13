package com.example.a1;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.activity.ComponentActivity;
import androidx.annotation.Nullable;

public class MainActivity extends ComponentActivity
{
    public static final String TAG = MainActivity.class.getSimpleName();

    private Button addTripBtn;
    private LinearLayout tripOneBanner;
    private LinearLayout tripTwoBanner;
    private LinearLayout tripThreeBanner;
    private LinearLayout tripFourBanner;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        addTripBtn = findViewById(R.id.add_trip_btn);
        tripOneBanner = findViewById(R.id.trip_1);
        tripTwoBanner = findViewById(R.id.trip_2);
        tripThreeBanner = findViewById(R.id.trip_3);
        tripFourBanner = findViewById(R.id.trip_4);

        initializeListeners();
    }

    // This function initializes all the event listeners needed in this activity
    private void initializeListeners()
    {
        addTripBtn.setOnClickListener(v -> openTicketActivity());
        tripOneBanner.setOnClickListener(v -> openTicketActivity());
        tripTwoBanner.setOnClickListener(v -> openTicketActivity());
        tripThreeBanner.setOnClickListener(v -> openTicketActivity());
        tripFourBanner.setOnClickListener(v -> openTicketActivity());
    }

    private void openTicketActivity()
    {
        Intent intent = new Intent(MainActivity.this, DestinationActivity.class);
        startActivity(intent);
    }
}