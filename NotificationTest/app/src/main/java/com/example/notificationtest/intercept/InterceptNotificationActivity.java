package com.example.notificationtest.intercept;

import android.content.Intent;
import android.os.Bundle;
import android.service.notification.NotificationListenerService;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationManagerCompat;
import androidx.databinding.DataBindingUtil;

import com.example.notificationtest.R;
import com.example.notificationtest.databinding.ActivityInterceptTestBinding;
import com.example.notificationtest.ui.DownLoadTestActivity;

/**
 * @author hy
 * @Date 2019/10/29 0029
 * <p>
 * 尝试拦截其他应用发出的Notification
 **/
public class InterceptNotificationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityInterceptTestBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_intercept_test);

        //判断是否开启监听通知权限
        if (NotificationManagerCompat.getEnabledListenerPackages(this).contains(getPackageName())) {
            Intent intent = new Intent(this, NotificationListenerService.class);
            startService(intent);
            Toast.makeText(this, "服务已开启", Toast.LENGTH_SHORT).show();
        } else {
            startActivity(new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"));
        }

        binding.jumpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(InterceptNotificationActivity.this, DownLoadTestActivity.class));
            }
        });
    }
}
