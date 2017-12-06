package com.ejar.carpurchase.utils;

import android.graphics.Bitmap;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by wxd1 on 2017/1/9.
 */

public class FileUtil {

    public static boolean saveMyBitmap(Bitmap bmp, String bitName) throws IOException {
        File dirFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "Wongxd");
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }
        File f = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "Wongxd" + File.separator + bitName + ".png");
        boolean flag = false;
        f.createNewFile();
        FileOutputStream fOut = null;
        try {
            fOut = new FileOutputStream(f);
            bmp.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            flag = true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            fOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return flag;
    }


    public static void MakeFileDir(File fileDir) {
        if (fileDir.isFile()) {
            return;
        }
        if (fileDir.getParentFile().exists()) {
            if (fileDir.exists()) {
                return;
            } else {
                fileDir.mkdir();
            }
        }
    }


    public static void delete(File file) {
//        if (!file.exists()) return;
        if (file.isFile()) {
            file.delete();
            return;
        }

        if (file.isDirectory()) {
            File[] childFiles = file.listFiles();
            if (childFiles == null || childFiles.length == 0) {
                file.delete();
                return;
            }

            for (int i = 0; i < childFiles.length; i++) {
                delete(childFiles[i]);
            }
            file.delete();
        }
    }


    public static boolean copyFile(String oldPath, String newPath) {
        try {
            int bytesum = 0;
            int byteread = 0;
            File oldfile = new File(oldPath);
            if (oldfile.exists()) { //文件存在时
                InputStream inStream = new FileInputStream(oldPath); //读入原文件
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1024 * 2];
                int length;
                while ((byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread; //字节数 文件大小
//                    System.out.println(bytesum);
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();

            }
        } catch (Exception e) {
            System.out.println("复制单个文件操作出错");
            e.printStackTrace();

        }
        return new File(newPath).exists();
    }


    public static boolean saveBitmap2file(Bitmap bmp, String filename) {
        Bitmap.CompressFormat format = Bitmap.CompressFormat.JPEG;
        int quality = 100;
        OutputStream stream = null;
        try {
            stream = new FileOutputStream(Environment.getDataDirectory() + File.separator + filename);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return bmp.compress(format, quality, stream);
    }


    /**
     * @param fName 文件全路径
     */
    public static String getFileName(String fName) {
//		举例：
//        String fName = " G:\\Java_Source\\navigation_tigra_menu\\demo1\\img\\lev1_arrow.gif ";

//		方法一：
//
//        File tempFile = new File(fName.trim());
//
//        String fileName = tempFile.getName();
//
//        System.out.println("fileName = " + fileName);

//		方法二：

        fName = fName.trim();

        String fileName = fName.substring(fName.lastIndexOf("/") + 1);
        //或者
//        String fileName = fName.substring(fName.lastIndexOf("\\") + 1);

//        System.out.println("fileName = " + fileName);

//		方法三：

//        String fName = fName.trim();
//
//        String temp[] = fName.split("\\\\"); /**split里面必须是正则表达式，"\\"的作用是对字符串转义*/
//
//        String fileName = temp[temp.length - 1];
//
//        System.out.println("fileName = " + fileName);


        return fileName;

    }

}
