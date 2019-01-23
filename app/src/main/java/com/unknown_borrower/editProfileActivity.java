package com.unknown_borrower;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class editProfileActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
    }

    public void goBackToProfileActivity(View view){

        EditText age, name, gender, org, city, state;
        age = findViewById(R.id.age_field);
        name = findViewById(R.id.edit_name);
        gender = findViewById(R.id.gender_field);
        org = findViewById(R.id.org_field);
        city = findViewById(R.id.city_field);
        state = findViewById(R.id.state_field);

        if(age.getText().toString().length() == 0) {
            age.requestFocus();
            age.setError("Field should not be empty");
        }

        else if(name.getText().toString().length() == 0) {
            name.requestFocus();
            name.setError("Field should not be empty");
        }

        else if(city.getText().toString().length() == 0) {
            city.requestFocus();
            city.setError("Field should not be empty");
        }

        else if(gender.getText().toString().length() == 0) {
            gender.requestFocus();
            gender.setError("Field should not be empty");
        }

        else if(state.getText().toString().length() == 0) {
            state.requestFocus();
            state.setError("Field should not be empty");
        }

        else if(org.getText().toString().length() == 0) {
            org.requestFocus();
            org.setError("Field should not be empty");
        }

        else if(Integer.parseInt(age.getText().toString()) <= 0 || Integer.parseInt(age.getText().toString()) > 120)
        {
            age.requestFocus();
            age.setError("The age must be an integer in range of 1 to 120");
        }

        else if(!gender.getText().toString().equals("M") && !gender.getText().toString().equals("F") && !gender.getText().toString().equals("O"))
        {
            gender.requestFocus();
            gender.setError("Type in M for Male, F for Female and O for Other");
        }

        else
        {
            Intent intent = new Intent(this, profileActivity.class);
            startActivity(intent);
        }
    }
}
