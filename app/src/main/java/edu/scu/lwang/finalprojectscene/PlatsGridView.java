package edu.scu.lwang.finalprojectscene;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Sarahwang on 6/3/16.
 */
public class PlatsGridView extends AppCompatActivity {
    final int notificationId = 1111;


    Context context;
    AlarmManager alarm_manager;
    Calendar calendar;

    String fileName;
    String userChoosenTask;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = this;
        setContentView(R.layout.plantsgridview);

        System.out.println(new Date().getTime());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  addNewPlant();
                Intent intent=new Intent(PlatsGridView.this, RecognitionActivity.class);
                startActivity(intent);
            }
        });

        final GridView gridview = (GridView) findViewById(R.id.pgridview);
        gridview.setAdapter(new ImageAdapter(this));

        System.out.println("got to tthe on create in PlantsGridView");
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Plant p = (Plant) gridview.getAdapter().getItem(position);
                Intent i = new Intent(PlatsGridView.this, PlantMenu.class);
                System.out.println("this is the ID after touch in the PlantsGridView: " + p.getId());
                Bundle b = new Bundle();
                b.putInt("_id", p.getId());
                i.putExtras(b);

                startActivity(i);

            }
        });
        // set callback for create notification button


        calendar = Calendar.getInstance();


        calendar.setTime(new Date());



        alarm_manager = (AlarmManager) getSystemService(ALARM_SERVICE);

        // create an intent to ReminderReceiver
        Intent toReceiver = new Intent(this, ReminderReceiver.class);

        // create a pending intent to delay the intent until the reminder time
        PendingIntent pendingIntent = PendingIntent.getBroadcast(PlatsGridView.this, 0, toReceiver, PendingIntent.FLAG_UPDATE_CURRENT);

        // Set the remainder manager
        alarm_manager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),pendingIntent);

        //acquireRunTimePermissions();
    }

    private void galleryIntent()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.infomation:
                Intent information = new Intent(PlatsGridView.this,Information.class);
                startActivity(information);
                break;
            case R.id.action_waterList:
                Intent waterList = new Intent(PlatsGridView.this,WaterList.class);
                startActivity(waterList);
                break;
            default:
        }

        return true;
    }

}
