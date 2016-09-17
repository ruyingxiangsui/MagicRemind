package com.example.leixiaowei.magicremind;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class RemindLaunchReceiver extends BroadcastReceiver {

    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(Intent.ACTION_RUN);
        i.setClass(context, NotificationService.class);
        context.startService(i);
    }
}