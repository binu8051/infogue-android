package com.sketchproject.infogue.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.sketchproject.infogue.models.Category;
import com.sketchproject.infogue.models.Subcategory;

/**
 * Create helper to create and destroy database.
 * <p>
 * Sketch Project Studio
 * Created by Angga on 24/04/2016 08.54.
 */
public class DBHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Infogue.db";

    private static final String SQL_CREATE_CATEGORY = "CREATE TABLE " + Category.TABLE + " (" +
            Category.ID + " INTEGER PRIMARY KEY," +
            Category.CATEGORY + " TEXT)";
    private static final String SQL_CREATE_SUBCATEGORY = "CREATE TABLE " + Subcategory.TABLE + " (" +
            Subcategory.ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            Subcategory.CATEGORY_ID + " INTEGER," +
            Subcategory.SUBCATEGORY + " TEXT," +
            Subcategory.LABEL + " TEXT)";

    private static final String SQL_DROP_CATEGORY = "DROP TABLE IF EXISTS " + Category.TABLE;
    private static final String SQL_DROP_SUBCATEGORY = "DROP TABLE IF EXISTS " + Subcategory.TABLE;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Create database for the first time.
     *
     * @param db SQLiteDatabase
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_CATEGORY);
        db.execSQL(SQL_CREATE_SUBCATEGORY);
    }

    /**
     * Upgrade database when android detect database version is decremented.
     *
     * @param db         SQLite database
     * @param oldVersion number old version
     * @param newVersion number new version
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DROP_SUBCATEGORY);
        db.execSQL(SQL_DROP_CATEGORY);
        onCreate(db);
    }

    /**
     * Downgrade database when android detect database version is decremented.
     *
     * @param db         SQLite database
     * @param oldVersion number old version
     * @param newVersion number new version
     */
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
    }
}
