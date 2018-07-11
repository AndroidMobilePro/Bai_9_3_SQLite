package com.proteam.bai_9_3_sqlite.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

//---------------- class OpenHelper ------------------
public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "DB_USER";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_ACCOUNT = "ACCOUNT";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_ACC = "tentaikhoan";
    public static final String COLUMN_PASSWORD = "matkhau";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Singleton pattern
    private static DatabaseHelper instance;

    public synchronized static DatabaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseHelper(context.getApplicationContext());
        }
        return instance;
    }


    @Override
    public void onCreate(SQLiteDatabase arg0) {
        arg0.execSQL("CREATE TABLE " + TABLE_ACCOUNT + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_ACC + " TEXT NOT NULL, "
                + COLUMN_PASSWORD + " TEXT NOT NULL);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
        arg0.execSQL("DROP TABLE IF EXISTS " + TABLE_ACCOUNT);
        onCreate(arg0);
    }
}