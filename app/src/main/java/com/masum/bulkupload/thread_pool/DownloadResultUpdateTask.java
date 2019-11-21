package com.masum.bulkupload.thread_pool;

import android.widget.TextView;

public class DownloadResultUpdateTask implements Runnable{
    private TextView message;
    private String backgroundMsg;

    public DownloadResultUpdateTask(TextView msg){
        message = msg;
    }
    public void setBackgroundMsg(String bmsg){
        backgroundMsg = bmsg;
    }
    @Override
    public void run() {
        message.setText(backgroundMsg);
    }
} 