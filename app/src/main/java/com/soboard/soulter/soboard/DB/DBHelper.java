package com.soboard.soulter.soboard.DB;

import android.content.ClipData;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2018/7/10.
 */

public class DBHelper extends SQLiteOpenHelper{
    private static final String DATABASE_NAME = "clipData.db";
    private static final int DATABASE_VERSION = 1;

    public DBHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_CLIP_DATA_TABLE = "CREATE TABLE "+ DBContruct.ClipDataEntry.TABLE_NAME
                +" ("
                + DBContruct.ClipDataEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + DBContruct.ClipDataEntry.COLUMN_CLIP_DATA_STRING+ " TEXT NOT NULL, "
                + DBContruct.ClipDataEntry.COLUMN_TIMESTAMP+" TIME DEFAULT CURRENT_TIMESTAMP"
                +");";
        sqLiteDatabase.execSQL(SQL_CREATE_CLIP_DATA_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ DBContruct.ClipDataEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
