package edu.scu.lwang.finalprojectscene;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.clarifai.api.ClarifaiClient;
import com.clarifai.api.RecognitionRequest;
import com.clarifai.api.RecognitionResult;
import com.clarifai.api.Tag;
import com.clarifai.api.exception.ClarifaiException;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.IDN;
import java.util.ArrayList;
import java.util.Hashtable;


public class RecognitionActivity extends AppCompatActivity {

    final CharSequence[] items = { "Take a Photo", "Choose from Gallery", "Cancel" };
    final int REQUEST_CAMERA = 0;
    final int SELECT_FILE = 1;

    private String userChoosenTask;

    private static final String TAG = RecognitionActivity.class.getSimpleName();
    private static final int CODE_PICK = 1;
    private final ClarifaiClient client = new ClarifaiClient(Credentials.CLIENT_ID,
            Credentials.CLIENT_SECRET);

    private Button selectPicButton;
    private ImageView imageView;
    private TextView tagOutput;
    private Bitmap bitmap;

    PlantCollectionDBHelper collectionDBHelper;
    IdentifyOutcomeDBHelper outcomeDBHelper;
    Hashtable<String, String> plantNameHash;
    Cursor cursor;

    String bitmapPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recognition);

        AlertDialog.Builder builder = new AlertDialog.Builder(RecognitionActivity.this);
        builder.setTitle("Add Photo");
        boolean result = Utility.checkPermission(RecognitionActivity.this);

        imageView = (ImageView) findViewById(R.id.imageView);
        tagOutput = (TextView) findViewById(R.id.tagOutput);
        selectPicButton = (Button) findViewById(R.id.btnSelectPhoto);
        selectPicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        collectionDBHelper = new PlantCollectionDBHelper(this);


        outcomeDBHelper = new IdentifyOutcomeDBHelper(this);
        plantNameHash = collectionDBHelper.fetchPlantName();
        outcomeDBHelper.clearTable();
        acquireRunTimePermissions();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE) {
                onSelectFromGalleryResult(data);
                bitmapPath = RealPathUtil.getRealPathFromURI_API19(this, data.getData());
            }
            else if (requestCode == REQUEST_CAMERA) {
                onCaptureImageResult(data);
            }
        }

        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
            tagOutput.setText("Recognizing...");
            selectPicButton.setEnabled(false);

            // Run recognition on a background thread since it makes a network call.
            new AsyncTask<Bitmap, Void, RecognitionResult>() {
                @Override protected RecognitionResult doInBackground(Bitmap... bitmaps) {
                    return recognizeBitmap(bitmaps[0]);
                }
                @Override protected void onPostExecute(RecognitionResult result) {
                    updateUIForResult(result);
                }
            }.execute(bitmap);

        }
        else {
            tagOutput.setText("Unable to load selected image.");
        }

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
        imageView.setImageBitmap(bitmap);
    }

    @SuppressWarnings("deprecation")
    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File destination = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), System.currentTimeMillis() + ".jpg");
        bitmapPath = destination.getPath();
        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        }
        catch(FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        imageView.setImageBitmap(thumbnail);
    }

    /** Sends the given bitmap to Clarifai for recognition and returns the result. */
    private RecognitionResult recognizeBitmap(Bitmap bitmap) {
        try {
            // Scale down the image. This step is optional. However, sending large images over the
            // network is slow and  does not significantly improve recognition performance.
            Bitmap scaled = Bitmap.createScaledBitmap(bitmap, 320,
                    320 * bitmap.getHeight() / bitmap.getWidth(), true);

            // Compress the image as a JPEG.
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            scaled.compress(Bitmap.CompressFormat.JPEG, 90, out);
            byte[] jpeg = out.toByteArray();

            // Send the JPEG to Clarifai and return the result.
            return client.recognize(new RecognitionRequest(jpeg)).get(0);
        } catch (ClarifaiException e) {
            Log.e(TAG, "Clarifai error", e);
            return null;
        }
    }

    /** Updates the UI by displaying tags for the given result. */
    private void updateUIForResult(RecognitionResult result) {
        if (result != null) {
            if (result.getStatusCode() == RecognitionResult.StatusCode.OK) {
                // Display the list of tags in the UI.
//                StringBuilder b = new StringBuilder();
//                for (Tag tag : result.getTags()) {
//                    b.append(b.length() > 0 ? ", " : "").append(tag.getName());
//                }
//                tagOutput.setText("Tags:\n" + b);

                // tagArraylist to hold every tag we get from the server
                ArrayList<String> tagArraylist = new ArrayList<String>();
                for (Tag tag : result.getTags()) {
                    tagArraylist.add(tag.getName());
                }

                for (int i = 0; i < tagArraylist.size(); i++) {
                    if (plantNameHash.size() == 0 || plantNameHash == null) {
                        break;
                    }

                    if (plantNameHash.containsKey(tagArraylist.get(i))) {
//                        possibleOutcome.put(tagArraylist.get(i), plantNameHash.get(tagArraylist.get(i)));
                        Plant plant = new Plant();
                        plant.plantName = tagArraylist.get(i);

//                        plant.photoPath = plantNameHash.get(tagArraylist.get(i));
                        plant.photoPath = bitmapPath;
                        outcomeDBHelper.add(plant);
//                        cursor.requery();
                    }

                }
                if (outcomeDBHelper.getMaxRecID() == 0) {
                    tagOutput.setText("Cannot recognize the flower, or the flower is not in our database.\n You can choose to retake picture.");
                }
                else {
                    Intent outcomeIntent = new Intent(RecognitionActivity.this, IdentifyOutcome.class);
                    startActivity(outcomeIntent);
                }



            } else {
                Log.e(TAG, "Clarifai: " + result.getStatusMessage());
                tagOutput.setText("Sorry, there was an error recognizing your image.");
            }
        } else {
            tagOutput.setText("Sorry, there was an error recognizing your image.");
        }
        selectPicButton.setEnabled(true);
    }

    private void selectImage() {
        final CharSequence[] items = { "Take Photo", "Choose from Library", "Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(RecognitionActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new android.content.DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = Utility.checkPermission(RecognitionActivity.this);
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


    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
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
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(userChoosenTask.equals("Take Photo"))
                        cameraIntent();
                    else if(userChoosenTask.equals("Choose from Library"))
                        galleryIntent();
                } else {
                    Toast.makeText(getApplicationContext(), "Cannot write to external storage! App will not work properly!", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }



    private void toast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
