package edu.scu.lwang.finalprojectscene;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

/**
 * Created by mingming on 6/3/16.
 */
public class AlarmReceiver extends BroadcastReceiver {
    final int notificationId = 1111;
    @Override
    public void onReceive(Context context, Intent intent) {
        int requestCode=2222;
        Intent resultIntent = new Intent(context.getApplicationContext(), WaterList.class); //AlarmReceiver.this
        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(context.getApplicationContext(), requestCode, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context.getApplicationContext()) //AlarmReceiver.this
                        .setSmallIcon(R.drawable.f2)
                        .setContentTitle("Garden Reminder")
                        .setContentText("A plant need to be watered!")
                        .setContentIntent(resultPendingIntent)
                        .setAutoCancel(true);
        // .addAction(R.drawable.ic_fish, "Fish", resultPendingIntent);

//        mBuilder.setStyle(createBigContent());

        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);//change
        // mId allows you to update the notification later on.
        mNotificationManager.notify(notificationId, mBuilder.build());



    }
    public void onDestroy() {

        // Tell the user we stopped.
    }

}