package com.example.stopwatch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private StopwatchViewModel viewModel;
    private TextView timeDisplay;
    private Button startButton, stopButton, resetButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewModel = new ViewModelProvider(this).get(StopwatchViewModel.class);
        timeDisplay = findViewById(R.id.timeDisplay);
        startButton = findViewById(R.id.startButton);
        stopButton = findViewById(R.id.stopButton);
        resetButton = findViewById(R.id.resetButton);

        viewModel.getElapsedTimeDisplay().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String elapsedTime) {
                timeDisplay.setText(elapsedTime);
            }
        });

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.start();
                startButton.setEnabled(false);
                stopButton.setEnabled(true);
                resetButton.setEnabled(true);
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.stop();
                startButton.setEnabled(true);
                stopButton.setEnabled(false);
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.reset();
                startButton.setEnabled(true);
                stopButton.setEnabled(false);
                resetButton.setEnabled(false);
            }
        });
    }
}
