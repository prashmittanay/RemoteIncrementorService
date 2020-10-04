package org.learn.remoteincrementorservice;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.os.RemoteException;

public class IncrementorService extends Service {
    private int mIntValue = 0;
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
        IncrementorClass incrementorClass = new IncrementorClass();
        incrementorClass.execute();
    }

    private class IncrementorClass extends AsyncTask {
        @Override
        protected Object doInBackground(Object[] objects) {
            try {
                while (true) {
                    Thread.sleep(900);
                    mIntValue++;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return null;
        };
    }
}
