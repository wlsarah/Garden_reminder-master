package edu.scu.lwang.finalprojectscene;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.Hashtable;
/**
 * Created by Sarah on 5/23/2016.
 */

public class PlantCollectionDBHelper extends SQLiteOpenHelper{
    static private final int VERSION = 4;
    static private final String DB_NAME="Plant-Collection.db";

    static private final String SQL_CREATE_TABLE =
            "CREATE TABLE plantCollection (" +
                    "  _id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "  plantName TEXT," +
                    "  photoPath TEXT," +
                    "  waterInterval INTEGER," +
                    "  sun TEXT," +
                    "  fertilizeTime TEXT," +
                    "  fertilizer TEXT," +
                    "  pestsAndDiseases TEXT," +
                    "  bloomTime TEXT);";

    static private final String SQL_DROP_TABLE = "DROP TABLE plantCollection";

    Context context;

    public PlantCollectionDBHelper(Context context) {
        super(context, DB_NAME, null, VERSION);     // we use default cursor factory (null, 3rd arg)
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE);
        String sql = "INSERT or replace INTO plantCollection (plantName, photoPath, waterInterval, sun, fertilizeTime, fertilizer, pestsAndDiseases, bloomTime) VALUES('Dianthus','http://www.homedepot.com/catalog/productImages/1000/e6/e65ebf33-56a0-49c5-84cb-32e9bc1797d9_1000.jpg',14,'Full sun','every spring','N-P-K Ratio 15-30-15',' Fusarium, spider, and slug','From spring to fall')";
        db.execSQL(sql);
        sql = "INSERT or replace INTO plantCollection (plantName, photoPath, waterInterval, sun, fertilizeTime, fertilizer, pestsAndDiseases, bloomTime) VALUES('violet','http://www.wildflowersofireland.net/image_uploads/flowers/Dog-Violet-Common-1A.jpg',14,'Full sun','when planting','Mix with compost when planting','Insect and disease problems are uncommon.','Summer')";
        db.execSQL(sql);
        sql = "INSERT or replace INTO plantCollection (plantName, photoPath, waterInterval, sun, fertilizeTime, fertilizer, pestsAndDiseases, bloomTime) VALUES('Autumn Fire Stonecrop','http://www.homedepot.com/catalog/productImages/400/84/84459158-1f37-44ac-9a89-6ed63b3d6097_400.jpg',7,'Full sun','No need','Succulent Plant Food','Insect and disease problems are uncommon.','Summer')";
        db.execSQL(sql);
        sql = "INSERT or replace INTO plantCollection (plantName, photoPath, waterInterval, sun, fertilizeTime, fertilizer, pestsAndDiseases, bloomTime) VALUES('Daisy','http://www.homedepot.com/catalog/productImages/400/3c/3c216a4d-330b-46ef-a69f-3b0e18f1b3bc_400.jpg',14,'Full sun to patial shade','Fertilize every spring with slow-release fertilizer','general-purpose slow-acting granular fertilizer','Aphids, Mealybugs, snails','From spring to fall')";
        db.execSQL(sql);
        sql = "INSERT or replace INTO plantCollection (plantName, photoPath, waterInterval, sun, fertilizeTime, fertilizer, pestsAndDiseases, bloomTime) VALUES('peony','http://g01.a.alicdn.com/kf/HTB11GSgJpXXXXb4XXXXq6xXFXXX9/Free-shipping-6-PackS-120-font-b-Seeds-b-font-blue-white-black-pink-red-font.jpg',1,'Full sun','every spring','ow nitrogen fertilizer (5-10-10)','gray mold,Peony blotch,Slugs Or Snails','mid-spring')";
        db.execSQL(sql);
        sql = "INSERT or replace INTO plantCollection (plantName, photoPath, waterInterval, sun, fertilizeTime, fertilizer, pestsAndDiseases, bloomTime) VALUES('Petunias','http://www.homedepot.com/catalog/productImages/400/08/086bf489-df7e-435b-a12a-2bb736f5e488_400.jpg',7,'full sun to 1/2 day shade','mid July','fertilizer with equal parts of nitrogen, phosphate and potash.','mites, caterpillars, thrips','From spring to fall')";
        db.execSQL(sql);
        sql = "INSERT or replace INTO plantCollection (plantName, photoPath, waterInterval, sun, fertilizeTime, fertilizer, pestsAndDiseases, bloomTime) VALUES('Geranium','http://www.homedepot.com/catalog/productImages/400/22/2249b071-5725-487c-8bff-a24611ebb190_400.jpg',7,'full sun to 1/2 day shade','Spring and July','5-10-5 fertilizer','Bacterial Blight and leave spots','From spring to fall')";
        db.execSQL(sql);
        sql = "INSERT or replace INTO plantCollection (plantName, photoPath, waterInterval, sun, fertilizeTime, fertilizer, pestsAndDiseases, bloomTime) VALUES('Impatiens','http://www.homedepot.com/catalog/productImages/400/c6/c60b71b8-7b4c-4322-806b-8821d0e9e901_400.jpg',7,'full sun to 1/2 day shade','every 2 months','15-15-15 or 20-10-20','Spider mites, mealybugs, aphids, and thrips','From spring to fall')";
        db.execSQL(sql);
        sql = "INSERT or replace INTO plantCollection (plantName, photoPath, waterInterval, sun, fertilizeTime, fertilizer, pestsAndDiseases, bloomTime) VALUES('rose','http://www.flowerdelivery.org.uk/wp-content/uploads/2012/07/Valentines-Flowers-01-300x225.jpg',3,'part shade to shade','once a month from Feb to Sep','acid lover fertilizer','Mealy bug','From spring to fall')";
        db.execSQL(sql);
        sql = "INSERT or replace INTO plantCollection (plantName, photoPath, waterInterval, sun, fertilizeTime, fertilizer, pestsAndDiseases, bloomTime) VALUES('Lantana','http://www.homedepot.com/catalog/productImages/400/a1/a1a42570-e9fa-41d0-a3a9-2b91525ee2f9_400.jpg',7,'full sun to 1/2 day shade','Spring and mid summer','low PH fertilizer with sulfur','Whiteflies and Leaf Miner','From spring to fall')";
        db.execSQL(sql);
        sql = "INSERT or replace INTO plantCollection (plantName, photoPath, waterInterval, sun, fertilizeTime, fertilizer, pestsAndDiseases, bloomTime) VALUES('Hydrangea','http://www.homedepot.com/catalog/productImages/400/be/be24e067-898f-4f11-8d36-25e58372ae06_400.jpg',1,'Sun to 1/2 day shade','twice in summer','Potassium (K), calcium, magnesium, iron, copper, manganese, zinc, and sulfur','Aphids, Scales and Whiteflies','Perennial ')";
        db.execSQL(sql);
        sql = "INSERT or replace INTO plantCollection (plantName, photoPath, waterInterval, sun, fertilizeTime, fertilizer, pestsAndDiseases, bloomTime) VALUES('Society Garlic','http://www.homedepot.com/catalog/productImages/400/d0/d043fc69-7ae8-4aef-9ffa-9436a0485f2f_400.jpg',7,'full sun   ','very less needed','general-purpose fertilizer','seldom have problems with insects','From spring to summer')";
        db.execSQL(sql);
        sql = "INSERT or replace INTO plantCollection (plantName, photoPath, waterInterval, sun, fertilizeTime, fertilizer, pestsAndDiseases, bloomTime) VALUES('Echinacea','http://www.homedepot.com/catalog/productImages/400/2d/2d8c241f-2246-4c4c-aa4e-c9c8f5889958_400.jpg',7,'full sun   ','every spring','compost or well-rotted manure','Sweet potato whiteflies, aphids, Japanese beetles, mites','From summer to fall')";
        db.execSQL(sql);
        sql = "INSERT or replace INTO plantCollection (plantName, photoPath, waterInterval, sun, fertilizeTime, fertilizer, pestsAndDiseases, bloomTime) VALUES('Angelonia','http://www.homedepot.com/catalog/productImages/400/05/05c69076-2a4d-4d8d-a9fc-f8960df98a49_400.jpg',7,'full sun','every 2 months','10-5-10 fertilizer','Deer and aphids','From spring to fall')";
        db.execSQL(sql);
        sql = "INSERT or replace INTO plantCollection (plantName, photoPath, waterInterval, sun, fertilizeTime, fertilizer, pestsAndDiseases, bloomTime) VALUES('chamomile','http://www.pawsdogdaycare.com/sites/default/files/imagecache/poisonous_plant/Anthemis%20nobilis.jpg',5,'Full sun to 1/2 day shade','every spring','complete fertilizer such as 16-4-8','leafminers, aphids, mites','From spring to fall')";
        db.execSQL(sql);
        sql = "INSERT or replace INTO plantCollection (plantName, photoPath, waterInterval, sun, fertilizeTime, fertilizer, pestsAndDiseases, bloomTime) VALUES('Gerbera','http://www.ourhouseplants.com/imgs-content/gerbera-flowers-red.jpg',2,'Full sun to 1/2 day shade','every spring','complete fertilizer such as 16-4-8','leafminers, aphids, mites','From spring to fall')";
        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // a simple crude implementation that does not preserve data on upgrade
        db.execSQL(SQL_DROP_TABLE);
        db.execSQL(SQL_CREATE_TABLE);

