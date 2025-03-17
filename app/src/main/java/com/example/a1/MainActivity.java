package com.example.a1;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.activity.ComponentActivity;
import androidx.annotation.Nullable;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ComponentActivity
{
    public static final String TAG = MainActivity.class.getSimpleName();
    private Button addTripBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        addTripBtn = findViewById(R.id.add_trip_btn);
        displayTrips();
        initializeListeners();
    }

    // This function initializes all the event listeners needed for this activity
    private void initializeListeners()
    {
        addTripBtn.setOnClickListener(v -> openTicketActivity());
    }

    // This function opens the ticket/destination activity
    private void openTicketActivity()
    {
        Intent intent = new Intent(MainActivity.this, DestinationActivity.class);
        startActivity(intent);
    }

    //This functions displays all the saved trips
    private void displayTrips()
    {
        List<Trip> trips = readTripsFromFiles();
        LinearLayout tripContainer = findViewById(R.id.trip_container);
        tripContainer.removeAllViews();

        for (Trip trip : trips)
        {
            View tripBanner = getLayoutInflater().inflate(R.layout.trip_banner, null);
            ImageView tripImage = tripBanner.findViewById(R.id.trip_image);
            TextView tripTitle = tripBanner.findViewById(R.id.trip_title);
            TextView tripDate = tripBanner.findViewById(R.id.trip_date);
            TextView tripCountdown = tripBanner.findViewById(R.id.trip_countdown);

            // Set the trip details
            tripTitle.setText(trip.name);
            tripDate.setText(trip.departureDate + " (" + trip.duration + " days)");

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            LocalDate today = LocalDate.now();
            LocalDate departure = LocalDate.parse(trip.departureDate, formatter);

            // Calculate countdown
            long daysUntilTrip = ChronoUnit.DAYS.between(today, departure);
            tripCountdown.setText("In " + daysUntilTrip + " days");

            // Set trip image based on destination (replace with your actual logic)
            if (trip.destination.equalsIgnoreCase("Italy"))
            {
                tripImage.setImageResource(R.drawable.italy);
            }
            else if (trip.destination.equalsIgnoreCase("France"))
            {
                tripImage.setImageResource(R.drawable.paris);
            }
            else if (trip.destination.equalsIgnoreCase("Japan"))
            {
                tripImage.setImageResource(R.drawable.tokyo);
            }
            else
            {
                tripImage.setImageResource(R.drawable.default_trip); // Default image
            }

            tripContainer.addView(tripBanner);
        }
    }

    // THis function read trips details from files
    private List<Trip> readTripsFromFiles()
    {
        List<Trip> trips = new ArrayList<>();
        File directory = getFilesDir();
        File[] files = directory.listFiles();

        /* Read all trip files */
        if (files != null)
        {
            for (File file : files)
            {
                if (file.getName().startsWith("trip-"))
                {
                    try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file))))
                    {
                        /* Get the details of trip */
                        String destination = reader.readLine().replace("Name: ", "");
                        String country = reader.readLine().replace("Destination: ", "");
                        String type = reader.readLine().replace("Type: ", "");
                        String departureDate = reader.readLine().replace("Departure: ", "");
                        int budget = Integer.parseInt(reader.readLine().replace("Budget: ", ""));
                        String duration = reader.readLine().replace("Duration: ", "");

                        trips.add(new Trip(destination, country, type, departureDate, budget, duration));
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        }
        return trips;
    }

}