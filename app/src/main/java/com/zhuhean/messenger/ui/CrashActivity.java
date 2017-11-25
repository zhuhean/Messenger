package com.zhuhean.messenger.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.zhuhean.messenger.R;
import com.zhuhean.messenger.ui.base.BaseActivity;

import java.io.PrintWriter;
import java.io.StringWriter;

import butterknife.BindView;
import butterknife.OnClick;

public class CrashActivity extends BaseActivity {

    @BindView(R.id.crashTV)
    TextView crashTV;

    @Override
    public int getContentView() {
        return R.layout.activity_crash;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Ooops...");
        String stackTrace = getIntent().getStringExtra("crash");
        crashTV.setText(stackTrace);
    }

    public static void handleCrash(final Context context) {
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                StringWriter sw = new StringWriter();
                e.printStackTrace(new PrintWriter(sw));
                String crashLog = sw.toString();
                Intent intent = new Intent(context, CrashActivity.class);
                intent.putExtra("crash", crashLog);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                context.startActivity(intent);
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        });

    }

    @OnClick(R.id.done)
    public void onClick() {
        finish();
    }

}
