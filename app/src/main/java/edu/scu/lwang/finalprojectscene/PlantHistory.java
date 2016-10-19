package edu.scu.lwang.finalprojectscene;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Sarahwang on 6/3/16.
 */
public class PlantHistory extends AppCompatActivity {
    final int notificationId = 1111;

    Context context;
    String plantHistoryName;

    //AlarmManager alarm_manager;
    //    ArrayList<AlarmManager> alarmList = new ArrayList<>();
    Calendar calendar;
//    ArrayList<Calendar> calList = new ArrayList<>();


    String fileName;
    String userChoosenTask;
    TextView plantNameText;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = this;
        setContentView(R.layout.planthistory);


        Bundle bundle = getIntent().getExtras();
        plantHistoryName = bundle.getString("plantHistoryName");
        // System.out.println("printsth");
        plantNameText = (TextView) findViewById(R.id.plantTextName);
        //assert plantNameText != null;
        plantNameText.setText(plantHistoryName);
        final GridView gridview = (GridView) findViewById(R.id.historygridview);
        gridview.setAdapter(new HistoryAdapter(this, plantHistoryName));

        System.out.println("got to the on create in PlantsGridView");


        ImageButton home = (ImageButton)findViewById(R.id.home);

        home.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                startActivity(new Intent(PlantHistory.this, PlatsGridView.class));
            }
        });


//Home button
//        ImageButton button4 = (ImageButton) findViewById(R.id.button4);
//
//        button4.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                finish();
//                startActivity(new Intent(WaterList.this, PlatsGridView.class));
//            }
//        });


        calendar = Calendar.getInstance();
        calendar.setTime(new Date());


        //acquireRunTimePermissions();
    }



}

