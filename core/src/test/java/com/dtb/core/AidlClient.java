package com.dtb.core;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import androidx.appcompat.app.AppCompatActivity;

/**
 * project frame
 * Created by dtb on 2020/4/13
 * email: 1750352866@qq.com
 *
 * @author: dtb
 * *
 * @version: 1.0
 * *
 * @description:
 */
public class AidlClient extends AppCompatActivity {
    private IStudentManager iStudentManager;

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            iStudentManager = IStudentManager.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            iStudentManager = null;
        }
    };

    private void bind() {
        Intent intent = new Intent("action这里是注册的时候的 action");
        intent.setPackage("");//隐式启动必须要包名
        bindService(intent, serviceConnection, BIND_AUTO_CREATE);

    }

}
