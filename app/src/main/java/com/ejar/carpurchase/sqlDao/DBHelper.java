package com.ejar.carpurchase.sqlDao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.orhanobut.logger.Logger;

/**
 * Created by wxd1 on 2016/12/19.
 */

public class DBHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "gulu.db";
    public static final int DB_VERSION = 1;
    public static String TABLE_NAME = "task_wait";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TASK_ID = "task_id";
    public static final String COLUMN_IMGPATH = "task_img_path";
    public static String COLUMN_IMGNAME = "task_img_name";
    public static final String COLUMN_NOTE = "task_note";
    public static final String COLUMN_NUMBER = "number";//广告位编号
    public static final String COLUMN_POSITION = "task_position"; //地址 栋 单元  位置
    public static final String COLUMN_NAME = "name"; //小区名字


    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Logger.e("创建表--" + TABLE_NAME);
        String DATABASE_CREATE
                = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME
                + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_TASK_ID + " INTEGER UNIQUE, "
                + COLUMN_IMGPATH + " TEXT, "
                + COLUMN_IMGNAME + " TEXT, "
                + COLUMN_NUMBER + " TEXT, "
                + COLUMN_POSITION + " TEXT, "
                + COLUMN_NAME + " TEXT, "
                + COLUMN_NOTE + " TEXT);";
        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
