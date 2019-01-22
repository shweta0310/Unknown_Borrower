package com.unknown_borrower;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatCheckBox;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class loginActivity extends AppCompatActivity {

    EditText mobileNumberEditText;
    EditText passwordEditText;
    AppCompatCheckBox checkBox;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mobileNumberEditText=findViewById(R.id.mobileNumberEditText);
        passwordEditText=findViewById(R.id.passwordEditText);
    }

    public static boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

    }

    public void showPassword(View view) {
        checkBox= findViewById(R.id.checkbox);
        if (checkBox.isChecked()){
            passwordEditText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        }
        else {
            passwordEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
    }

    public void loginButtonClicked(View view) {
        if(mobileNumberEditText.getText().toString().length() == 0)
        {
            mobileNumberEditText.requestFocus();
            mobileNumberEditText.setError("Field cannot be empty");
        }
        else if(mobileNumberEditText.getText().toString().length() !=10)
        {
            mobileNumberEditText.requestFocus();
            mobileNumberEditText.setError("Not valid");
        }
        else if(passwordEditText.getText().toString().length() == 0)
        {
            passwordEditText.requestFocus();
            passwordEditText.setError("Field cannot be empty");
        }
        else if(passwordEditText.getText().length()<8 &&(!isValidPassword(passwordEditText.getText().toString()))){
            passwordEditText.requestFocus();
            passwordEditText.setError("Not valid or 8 characters minimum");
        }
        else {
           // WHEN LOGIN BUTTON IS CLICKED
            Log.d("login button clicked", "Successful login");
        }
    }
}
