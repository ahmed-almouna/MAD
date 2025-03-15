package com.example.a1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.SeekBar;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.activity.ComponentActivity;

public class BookingActivity extends ComponentActivity {
    private Button finishButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        // Initialize views
        finishButton = findViewById(R.id.finish_button);

        // Get destination from intent
        String destination = getIntent().getStringExtra("destination");
        String country = getIntent().getStringExtra("country");

        finishButton.setOnClickListener(v -> {
                Intent intent = new Intent(BookingActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            });

    }
}
