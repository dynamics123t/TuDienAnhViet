package com.luantan.tratudienanhviet.Models;


import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.Constraints;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.luantan.tratudienanhviet.DBHelper;
import com.luantan.tratudienanhviet.DetailWordActivity;
import com.luantan.tratudienanhviet.MainActivity;
import com.luantan.tratudienanhviet.R;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class EventHandler<Public> extends Worker {
    SharedPreferences sharedPreferences;
    MainActivity activity;
    public EventHandler(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        Log.e("TAG", "start work");
        createNofication();
        return Result.success();
    }

    public void createNofication() {
        sendNotification();
    }

    //create work request
    public static void oneOffRequest() {
        OneTimeWorkRequest oneTimeWorkRequest = new OneTimeWorkRequest.Builder(EventHandler.class)
                .setInitialDelay(10, TimeUnit.SECONDS)
                .setConstraints(setConstraints())
                .build();
        WorkManager.getInstance().enqueue(oneTimeWorkRequest);
    }

    public static Constraints setConstraints() {
        Constraints constraints = new Constraints.Builder()
//                .setRequiresCharging(true)
//                .setRequiredNetworkType(NetworkType.UNMETERED)
                .build();
        return constraints;
    }

    public static void periodicWorkRequest() {
        PeriodicWorkRequest periodicWorkRequest = new PeriodicWorkRequest.Builder(EventHandler.class, 3, TimeUnit.HOURS)
                .setInitialDelay(10, TimeUnit.SECONDS)
                .setConstraints(setConstraints())
                .addTag("job")
                .build();
        WorkManager.getInstance().enqueue(periodicWorkRequest);
    }
    public static void CancelWork()
    {
        Log.e("TAG", "stop work");
        WorkManager.getInstance().cancelAllWorkByTag("job");
        //WorkManager.getInstance().cancelWorkById();
    }

    private void sendNotification() {
        DBHelper dbHelper = new DBHelper(getApplicationContext());
        Utils.lstTuVung = dbHelper.getALLWord();
        Utils.lstLichSu = dbHelper.getALLWordHistory();
        Utils.lstIrregular = dbHelper.getIrregulars();
        Random rd = new Random();
        int idx = rd.nextInt(120000) + 1;
        Word word = Utils.lstTuVung.get(idx);

        String strTitle = word.getTenTu();
        String strText = replaceString(word.getNghia());
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Intent intent = new Intent(getApplicationContext(), DetailWordActivity.class);
        intent.putExtra("Word", word);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(),0,intent,0);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), getIdNotification(), intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new NotificationCompat.Builder(getApplicationContext(), MyApp.CHANNEL_ID)
                .setContentTitle(strTitle)
                .setContentText(strText)
                .setSmallIcon(R.drawable.star)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(strText))
                .setColor(getApplicationContext().getResources().getColor(R.color.blue))
                .setSound(uri)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .build();

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());
        notificationManagerCompat.notify(getIdNotification(), notification);
    }

    private int getIdNotification() {
        return (int) new Date().getTime();
    }
    public String replaceString(String str)
    {
        String strPr = str.replace("@", "Cách viết: ")
                .replace("*  thán từ", "\n\nThán từ")
                .replace("*  nội động từ", "\n\nNội động từ")
                .replace("*  tính từ", "\n\nTính từ")
                .replace("* tính từ", "\n\nTính từ")
                .replace("*  danh từ", "\n\nDanh từ")
                .replace("* danh từ", "\n\nDanh từ")
                .replace("*  ngoại động từ", "\n\nNgoại động từ")
                .replaceAll("- ", "\n\t- ");
        return strPr;
    }
}
