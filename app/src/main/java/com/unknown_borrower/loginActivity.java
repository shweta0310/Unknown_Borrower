package com.unknown_borrower;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatCheckBox;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
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

    // To check the password is valid or not
    public static boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

    }

    // to show or hide Password
    public void showPassword(View view) {
        checkBox= findViewById(R.id.checkbox);
        if (checkBox.isChecked()){
            passwordEditText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        }
        else {
            passwordEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
    }

    // Validation when login button is clicked
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
           // connecting to backend using volley

            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

            // Request a string response from the provided URL.

            String url="http://unknownborrowersbk-dev.us-east-1.elasticbeanstalk.com/users/login";
            HashMap<String,String> params= new HashMap<>();
            params.put("contactNum", mobileNumberEditText.getText().toString());
            params.put("password", passwordEditText.getText().toString());

            JsonObjectRequest loginRequest = new JsonObjectRequest(Request.Method.POST, url,new JSONObject(params),new Response.Listener<JSONObject>(){
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        if(response.getString("token") != null){
                                    //login authenticated. Start the next activity of your app
                                    Toast.makeText(getApplicationContext(), "Logged In", Toast.LENGTH_SHORT).show();
                            Log.d("loginActivity","Logged In");

                                }
                        if(response.toString() == "Passwords do not match")
                        {  //login failed. prompt to re-enter the credentials
                            Toast.makeText(getApplicationContext(), "wrong", Toast.LENGTH_SHORT).show();
                            Log.d("loginActivity", "Wrong password");
                            passwordEditText.setError("Wrong mobile or password!");
                            passwordEditText.requestFocus();
                        }

                    } catch (JSONException e) {

                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                }

            });



            queue.add(loginRequest);
            // volley part ends here
        }

        }


//    // forwarding to SignUp page
//    public void signUP(View view){
//        Intent intent = new Intent(getApplicationContext(),);
//        startActivity(intent);
//    }
}
