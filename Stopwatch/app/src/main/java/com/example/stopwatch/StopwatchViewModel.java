package com.example.stopwatch;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import java.util.Timer;
import java.util.TimerTask;

public class StopwatchViewModel extends ViewModel {
    private long startTime = 0;
    private boolean isRunning = false;
    private long elapsedTime = 0;

    private Timer timer;

    // LiveData for elapsed time
    private MutableLiveData<String> elapsedTimeDisplay = new MutableLiveData<>();

    public MutableLiveData<String> getElapsedTimeDisplay() {
        return elapsedTimeDisplay;
    }

    // Start the stopwatch
    public void start() {
        if (!isRunning) {
            startTime = System.currentTimeMillis() - elapsedTime;
            isRunning = true;

            // Use the class-level timer variable
            timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    long currentTime = System.currentTimeMillis();
                    elapsedTime = currentTime - startTime;
                    updateElapsedTimeDisplay(elapsedTime);
                }
            }, 0, 10);
        }
    }


    // Stop the stopwatch
    public void stop() {
        if (isRunning) {
            isRunning = false;
            if(timer!= null){
                timer.cancel();
            }
        }
    }


    // Reset the stopwatch
    public void reset() {
        isRunning = false;
        elapsedTime = 0;
        updateElapsedTimeDisplay(elapsedTime);

        // Reset the startTime to the current time
        startTime = System.currentTimeMillis();

        if (timer != null) {
            timer.cancel();

        }
    }


    private void updateElapsedTimeDisplay(long timeInMillis) {
        // Format elapsed time as "HH:MM:SS.T"
        long hours = timeInMillis / 3600000;
        long minutes = (timeInMillis % 3600000) / 60000;
        long seconds = (timeInMillis % 60000) / 1000;
        long tenths = (timeInMillis % 1000) / 100;

        String elapsedTimeString = String.format("%02d:%02d:%02d.%d", hours, minutes, seconds, tenths);
        elapsedTimeDisplay.postValue(elapsedTimeString);
    }
}

