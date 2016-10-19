package edu.scu.lwang.finalprojectscene;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by mingming on 5/20/16.
 */
public class ReminderReceiver extends BroadcastReceiver{
    public void onReceive(Context context, Intent intent) {
        Log.e("We are in the receiver", "Yay");

//        create an intent to ringtone service
        Intent service_intent = new Intent(context, NotiService.class);

        //start the reminder service
        context.startService(service_intent);

        // Creates an explicit intent for an Activity




    }
}
