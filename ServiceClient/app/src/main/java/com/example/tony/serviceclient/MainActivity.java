package com.example.tony.serviceclient;

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

import com.example.tony.servicelocal.IRemoteServiceTest;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    String TAG = "MainActivity";
    Button start;
    Button testInt;
    Button testFloat;
    Button testString;

    Intent it;
    IRemoteServiceTest remoteServiceTest;

    ServiceConnection sc = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.d(TAG, "onServiceConnected");
            remoteServiceTest = IRemoteServiceTest.Stub.asInterface(iBinder);
            Log.d(TAG, "onServiceConnected");
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.d(TAG, "onServiceDisconnected");
            remoteServiceTest = null;
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        start = (Button)findViewById(R.id.startService);
        testInt = (Button)findViewById(R.id.testInt);
        testFloat = (Button)findViewById(R.id.testfloat);
        testString = (Button)findViewById(R.id.testString);

        start.setOnClickListener(this);
        testInt.setOnClickListener(this);
        testFloat.setOnClickListener(this);
        testString.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.startService:
                it = new Intent();
                //远程服务的intent-filter中的动作
                it.setAction("android.intent.action.TestRemoteService");
                //IRemoteServiceTest.aidl的包名
                it.setPackage("com.example.tony.servicelocal");
                if(bindService(it, sc, Service.BIND_AUTO_CREATE)==true)
                    Toast.makeText(this, "启动服务成功", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(this, "启动服务失败", Toast.LENGTH_SHORT).show();
                break;
            case R.id.testInt:

                try {
                    int i = remoteServiceTest.TestInt(2);
                    Toast.makeText(this, "测试整型:i="+i, Toast.LENGTH_SHORT).show();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.testfloat:
                try {
                    double i = remoteServiceTest.TestDouble(2.34);
                    Toast.makeText(this, "测试浮点:i="+i, Toast.LENGTH_SHORT).show();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.testString:
                try {
                    String i = remoteServiceTest.TestString("hello world");
                    Toast.makeText(this, "测试字符串:i="+i, Toast.LENGTH_SHORT).show();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
        }

    }


}
