package com.example.leixiaowei.magicremind;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

public class NotificationService extends AccessibilityService {

    public static final String SERVICE = "com.example.leixiaowei.magicremind.NotificationService";

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        int eventType = event.getEventType();
        switch (eventType) {
            case AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED:
                handleNotification(event);
                break;
            default:
                break;

        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        flags = START_STICKY;
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onInterrupt() {

    }

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        AccessibilityServiceInfo serviceInfo = getCustomServiceInfo();
        setServiceInfo(serviceInfo);
    }


    // 处理通知栏信息
    private void handleNotification(AccessibilityEvent event) {
        Log.e("handleNotification", "start");
        Log.e("event.getText:", (event.getText() == null) ? "null" : "" + event.getText().size());
        for (CharSequence charSequence : event.getText()) {
            Log.e("text: " , charSequence.toString());
        }
        Observable.just(event.getText())
                .filter(list -> !isEmptyList(list))
                .map(this::checkContainsRemindText)
                .filter(aBoolean -> aBoolean)
                .subscribe(action -> playAlarm(), throwable -> {
                    Log.e("handleNotification", "error");
                });
    }

    private void playAlarm() {
        Ringtone ringtone = RingtoneManager.getRingtone(this, getSystemDefaultRingtoneUri());
        ringtone.play();
    }

    @Override
    public void onDestroy() {

        super.onDestroy();
    }

    //获取系统默认铃声的Uri
    private Uri getSystemDefaultRingtoneUri() {
        return RingtoneManager.getActualDefaultRingtoneUri(this,
                RingtoneManager.TYPE_RINGTONE);
    }


    // 是否包含设置的提示内容
    private boolean checkContainsRemindText(@NonNull List<CharSequence> checkTextList) {
        List<RemindItem> settingRemindList = getRemindTextList();
        for (CharSequence checkText : checkTextList) {
            for (RemindItem remindItem : settingRemindList) {
                if (checkText != null && remindItem != null && checkText.toString().contains(remindItem.remindText)) {
                    return true;
                }
            }
        }
        return false;

    }

    private List<RemindItem> getRemindTextList() {
        List<RemindItem> list = new ArrayList<>();
        Object data = RemindFileUtil.readObject(this);
        if (data instanceof RemindTable) {
            list = ((RemindTable) data).getRemindItems();
        }
        return list;
    }

    private boolean isEmptyList(List<?> list) {
        return list == null || list.size() == 0;
    }

    // 获取
    private AccessibilityServiceInfo getCustomServiceInfo() {
        AccessibilityServiceInfo serviceInfo = new AccessibilityServiceInfo();

        serviceInfo.eventTypes = AccessibilityEvent.TYPES_ALL_MASK;
        serviceInfo.feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC;
        serviceInfo.packageNames = getPackageNameArray();
        serviceInfo.notificationTimeout = 100;

        return serviceInfo;
    }

    // 获取包名信息
    public String[] getPackageNameArray() {
        List<PackageInfo> packages = getPackageManager().getInstalledPackages(0);
        return Observable.from(packages)
                .map(item -> item.packageName)
                .toList()
                .map(list -> list.toArray(new String[list.size()]))
                .toBlocking()
                .first();

    }
}
