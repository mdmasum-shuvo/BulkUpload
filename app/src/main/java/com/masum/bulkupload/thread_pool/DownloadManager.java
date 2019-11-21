package com.masum.bulkupload.thread_pool;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class DownloadManager {

    private final ThreadPoolExecutor downloadThreadPool;
    private final BlockingDeque<Runnable> downaloadWorkQueue;
    private static final int CORE_POOL_SIZE = 5;
    private static final int MAX_POOL_SIZE = 5;
    private static final int KEEP_ALIVE_TIME = 50;

    private static DownloadManager downloadManager = null;
    private static MainThreadExecutor handler;

    static {

        downloadManager=new DownloadManager();
        handler=new MainThreadExecutor();
    }

    private DownloadManager (){
        downaloadWorkQueue=new LinkedBlockingDeque<>();
        downloadThreadPool=new ThreadPoolExecutor(5, 5,
                50, TimeUnit.MILLISECONDS, downaloadWorkQueue);
    }
    public static DownloadManager getDownloadManager(){
        return downloadManager;
    }

    public void runDownloadFile(Runnable task){
        downloadThreadPool.execute(task);
    }


    public MainThreadExecutor getMainThreadExecutor(){
        return handler;
    }
}
