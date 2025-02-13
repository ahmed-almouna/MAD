package com.example.a1;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.activity.ComponentActivity;
import androidx.annotation.Nullable;

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


        initializeListeners();
    }

    // This function initializes all the event listeners needed in this activity
    private void initializeListeners()
    {
        addTripBtn.setOnClickListener(v -> openTicketActivity());
    }

    private void openTicketActivity()
    {
        Intent intent = new Intent(MainActivity.this, SecondActivity.class);
        startActivity(intent);
    }
}