package edu.scu.lwang.finalprojectscene;


import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.SyncStateContract;
import android.renderscript.ScriptGroup;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Hashtable;

/**
 * Created by Lexie on 5/22/16.
 * ===========================================================================================================================================
 * This dbhelper is not used!!! Because I can't solve the problem of "cannot find table". But this should be the more efficient way to import flower collection database.
 */

public class PlantInfoDBHelper extends SQLiteOpenHelper {
    static private final int VERSION = 1;
    //    static private final String DB_PACKAGENAME = "edu.xlaiscu.gardenreminding";
    static private final String DB_NAME="flowerInfoTotal";
    static private final String DB_Path = "/data/data/edu.xlaiscu.gardenreminding/databases/";
    public SQLiteDatabase myDataBase;
    private final Context myContext;

//    static private final String SQL_CREATE_TABLE =
//            "CREATE TABLE plantInfo (" +
//                    "  _id INTEGER PRIMARY KEY AUTOINCREMENT," +
//                    "  PlantName TEXT," +
//                    "  PhotoPath TEXT," +
//                    "  WaterInterval INTEGER" +
//                    "  LastWater DATE);";


//    static private final String SQL_DROP_TABLE = "DROP TABLE plantInfo";


    public PlantInfoDBHelper(Context context) {
        super(context, DB_NAME, null, VERSION);     // we use default cursor factory (null, 3rd arg)
        this.myContext = context;
//        this.DB_Path = context.getFilesDir().getPath();
//        DB_Path = context.getApplicationInfo().dataDir + "/databases/";
//        boolean dbExist = checkDataBase();
//        if (dbExist) {
//            openDataBase();
//        }
//        else {
//            createDataBase();
//        }
    }

    /**
     * Creates a empty database on the system and rewrites it with your own database.
     * */

    public void createDataBase() throws IOException {
        boolean dbExist = checkDataBase();
//        SQLiteDatabase db_Read = null;
        if (dbExist) {
            Toast.makeText(myContext, "Database exists", Toast.LENGTH_SHORT).show();
        }
        else {

            //By calling this method and empty database will be created into the default system path
            //of your application so we are gonna be able to overwrite that database with our database.
            this.getReadableDatabase();
//            db_Read.close();
            try {
                copyDataBase();
            }
            catch (IOException e) {
                throw new Error ("Error copying database");
            }
        }
    }

    /**
     * Check if the database already exist to avoid re-copying the file each time you open the application.
     * @return true if it exists, false if it doesn't
     */

    private boolean checkDataBase() {
        SQLiteDatabase checkDB = null;
//        boolean checkdb = false;
        try {
            String myPath = DB_Path + DB_NAME;
//            File dbfile = context.getDatabasePath(DB_NAME);
//            checkdb = dbfile.exists();
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
        }
        catch (SQLiteException e) {
            Toast.makeText(myContext, "Database doesn't exist!", Toast.LENGTH_SHORT).show();

        }

        if (checkDB != null) {
            checkDB.close();
        }
        return checkDB != null ? true : false;
    }

    /**
     * Copies your database from your local assets-folder to the just created empty database in the
     * system folder, from where it can be accessed and handled.
     * This is done by transfering bytestream.
     * */

    private void copyDataBase() throws IOException {
        InputStream myInput = myContext.getAssets().open(DB_NAME);
        String outFileName = DB_Path + DB_NAME;
        OutputStream myOutput = new FileOutputStream(outFileName);

        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }

        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    private void openDataBase() throws SQLException {
        String mypath = DB_Path + DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(mypath, null, SQLiteDatabase.OPEN_READONLY);
    }

    public synchronized void close() {
        if (myDataBase != null) {
            myDataBase.close();
        }
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.v("TAG", "On create called");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        if (newVersion > oldVersion) {
//            try {
//                copyDataBase();
//            }
//            catch (IOException e) {
//
//            }
//        }

        Log.w(PlantInfoDBHelper.class.getName(), "Upgrading database from version " + oldVersion  + " to "
                + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + DB_NAME);
        onCreate(db);

    }

    public Cursor fetchAll() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM flowerInfoTotal;", null);
    }

    public Hashtable<String, String> fetchPlantName() {
        Hashtable<String, String> plantNameHash = new Hashtable<String, String>();
//        SQLiteDatabase db = this.getReadableDatabase();
        String mypath = DB_Path + DB_NAME;
        SQLiteDatabase db = SQLiteDatabase.openDatabase(mypath, null, SQLiteDatabase.CREATE_IF_NECESSARY);

        Cursor cursor = fetchAll();
        if (cursor.moveToFirst()) {
            do {
                plantNameHash.put(cursor.getString(cursor.getColumnIndex("name")), cursor.getString(cursor.getColumnIndex("picture")));
            } while (cursor.moveToNext());
        }
        else {
            return null;
        }
        return plantNameHash;
    }
}
