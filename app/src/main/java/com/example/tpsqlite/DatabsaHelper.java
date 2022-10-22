package com.example.tpsqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabsaHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME="pontInterert.db";
    public static final String TABLE_NAME="pointInteret";
    public static final String id="id";
    public static final String designation="designation";
    public static final String adresse="adresse";
    public static final String description="description";

    public DatabsaHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE "+TABLE_NAME+" ( "+id+" INTEGER PRIMARY KEY AUTOINCREMENT, "+designation+" TEXT, "+adresse+" TEXT, "+description+" TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
    public boolean insertData(String designation_, String adresse_,String description_)
    {
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(designation,designation_);
        contentValues.put(adresse,adresse_);
        contentValues.put(description,description_);
        Long result= sqLiteDatabase.insert(TABLE_NAME,null,contentValues);
        if(result==-1)
            return false;
        else
            return true;
    }

    public Cursor getALlData()
    {
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        Cursor res=sqLiteDatabase.rawQuery("SELECT * FROM "+TABLE_NAME,null);
        return res;
    }



    public boolean updateData(String id_,String designation_, String adresse_,String description_)
    {
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(id,id_);
        contentValues.put(designation,designation_);
        contentValues.put(adresse,adresse_);
        contentValues.put(description,description_);
        sqLiteDatabase.update(TABLE_NAME, contentValues,"id = ? ",new String[] { id_ });
        return true;
    }
    public Cursor getById(String id_)
    {
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        Cursor res=sqLiteDatabase.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE id = ?",
                new String[] {id_});
        return res;
    }
    public Integer deleteData(String id_)
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
       return sqLiteDatabase.delete(TABLE_NAME,"id = ?", new String[] { id_ });
    }
}
