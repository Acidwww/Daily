package com.example.daily.Others;


import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;


import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.example.daily.MyActivity.MainActivity;
import com.example.daily.R;

import java.util.Calendar;

public class MyService extends Service {
    private static final int NOTIFICATION_FLAG = 1;
    private NotificationManager manager;//通知
    private Context context=MyApplication.getInstance();
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    String channelId = "chat";
                    String channelName = "聊天消息";
                    int importance = NotificationManager.IMPORTANCE_HIGH;
                    createNotificationChannel(channelId, channelName, importance);
                }
                    Intent resultIntent = new Intent(context, MainActivity.class);
                    PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, resultIntent, 0);
                    Notification notification = new NotificationCompat.Builder(context, "chat")
                            .setAutoCancel(true)
                            .setTicker("计划提醒")
                            .setContentTitle("八点了！！！")
                            .setContentText("今天也要记得制定计划哦！")
                            .setWhen(System.currentTimeMillis())
                            .setSmallIcon(R.drawable.task)
                            .setContentIntent(pendingIntent)
                            .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),R.drawable.clock_selected))
                            .build();
                    manager.notify(1, notification);
                    // level16及之后增加的，API11可以使用getNotificatin()来替代
                    notification.flags |= Notification.FLAG_AUTO_CANCEL; // FLAG_AUTO_CANCEL表明当通知被用户点击时，通知将被清除。
                    notification.defaults |= Notification.DEFAULT_VIBRATE;//使用默认的震动
                    // 在Android进行通知处理，首先需要重系统哪里获得通知管理器NotificationManager，它是一个系统Service。
                    manager.notify(NOTIFICATION_FLAG, notification);// 步骤4：通过通知管理器来发起通知。如果id不同，则每click，在status哪里增加一个提示
                    //关闭广播  这里最开始用的是取消注册好的广播，但是注销以后无法再次启动
//            context.getPackageManager().setComponentEnabledSetting( new ComponentName("广播的包名", AutoReceiver.class.getName()),
//                    PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
//                    PackageManager.DONT_KILL_APP);
//            PendingIntent pendIntent1 = PendingIntent.getBroadcast(context, 0,
//                    intent, 0);
//            // 与上面的intent匹配（filterEquals(intent)）的闹钟会被取消
//            // 进行闹铃取消
//            AlarmManager manager1 = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//            manager1.cancel(pendIntent1);


            }

            //通知消息
            @TargetApi(Build.VERSION_CODES.O)
            private void createNotificationChannel(String channelId, String channelName, int importance) {
                NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
                NotificationManager notificationManager = (NotificationManager)context. getSystemService(
                        Context.NOTIFICATION_SERVICE);
                notificationManager.createNotificationChannel(channel);
            }

        }).start();

        AlarmManager alarmMgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 7);
        calendar.set(Calendar.MINUTE, 30);

        Intent Intent = new Intent(this,MyService.class);

        PendingIntent alarmIntent = PendingIntent.getService(this, 0, Intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmMgr.cancel(alarmIntent);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {// 6.0
            alarmMgr.setInexactRepeating(AlarmManager.RTC, calendar.getTimeInMillis(),
                            AlarmManager.INTERVAL_DAY, alarmIntent);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//  4.4
            alarmMgr.setInexactRepeating(AlarmManager.RTC, calendar.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY, alarmIntent);
        } else {
            alarmMgr.setInexactRepeating(AlarmManager.RTC, calendar.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY, alarmIntent);
        }


        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
