package edu.scu.lwang.finalprojectscene;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;

/**
 * Created by mingming on 6/2/16.
 */
public class AlarmService extends IntentService {

    public static final String CREATE = "CREATE";
    public static final String CANCEL = "CANCEL";
    PlantDBHelper plantDB = new PlantDBHelper(this);

    private IntentFilter matcher;

    public AlarmService() {
        super("AlarmService");
        matcher = new IntentFilter();
        matcher.addAction(CREATE);
        matcher.addAction(CANCEL);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String action = intent.getAction();
        int recID = intent.getIntExtra("recID",0);

        if (matcher.matchAction(action)) {
            execute(action, recID);
        }
    }

    private void execute(String action, int recID) {
        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Cursor c = plantDB.getPlant(recID);

        if (c.moveToFirst()) {
            Intent i = new Intent(this, AlarmReceiver.class);

            PendingIntent pi = PendingIntent.getBroadcast(this, 0, i,
                    PendingIntent.FLAG_UPDATE_CURRENT);

            long time = c.getLong(c.getColumnIndex("NextWater"));
            if (CREATE.equals(action)) {
                am.set(AlarmManager.RTC_WAKEUP, time, pi);

            } else if (CANCEL.equals(action)) {
                am.cancel(pi);
            }
        }
        c.close();
    }

}
