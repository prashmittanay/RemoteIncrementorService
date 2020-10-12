package org.learn.remoteincrementorservice;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class IncrementorService extends Service {
    private static final String TAG = "IncrementorService";
    private int mIntValue = 0;
    private IncrementorClass mIncrementorClass;
    public IncrementorService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    private final IIncrementor.Stub mBinder = new IIncrementor.Stub() {
        @Override
        public int getCurrentValue() throws RemoteException {
            return mIntValue;
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "Remote Incrementor Service Created!");
        mIncrementorClass = new IncrementorClass();
        mIncrementorClass.execute();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mIncrementorClass.cancel(true);
        Log.d(TAG, "Remote Incrementor Service Destroyed!");
    }

    private class IncrementorClass extends AsyncTask {
        @Override
        protected Object doInBackground(Object[] objects) {
            try {
                while (true) {
                    Thread.sleep(20);
                    mIntValue++;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        };
    }
}
