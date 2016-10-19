package edu.scu.lwang.finalprojectscene;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;


import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Sarah on 5/23/2016.
 */

public class PlantFrameName extends AppCompatActivity {
    String plantPicPath;
    EditText plantNameET;

    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.picframename);

        Bundle bundle = getIntent().getExtras();

        plantPicPath = bundle.getString("plantPicPath");
        plantNameET = (EditText) findViewById(R.id.editText);

        ImageView imageView = (ImageView) findViewById(R.id.plantImageFrame);
        imageView.setImageURI(Uri.parse(plantPicPath));
        //imageView.setRotation(180f);

    }

    public void createClick(View v){

        String plantName = plantNameET.getText().toString();

        if (plantName == null){
            final CharSequence[] items = {"OK"};
            AlertDialog.Builder builder = new AlertDialog.Builder(PlantFrameName.this);
            builder.setTitle("Enter a plant name before saving!");
            builder.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int item) {
                    if (items[item].equals("OK")) {
                        dialog.dismiss();
                    }
                }
            });
            builder.show();
        }else{

            Date date = new Date();
            PlantDBHelper db=new PlantDBHelper(this);
            Intent intent=new Intent(PlantFrameName.this, PlatsGridView.class);
//            Plant p = new Plant(0, plantName, plantPicPath, current_date, 0, new Date());
//            p.setPlantName(plantName);
//            p.setPhotoPath(plantPicPath);

            String current_date = new SimpleDateFormat("MM/dd/yyyy").format(new java.util.Date());
            Plant p = new Plant(0, plantName, plantPicPath, current_date, 0, new Date());

//            p.setDate(current_date);

            db.add(p);

            startActivity(intent);
        }

    }
}
