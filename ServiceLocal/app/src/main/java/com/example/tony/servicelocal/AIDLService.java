package com.example.tony.servicelocal;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;



public class AIDLService extends Service {
    String TAG = "AIDLService";

    class RemoteServiceTest extends IRemoteServiceTest.Stub {

        @Override
        public int TestInt(int i) throws RemoteException {
            return lTestInt(i);
        }

        @Override
        public double TestDouble(double i) throws RemoteException {
            return lTestDouble(i);
        }

        @Override
        public String TestString(String i) throws RemoteException {
            return lTestString(i);
        }
    }
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        //throw new UnsupportedOperationException("Not yet implemented");
        return new RemoteServiceTest();
    }
    private int lTestInt(int i){
        Log.d(TAG, "lTestInt:"+i);
        return i+1;
    }
    private double lTestDouble(double i){
        Log.d(TAG, "lTestDouble:"+i);
        return i+1;
    }
    private String lTestString(String i){
        Log.d(TAG, "lTestString:"+i);
        return i+" fuck";
    }
}
