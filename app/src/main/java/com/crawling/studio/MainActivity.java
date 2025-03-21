package com.crawling.studio;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import com.hjq.window.EasyWindow;


public class MainActivity extends AppCompatActivity {
    private EasyWindow mEasyWindow;
    private AlertDialog mAlertDialog;
    private static final int REQUEST_CODE_FLOATING_WINDOW = 123; // 自定义的请求码

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.color));

        startActivity(new Intent(MainActivity.this, homeActivity.class));
        finish();
    }
}