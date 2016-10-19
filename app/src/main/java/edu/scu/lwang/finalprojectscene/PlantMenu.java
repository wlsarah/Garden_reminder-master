package edu.scu.lwang.finalprojectscene;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PlantMenu extends AppCompatActivity{
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
    PlantCollectionDBHelper cdb = new PlantCollectionDBHelper(this);

    PlantDBHelper db = new PlantDBHelper(this);
    final String myPreference = "wateve";
    SharedPreferences mySharedPreferences;
    SharedPreferences.Editor myEditor;

    final int requestCode = 1234;
    final String albumName = "L11-camera-external-file";
    String fileName = "";
    int id;
    String userChoosenTask;
    final int SELECT_FILE = 1;
    private Bitmap bitmap;
    String bitmapPath;
    Plant plant;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plantmenu);

        Bundle bundle = getIntent().getExtras();
        id = bundle.getInt("_id");
        System.out.println("this is _id after fetching it in PlantMenu: " + id);
        //PlantDBHelper db=new PlantDBHelper(this);
        plant = db.fetchPlantWithId(id);

        ImageView iv = (ImageView) findViewById(R.id.plantImage);

        if(plant.getPhotoPath().charAt(0) == 'h'){
            new DownloadImageTask(iv)
                    .execute(plant.getPhotoPath());

        }else{
            iv.setImageURI(Uri.parse(plant.getPhotoPath()));
        }

        Button button3 = (Button) findViewById(R.id.button3);
        Button button4 = (Button) findViewById(R.id.button4);
        Button addPlantPhotoB = (Button) findViewById(R.id.addPlantPhoto);
        addPlantPhotoB.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                addPlantPhotoTap();
            }
        });

        acquireRunTimePermissions();


        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("ID is " + id);
//                Cursor another = cdb.fetchAll();
//                while(another.moveToNext())
//                {System.out.println("IDE is " + another.getInt(0));}
                Cursor c = cdb.getAPlant(id);
                c.moveToFirst();


                AlertDialog.Builder builder = new AlertDialog.Builder(PlantMenu.this);
                builder.setIcon(R.mipmap.ic_launcher)
                        .setTitle("Plant Details")
                        .setMessage("Sun Tolerance: " + c.getString(1) + "\n" + "Fertilize Time: " + c.getString(2) + "\n" + "Fertilizer: " + c.getString(3) + "\n" + "Pest & Disease: " + c.getString(4) + "\n" + "Bloom Time: " + c.getString(5))
                        .setCancelable(true)
                ;
                builder.create().show();
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(PlantMenu.this, PlantHistory.class);
                Bundle bundle = new Bundle();
                bundle.putString("plantHistoryName", plant.getPlantName());
                intent.putExtras(bundle);
                // System.out.println("Waht is the PlantName"+ plant.getPlantName());
                startActivity(intent);
            }
        });

        Button button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(PlantMenu.this);
//                Plant plant = db.fetchPlantWithIdFor(id);
//                Date lastDate = plant.lastWater;
//                Calendar cal = Calendar.getInstance();
//                cal.setTime(lastDate);
//                cal.add(Calendar.DATE, plant.waterInterval);
//                Date nextDate = cal.getTime();`````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````
                Cursor cursor = db.getPlant(id);
                cursor.moveToFirst();
                long date = cursor.getLong(cursor.getColumnIndex("NextWater"));
                Date next = new Date(date);
                Format format = new SimpleDateFormat("MM/dd/yyyy");
//                format.format(date);
                builder.setIcon(R.mipmap.ic_launcher)
                        .setTitle("Next Watering date")
                        .setMessage(format.format(next))
                        //            .setMessage(cursor.getString(cursor.getColumnIndex("lastWater")))
                        .setCancelable(true)
                ;
                builder.create().show();
            }
        });

    }
    //        button3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                readEditPreference();
//            }
//        });
//
//
//        mySharedPreferences = getSharedPreferences(myPreference, Activity.MODE_PRIVATE);

    public void addPlantPhotoTap() {
        System.out.println("got to addPlantPhotoTap!!!");

        final CharSequence[] items = { "Take Photo", "Choose from Library", "Cancel" };
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(PlantMenu.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new android.content.DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = Utility.checkPermission(PlantMenu.this);
                if (items[item].equals("Take Photo")) {
                    userChoosenTask = "Take Photo";
                    if (result)
                        cameraIntent();
                } else if (items[item].equals("Choose from Library")) {
                    userChoosenTask = "Choose from Library";
                    if (result)
                        galleryIntent();
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void cameraIntent(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) == null) {
            Toast.makeText(getApplicationContext(), "Cannot take pictures on this device!", Toast.LENGTH_SHORT).show();
            return;
        }

        fileName = getOutputFileName();
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.parse(fileName));

        startActivityForResult(intent, 2345);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // if (requestCode != 2345 || resultCode != RESULT_OK) return;

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE) {
                onSelectFromGalleryResult(data);
                fileName = RealPathUtil.getRealPathFromURI_API19(this, data.getData());
            }else{
                fileName = Uri.parse(fileName).getPath();
            }
        }
        String current_date = new SimpleDateFormat("MM/dd/yyyy").format(new java.util.Date());
        Plant p = new Plant(plant.getId(),plant.getPlantName(),fileName, current_date, 0, new java.util.Date());
//        Cursor c = cdb.getAPlant(id);
//        c.moveToFirst();

//        p.setPlantName(plant.getPlantName());
//
//        // System.out.println("FlowerSSS" + c.getString(0));
//        p.setPhotoPath(fileName);
//
//        String current_date = new SimpleDateFormat("MM/dd/yyyy").format(new java.util.Date());
//        p.setDate(current_date);



        db.add(p);

    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {
        bitmap = null;
        if (data != null) {
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String getOutputFileName() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new java.util.Date());
        String filename =
                "file://"
                        + Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                        + "/JPEG_"
                        + timeStamp
                        + ".jpg";
        Log.i("lwang", filename);
        return filename;
    }

    private void acquireRunTimePermissions() {
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    111);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode != 111) return;
        if (grantResults.length >= 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getApplicationContext(), "Great! We have the permission!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "Cannot write to external storage! App will not work properly!", Toast.LENGTH_SHORT).show();
        }
    }


    private void galleryIntent()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
    }


}