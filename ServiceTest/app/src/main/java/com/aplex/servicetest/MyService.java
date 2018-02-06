package com.aplex.servicetest;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class MyService extends Service {
    String TAG = "MyService";

//    public class MyServiceBinder extends Binder implements IMyServiceBinder{
//
//        @Override
//        public void callService() {
//            InnercallService();
//        }
//    }
        public class MyServiceBinder extends IMyServiceBinder.Stub{

        @Override
        public void callService() {
            InnercallService();
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new MyServiceBinder();
//        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    public void InnercallService(){
        Toast.makeText(this, "呼叫服务", Toast.LENGTH_SHORT).show();
    }
}
