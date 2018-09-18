package com.example.hotel.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.hotel.R;

public class Last extends Activity {

	Button viewRegistration;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_last);


		viewRegistration = (Button) findViewById(R.id.viewReservations);

        viewRegistration.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent iL = new Intent(Last.this, MainPage.class);
                startActivity(iL);
            }
        });
	}

	

}
