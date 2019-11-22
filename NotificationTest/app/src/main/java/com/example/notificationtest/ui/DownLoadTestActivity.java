package com.example.notificationtest.ui;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.notificationtest.R;
import com.example.notificationtest.databinding.ActivityDownloadTestBinding;
import com.example.notificationtest.service.DownloadService;

import java.util.List;

/**
 * @author hy
 * @Date 2019/10/22 0022
 * <p>
 * 模拟一下下载应用的场景，会实时显示进度
 **/
public class DownLoadTestActivity extends AppCompatActivity {

    private final static String TAG = "DownLoadTestActivity";

    final Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            if (msg.what <= 100) {
                Intent intent = new Intent(DownLoadTestActivity.this, DownloadService.class);
                intent.putExtra("progress", msg.what);
                startService(intent);
                msg.getTarget().sendEmptyMessageDelayed(msg.what + 10, 500);
            } else {
                Intent intent = new Intent(DownLoadTestActivity.this, DownloadService.class);
                stopService(intent);
            }
            Log.d(TAG, "handleMessage: isServiceRunning = " + isServiceRunning(DownloadService.class.getName()));
            return true;
        }
    });

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityDownloadTestBinding downloadTestBinding = DataBindingUtil.setContentView(this, R.layout.activity_download_test);
        downloadTestBinding.setLifecycleOwner(this);

        downloadTestBinding.startDownloadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHandler.sendEmptyMessage(0);
            }
        });
    }

    /**
     * @param serviceName 服务类的全路径名称 例如： com.jaychan.demo.service.PushService
     * @return 服务是否正在运行
     */
    private boolean isServiceRunning(String serviceName) {
        //活动管理器
        ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        if (am != null) {
            List<ActivityManager.RunningServiceInfo> runningServices = am.getRunningServices(100); //获取运行的服务,参数表示最多返回的数量

            for (ActivityManager.RunningServiceInfo runningServiceInfo : runningServices) {
                String className = runningServiceInfo.service.getClassName();
                if (className.equals(serviceName)) {
                    return true; //判断服务是否运行
                }
            }
        }
        return false;
    }
}
