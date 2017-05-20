package com.example.leixiaowei.magicremind;

import android.app.Application;

/**
 * Created by leixiaowei on 17/5/20.
 */

public class MagicRemindApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SettingsUtil.initDefaultSettingsWhenFirstTimeLaunched(this);
    }
}
