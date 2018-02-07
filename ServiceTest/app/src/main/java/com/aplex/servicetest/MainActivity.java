package com.aplex.servicetest;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    String TAG = "MainActivity";

    Button startBT;
    Button startImplicitBT;
    Button stopBT;
    Button bindBT;
    Button unbindBT;
    Button callBT;

    Intent it;
    Intent it2;
    IMyServiceBinder myServiceBinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //1. startService
        startBT = (Button)findViewById(R.id.startServiceID);
        stopBT = (Button)findViewById(R.id.stopServiceID);
        startImplicitBT = (Button)findViewById(R.id.startServiceImplicitID);
        bindBT = (Button)findViewById(R.id.bindServiceID);
        unbindBT = (Button)findViewById(R.id.unbindServiceID);
        callBT = (Button)findViewById(R.id.callServiceID);

        startBT.setOnClickListener(this);
        startImplicitBT.setOnClickListener(this);
        stopBT.setOnClickListener(this);
        bindBT.setOnClickListener(this);
        unbindBT.setOnClickListener(this);
        callBT.setOnClickListener(this);
    }

    ServiceConnection myServiceConnection = new ServiceConnection(){

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.d(TAG,"onServiceConnected");
            myServiceBinder = (IMyServiceBinder) iBinder;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.d(TAG,"onServiceDisconnected");
        }
    };


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.startServiceID:   //显示调用startService
                it = new Intent(MainActivity.this, MyService.class);
                startService(it);
                break;
            case R.id.startServiceImplicitID://隐式调用startService：android5.0以后废除了，会报错
                it = new Intent();
                it.setAction("android.intent.action.Myservice");
                startService(it);
                break;
            case R.id.stopServiceID:
                stopService(it);
                break;

            case R.id.bindServiceID:  //本地bind第二个服务
                Log.d(TAG, "onClick");
//                Intent service, ServiceConnection conn, int flags
                it2 = new Intent(MainActivity.this, MyService.class);
                bindService(it2, myServiceConnection ,Service.BIND_AUTO_CREATE);
                break;
            case R.id.unbindServiceID:  //本地unbind第二个服务
                unbindService(myServiceConnection);
                break;
            case R.id.callServiceID:  //本地调用第二个服务中的方法
                try {
                    myServiceBinder.callService();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
        }
    }
}
