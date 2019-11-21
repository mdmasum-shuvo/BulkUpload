package com.masum.bulkupload.thread_pool;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;


public class MainThreadExecutor implements Executor {
    private Handler handler = new Handler(Looper.getMainLooper());

    @Override
    public void execute(Runnable runnable) {
        handler.post(runnable);
    }
}
