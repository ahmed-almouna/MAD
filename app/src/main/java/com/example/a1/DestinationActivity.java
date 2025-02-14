package com.example.a1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import androidx.activity.ComponentActivity;

public class DestinationActivity extends ComponentActivity {
    private EditText destinationInput;
    private Spinner countrySpinner;
    private RadioGroup travelTypeGroup;
    private Button nextButton;

    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destination);

        // Initialize views
        destinationInput = findViewById(R.id.destination_input);
        countrySpinner = findViewById(R.id.country_spinner);
        travelTypeGroup = findViewById(R.id.travel_type_group);
        nextButton = findViewById(R.id.next_button);
        backButton = findViewById(R.id.back_button);

        // Populate the Spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.countries,  // Reference to the string array
                android.R.layout.simple_spinner_item // Layout for dropdown
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        countrySpinner.setAdapter(adapter);

        nextButton.setOnClickListener(v -> {
            Intent intent = new Intent(DestinationActivity.this, BookingActivity.class);
            intent.putExtra("destination", destinationInput.getText().toString());
            startActivity(intent);
        });

        backButton.setOnClickListener(View -> {
            Intent intent = new Intent(DestinationActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }
}