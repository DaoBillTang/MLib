package com.dtb.core;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;

import androidx.annotation.Nullable;

import java.util.List;

/**
 * project frame
 * Created by dtb on 2020/4/13
 * email: 1750352866@qq.com
 *
 * @author: dtb
 * *
 * @version: 1.0
 * *
 * @description: 需要注册 这个服务
 *  enabled =true
 *  exported =true
 *  intent-filter>
 *      <action android:name=当前服务/>
 */
public class AidlService extends Service {

    private Binder mBinder = new IStudentManager.Stub() {
        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public List<Student> getStudents() throws RemoteException {
            return null;
        }

        @Override
        public void addStudent(Student s) throws RemoteException {

        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
}
