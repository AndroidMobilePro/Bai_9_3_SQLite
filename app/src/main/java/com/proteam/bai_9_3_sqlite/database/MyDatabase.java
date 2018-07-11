package com.proteam.bai_9_3_sqlite.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import static com.proteam.bai_9_3_sqlite.database.DatabaseHelper.COLUMN_ACC;
import static com.proteam.bai_9_3_sqlite.database.DatabaseHelper.COLUMN_ID;
import static com.proteam.bai_9_3_sqlite.database.DatabaseHelper.COLUMN_PASSWORD;
import static com.proteam.bai_9_3_sqlite.database.DatabaseHelper.TABLE_ACCOUNT;

public class MyDatabase {

    private static Context context;
    static SQLiteDatabase db;
    private DatabaseHelper openHelper;

    public MyDatabase(Context c) {
        MyDatabase.context = c;
    }

    public MyDatabase open() throws SQLException {
        openHelper = DatabaseHelper.getInstance(context);
        db = openHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        openHelper.close();
    }

    public long createData(String tenDN, String matKhau) {
        ContentValues cv = new ContentValues();//doi tuong dua du lieu vao bang
        cv.put(COLUMN_ACC, tenDN);
        cv.put(COLUMN_PASSWORD, matKhau);
        return db.insert(TABLE_ACCOUNT, null, cv);
    }

    public String getData() {
        String[] columns = new String[]{COLUMN_ID, COLUMN_ACC, COLUMN_PASSWORD};
        //Cursor c = db.query(TABLE_ACCOUNT, columns, COLUMN_ID+"=?", new String[]{"12"}, null, null, null);
        Cursor c = db.query(TABLE_ACCOUNT, columns, null, null, null, null, null);
        /*if(c==null)
            Log.v("Cursor", "C is NULL");*/
        String result = "";
        int iRow = c.getColumnIndex(COLUMN_ID);
        int iN = c.getColumnIndex(COLUMN_ACC);
        int iMK = c.getColumnIndex(COLUMN_PASSWORD);
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            result = result + " " + c.getString(iRow)
                    + " - id:" + c.getString(iN)
                    + " - pw:" + c.getString(iMK) + "\n";

        }
        c.close();
        return result;
    }

    /*Hàm đăng nhập với đối số đầu vào là tên acc và mật khẩu*/
    public boolean kiemTraLogin(String acc, String mk) {
        Cursor c = db.rawQuery("select * from " + TABLE_ACCOUNT + " where " + COLUMN_ACC + " = ? and " + COLUMN_PASSWORD + " = ?", new String[]{acc, mk});
        if (c.getCount() == 1) {
            c.close();
            return true;
        } else {
            c.close();
            return false;
        }
    }

    /*Hàm xóa một tài khoản với đối số đầu vào là acc cần xóa*/
    public int deleteAcc(String acc) {
        return db.delete(TABLE_ACCOUNT, COLUMN_ACC + "='" + acc + "'", null);
    }

    /*Hàm xóa toàn bộ table ACCOUNT*/
    public int deleteAccountAll() {
        return db.delete(TABLE_ACCOUNT, null, null);
    }

    /*Hàm cập nhật tên người dùng với đầu vào là acc, mật khẩu và tên cần thay đổi*/
    public boolean changePass(String acc, String mk) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_PASSWORD, mk);
        long kq = db.update(TABLE_ACCOUNT, cv, COLUMN_ACC + "=?", new String[]{acc});
        if (kq == 0)
            return false;
        else
            return true;
    }

    public boolean thucthi(String mySQL) {
        db.beginTransaction();// bat dau quan li transaction
        try {
            db.execSQL(mySQL);
            // commit your changes
            db.setTransactionSuccessful();
            return true;

        } catch (SQLException e1) {
            Log.i("abcd22", e1 + "");
            return false;
        } finally {
            db.endTransaction();

        }
    }
}
