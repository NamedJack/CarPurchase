package com.ejar.carpurchase.downLoadApk;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;

import com.ejar.carpurchase.utils.TU;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by wxd1 on 2017/3/29.
 */

public class TakePicture extends AppCompatActivity {

    public final int REQ_TAKE_PHOTO = 100;
    public final int REQ_ALBUM = 101;
    public final int REQ_ZOOM = 102;

    public final String imgPath = Environment.getExternalStorageDirectory().getAbsolutePath()
            + File.separator + "temp.jpg"; //拍照后原图
    public final String outputPath = Environment.getExternalStorageDirectory().getAbsolutePath()
            + File.separator + "temp_zoom.jpg";//拍照裁剪后的图
    public Uri outputUri = null;

    private void TakePhoto() {

        // 指定调用相机拍照后照片的储存路径
        File imgFile = new File(imgPath);
        Uri imgUri = null;
        if (Build.VERSION.SDK_INT >= 24) {
            //如果是7.0或以上，使用getUriForFile()获取文件的Uri
            imgUri = FileProvider.getUriForFile(this, "com.chaychan.demo" + ".fileprovider", imgFile);
        } else {
            imgUri = Uri.fromFile(imgFile);
        }
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imgUri);
        startActivityForResult(intent, REQ_TAKE_PHOTO);
    }

    private void SelectePhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                "image/*");
        startActivityForResult(intent, REQ_ALBUM);
    }

    /**
     * 发起剪裁图片的请求 兼容7.0
     *
     * @param activity    上下文
     * @param srcFile     原文件的File
     * @param output      输出文件的File
     * @param requestCode 请求码
     */
    public void startPhotoZoom(Activity activity, File srcFile, File output, int requestCode) {

        Intent intent = new Intent("com.android.camera.action.CROP");

        //主要修改这行代码,不再使用Uri.fromFile()方法获取文件的Uri
        intent.setDataAndType(getImageContentUri(activity, srcFile), "image/*");

        // crop为true是设置在开启的intent中设置显示的view可以剪裁
        intent.putExtra("crop", "true");

        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);

        // outputX,outputY 是剪裁图片的宽高
        intent.putExtra("outputX", 800);
        intent.putExtra("outputY", 480);

        intent.putExtra("return-data", false);// true:不返回uri，false：返回uri
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(output));
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());

        activity.startActivityForResult(intent, requestCode);
    }

    /**
     * 安卓7.0裁剪根据文件路径获取uri
     *
     * @param context
     * @param imageFile 原文件的File
     * @return 文件uri
     */
    public Uri getImageContentUri(Context context, File imageFile) {
        String filePath = imageFile.getAbsolutePath();
        Cursor cursor = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.Media._ID},
                MediaStore.Images.Media.DATA + "=? ",
                new String[]{filePath}, null);

        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor
                    .getColumnIndex(MediaStore.MediaColumns._ID));
            Uri baseUri = Uri.parse("content://media/external/images/media");
            return Uri.withAppendedPath(baseUri, "" + id);
        } else {
            if (imageFile.exists()) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, filePath);
                return context.getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            } else {
                return null;
            }
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case RESULT_OK://调用图片选择处理成功
                String zoomImgPath = "";
                Bitmap bm = null;
                File temFile = null;
                File srcFile = null;
                File outPutFile = null;
                switch (requestCode) {
                    case REQ_TAKE_PHOTO:// 拍照后在这里回调
                        srcFile = new File(imgPath);
                        outPutFile = new File(outputPath);
                        if (srcFile.exists()) srcFile.delete();
                        if (outPutFile.exists()) outPutFile.delete();

                        outputUri = Uri.fromFile(outPutFile);
                        startPhotoZoom(this, srcFile, outPutFile, REQ_ZOOM);// 发起裁剪请求
                        break;

                    case REQ_ALBUM:// 选择相册中的图片
                        if (data != null) {
                            Uri sourceUri = data.getData();
                            String[] proj = {MediaStore.Images.Media.DATA};

                            // 好像是android多媒体数据库的封装接口，具体的看Android文档
                            Cursor cursor = managedQuery(sourceUri, proj, null, null, null);

                            // 按我个人理解 这个是获得用户选择的图片的索引值
                            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                            // 将光标移至开头 ，这个很重要，不小心很容易引起越界
                            cursor.moveToFirst();
                            // 最后根据索引值获取图片路径
                            String imgPath = cursor.getString(column_index);

                            srcFile = new File(imgPath);
                            outPutFile = new File(outputPath);
                            if (srcFile.exists()) srcFile.delete();
                            if (outPutFile.exists()) outPutFile.delete();

                            outputUri = Uri.fromFile(outPutFile);
                            startPhotoZoom(this, srcFile, outPutFile, REQ_ZOOM);// 发起裁剪请求
                        }
                        break;


                    case REQ_ZOOM://裁剪后回调
                        if (data != null) {
                            if (outputUri != null) {
                                bm = decodeUriAsBitmap(this, outputUri);

                                String scaleImgPath = saveBitmapByQuality(bm, 80);//复制并压缩到自己的目录并压缩

                                //bm可以用于显示在对应的ImageView中，scaleImgPath是剪裁并压缩后的图片的路径，可以用于上传操作

                                //实现自己的业务逻辑

                            }
                        } else {
                            TU.cT("选择图片发生错误，图片可能已经移位或删除");
                        }
                        break;
                }
        }
    }


    public Bitmap decodeUriAsBitmap(Context context, Uri uri) {
        Bitmap bitmap = null;
        try {
            // 先通过getContentResolver方法获得一个ContentResolver实例，
            // 调用openInputStream(Uri)方法获得uri关联的数据流stream
            // 把上一步获得的数据流解析成为bitmap
            bitmap = BitmapFactory.decodeStream(context.getContentResolver().openInputStream(uri));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return bitmap;
    }


    /**
     * 按质量压缩bm
     * 是对图片进行压缩，第一个参数传入的是图片的Bitmap对象
     * ，第二个参数是压缩的保留率，比如使用的是80
     * ，即压缩后为原来的80%，则是对其压缩了20%
     *
     * @param bm
     * @param quality 压缩保存率
     * @return
     */
    public String saveBitmapByQuality(Bitmap bm, int quality) {
        String croppath = "";
        try {
            File f = new File(outputPath);
            //得到相机图片存到本地的图片
            croppath = f.getPath();
            if (f.exists()) {
                f.delete();
            }
            FileOutputStream out = new FileOutputStream(f);
            bm.compress(Bitmap.CompressFormat.JPEG, quality, out);
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return croppath;
    }
}
