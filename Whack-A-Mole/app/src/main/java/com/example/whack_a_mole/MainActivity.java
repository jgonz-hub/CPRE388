package com.example.whack_a_mole;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private GameManager gameManager;
    private ImageView[] moleHoles;
    private TextView scoreTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize moleHoles
        moleHoles = new ImageView[]{
                findViewById(R.id.mole1),
                findViewById(R.id.mole2),
                findViewById(R.id.mole3),
                findViewById(R.id.mole4),
                findViewById(R.id.mole5),
                findViewById(R.id.mole6),
                findViewById(R.id.mole7),
                findViewById(R.id.mole8),
                findViewById(R.id.mole9)
        };

        scoreTextView = findViewById(R.id.scoreTextView);

        // Set click listeners for mole holes
        for (ImageView moleHole : moleHoles) {
            moleHole.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onMoleTap(view);
                }
            });
        }

        // Initialize gameManager, moleHoles, and scoreTextView
        Handler mainThreadHandler = new Handler(Looper.getMainLooper());
        gameManager = new GameManager(this, 3, 2000, moleHoles, mainThreadHandler);

        startGame();
    }

    private void startGame() {
        gameManager.startGame();
    }

    public void onMoleTap(View view) {
        int position = getPositionForView(view);
        gameManager.tapMole(position);
        updateUI();
    }

    private int getPositionForView(View view) {
        // Implement logic to determine the position (hole) based on the clicked view
        // You can use the moleHoles array or other identifiers to do this
        return 0; // Placeholder, replace with actual logic
    }

    private void updateUI() {
        scoreTextView.setText("Score: " + gameManager.getScore());
    }
}
