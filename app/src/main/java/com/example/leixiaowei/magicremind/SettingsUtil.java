package com.example.leixiaowei.magicremind;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by leixiaowei on 17/5/20.
 */

public final class SettingsUtil {
    public static final String SP_NAME = "remind_magic_settings";
    public static final String AUTO_ADJUST_VOLUME = "setting_auto_adjust_volume";
    public static final String WITH_VIBRATE = "setting_vibrate";
    public static final String FIRST_INIT_DEFAULT_SETTINGS = "first_init_default_settings";
    private SettingsUtil() {
    }

    public static void initDefaultSetting(Context context) {
        updateSetting(context, WITH_VIBRATE, true);
        updateSetting(context, AUTO_ADJUST_VOLUME, true);
    }

    public static void initDefaultSettingsWhenFirstTimeLaunched(Context context) {
        boolean hasInitedDefaultSettings  = getSettingState(context, FIRST_INIT_DEFAULT_SETTINGS);
        if (!hasInitedDefaultSettings) {
            initDefaultSetting(context);
            updateSetting(context, FIRST_INIT_DEFAULT_SETTINGS, true);
        }
    }

    public static void updateSetting(Context context, String setting, boolean state) {
        SharedPreferences sp = context.getSharedPreferences(SP_NAME, MODE_PRIVATE);
        sp.edit().putBoolean(setting, state).apply();
    }

    public static boolean getSettingState(Context context, String setting) {
        SharedPreferences sp = context.getSharedPreferences(SP_NAME, MODE_PRIVATE);
        return sp.getBoolean(setting, false);
    }
}