        Toast.makeText(context, "Upgrading DB and dropping data!!!", Toast.LENGTH_SHORT).show();
    }

    public int getMaxRecID() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT MAX(_id) FROM plantCollection;", null);

        if (cursor.getCount() == 0) {
            return 0;
        } else {
            cursor.moveToFirst();
            return cursor.getInt(0);
        }
    }

    public Cursor fetchAll() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM plantCollection;", null);
    }

    public void add(Plant plant) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("plantName", plant.plantName);
        contentValues.put("photoPath", plant.photoPath);
        contentValues.put("waterInterval", plant.waterInterval);
        //   contentValues.put("plantPicDate", plant.date);

        db.insert("plantCollection", null, contentValues);

        /*
        String SQL_ADD =
                "INSERT INTO contact (name, surname, email, phone) VALUES ('"
                + ci.name + "', '"
                + ci.surname + "', '"
                + ci.email + "', '"
                + ci.phone +"');";
        db.execSQL(SQL_ADD);
        */
    }


    public Cursor getAPlant(int recID){
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT plantName, sun, fertilizeTime, fertilizer, pestsAndDiseases, bloomTime FROM plantCollection WHERE _id=?;", new String[]{String.valueOf(recID)});
    }




    public void delete(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        db.delete("contact", "_id=?", new String[]{String.valueOf(id)});

        /*
        String SQL_DELETE="DELETE FROM contact WHERE _id=" + id + ";";
        db.execSQL(SQL_DELETE);
         */
    }

    public Hashtable<String, String> fetchPlantName() {
        Hashtable<String, String> plantNameHash = new Hashtable<String, String>();
//        SQLiteDatabase db = this.getReadableDatabase();
//        String mypath = DB_Path + DB_NAME;
//        SQLiteDatabase db = SQLiteDatabase.openDatabase(mypath, null, SQLiteDatabase.CREATE_IF_NECESSARY);

        Cursor cursor = fetchAll();
        if (cursor.moveToFirst()) {
            do {
                plantNameHash.put(cursor.getString(cursor.getColumnIndex("plantName")), cursor.getString(cursor.getColumnIndex("photoPath")));
            } while (cursor.moveToNext());
        }
        else {
            return null;
        }
        return plantNameHash;
    }

    public Hashtable<String, Integer> fetchWaterInterval() {
        Hashtable<String, Integer> plantWaterInterval = new Hashtable<String, Integer>();
        Cursor cursor = fetchAll();
        if (cursor.moveToFirst()) {
            do {
                plantWaterInterval.put(cursor.getString(cursor.getColumnIndex("plantName")), cursor.getInt(cursor.getColumnIndex("waterInterval")));
            } while (cursor.moveToNext());
        }
        else {
            return null;
        }
        return plantWaterInterval;
    }

}