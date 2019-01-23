package com.unknown_borrower;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class Wallet extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);


        Button button = (Button) findViewById(R.id.add_money);

        button.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.wtf("onCLick", "WorkingIntent1");
                Intent i1 = new Intent(Wallet.this, AddMoney.class);
                startActivity(i1);
            }
        });
    }
}
