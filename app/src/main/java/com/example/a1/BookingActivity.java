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
    private DatePicker departurePicker;
    private SeekBar budgetSeekBar;
    private ImageView hotelPreview;
    private TextView budgetText;
    private Button finishButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        // Initialize views
        departurePicker = findViewById(R.id.departure_date_picker);
        budgetSeekBar = findViewById(R.id.budget_seekbar);
        hotelPreview = findViewById(R.id.hotel_preview);
        budgetText = findViewById(R.id.budget_label);
        finishButton = findViewById(R.id.finish_button);

        // Get destination from intent
        String destination = getIntent().getStringExtra("destination");
        String country = getIntent().getStringExtra("country");

        finishButton.setOnClickListener(v -> {
                Intent intent = new Intent(BookingActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            });

        // Update text when SeekBar is moved
        budgetSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                budgetText.setText("Budget: $" + progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Optional: Do something when the user starts touching the SeekBar
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Optional: Do something when the user stops moving the SeekBar
            }
        });
    }
}
