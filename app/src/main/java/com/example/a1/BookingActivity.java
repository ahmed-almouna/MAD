package com.example.a1;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.SeekBar;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import androidx.activity.ComponentActivity;
import android.widget.Toast;
import android.net.Uri;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationListener;
import android.os.Bundle;
import android.content.pm.PackageManager;
import android.widget.TextView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import java.util.List;
import java.util.Locale;

import java.util.List;
import java.util.Locale;

public class BookingActivity extends ComponentActivity {
    private Button finishButton;
    private Button openGoogleButton;
    private TextView locationText;

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
        locationText = findViewById(R.id.location_text);

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            getLocation();
        }

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


    private void getLocation() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        try {
            locationManager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    Geocoder geocoder = new Geocoder(BookingActivity.this, Locale.getDefault());
                    try {
                        List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                        if (!addresses.isEmpty()) {
                            Address address = addresses.get(0);
                            String city = address.getLocality();
                            String country = address.getCountryName();
                            locationText.setText("You are in " + city + ", " + country);
                        }
                    } catch (Exception e) {
                        locationText.setText("Unable to get location.");
                    }
                }

                public void onStatusChanged(String provider, int status, Bundle extras) {}
                public void onProviderEnabled(String provider) {}
                public void onProviderDisabled(String provider) {}
            }, null);
        } catch (SecurityException e) {
            locationText.setText("Location permission not granted.");
        }
    }
}



