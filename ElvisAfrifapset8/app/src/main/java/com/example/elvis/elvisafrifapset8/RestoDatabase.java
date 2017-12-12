package com.example.elvis.elvisafrifapset8;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ELV on 04-Dec-17.
 */

public class RestoDatabase extends SQLiteOpenHelper {

    private static RestoDatabase instance;

//    SQLiteDatabase db = this.getReadableDatabase();

    private RestoDatabase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table orders(_id INTEGER PRIMARY KEY, dish TEXT, price REAL, amount INTEGER);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE orders");
    }

    public static RestoDatabase getInstance(Context context) {
        if (instance != null) {
            return instance;
        } else {
            instance = new RestoDatabase(context, "orders", null, 1);
            return instance;
        }
    }

    public Cursor selectAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM orders", null);
        return cursor;
    }

    public void addItem(String dish, double price, long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        // get the amount
        if ( db.rawQuery("SELECT amount FROM orders WHERE _id = "+ id, null).moveToFirst()) {
            db.execSQL("UPDATE orders SET amount = amount + 1 WHERE _id =" + id);
        } else {
            cv.put("dish", dish);
            cv.put("_id", id);
            cv.put("price", price);
            cv.put("amount", 1);

            db.insert("orders", "null", cv);
        }

    }

    public void clear() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS" + " orders");
        onCreate(db);
    }

}