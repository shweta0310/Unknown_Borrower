package com.unknown_borrower;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class profileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
    }

    public void goToEditProfileActivity(View view){
        Intent intent = new Intent(this, editProfileActivity.class);
        startActivity(intent);
    }

}
