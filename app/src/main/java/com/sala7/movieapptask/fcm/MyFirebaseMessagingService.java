package com.sala7.movieapptask.fcm;


import static com.sala7.movieapptask.utills.Constants.MOVIES_NOTIFICATION_ID;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.widget.RemoteViews;

import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.sala7.movieapptask.R;
import com.sala7.movieapptask.ui.activity.MainActivity;

public class MyFirebaseMessagingService extends FirebaseMessagingService {


    @Override
    public void onNewToken(@NonNull String token) {
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        if (remoteMessage.getNotification() != null) {

            showNotification(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());
        }
    }

    private RemoteViews getCustomDesign(String title, String message) {
        RemoteViews remoteViews = new RemoteViews(getApplicationContext().getPackageName(), R.layout.notification);
        remoteViews.setTextViewText(R.id.notification_title, title);
        remoteViews.setTextViewText(R.id.notification_message, message);
        remoteViews.setImageViewResource(R.id.notification_logo, R.drawable.tmdb_logo);
        return remoteViews;
    }

    public void showNotification(String title, String message) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), MOVIES_NOTIFICATION_ID).setSmallIcon(R.drawable.tmdb_logo).setAutoCancel(true).setVibrate(new long[]{1000, 1000, 1000, 1000, 1000}).setOnlyAlertOnce(true).setContentIntent(pendingIntent);

        builder = builder.setContent(getCustomDesign(title, message));
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(MOVIES_NOTIFICATION_ID, getString(R.string.moviesapp), NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(notificationChannel);
        }

        notificationManager.notify(0, builder.build());
    }
}