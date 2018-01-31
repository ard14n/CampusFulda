package com.amedia.campusfulda;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Dini on 17.01.2018.
 */


public class DatabaseHelper extends SQLiteOpenHelper{

    private static final int DB_VERSION = 2;
    private static final String DB_NAME = "so_systems.db";
    private static final String DB_TABLE = "campus";
    private static final String COL_ID = "_id";
    private static final String COL_NAME = "c_name";
    private static final String COL_OPENED = "c_oezeiten";
    private static final String COL_ADDRESS = "c_address";
    private static final String COL_LAT = "c_lat";
    private static final String COL_LONG = "c_long";
    private static final String COL_INFO = "c_info";
    private static final String COL_GEBAEUDE = "c_is_gebaeude";
    private static final String COL_WASSER = "c_is_wasser";
    private static final String COL_SNACKS = "c_is_snacks";
    private static final String COL_DRUCKER = "c_is_drucker";
    private static final String COL_GELD = "c_is_geld";
    private static final String COL_PARK = "c_is_park";
    private static final String COL_BUS = "c_is_bushaltestelle";
    private static final String COL_FAHRRAD = "c_is_fahrradstaender";
    private static final String COL_FREIZEIT= "c_is_freizeitmoeglichkeit";
    private static final String COL_TAGS = "c_tags";


    private static final String CREATE_SO_TABLE = "CREATE TABLE "+ DB_TABLE +"("+
            COL_ID	+" INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE, " +
            COL_NAME + " TEXT, "+
            COL_ADDRESS + " TEXT, " +
            COL_LAT	+ " FLOAT, "+
            COL_LONG + " FLOAT, "+
            COL_INFO + " INTEGER, "+
            COL_GEBAEUDE + " INTEGER, "+
            COL_WASSER + " INTEGER, "+
            COL_SNACKS + " INTEGER, "+
            COL_DRUCKER + " INTEGER, "+
            COL_GELD +	" INTEGER, "+
            COL_PARK + " INTEGER, "+
            COL_BUS	+ " INTEGER, "+
            COL_FAHRRAD + " INTEGER, "+
            COL_FREIZEIT + " INTEGER, "+
            COL_TAGS + " TEXT, "+
            COL_OPENED + " TEXT );";


    private SQLiteDatabase myDataBase;
    private static DatabaseHelper instance;


    public static DatabaseHelper createInstance(Context context, String dbname, int dbversion){

        if(instance == null){

            instance = new DatabaseHelper(context, dbname, dbversion);

        }

        return instance;

    }


    private DatabaseHelper(Context context, String name, int version) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_SO_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE);
        onCreate(db);
    }

    /*
    *  Fügt einen Datensatz in die Datenbank ein
    *
    *
    * @param name
    * @param opened
    * @param address
    * @param info
    * @param tags
    * @param clat
    * @param clong
    * @param gebaeude
    * @param wasser
    * @param snacks
    * @param drucker
    * @param geld
    * @param park
    * @param bus
    * @param fahrrad
    * @param freizeit
    *
    * */

    //TODO ArrayList mit CampusItems als Parameter
    public void insertData(String name, String opened, String address, String info, String tags, double clat, double clong, int gebaeude, int wasser, int snacks, int drucker, int geld, int park, int bus, int fahrrad, int freizeit ){

        myDataBase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();


        contentValues.put(COL_NAME, name);
        contentValues.put(COL_ADDRESS, address);
        contentValues.put(COL_OPENED, opened);
        contentValues.put(COL_LAT, clat);
        contentValues.put(COL_LONG, clong);
        contentValues.put(COL_INFO, info);
        contentValues.put(COL_GEBAEUDE, gebaeude);
        contentValues.put(COL_WASSER, wasser);
        contentValues.put(COL_SNACKS, snacks);
        contentValues.put(COL_DRUCKER, drucker);
        contentValues.put(COL_GELD, geld);
        contentValues.put(COL_PARK, park);
        contentValues.put(COL_BUS, bus);
        contentValues.put(COL_FAHRRAD, fahrrad);
        contentValues.put(COL_FREIZEIT, freizeit);
        contentValues.put(COL_TAGS, tags);


        long affectedColumnId = myDataBase.insert(DB_TABLE, null, contentValues);

        myDataBase.close();

    }


    /*
    *
    * Holt alle Informationen für den Campusplan aus der Datenbank.
    *
    * @return campuslist vom Typ CampusItems
    *
    *
    * */
    public List<CampusItems> getAllItems(){
        SQLiteDatabase db = getReadableDatabase();

        List<CampusItems> campuslist = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + DB_TABLE + ";";

        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            do {
                CampusItems campus = new CampusItems();
                HashMap<String, Number> statematrix = new HashMap<>();

                campus.setId(c.getInt(c.getColumnIndex(COL_ID)));
                campus.setName(c.getString((c.getColumnIndex(COL_NAME))));
                campus.setInfo(c.getString((c.getColumnIndex(COL_INFO))));
                campus.setOpened(c.getString((c.getColumnIndex(COL_OPENED))));
                campus.setTags(c.getString(c.getColumnIndex(COL_TAGS)));
                campus.setLat(c.getDouble(c.getColumnIndex(COL_LAT)));
                campus.setLong(c.getDouble(c.getColumnIndex(COL_LONG)));
                campus.setAddress(c.getString(c.getColumnIndex(COL_ADDRESS)));

                statematrix.put("gebaeude", c.getInt(c.getColumnIndex(COL_GEBAEUDE)));
                statematrix.put("wasserspender", c.getInt(c.getColumnIndex(COL_WASSER)));
                statematrix.put("snacks", c.getInt(c.getColumnIndex(COL_SNACKS)));
                statematrix.put("drucker", c.getInt(c.getColumnIndex(COL_DRUCKER)));
                statematrix.put("geld", c.getInt(c.getColumnIndex(COL_GELD)));
                statematrix.put("park", c.getInt(c.getColumnIndex(COL_PARK)));
                statematrix.put("bus", c.getInt(c.getColumnIndex(COL_BUS)));
                statematrix.put("fahrrad", c.getInt(c.getColumnIndex(COL_FAHRRAD)));
                statematrix.put("freizeit", c.getInt(c.getColumnIndex(COL_FREIZEIT)));

                campus.setStatematrix(statematrix);
                campuslist.add(campus);

                Log.d("LAT LONG DEBUG "+c.getDouble(c.getColumnIndex(COL_LAT)), "Hi");

            } while (c.moveToNext());
        }

        c.close();
        db.close();
        return campuslist;
    }





}
