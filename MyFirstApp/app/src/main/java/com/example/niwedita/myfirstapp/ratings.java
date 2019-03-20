package com.example.niwedita.myfirstapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class ratings extends AppCompatActivity {

    RatingBar rb;
    TextView value;

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
                        "Rating changed, current rating "+ rb.getRating(),
                        Toast.LENGTH_SHORT).show();
            }
        });

    }
}
