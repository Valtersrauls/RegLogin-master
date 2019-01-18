package com.example.daili.reglogin;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.strictmode.SqliteObjectLeakedViolation;
import android.util.Log;
import android.widget.Toast;

public class establishDatabase extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";
    private static final String DATABASE_NAME = "login.db";
    private static final String TABLE_NAME = "registered_table";
    private static final String col2 = "Email";
    private static final String col3 = "Username";
    private static final String col4 = "Password";
    private static final String col5 = "DanceGroupName";
    private static final String col6 = "DanceGroupCategory";
    private static final String col7 = "DanceGroupTeacher";
    private static String UserSelected = "";
    private SQLiteDatabase db = this.getReadableDatabase();

    establishDatabase(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    //izveido datubāzes tabulu registered_table ar kolonām
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + col2 + " TEXT," + col3 + " TEXT," + col4 + " TEXT," + col5 + " TEXT ," + col6 + " CHAR," + col7 + " INTEGER)";
        db.execSQL(createTable);
    }

    //ja tabula jau eksistē, to izdzēš
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    //datu pievienošana tabulā no klases RegisterActivity
    boolean addData(String email, String username, String password, String DanceGroupName, String DanceGroupCategory, int DanceGroupTeacher) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(col2, email);
        contentValues.put(col3, username);
        contentValues.put(col4, password);
        contentValues.put(col5, DanceGroupName);
        contentValues.put(col6, DanceGroupCategory);
        contentValues.put(col7, DanceGroupTeacher);

        //log paziņojums
        long result = db.insert(TABLE_NAME, null, contentValues);
        Log.d(TAG, "addData:Adding" + col3 + "to" + TABLE_NAME);
        if (result == -1) return false;
        else return true;

    }

    //pārbauda vai reģistrācijā ievadītais epasts jau pastāv
    Boolean checkEmail(String email) {
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE Email=?", new String[]{email});
        if (cursor.getCount() > 0) {
            cursor.close();
            return false;
        } else {
            cursor.close();
            return true;
        }

    }

    //izsauc RegisterActivity, pārbauda vai reģistrācijā ievadītais lietotājvārds jau pastāv
    Boolean checkUsername(String username) {
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE Username=?", new String[]{username});
        if (cursor.getCount() > 0) {
            cursor.close();
            return false;

        } else {
            cursor.close();
            return true;
        }
    }

//    public Cursor getUsername(String email) {
//        Cursor cursor = db.rawQuery("SELECT Username FROM " + TABLE_NAME + " WHERE Email=?", null);
//            return cursor;
//    }

    //izsauktā metode no LoginActivity, vai login dati atbilst datiem no datubāzes
    Boolean checkData(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE Email=? AND Password=?", new String[]{email, password});
        if (cursor.getCount() > 0) {
            cursor.close();
            return true;
        } else {
            cursor.close();
            return false;
        }
    }
}
