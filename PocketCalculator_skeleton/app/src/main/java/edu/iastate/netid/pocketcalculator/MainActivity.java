package edu.iastate.netid.pocketcalculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    /**
     * The instance of the calculator model for use by this controller.
     */
    private final CalculationStream mCalculationStream = new CalculationStream();

    /*
     * The instance of the calculator display TextView. You can use this to update the calculator display.
     */
    private TextView mCalculatorDisplay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* TODO - uncomment the below line after you make your layout. This line locates
           the instance of the calculator display's TextView.  You need to create this TextView
           and set its ID to CalculatorDisplay in your layout resource file.
         */
        mCalculatorDisplay = findViewById(R.id.CalculatorDisplay);

        Button button1 = findViewById(R.id.button1);

        Button button2 = findViewById(R.id.button2);

        Button button3 = findViewById(R.id.button3);

        Button button4 = findViewById(R.id.button4);

        Button button5 = findViewById(R.id.button5);

        Button button6 = findViewById(R.id.button6);

        Button button7 = findViewById(R.id.button7);

        Button button8 = findViewById(R.id.button8);

        Button button9 = findViewById(R.id.button9);

        Button button0 = findViewById(R.id.button0);

        Button button_add = findViewById(R.id.button_add);

        Button button_minus = findViewById(R.id.button_minus);

        Button button_mult = findViewById(R.id.button_mult);

        Button button_div = findViewById(R.id.button_division);

        Button button_clear = findViewById(R.id.button_clear);

        Button button_res = findViewById(R.id.button_equal);

        Button button_dot = findViewById(R.id.button_dot);
        button0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle button 0 click
                mCalculationStream.inputDigit(CalculationStream.Digit.ZERO);
                updateCalculatorDisplay();
            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle button 0 click
                mCalculationStream.inputDigit(CalculationStream.Digit.ONE);
                updateCalculatorDisplay();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle button 0 click
                mCalculationStream.inputDigit(CalculationStream.Digit.TWO);
                updateCalculatorDisplay();
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle button 0 click
                mCalculationStream.inputDigit(CalculationStream.Digit.THREE);
                updateCalculatorDisplay();
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle button 0 click
                mCalculationStream.inputDigit(CalculationStream.Digit.FOUR);
                updateCalculatorDisplay();
            }
        });

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle button 0 click
                mCalculationStream.inputDigit(CalculationStream.Digit.FIVE);
                updateCalculatorDisplay();
            }
        });

        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle button 0 click
                mCalculationStream.inputDigit(CalculationStream.Digit.SIX);
                updateCalculatorDisplay();
            }
        });

        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle button 0 click
                mCalculationStream.inputDigit(CalculationStream.Digit.SEVEN);
                updateCalculatorDisplay();
            }
        });

        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle button 0 click
                mCalculationStream.inputDigit(CalculationStream.Digit.EIGHT);
                updateCalculatorDisplay();
            }
        });

        button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle button 0 click
                mCalculationStream.inputDigit(CalculationStream.Digit.NINE);
                updateCalculatorDisplay();
            }
        });

        button_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle operator click
                mCalculationStream.inputOperation(CalculationStream.Operation.ADD);
                updateCalculatorDisplay();
            }
        });

        button_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCalculationStream.inputOperation(CalculationStream.Operation.SUBTRACT);
                updateCalculatorDisplay();
            }
        });

        button_mult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCalculationStream.inputOperation(CalculationStream.Operation.MULTIPLY);
                updateCalculatorDisplay();
            }
        });

        button_div.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCalculationStream.inputOperation(CalculationStream.Operation.DIVIDE);
                updateCalculatorDisplay();
            }
        });

        button_dot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCalculationStream.inputDigit(CalculationStream.Digit.DECIMAL);
                updateCalculatorDisplay();
            }
        });

        button_res.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onEqualClicked(mCalculatorDisplay);
            }
        });

        button_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCalculationStream.clear();
                updateCalculatorDisplay();
            }
        });
    }

    /* TODO - add event listeners for your calculator's buttons. See the model's API to figure out
       what methods should be called. The equals button event listener has been done for you. Once
       you've created the layout, you'll need to add these methods as the onClick() listeners
       for the corresponding buttons in the XML layout. */

    // Add onClick listeners for numeric buttons (0-9)



    public void onEqualClicked(View view) {
        try {
            mCalculationStream.calculateResult();
        } finally {
            updateCalculatorDisplay();
        }
    }

    /**
     * Call this method after every button press to update the text display of your calculator.
     */
    public void updateCalculatorDisplay() {
        String value = getString(R.string.empty);
        try {
            value = Double.toString(mCalculationStream.getCurrentOperand());
        } catch(NumberFormatException e) {
            value = getString(R.string.error);
        } finally {
            // TODO: this value may need to be formatted first so it fits on one line... try 0.8 - 0.2
            mCalculatorDisplay.setText(value);
        }
    }
}
