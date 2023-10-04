package com.example.whack_a_mole;

import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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

        // Initialize moleHoles and scoreTextView
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

        // Initialize gameManager
        Handler mainThreadHandler = new Handler(Looper.getMainLooper());
        gameManager = new GameManager(this, 3, 2000, moleHoles, mainThreadHandler);

        // Start the game
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

    // Handle the game over scenario
//    public void handleGameOver() {
//        // Stop the game
//        gameManager.stopGame();
//
//        // Show a game over dialog with a restart button
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("Game Over");
//        builder.setMessage("Your score: " + gameManager.getScore());
//
//        // Add a restart button
//        builder.setPositiveButton("Restart", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                // Handle restart button click
//                gameManager.restartGame();
//                updateUI(); // Update UI to reset the score
//
//                // Dismiss the game over dialog
//                dialog.dismiss();
//            }
//        });
//
//        builder.setCancelable(false); // Prevent dialog from being canceled by touching outside
//
//        AlertDialog gameOverDialog = builder.create();
//        gameOverDialog.show();
//    }
}
