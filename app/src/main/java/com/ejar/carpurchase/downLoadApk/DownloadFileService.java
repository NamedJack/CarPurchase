package com.ejar.carpurchase.downLoadApk;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.widget.RemoteViews;

import com.ejar.carpurchase.R;
import com.ejar.carpurchase.utils.TU;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static android.app.PendingIntent.getActivity;

public class DownloadFileService extends IntentService {
    private static final String TAG = DownloadFileService.class.getSimpleName();

    public DownloadFileService() {
        super("com.wongxd.download");
    }

    private NotificationManager notificationManager;
    private RemoteViews rViews;
    private NotificationCompat.Builder builder;

    protected void onHandleIntent(Intent intent) {
        Bundle bundle = intent.getExtras();
        // 获得下载文件的url
        String downloadUrl = bundle.getString("url");
        // 设置文件下载后的保存路径，保存在SD卡根目录的Download文件夹
        File dirs = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/E_ShopUnit");
        // 检查文件夹是否存在，不存在则创建
        if (!dirs.exists()) {
            dirs.mkdir();
        }
        File file = new File(dirs, "update.apk");
        // 设置Notification
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        notification = new Notification(R.drawable.ic_launcher, getString(R.string.app_name) + " 版本更新下载", System.currentTimeMillis());
//        Intent intentNotifi = new Intent(this, MainActivity.class);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intentNotifi, 0);
//        notification.contentIntent = pendingIntent;
//        // 加载Notification的布局文件
//        rViews = new RemoteViews(getPackageName(), R.layout.downloadfile_layout);
//        // 设置下载进度条
//        rViews.setProgressBar(R.id.downloadFile_pb, 100, 0, false);
//        notification.contentView =Views;

        builder = new NotificationCompat.Builder(this);
        builder.setContentTitle(getString(R.string.app_name))
                .setContentText("新版本下载中")
                .setProgress(100, 0, false)
                .setSmallIcon(R.drawable.icon);
        notificationManager.notify(0, builder.build());
        // 开始下载
        downloadFile(downloadUrl, file);
        // 移除通知栏
        notificationManager.cancel(0);

        showNotification(getApplicationContext(), file, true, R.drawable.icon);

//        // 广播出去，由广播接收器来处理下载完成的文件
//        Intent sendIntent = new Intent("com.test.downloadComplete");
//        // 把下载好的文件的保存地址加进Intent
//        sendIntent.putExtra("downloadFile", file.getPath());
//        sendBroadcast(sendIntent);
    }

    private int fileLength, downloadLength;

    private void downloadFile(String downloadUrl, File file) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            Log.e(TAG, "找不到保存下载文件的目录");
            e.printStackTrace();
        }
        InputStream ips = null;
        try {
            URL url = new URL(downloadUrl);
            HttpURLConnection huc = (HttpURLConnection) url.openConnection();
            huc.setRequestMethod("GET");
            huc.setReadTimeout(10000);
            huc.setConnectTimeout(3000);
            fileLength = Integer.valueOf(huc.getHeaderField("Content-Length"));
            ips = huc.getInputStream();
            // 拿到服务器返回的响应码
            int hand = huc.getResponseCode();
            if (hand == 200) {
                // 开始检查下载进度
                handler.post(run);
                // 建立一个byte数组作为缓冲区，等下把读取到的数据储存在这个数组
                byte[] buffer = new byte[1024 * 20];
                int len = 0;
                while ((len = ips.read(buffer)) != -1) {
                    fos.write(buffer, 0, len);
                    downloadLength = downloadLength + len;
                }
            } else {
                Log.e(TAG, "服务器返回码" + hand);
            }

        } catch (Exception e) {
            e.printStackTrace();
            TU.cT("下载地址有误\n" + e.getMessage());
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
                if (ips != null) {
                    ips.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public void onDestroy() {
        // 移除定時器
        handler.removeCallbacks(run);
        super.onDestroy();
    }

    // 定时器，每隔一段时间检查下载进度，然后更新Notification上的ProgressBar
    private Handler handler = new Handler();
    private Runnable run = new Runnable() {
        public void run() {
//            rViews.setProgressBar(R.id.downloadFile_pb, 100, downloadLength * 100 / fileLength, false);
//            notificationontentView = rViews;

            builder.setProgress(100, downloadLength * 100 / fileLength, false);
            notificationManager.notify(0, builder.build());
            handler.postDelayed(run, 1000);
        }
    };


    /**
     * @param context
     * @param file
     * @param isCanClear 通知是否可以清除
     * @param iconRes    通知小图标的res
     */
    private static void showNotification(Context context, File file, boolean isCanClear, int iconRes) {

        // 创建一个开启安装App界面的意图
        Intent installIntent = new Intent();
        installIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        installIntent.setAction(Intent.ACTION_VIEW);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //判读断版本是否在7.0以上
            Uri apkUri =
                    FileProvider.getUriForFile(context,
                            "com.ejar.carpurchase" + ".fileprovider",
                            file);
            //添加这一句表示对目标应用临时授权该Uri所代表的文件
            installIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            installIntent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        } else {
            installIntent.setDataAndType(Uri.fromFile(file),
                    "application/vnd.android.package-archive");
        }


        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        // 创建一个Notification并设置相关属性
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setAutoCancel(false)//通知设置不会自动显示
                .setShowWhen(true)//显示时间
                .setSmallIcon(iconRes)//设置通知的小图标
                .setContentTitle(context.getString(R.string.app_name))
                .setContentText(context.getString(R.string.app_name) + "  下载完成,点击安装");//设置通知的内容

        //创建PendingIntent，用于点击通知栏后实现的意图操作
        PendingIntent pendingIntent = getActivity(context, 0, installIntent, PendingIntent.FLAG_UPDATE_CURRENT);


//        if (Build.VERSION.SDK_INT >= 21) {
//            //5.0以上可用浮动通知
//            builder.setFullScreenIntent(pendingIntent, false);
//        }
        builder.setContentIntent(pendingIntent);

        Notification notification = builder.build();
        notification.defaults = Notification.DEFAULT_SOUND;// 设置为默认的声音
        notification.flags = isCanClear ? Notification.FLAG_ONLY_ALERT_ONCE : Notification.FLAG_ONLY_ALERT_ONCE | Notification.FLAG_NO_CLEAR;
        manager.notify(1, notification);// 显示通知

    }

}