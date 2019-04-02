package com.example.niwedita.myfirstapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class editProfileActivity extends AppCompatActivity {

    private EditText name, city, state, org, age, gender;
    private TextView contactNum,email;
    private RequestQueue mQueue;
    private CircleImageView photo;
    String token=null;
    private Uri mainImageURI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        this.token = getIntent().getStringExtra("token");
        Log.e("Token",token);

        age = findViewById(R.id.age_field);
        name = findViewById(R.id.edit_name);
        gender = findViewById(R.id.gender_field);
        org = findViewById(R.id.org_field);
        city = findViewById(R.id.city_field);
        state = findViewById(R.id.state_field);
        photo = findViewById(R.id.image_upload);
        contactNum = findViewById(R.id.contactNum_field);
        email = findViewById(R.id.email_field);

        mQueue = Volley.newRequestQueue(this);

        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                    if (ContextCompat.checkSelfPermission(editProfileActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                        //Toast.makeText(SetupActivity.this, "Permission Denied", Toast.LENGTH_LONG).show();
                        ActivityCompat.requestPermissions(editProfileActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

                    } else {

                        BringImagePicker();

                    }

                } else {
                    BringImagePicker();
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            BringImagePicker();
        }
    }

    private void BringImagePicker() {

        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAspectRatio(120, 160)
                .start(editProfileActivity.this);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {

                mainImageURI = result.getUri();
                photo.setImageURI(mainImageURI);

                //isChanged = true;

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {

                Exception error = result.getError();

            }
        }

    }

    @Override
    public void onResume()
    {
        super.onResume();
        viewUser();
    }

    private void viewUser()
    {
        String url = "http://unknownborrowersbk-dev.us-east-1.elasticbeanstalk.com/profile/getProfile";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        String str = response.toString();
                        Log.d("INFO",str);
                        try
                        {
                            String namee = response.getString("name");
                            name.setText(namee);

                            String num = response.getString("contactNum");
                            contactNum.setText(num);

                            String Email = response.getString("emailId");
                            email.setText(Email);

                            String City = response.getString("city");
                            city.setText(City);

                            String State = response.getString("state");
                            state.setText(State);

                            String Org = response.getString("org");
                            org.setText(Org);

                            Integer Age = response.getInt("age");
                            String ageInt = Integer.toString(Age);
                            age.setText(ageInt);

                            Integer Gender = response.getInt("gender");
                            if(Gender==1)
                            {
                                gender.setText("F");
                            }
                            if(Gender==0)
                            {
                                gender.setText("M");
                            }
                        }
                        catch (JSONException e)
                        {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                error.printStackTrace();
            }
        })

        {
            @Override
            public Map getHeaders() throws AuthFailureError
            {
                HashMap headers = new HashMap();
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", "Token " + token);
                return headers;
            }
        };

        mQueue.add(request);
    };

    public void goBackToProfileActivity(View view) throws JSONException {

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
            String url = "http://unknownborrowersbk-dev.us-east-1.elasticbeanstalk.com/profile/editProfile";
            JSONObject requestObject = new JSONObject();
            requestObject.put("age",Integer.parseInt(age.getText().toString()));
            requestObject.put("city",city.getText().toString());
            requestObject.put("state",state.getText().toString());
            requestObject.put("org",org.getText().toString());
            requestObject.put("name",name.getText().toString());
            if(gender.getText().toString().equals("F")) {
                requestObject.put("gender",1);
            }
            else if(gender.getText().toString().equals("M")) {
                requestObject.put("gender",0);
            }

            final String requestString = requestObject.toString();

            StringRequest request = new StringRequest(Request.Method.PUT, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("Your Array Response", response);
                    Toast.makeText(getApplicationContext(), "Profile updated Successfully", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(editProfileActivity.this, profile.class);
                    intent.putExtra("token",token);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("error is ", "" + error);
                    Toast.makeText(getApplicationContext(), "Could not update the profile. Please try again", Toast.LENGTH_SHORT).show();
                }
            }) {

                //This is for Headers If You Needed
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Content-Type", "application/json; charset=UTF-8");
                    headers.put("Authorization", "Token " + token);
                    return headers;
                }

                //Pass Your Parameters here
                // @Override
//                protected Map<String, String> getParams() {
//                    Map<String, Double> params = new HashMap<String, Double>();
//                    params.put("balance", Double.parseDouble(amt));
//                    Map<String,String> returnParams = new HashMap<>();
//                    returnParams.put("balance",amt);
//                    return returnParams;
//                }

                @Override
                public byte[] getBody() {
                    try{
                        return requestString.getBytes();
                    }catch (Exception e){
                        return null;
                    }
                }
            };

            mQueue.add(request);
        }
    }
}

