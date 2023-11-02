package com.example.cameraapp;
import android.Manifest;
import android.content.pm.PackageManager;
import android.media.Image;
import android.os.Bundle;
import android.util.Size;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;
import androidx.camera.view.PreviewView;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.label.ImageLabel;
import com.google.mlkit.vision.label.ImageLabeler;
import com.google.mlkit.vision.label.ImageLabeling;

import com.google.mlkit.vision.label.defaults.ImageLabelerOptions;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 100;
    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;
    private PreviewView previewView;
    private Executor executor;
    private ImageLabeler labeler;
    private TextView labelTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        previewView = findViewById(R.id.previewView);
        executor = ContextCompat.getMainExecutor(this);

        // Initialize the labeler with image labeling options
        ImageLabelerOptions options =
                new ImageLabelerOptions.Builder()
                        .setConfidenceThreshold(0.7f)
                        .build();

        labeler = ImageLabeling.getClient(options);
        labelTextView = findViewById(R.id.labelTextView); // Reference to the label TextView

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST_CODE);
        } else {
            initializeCamera();
        }
    }

    private void initializeCamera() {
        cameraProviderFuture = ProcessCameraProvider.getInstance(this);
        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                bindPreview(cameraProvider);
            } catch (ExecutionException | InterruptedException e) {
                // Handle any errors.
            }
        }, executor);
    }

    void bindPreview(@NonNull ProcessCameraProvider cameraProvider) {
        Preview preview = new Preview.Builder().build();
        CameraSelector cameraSelector = new CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build();
        preview.setSurfaceProvider(previewView.getSurfaceProvider());
        Camera camera = cameraProvider.bindToLifecycle((LifecycleOwner) this, cameraSelector, preview);

        ImageAnalysis imageAnalysis = new ImageAnalysis.Builder()
                .setTargetResolution(new Size(1280, 720))
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build();

        imageAnalysis.setAnalyzer(executor, new YourAnalyzer());

        cameraProvider.bindToLifecycle((LifecycleOwner) this, cameraSelector, imageAnalysis, preview);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                initializeCamera();
            } else {

            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults); // Call super to resolve the warning
    }

    private class YourAnalyzer implements ImageAnalysis.Analyzer {
        @androidx.camera.core.ExperimentalGetImage
        @Override
        public void analyze(ImageProxy imageProxy) {
            Image mediaImage = imageProxy.getImage();
            if (mediaImage != null) {
                InputImage image = InputImage.fromMediaImage(mediaImage, imageProxy.getImageInfo().getRotationDegrees());

                labeler.process(image)
                        .addOnSuccessListener(imageLabels -> {

                            StringBuilder labelString = new StringBuilder();
                            for (ImageLabel label : imageLabels) {
                                String text = label.getText();
                                float confidence = label.getConfidence();
                                int index = label.getIndex();
                                labelString.append("Label: ").append(text).append("\nConfidence: ").append(confidence).append("\n\n");
                            }

                            updateUIWithLabels(labelString.toString());
                        })
                        .addOnFailureListener(e -> {

                            e.printStackTrace();
                        });

                // Release resources
                mediaImage.close();
                imageProxy.close();
            }
        }
    }

    private void updateUIWithLabels(String labelText) {
        runOnUiThread(() -> {
            labelTextView.setVisibility(View.VISIBLE);
            labelTextView.setText(labelText);
        });
    }
}