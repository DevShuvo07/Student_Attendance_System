package com.example.student;

import android.app.Application;

import java.util.Timer;
import java.util.TimerTask;

public class myapp extends Application {
    private LogOutListener listener;
    private Timer timer;

    public void startUserSession() {
        cancelTimer();
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                listener.onsessionlogout();
            }
        }, 86400000);
    }
//86400000
    private void cancelTimer() {
        if (timer != null)
        {
            timer.cancel();
        }
    }

    public void registersessionlistener(BaseActivity baseActivity) {
        this.listener = baseActivity;
    }

    public void onUserInterected() {
        startUserSession();
    }
}
