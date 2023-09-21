package com.example.whack_a_mole;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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

        // Initialize gameManager, moleHoles, and scoreTextView
        gameManager = new GameManager(3, 2000); // Example values, adjust as needed

        moleHoles = new ImageView[9]; // Assuming 3x3 grid
        moleHoles[0] = findViewById(R.id.mole1);
        // Initialize the rest of the moleHoles array with findViewById

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

