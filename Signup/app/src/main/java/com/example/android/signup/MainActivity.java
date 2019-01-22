package com.example.android.signup;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    EditText name;
    EditText email;
    EditText phone;
    EditText age;
    EditText gender;
    EditText occupation;
    EditText organization;
    EditText city;
    EditText state;
    EditText country;
    Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name=findViewById(R.id.name);
        email=findViewById(R.id.email);
        phone=findViewById(R.id.phone);
        age=findViewById(R.id.age);
        gender=findViewById(R.id.gender);
        occupation=findViewById(R.id.occupation);
        organization=findViewById(R.id.organization);
        city=findViewById(R.id.city);
        state=findViewById(R.id.state);
        country=findViewById(R.id.country);
        register=findViewById(R.id.button);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkDataEntered();
            }
        });
    }

    boolean isEmpty(EditText text){
        CharSequence str=text.getText().toString();
        return TextUtils.isEmpty(str);
    }

    boolean isEmail(EditText text){
        CharSequence email=text.getText().toString();
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    boolean isPhone(EditText text){
        CharSequence phone=text.getText().toString();
        Pattern p=Pattern.compile("[0-9]{10}");
        Matcher m=p.matcher(phone);
        boolean ans=m.matches();
        return (!TextUtils.isEmpty(phone) && ans);
    }

    boolean isAge(EditText text){
        CharSequence age = text.getText().toString();
        Pattern p=Pattern.compile("[0-9]+");
        Matcher m=p.matcher(age);
        boolean ans=m.matches();
        return (!TextUtils.isEmpty(age) && ans);
    }

    boolean isGender(EditText text){
        CharSequence gender = text.getText().toString();
        Pattern p=Pattern.compile("[MF]");
        Matcher m=p.matcher(gender);
        boolean ans=m.matches();
        return (!TextUtils.isEmpty(gender) && ans);
    }

    void checkDataEntered(){
        if(isEmpty(name)) {
           name.setError("Enter Valid Name");
        }
        if(!isEmail(email)){
            email.setError("Enter Valid Email-ID");
        }
        if(!isPhone(phone)){
            phone.setError("Enter Valid Phone Number");
        }
        if(!isAge(age)){
            age.setError("Enter Valid Age");
        }
        if(!isGender(gender)){
            gender.setError("Enter Valid Gender");
        }
        if(isEmpty(occupation)) {
            occupation.setError("Enter Occupation");
        }
        if(isEmpty(organization)) {
            organization.setError("Enter Organization");
        }
        if(isEmpty(city)) {
            city.setError("Enter City");
        }
        if(isEmpty(state)) {
            state.setError("Enter State");
        }
        if(isEmpty(country)) {
            country.setError("Enter Country");
        }
    }


}
