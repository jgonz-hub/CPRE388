package com.example.whack_a_mole;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class GameOverActivity extends AppCompatActivity {
    private TextView gameOverTextView;
    private Button restartButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        // Initialize views
        gameOverTextView = findViewById(R.id.gameOverTextView);
        restartButton = findViewById(R.id.restartButton);

        // Retrieve the score and high score passed from the main activity
        int score = getIntent().getIntExtra("score", 0);
        int highScore = getIntent().getIntExtra("highScore", 0);

        // Display the scores in TextViews
        TextView scoreTextView = findViewById(R.id.scoreTextView);
        TextView highScoreTextView = findViewById(R.id.highScoreTextView);

        scoreTextView.setText("Score: " + score);
        highScoreTextView.setText("High Score: " + highScore);

        // Display the game over message with the score
        gameOverTextView.setText("Game Over!\nYour Score: " + score);

        // Set an OnClickListener for the restart button
        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle restart button click
                Intent intent = new Intent(GameOverActivity.this, MainActivity.class);
                startActivity(intent);
                finish(); // Finish the current activity (game over screen)
            }
        });
    }
}
