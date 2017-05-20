package com.example.leixiaowei.magicremind;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CheckBox;

import static com.example.leixiaowei.magicremind.SettingsUtil.AUTO_ADJUST_VOLUME;
import static com.example.leixiaowei.magicremind.SettingsUtil.WITH_VIBRATE;
import static com.example.leixiaowei.magicremind.SettingsUtil.getSettingState;
import static com.example.leixiaowei.magicremind.SettingsUtil.updateSetting;

public class AppSettingActivity extends AppCompatActivity {

    CheckBox autoSetVolume;
    CheckBox withVibrate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_setting);

        autoSetVolume = (CheckBox) findViewById(R.id.auto_adjust_volume);
        autoSetVolume.setChecked(getSettingState(this, AUTO_ADJUST_VOLUME));
        autoSetVolume.setOnCheckedChangeListener((buttonView, isChecked) -> updateSetting(this, AUTO_ADJUST_VOLUME, isChecked));

        withVibrate = (CheckBox) findViewById(R.id.with_vibrate);
        withVibrate.setChecked(getSettingState(this, WITH_VIBRATE));
        withVibrate.setOnCheckedChangeListener((buttonView, isChecked) -> updateSetting(this, WITH_VIBRATE, isChecked));

    }

}
