package com.example.tony.serviceremote;

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

import com.aplex.servicetest.IMyServiceBinder;

public class MainActivity extends AppCompatActivity {

    String TAG = "MainActivity";
    Intent it;
    Button bt;
    Button bt2;
    private IMyServiceBinder myServiceBinder;
    ServiceConnection sc = new ServiceConnection(){
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.d(TAG, "222onServiceConnected");
            myServiceBinder = IMyServiceBinder.Stub.asInterface(iBinder);
            Log.d(TAG, "222onServiceConnected");
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            myServiceBinder = null;
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bt = (Button)findViewById(R.id.bt);
        bt2 = (Button)findViewById(R.id.bt2);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                it = new Intent();
                Log.d(TAG, "222");
                it.setAction("android.intent.action.Myservice");
                //Intent service, ServiceConnection conn, int flags
                it.setPackage("com.aplex.servicetest");
                bindService(it, sc, Service.BIND_AUTO_CREATE);
            }
        });

        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Log.d(TAG, "555");
                    myServiceBinder.callService();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
    }


}
