package edu.scu.lwang.finalprojectscene;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
//import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by mingming on 5/20/16.
 */
public class NotiService extends Service {
    final int notificationId = 1111;

    //@Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("LocalService", "Received start id " + startId + ": " + intent);

//        media_song = MediaPlayer.create(this, R.raw.put);
//        media_song.start();

        int requestCode=2222;
        Intent resultIntent = new Intent(NotiService.this, WaterList.class);
        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(getApplicationContext(), requestCode, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(NotiService.this)
                        .setSmallIcon(R.drawable.f2)
                        .setContentTitle("Garden Reminder")
                        .setContentText("A plant need to be watered!")
                        .setContentIntent(resultPendingIntent)
                        .setAutoCancel(true);
        // .addAction(R.drawable.ic_fish, "Fish", resultPendingIntent);

//        mBuilder.setStyle(createBigContent());

        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        // mId allows you to update the notification later on.
        mNotificationManager.notify(notificationId, mBuilder.build());



        return START_NOT_STICKY;
    }

    NotificationCompat.InboxStyle createBigContent() {
        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
        String[] events = {"First event", "second event", "Third event", "Forth event"};
        // Sets a title for the Inbox in expanded layout
        inboxStyle.setBigContentTitle("Event tracker details:");
        // Moves events into the expanded layout
        for (int i=0; i < events.length; i++) {
            inboxStyle.addLine(events[i]);
        }
        inboxStyle.setSummaryText("There are total of " + events.length + " events");
        return inboxStyle;
    }
    @Override
    public void onDestroy() {

        // Tell the user we stopped.
        Toast.makeText(this, "on Destroy called", Toast.LENGTH_SHORT).show();
    }



    // This is the object that receives interactions from clients.  See
    // RemoteService for a more complete example.

}
