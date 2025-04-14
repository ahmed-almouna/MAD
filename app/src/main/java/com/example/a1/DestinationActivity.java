package com.example.a1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.NumberPicker;
import android.Manifest;
import android.content.pm.PackageManager;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.os.Build;
import android.content.IntentFilter;

import androidx.activity.ComponentActivity;
import android.os.AsyncTask;
import android.util.Log;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import android.Manifest;
import android.content.pm.PackageManager;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.os.Build;
import android.content.IntentFilter;

public class DestinationActivity extends ComponentActivity {
    private EditText destinationInput;
    private Spinner countrySpinner;
    private RadioGroup travelTypeGroup;
    private Button nextButton;
    private Button backButton;
    private DatePicker departurePicker;
    private SeekBar budgetSeekBar;
    private TextView budgetText;
    private TextView durationText;
    private NumberPicker numberOfDays;
    private BatteryLowReceiver batteryLowReceiver; // Declare the receiver

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destination);

        // Register the BatteryLowReceiver
        batteryLowReceiver = new BatteryLowReceiver();
        IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_LOW);
        registerReceiver(batteryLowReceiver, filter);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.POST_NOTIFICATIONS}, 1);
            }
        }

        // Initialize views
        destinationInput = findViewById(R.id.destination_input);
        countrySpinner = findViewById(R.id.country_spinner);
        travelTypeGroup = findViewById(R.id.travel_type_group);
        nextButton = findViewById(R.id.next_button);
        backButton = findViewById(R.id.back_button);
        departurePicker = findViewById(R.id.departure_date_picker);
        budgetSeekBar = findViewById(R.id.budget_seekbar);
        budgetText = findViewById(R.id.budget_label);
        durationText = findViewById(R.id.duration_label);
        numberOfDays = findViewById(R.id.stay_days_picker);

        // set min and max values for days picker
        numberOfDays.setValue(1);
        numberOfDays.setMinValue(1);
        numberOfDays.setMaxValue(30);

        // Start async tasks
        executeChainedAsyncTasks();

        // Populate the Spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.countries,  // Reference to the string array
                android.R.layout.simple_spinner_item // Layout for dropdown
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        countrySpinner.setAdapter(adapter);

        nextButton.setOnClickListener(v -> {
            try {
                saveTripToFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            String selectedCountry = countrySpinner.getSelectedItem().toString();  // Get selected country data
            Intent intent = new Intent(DestinationActivity.this, BookingActivity.class);
            intent.putExtra("destination", destinationInput.getText().toString());
            intent.putExtra("country", selectedCountry);  // Pass country to BookingActivity
            startActivity(intent);
        });

        backButton.setOnClickListener(View -> {
            Intent intent = new Intent(DestinationActivity.this, MainActivity.class);
            startActivity(intent);
        });

        // Get destination from intent
        String destination = getIntent().getStringExtra("destination");
        String country = getIntent().getStringExtra("country");

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

        // Update duration of trip when number is changed
        numberOfDays.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                durationText.setText("Duration: " + newVal + " day(s)");
            }
        });
    }

    private void executeChainedAsyncTasks() {
        new DownloadFileTask().execute();
    }

    private class DownloadFileTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            try {
                Thread.sleep(2000); // Simulate file download delay
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "trip_details.txt"; // Simulated file name
        }

        @Override
        protected void onPostExecute(String fileName) {
            new ReadFileTask().execute(fileName);
        }
    }

    private class ReadFileTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String fileName = params[0];

            try {
                Thread.sleep(1000); // Simulate file read delay
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return "File " + fileName + " read successfully!";
        }

        @Override
        protected void onPostExecute(String result) {
            Log.d("AsyncTasks", result);
        }
    }

    // This function saves trips to files
    private void saveTripToFile() throws IOException
    {
        /* Extract the trip details entered by the user */
        String tripName = destinationInput.getText().toString();
        String country =  countrySpinner.getSelectedItem().toString();
        int selectedTripType = travelTypeGroup.getCheckedRadioButtonId();
        String tripType;
        if (selectedTripType == R.id.type_business)
        {
            tripType = "Business";
        }
        else
        {
            tripType = "Leisure";
        }
        int departureDay = departurePicker.getDayOfMonth();
        int departureMonth = departurePicker.getMonth();
        int departureYear = departurePicker.getYear();
        String departureDate = String.format("%02d-%02d-%d", departureDay, departureMonth, departureYear);
        int budget = budgetSeekBar.getProgress();
        int tripDuration = numberOfDays.getValue();

        String tripData = "Name: " + tripName +
                "\nDestination: " + country +
                "\nType: " + tripType +
                "\nDeparture: " + departureDate +
                "\nBudget: " + budget +
                "\nDuration: " + tripDuration;

        /* Write details to file */
        FileOutputStream out = openFileOutput("trip-" + tripName, MODE_PRIVATE);
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));
        writer.write(tripData);
        writer.close();
        out.close();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Unregister the receiver to avoid memory leaks
        if (batteryLowReceiver != null) {
            unregisterReceiver(batteryLowReceiver);
        }
    }
}

