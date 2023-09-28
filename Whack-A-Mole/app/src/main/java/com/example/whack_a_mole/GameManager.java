package com.example.whack_a_mole;

import android.content.Context;
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
                    }
                }
            }
        }, MOLE_VISIBILITY_DURATION); // Set MOLE_VISIBILITY_DURATION to the desired duration
    }


    public void tapMole(int position) {
        Mole mole = moles.get(position);
        if (mole.isVisible()) {
            mole.despawn();
            score++;
            // Adjust the spawn interval or other game logic here
            // For example, you can decrease spawnIntervalMillis here.
        } else {
            missedMoles++;
            if (missedMoles >= maxMissedMoles) {
                // Handle game over
                handleGameOver();
            }
        }
    }

    public void restartGame() {
        // Reset game state, score, missed moles, etc.
        score = 0;
        missedMoles = 0;

        // Restart mole spawning
        startGame();
    }

    public void handleGameOver() {
        // Stop spawning moles by removing callbacks
        moleHandler.removeCallbacksAndMessages(null);

        // Show a game over dialog with a restart button
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Game Over");
        builder.setMessage("Your score: " + score);

        // Add a restart button
        builder.setPositiveButton("Restart", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Handle restart button click
                restartGame();
            }
        });

        builder.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Handle exit button click or any other actions
            }
        });

        builder.setCancelable(false); // Prevent dialog from being canceled by touching outside

        AlertDialog gameOverDialog = builder.create();
        gameOverDialog.show();
    }

    public boolean isGameOver() {

        return missedMoles >= maxMissedMoles;
    }

    public int getScore() {
        return score;
    }

    // Add methods to handle increasing spawn rates and decreasing visibility times
}


