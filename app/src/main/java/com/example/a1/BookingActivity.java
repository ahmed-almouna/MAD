package com.example.a1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.SeekBar;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.activity.ComponentActivity;
import android.widget.Toast;
import android.net.Uri;

public class BookingActivity extends ComponentActivity {
    private Button finishButton;
    private Button openGoogleButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        // Initialize views
        finishButton = findViewById(R.id.finish_button);
        openGoogleButton = findViewById(R.id.openGoogleButton);

        // Get destination from intent
        String destination = getIntent().getStringExtra("destination");
        String country = getIntent().getStringExtra("country");

        finishButton.setOnClickListener(v -> {
                Intent intent = new Intent(BookingActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            });

        openGoogleButton.setOnClickListener(v -> {
            /* Show confirmation box */
            if (country != null && !country.isEmpty()) {
                new android.app.AlertDialog.Builder(BookingActivity.this)
                    .setTitle("Go to wikipedia")
                    .setMessage("Are you sure you want to leave the app to Wikipedia?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        String url = "https://en.wikipedia.org/wiki/" + country.replace(" ", "_");
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        startActivity(intent);
                    })
                    .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                    .show();
            } else {
                Toast.makeText(BookingActivity.this, "Country not selected", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
