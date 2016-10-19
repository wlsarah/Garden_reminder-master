package edu.scu.lwang.finalprojectscene;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

public class WaterList extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private int maxRecId;
    private static final int REQUEST_FOR_Photo = 0;
    private String ImagePath;
    private String ThumbPath;
    private String Caption;
    WaterListAdapter pa;
    PlantDBHelper dbHelper;
    Cursor cursor;
    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_list);

        dbHelper = new PlantDBHelper(this);
        cursor = dbHelper.waterList();
        pa = new WaterListAdapter(this, cursor, 0);

        list = (ListView) findViewById(R.id.listView);
        list.setAdapter(pa);
        list.setOnItemClickListener(this);

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DATE, -1);
        Date date = cal.getTime();

        Plant pi = new Plant(111, "flowerName", "path", date.toString(), 2, date);
//        pi.setId(111);
//        pi.setPlantName("flowerName");
//        pi.setPhotoPath("path");
//        pi.setWaterInterval(2);
//        pi.setDate(date.toString());
        //   dbHelper.add(pi);

        maxRecId = dbHelper.getMaxRecID();
//        toastShow("MacRecID is " + maxRecId);


    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        String rowDir = cursor.getString(cursor.getColumnIndex("ImagePath"));
//        String rowNote = cursor.getString(cursor.getColumnIndex("Caption"));
//        Intent viewPhoto = new Intent(MainActivity.this, ViewPhoto.class);
//        viewPhoto.putExtra("ImagePath", rowDir).putExtra("Caption", rowNote);
//        startActivity(viewPhoto);
        String plantName = cursor.getString(cursor.getColumnIndex("PlantName"));
        int waterInterval = cursor.getInt(cursor.getColumnIndex("WaterInterval"));
        waterPlant(plantName, waterInterval);
//        deletePlant(cursor.getInt(cursor.getColumnIndex("_id")));
    }

    private void addNewPhoto() {
//        Plant pi = new Plant("flower", "path", new Date(), 3);
//        dbHelper.add(pi);

        // update cursor as well as notifying the listview on the change
        cursor.requery();
        pa.notifyDataSetChanged();
        // alternatively we can call list.invalidateViews();
    }

    private void deletePlant(int id) {
        dbHelper.delete(id);
        cursor.requery();
        pa.notifyDataSetChanged();
    }
    private void waterPlant(String plantName, int waterInterval){
        dbHelper.waterToday(plantName, waterInterval);
        cursor.requery();
        pa.notifyDataSetChanged();
    }

    private void toastShow(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (REQUEST_FOR_Photo != 0 || resultCode != RESULT_OK) return;
//        ImagePath = data.getStringExtra("ImagePath");
//        ThumbPath = data.getStringExtra("ThumbPath");
//        Caption = data.getStringExtra("Caption");
//        addNewPhoto();
    }



}
