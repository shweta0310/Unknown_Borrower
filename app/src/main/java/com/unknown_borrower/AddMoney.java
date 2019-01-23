package com.unknown_borrower;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AddMoney extends AppCompatActivity {

    private EditText amountText;
    private Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_money);

        amountText = (EditText) findViewById(R.id.amount);
        submitButton = (Button) findViewById(R.id.submit);

        submitButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                addMoneyButtonClicked();
            }
        });
    }

    public void addMoneyButtonClicked()
    {
        String amt = amountText.getText().toString();

        if(amt.length() == 0)
        {
            amountText.requestFocus();
            amountText.setError("Field cannot be empty");
        }
        else if(amt.startsWith("0"))
        {
            amountText.requestFocus();
            amountText.setError("Field cannot start with zero");
        }
        else if(amt.startsWith("."))
        {
            amountText.requestFocus();
            amountText.setError("Field cannot start with decimal");
        }
        else if(amt.endsWith("."))
        {
            amountText.requestFocus();
            amountText.setError("Field should not end with decimal");
        }
        else
        {
            View linearLayout =  findViewById(R.id.info);
            //LinearLayout layout = (LinearLayout) findViewById(R.id.info);


            TextView valueTV = new TextView(this);
            valueTV.setText("Money Added succesfully");
            valueTV.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.FILL_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));

            ((LinearLayout) linearLayout).addView(valueTV);
        }
    }
}
