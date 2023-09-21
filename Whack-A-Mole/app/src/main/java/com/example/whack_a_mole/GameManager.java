package com.example.whack_a_mole;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameManager {
    private List<Mole> moles;
    private int score;
    private int missedMoles;
    private int spawnIntervalMillis;
    private int initialSpawnIntervalMillis;
    private int maxMissedMoles;

    public GameManager(int maxMissedMoles, int initialSpawnIntervalMillis) {
        this.moles = new ArrayList<>();
        this.score = 0;
        this.missedMoles = 0;
        this.maxMissedMoles = maxMissedMoles;
        this.initialSpawnIntervalMillis = initialSpawnIntervalMillis;
        this.spawnIntervalMillis = initialSpawnIntervalMillis;
        initializeMoles();
    }

    private void initializeMoles() {
        for (int i = 0; i < 9; i++) {
            moles.add(new Mole(i));
        }
    }

    public void startGame() {
        // Start spawning moles at random holes at the specified interval
        // You'll need a timer or a handler to manage this.
    }

    public void tapMole(int position) {
        if (moles.get(position).isVisible()) {
            moles.get(position).despawn();
            score++;
            // Adjust the spawn interval or other game logic here
        } else {
            missedMoles++;
            if (missedMoles >= maxMissedMoles) {
                // Handle game over
            }
        }
    }

    public boolean isGameOver() {
        return missedMoles >= maxMissedMoles;
    }

    public int getScore() {
        return score;
    }

    // Add methods to handle increasing spawn rates and decreasing visibility times
}


