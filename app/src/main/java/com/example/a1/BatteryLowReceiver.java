package com.example.a1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
import android.util.Log;

public class BatteryLowReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BATTERY_LOW.equals(intent.getAction())) {
            Log.d("BatteryLowReceiver", "Battery low received");

            // Ensure that you're updating the UI on the main thread
            if (context instanceof DestinationActivity) {
                DestinationActivity activity = (DestinationActivity) context;
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // Show a Toast or update any UI element (for example, change text on a TextView)
                        Toast.makeText(activity, "Battery is low!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }
}



