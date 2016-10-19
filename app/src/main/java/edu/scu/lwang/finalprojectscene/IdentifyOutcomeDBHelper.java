package edu.scu.lwang.finalprojectscene;

/**
 * Created by Sarahwang on 5/24/16.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Lexie on 5/22/16.
 */
public class IdentifyOutcomeDBHelper extends SQLiteOpenHelper{

    static private final int VERSION = 4;
    static private final String DB_NAME = "Identifi-Outcome-CursorAdaptor.db";

    static private final String SQL_CREATE_TABLE =
            "CREATE TABLE outcome (" +
                    "  _id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "  plantName TEXT," +
                    "  imageTakePath TEXT);";

    static private final String SQL_DROP_TABLE = "DROP TABLE outcome";

    Context context;

    public IdentifyOutcomeDBHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DROP_TABLE);
        db.execSQL(SQL_CREATE_TABLE);

    }

    public int getMaxRecID() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT MAX(_id) FROM outcome;", null);

        if (cursor.getCount() == 0) {
            return 0;
        }
        else {
            cursor.moveToFirst();
            return cursor.getInt(0);
        }
    }

    public Cursor fetchAll() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM outcome;", null);
    }

    public void clearTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("outcome", null, null);
    }

    public void add(Plant plant) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("plantName", plant.plantName);
        contentValues.put("imageTakePath", plant.photoPath);

        db.insert("outcome", null, contentValues);
    }

    public void delete(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        db.delete("outcome", "_id=?", new String[]{String.valueOf(id)});
    }

}
