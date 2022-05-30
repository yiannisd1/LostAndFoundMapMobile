package com.example.lostandfoundappfinal.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.lostandfoundappfinal.model.LostAndFound;
import com.example.lostandfoundappfinal.util.Util;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {


    public DatabaseHelper(@Nullable Context context) {
        super(context.getApplicationContext(), Util.DATABASE_NAME, null, Util.DATABASE_VERSION);

        context.deleteDatabase(Util.DATABASE_NAME);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String CREATE_LOST_FOUND_TABLE = "CREATE TABLE " + Util.TABLE_NAME + " ("
                + Util.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Util.TYPEOFADVERT + " TEXT, "
                + Util.NAME + " TEXT, " + Util.PHONE + " TEXT, "
                + Util.DESCRIPTION + " TEXT, " + Util.DATE + " TEXT, "
                + Util.LOCATION + " TEXT, " + Util.LATITUDE + " REAL, " + Util.LONGITUDE + " REAL );";
        sqLiteDatabase.execSQL(CREATE_LOST_FOUND_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        String DROP_USER_TABLE = "DROP TABLE IF EXISTS USERS";
        sqLiteDatabase.execSQL(DROP_USER_TABLE, new String[]{Util.TABLE_NAME});

        onCreate(sqLiteDatabase);

    }

    public boolean insertLostAndFound(LostAndFound lostAndFound) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Util.TYPEOFADVERT, lostAndFound.getTypeOfAdvert());
        contentValues.put(Util.NAME, lostAndFound.getName());
        contentValues.put(Util.PHONE, lostAndFound.getPhone());
        contentValues.put(Util.DESCRIPTION, lostAndFound.getDescription());
        contentValues.put(Util.DATE, lostAndFound.getDate());
        contentValues.put(Util.LOCATION, lostAndFound.getLocation());
        contentValues.put(Util.LATITUDE, lostAndFound.getLatitude());
        contentValues.put(Util.LONGITUDE, lostAndFound.getLongitude());

        long newRowId = db.insert(Util.TABLE_NAME, null, contentValues);
        db.close();

        if(newRowId==-1){
            return false;
        }else{
            return true;
        }
    }

    public LostAndFound fetchLostAndFound(Integer id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectAll = " SELECT * FROM " + Util.TABLE_NAME + " WHERE " + Util.ID + "='" + id + "'";
        Cursor cursor = db.rawQuery(selectAll, null);

        LostAndFound lostAndFound = null;
        try{
            if (cursor.moveToFirst()){
                do{
                    lostAndFound = new LostAndFound(
                            cursor.getInt(0),
                            cursor.getString(1),
                            cursor.getString(2),
                            cursor.getString(3),
                            cursor.getString(4),
                            cursor.getString(5),
                            cursor.getString(6),
                            cursor.getDouble(7),
                            cursor.getDouble(8)
                    );
                }while (cursor.moveToNext());
            }
        } catch (Exception e){ e.printStackTrace(); }
        finally { db.close(); }

        return lostAndFound;
    }

    public ArrayList<LostAndFound> fetchAllLostAndFound() {
        ArrayList<LostAndFound> lostAndFoundList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String selectAll = " SELECT * FROM " + Util.TABLE_NAME;
        Cursor cursor = db.rawQuery(selectAll, null);

        try{
            if (cursor.moveToFirst()){
                do{
                    lostAndFoundList.add(new LostAndFound(
                            cursor.getInt(0),
                            cursor.getString(1),
                            cursor.getString(2),
                            cursor.getString(3),
                            cursor.getString(4),
                            cursor.getString(5),
                            cursor.getString(6),
                            cursor.getDouble(7),
                            cursor.getDouble(8)
                    ));
                }while (cursor.moveToNext());
            }
        } catch (Exception e){ e.printStackTrace(); }
        finally { db.close(); }

        return lostAndFoundList;
    }

    public void deleteData (Integer id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL( " DELETE FROM " + Util.TABLE_NAME + " WHERE " + Util.ID + " = '" + id + "'");
    }

}
