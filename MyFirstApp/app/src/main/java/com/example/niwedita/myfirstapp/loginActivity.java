package com.example.niwedita.myfirstapp;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatCheckBox;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

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
            // WHEN LOGIN BUTTON IS CLICKED
            Log.d("login button clicked", "Successful login"); // Sample

            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

            // Request a string response from the provided URL.
            StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://unknownborrowersbk-dev.us-east-1.elasticbeanstalk.com/users/login",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try{
                                JsonParser parser = new JsonParser();
                                JsonElement jsonObject = parser.parse(response);
                                String token = jsonObject.getAsJsonObject().get("token").getAsString();
                                Log.e("token",token);

                                Intent intent=new Intent(loginActivity.this,MainActivity.class);
                                intent.putExtra("token",token);
                                startActivity(intent);
                            }catch (Exception e){
                                passwordEditText.setError("Wrong mobile or password!");
                                passwordEditText.requestFocus();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //error in sending requst
                    Toast.makeText(getApplicationContext(), "Wrong mobile or password!", Toast.LENGTH_SHORT).show();
                    passwordEditText.setError("Wrong mobile or password!");
                    passwordEditText.requestFocus();
                }
            }){
                //adding parameters to the request
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("contactNum", mobileNumberEditText.getText().toString());
                    params.put("password", passwordEditText.getText().toString());
                    return params;
                }
            };
            // Add the request to the RequestQueue.
            queue.add(stringRequest);
        }

    }


    // Notifying the user that email is sent to change Password
    public void forgotPassword(View view){

    }
}
