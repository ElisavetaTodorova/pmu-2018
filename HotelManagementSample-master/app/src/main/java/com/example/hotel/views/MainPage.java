package com.example.hotel.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.example.hotel.Customer;
import com.example.hotel.R;

public class MainPage extends Activity {

    Button menu, order, reservations;
    String user_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainpage);

        Intent myintent = getIntent();
        Bundle extras = myintent.getExtras();
//        user_name = extras.getString("cust_name");
//        Toast.makeText(MainPage.this, "Welcome " + user_name, Toast.LENGTH_LONG).show();

        // initialise form widget
//        menu=(Button)findViewById(R.id.bmenu);
        order = (Button) findViewById(R.id.bOrder);

        order.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                //starting new activity on button click
                Intent i = new Intent(MainPage.this, MenuScreen.class);
//                Bundle bundle = new Bundle();
//                bundle.putString("cust_name", user_name);
                MainPage.this.startActivity(i);
            }
        });

        reservations = (Button) findViewById(R.id.bReservatons);

        reservations.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                Intent i = new Intent(MainPage.this, OrderList.class);
                Bundle bundle = new Bundle();
                bundle.putString("cust_name", user_name);
                MainPage.this.startActivity(i);
            }
        });
    }
}
