package com.example.a1;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.Toast;

import java.util.Objects;

public class TripWidgetProvider extends AppWidgetProvider {
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds)
    {

    }

    public static void updateWidget(Context context, String upcomingTripTitle, long upcomingTripCountdown)
    {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        ComponentName widget = new ComponentName(context, TripWidgetProvider.class);
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);

        if (!Objects.equals(upcomingTripTitle, ""))
        {
            views.setTextViewText(R.id.widget_trip_name, upcomingTripTitle);
            views.setTextViewText(R.id.widget_trip_countdown, "In " + upcomingTripCountdown + " days");
        }
        else
        {
            views.setTextViewText(R.id.widget_trip_name, "No upcoming trips");
            views.setTextViewText(R.id.widget_trip_countdown, "");
        }

        appWidgetManager.updateAppWidget(widget, views);
    }



}
