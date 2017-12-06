package com.ejar.carpurchase.sqlDao;//package com.wongxd.partymanage.sqlDao;
//
//import android.content.ContentValues;
//import android.content.Context;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Created by wxd1 on 2016/12/19.
// */
//
//public class guLuDB {
//    private SQLiteDatabase db;
//    private static guLuDB guLuDBInstance;
//
//    public guLuDB(Context context) {
//
//        DBHelper dbHelper = new DBHelper(context);
//        db = dbHelper.getWritableDatabase();
//        dbHelper.onCreate(db);
//    }
//
//
//    public static synchronized guLuDB getInstance(Context context) {
//
//        if (guLuDBInstance == null) {
//            guLuDBInstance = new guLuDB(context);
//        }
//
//        return guLuDBInstance;
//    }
//
//
//    /**
//     * -1 失败
//     *
//     * @param uploadBean
//     * @return
//     */
//    public long addTask(BatchUploadBean uploadBean) {
//        if (uploadBean == null) return -1;
//        ContentValues values = new ContentValues();
//        values.put(DBHelper.COLUMN_TASK_ID, uploadBean.getId());
//        values.put(DBHelper.COLUMN_IMGPATH, uploadBean.getImgPath());
//        values.put(DBHelper.COLUMN_IMGNAME, uploadBean.getImgName());
//        values.put(DBHelper.COLUMN_NOTE, uploadBean.getNote());
//        values.put(DBHelper.COLUMN_NAME, uploadBean.getName());
//        values.put(DBHelper.COLUMN_NUMBER, uploadBean.getNumber());
//        values.put(DBHelper.COLUMN_POSITION, uploadBean.getPosition());
//        long newId = db.insert(DBHelper.TABLE_NAME, null, values);
//        return newId;
//    }
//
//
//    /**
//     * 0 失败
//     *
//     * @param uploadBean
//     * @return
//     */
//    public int deleteTask(BatchUploadBean uploadBean) {
//        if (uploadBean == null) return 0;
//        int info = db.delete(DBHelper.TABLE_NAME, DBHelper.COLUMN_TASK_ID + "=?", new String[]{uploadBean.getId() + ""});
//        return info;
//    }
//
//    /**
//     * 0 失败
//     *
//     * @param taskId
//     * @return
//     */
//    public int deleteTaskById(int taskId) {
//        int info = db.delete(DBHelper.TABLE_NAME, DBHelper.COLUMN_TASK_ID + "=?", new String[]{taskId + ""});
//        return info;
//
//    }
//
//    public void closeDB() {
//        if (guLuDBInstance != null) {
//            db.close();
//            guLuDBInstance = null;
//        }
//    }
//
//
//    public List<BatchUploadBean> LoadAllTask() {
//        List<BatchUploadBean> uploadBeens = new ArrayList<>();
//        Cursor c = db.query(DBHelper.TABLE_NAME, null, null, null, null, null, null, null);
//        int idIndex = c.getColumnIndex(DBHelper.COLUMN_TASK_ID);
//        int imagePathIndex = c.getColumnIndex(DBHelper.COLUMN_IMGPATH);
//        int imageNameIndex = c.getColumnIndex(DBHelper.COLUMN_IMGNAME);
//        int noteIndex = c.getColumnIndex(DBHelper.COLUMN_NOTE);
//        int nameIndex = c.getColumnIndex(DBHelper.COLUMN_NAME);
//        int numberIndex = c.getColumnIndex(DBHelper.COLUMN_NUMBER);
//        int positionIndex = c.getColumnIndex(DBHelper.COLUMN_POSITION);
//        if (c.moveToFirst()) {
//            do {
//                int id = c.getInt(idIndex);
//                String imagePath = c.getString(imagePathIndex);
//                String imageName = c.getString(imageNameIndex);
//                String note = c.getString(noteIndex);
//                String name = c.getString(nameIndex);
//                String number = c.getString(numberIndex);
//                String position = c.getString(positionIndex);
//                BatchUploadBean batchUploadBean = new BatchUploadBean(id, imagePath, imageName, note, name, number, position);
//                uploadBeens.add(batchUploadBean);
//            } while (c.moveToNext());
//
//        }
//        c.close();
//        return uploadBeens;
//    }
//
//
//    public void deleteAll() {
//        db.execSQL("delete from " + DBHelper.TABLE_NAME);
//    }
//}
