package com.example.whack_a_mole;

import android.widget.ImageView;

public class Mole {
    private ImageView moleHole;
    private boolean isVisible;

    public Mole(ImageView moleHole) {
        this.moleHole = moleHole;
        this.isVisible = false;
    }

    public void spawn() {
        isVisible = true;
        moleHole.setImageResource(R.drawable.molenobackground); // Set mole drawable
    }

    public void despawn() {
        isVisible = false;
        moleHole.setImageResource(R.drawable.hole); // Set mole hole background
    }

    public boolean isVisible() {
        return isVisible;
    }
}


