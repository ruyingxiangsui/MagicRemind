package com.example.leixiaowei.magicremind;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String READ_PAGE_URL = "https://ruyingxiangsui.github.io/magic_remind.htm";

    private LinearLayout settingLayout;
    private Button settingBtn;
    private TextView contentTitleTv;
    private ListView remindListView;
    private EditText editText;
    private Button addBtn;

    private List<RemindItem> remindItems = new ArrayList<>();
    private RemindAdapter remindAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        settingLayout = (LinearLayout) findViewById(R.id.setting_layout);
        settingBtn = (Button) findViewById(R.id.set_remind);
        settingBtn.setOnClickListener(v -> toSystemSettingPage());

        Object object = RemindFileUtil.readObject(this);
        if (object instanceof RemindTable) {
            RemindTable remindTable = (RemindTable) object;
            remindItems = remindTable.getRemindItems();
        }


        contentTitleTv = (TextView) findViewById(R.id.content_title);
        setContentTitle();

        remindAdapter = new RemindAdapter(this, remindItems);
        remindListView = (ListView) findViewById(R.id.list_item);
        remindListView.setAdapter(remindAdapter);

        editText = (EditText) findViewById(R.id.edit_add);
        addBtn = (Button) findViewById(R.id.add_btn);
        addBtn.setOnClickListener(v -> {
            String str = editText.getText().toString();
            if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str.trim())) {
                RemindItem item = new RemindItem();
                item.remindText = str;
                remindItems.add(item);
                remindAdapter.notifyDataSetChanged();
                editText.setText("");
                setContentTitle();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.system_setting:
                toSystemSettingPage();
                break;
            case R.id.app_setting:
                toAppSettingPage();
                break;
            case R.id.read_me:
                toReadMePage();
                break;
            default:
                break;
        }
        return true;
    }

    private void toSystemSettingPage() {
        Intent intent = new Intent(android.provider.Settings.ACTION_ACCESSIBILITY_SETTINGS);
        startActivity(intent);
    }

    private void toAppSettingPage() {
        startActivity(new Intent(this, AppSettingActivity.class));
    }

    private void toReadMePage() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(READ_PAGE_URL));
        startActivity(intent);
    }

    private void setContentTitle() {
        if (notEmptyContent()) {
            contentTitleTv.setText(R.string.title_content_not_empty);
        } else {
            contentTitleTv.setText(R.string.title_content_empty);
        }
    }

    private boolean notEmptyContent() {
        return remindItems != null && remindItems.size() > 0;
    }

    @Override
    protected void onResume() {
        super.onResume();
        settingLayout.setVisibility(isServiceRunning() ? View.GONE : View.VISIBLE);
    }

    public boolean isServiceRunning() {
        boolean isWork = false;
        ActivityManager myAM = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> runningServices = myAM.getRunningServices(40);
        if (runningServices.size() <= 0) {
            return false;
        }
        for (int i = 0; i < runningServices.size(); i++) {
            String name = runningServices.get(i).service.getClassName();
            if (name.equals(NotificationService.SERVICE)) {
                isWork = true;
                break;
            }
        }
        return isWork;
    }
}
