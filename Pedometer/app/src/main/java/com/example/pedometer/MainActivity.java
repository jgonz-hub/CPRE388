package com.example.pedometer;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private int stepCount = 0;
    private boolean isStepCounting = false;
    private TextView stepCountTextView;
    private double lastAcceleration = 0;
    private boolean isPositivePeak = false;
    private boolean isNegativePeak = false;

    private final double THRESHOLD = 5.0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        stepCountTextView = findViewById(R.id.stepCountTextView);
        Button resetButton = findViewById(R.id.resetButton);

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stepCount = 0;
                updateStepCountDisplay();
            }
        });

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        sensorManager.registerListener(sensorListener, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    private void updateStepCountDisplay() {
        stepCountTextView.setText("Step Count: " + stepCount);
    }

    private final SensorEventListener sensorListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            float[] values = event.values;
            float x = values[0];
            float y = values[1];
            float z = values[2];
            double acceleration = Math.sqrt(x * x + y * y + z * z);

            if (acceleration > 10 && !isStepCounting) {
                isStepCounting = true;
                stepCount++;
                updateStepCountDisplay();
            } else if (acceleration < 10) {
                isStepCounting = false;
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            // Handle sensor accuracy changes if needed.
        }
    };
}
