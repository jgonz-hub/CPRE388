package com.example.whack_a_mole;

import android.content.Context;
import android.content.Intent;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import android.os.Handler;
import java.util.logging.LogRecord;
import android.app.AlertDialog;
import android.content.DialogInterface;


public class GameManager {
    private List<Mole> moles;
    private int score;
    private int missedMoles;
    private int spawnIntervalMillis;
    private int initialSpawnIntervalMillis;
    private int maxMissedMoles;
    private ImageView[] moleHoles;
    private Handler moleHandler;

    private Random rand = new Random();

    private Context context;
    private static final int MOLE_VISIBILITY_DURATION = 2000; // 2000 milliseconds (2 seconds)

    public GameManager(Context context, int maxMissedMoles, int initialSpawnIntervalMillis, ImageView[] moleHoles, Handler handler)  {
        this.context = context;
        this.moles = new ArrayList<>();
        this.score = 0;
        this.missedMoles = 0;
        this.maxMissedMoles = maxMissedMoles;
        this.initialSpawnIntervalMillis = initialSpawnIntervalMillis;
        this.spawnIntervalMillis = initialSpawnIntervalMillis;
        this.moleHoles = moleHoles;
        this.moleHandler = handler; // Use the provided Handler
        initializeMoles();
    }

    private void initializeMoles() {
        for (ImageView moleHole : moleHoles) {
            moles.add(new Mole(moleHole));
        }
    }

    private int getRandomHoleIndex() {

        return rand.nextInt(moleHoles.length);
    }

    private int getRandomSpawnDelay() {
        // Generate a random delay between spawns (e.g., between 1000 and 3000 milliseconds)
        return rand.nextInt(2000) + 1000;
    }


    public void startGame() {
        // Define a Runnable to spawn moles at random intervals
        Runnable moleSpawner = new Runnable() {
            @Override
            public void run() {
                if (!isGameOver()) {
                    int randomHoleIndex = getRandomHoleIndex();
                    spawnMole(randomHoleIndex);

                    // Schedule the next mole spawn after a random interval
                    int nextSpawnDelay = getRandomSpawnDelay();
                    moleHandler.postDelayed(this, nextSpawnDelay);
                }
            }
        };

        // Start spawning moles immediately
        moleHandler.post(moleSpawner);
    }


    public void spawnMole(int position) {
        Mole mole = moles.get(position);
        if (!mole.isVisible()) {
            mole.spawn();
            // Start a timer to despawn the mole after a certain duration
            startDespawnTimer(position);
        }
    }

    private void startDespawnTimer(final int position) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (position >= 0 && position < moles.size()) {
                    Mole mole = moles.get(position);
                    if (mole.isVisible()) {
                        mole.despawn();
                        // Handle any logic for when a mole times out (e.g., decrease score)

                        incrementMissedMoles();
                    }
                }
            }
        }, MOLE_VISIBILITY_DURATION); // Set MOLE_VISIBILITY_DURATION to the desired duration
    }

    private void incrementMissedMoles() {
        missedMoles++;
        if (missedMoles >= maxMissedMoles) {
            // Handle game over
            handleGameOver();
        }
    }
    public void tapMole(int position) {
        Mole mole = moles.get(position);
        if (mole.isVisible()) {
            mole.despawn();
            score++;

        } else {
            incrementMissedMoles();
//            missedMoles++;
//            if (missedMoles >= maxMissedMoles) {
//                // Handle game over
//                handleGameOver();
//            }
        }
    }




//    public void restartGame() {
//        // Reset game state, score, missed moles, etc.
//        score = 0;
//        missedMoles = 0;
//
//        // Restart mole spawning
//        startGame();
//    }

    public void handleGameOver() {
        // Stop spawning moles by removing callbacks
        moleHandler.removeCallbacksAndMessages(null);

        // Load the high score
        int highScore = HighScoreManager.getHighScore(context);

        // Compare with the current score
        if (score > highScore) {
            // Update and save the high score
            highScore = score;
            HighScoreManager.setHighScore(context, highScore);
        }

        // Create an explicit intent to start the GameOverActivity
        Intent intent = new Intent(context, GameOverActivity.class);
        intent.putExtra("score", score); // Pass the player's score to the GameOverActivity
        intent.putExtra("highScore", highScore); // Pass the high score to the GameOverActivity

        // Start the GameOverActivity
        context.startActivity(intent);
    }



    public boolean isGameOver() {

        return missedMoles >= maxMissedMoles;
    }

    public int getScore() {
        return score;
    }

    // Add methods to handle increasing spawn rates and decreasing visibility times
}


