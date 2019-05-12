package com.example.handler;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    ProgressBar progressBar;
    Button startProgress,stopProgress;
    TextView textView;
    int MAX = 100;
    Handler mHandlerThread;
    private static final int START_PROGRESS = 100;
    private static final int UPDATE_COUNT = 101;
    Thread thread1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        startProgress = (Button) findViewById(R.id.start_progress);
        textView = (TextView) findViewById(R.id.textView);
        progressBar.setMax(MAX);

        thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int  i = 0;i<100;i++){
                    Log.d("I",":"+i);
                    progressBar.setProgress(i);
                    try{
                        Thread.sleep(1000);
                    }
                    catch (InterruptedException ex){
                        ex.printStackTrace();
                    }
                    Message message = new Message();
                    message.what = UPDATE_COUNT;
                    message.arg1 = i;
                    mHandlerThread.sendMessage(message);
                }
            }
        });


        startProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentProgess = progressBar.getProgress();
                /*Message message = new Message();
                message.what = START_PROGRESS;*/
                mHandlerThread.sendEmptyMessage(START_PROGRESS);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mHandlerThread = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == START_PROGRESS){
                    thread1.start();
                }
                else if(msg.what == UPDATE_COUNT){
                    textView.setText(msg.arg1+"  Seconds or Counts");
                }
            }
        };
    }



}