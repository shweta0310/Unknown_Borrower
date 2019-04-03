package com.example.niwedita.myfirstapp;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class ratings extends AppCompatActivity {

    RatingBar rb;
    TextView value;
    Button bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ratings);
        rb = (RatingBar) findViewById(R.id.ratingBar);
        value = (TextView) findViewById(R.id.valueset);
        rb.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
              //  value.setText("Rated: "+rating);
                Toast.makeText(ratings.this,
                        "Current rating "+ rb.getRating(),
                        Toast.LENGTH_SHORT).show();
            }
        });

        bt = (Button) findViewById(R.id.submitratings);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ratings.this,R.style.MyDialogTheme);
                builder.setMessage("Confirm Ratings?")
                        .setPositiveButton("Yes",null)

                        .setNegativeButton("No",null);

            AlertDialog alert= builder.create();
            alert.show();

            }
        });


    }
}
