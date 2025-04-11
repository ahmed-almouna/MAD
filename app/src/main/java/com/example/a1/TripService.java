package com.example.a1;

import android.app.IntentService;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Notification;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

public class TripService extends IntentService {

    private static final String CHANNEL_ID = "TripNotificationChannel";

    public TripService() {
        super("TripService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            String tripName = intent.getStringExtra("tripName");

            createNotificationChannel();

            Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_trip)
                    .setContentTitle("Trip Saved!")
                    .setContentText("Your trip to " + tripName + " was saved successfully.")
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setDefaults(Notification.DEFAULT_ALL) // Sound, lights, vibration
                    .setCategory(NotificationCompat.CATEGORY_MESSAGE) // Helps Android know it’s important
                    .build();


            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.notify((int) System.currentTimeMillis(), notification);
        }
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String name = "Trip Channel";
            String description = "Notifications for trip updates";
            int importance = NotificationManager.IMPORTANCE_HIGH;


            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
