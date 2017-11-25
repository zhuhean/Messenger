package com.zhuhean.messenger.ui;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Telephony;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.MenuItem;

import com.zhuhean.messenger.App;
import com.zhuhean.messenger.R;
import com.zhuhean.messenger.component.Remember;
import com.zhuhean.messenger.component.permission.EasyPermission;
import com.zhuhean.messenger.component.permission.PermissionCallback;
import com.zhuhean.messenger.helper.Helper;
import com.zhuhean.messenger.model.TextMessage;
import com.zhuhean.messenger.model.TextMessageDao;
import com.zhuhean.messenger.ui.base.BaseActivity;
import com.zhuhean.messenger.ui.widget.Toasty;

public class MainActivity extends BaseActivity implements PermissionCallback {

    private static final String HAS_READ_SMS = "HAS_READ_SMS";
    private static final Uri SMS_URI = Uri.parse("content://sms");

    @Override
    public int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        askSmsPermissions();
    }

    private void askSmsPermissions() {
        EasyPermission.askFor(this, this,
                Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS);
    }

    private void showAskDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
        dialog.setTitle("设为默认短信应用？");
        dialog.setMessage("把" + getString(R.string.app_name) + "设为你的默认短信应用好不好哇？");
        dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startIntent();
            }
        });
        dialog.setNegativeButton("不行", null);
        dialog.show();
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void startIntent() {
        Intent intent = new Intent(Telephony.Sms.Intents.ACTION_CHANGE_DEFAULT);
        intent.putExtra(Telephony.Sms.Intents.EXTRA_PACKAGE_NAME, getPackageName());
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void permissionGranted() {
        if (Remember.getBoolean(HAS_READ_SMS, false)) {
            showMain();
        } else {
            readSms();
        }
        if (!Helper.isDefaultSmsApp(this)) {
            postDelayed(new Runnable() {
                @Override
                public void run() {
                    showAskDialog();
                }
            }, 1000);
        }
    }

    private void showMain() {
        postDelayed(new Runnable() {
            @Override
            public void run() {
                hideActionBarShadow();
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.container, new MainFragment())
                        .commit();
            }
        }, 300);
    }

    private void readSms() {
        int count = 50;
        TextMessageDao dao = App.daoSession().getTextMessageDao();
        ContentResolver resolver = getContentResolver();
        Cursor cursor = resolver.query(SMS_URI, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                if (count-- == 0) break;
                TextMessage message = new TextMessage();
                message.setContent(cursor.getString(cursor.getColumnIndexOrThrow("body")));
                message.setFrom(cursor.getString(cursor.getColumnIndexOrThrow("address")));
                message.setTimestamp(cursor.getLong(cursor.getColumnIndexOrThrow("date")));
                dao.insert(message);
                cursor.moveToNext();
            }
            cursor.close();
        }
        Remember.putBoolean(HAS_READ_SMS, true);
        showMain();
    }

    @Override
    public void permissionRefused() {
        Toasty.error(MainActivity.this, "不给权限没办法使用哦").show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermission.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

}
