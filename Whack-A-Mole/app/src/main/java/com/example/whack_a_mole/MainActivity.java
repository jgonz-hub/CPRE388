package com.example.whack_a_mole;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
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
        boolean isGameOver = gameManager.isGameOver();// Your logic to determine if it's a game over scenario;

        if (isGameOver) {
            // Load the game over layout
            setContentView(R.layout.activity_game_over);

            // Find the "Restart Game" button within the game over layout
            Button restartButton = findViewById(R.id.restartButton);

            // Set an OnClickListener for the restart button
            restartButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Handle restart button click
                    gameManager.restartGame();
                    updateUI(); // Update UI to reset the score
                    // You might also want to change the layout back to your main game layout
                    setContentView(R.layout.activity_main);
                }
            });
        } else {
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

            // Find the "Restart Game" button and set an OnClickListener
            Button restartButton = findViewById(R.id.restartButton);


            startGame();
        }
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
        for (int i = 0; i < moleHoles.length; i++) {
            if (view == moleHoles[i]) {
                return i;
            }
        }
        return -1; // Return -1 if the view is not found in the moleHoles array
    }


    private void updateUI() {
        scoreTextView.setText("Score: " + gameManager.getScore());
    }



}
