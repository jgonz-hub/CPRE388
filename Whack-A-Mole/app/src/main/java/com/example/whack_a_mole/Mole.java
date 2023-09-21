package com.example.whack_a_mole;

public class Mole {
    private int position;
    private boolean isVisible;

    public Mole(int position) {
        this.position = position;
        isVisible = false;
    }

    public void spawn() {
        isVisible = true;
        // Start a timer to despawn the mole after a certain duration
    }

    public void despawn() {
        isVisible = false;
        // Stop the timer or perform any other required cleanup
    }

    public boolean isVisible() {
        return isVisible;
    }

    public int getPosition() {
        return position;
    }
}

