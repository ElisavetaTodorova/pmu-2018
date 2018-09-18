package com.example.hotel.views;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.hotel.DatabaseHelper;
import com.example.hotel.R;

import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OrderScreen extends Activity {
    private static String NUMBERS_REGEX = "[0-9]*";
    private static String ERROR_MSG = "The number of %s must be a valid non negative number";
    EditText numberOfPeople, numberOfRooms;
    Button reserve, dateButtonFrom, dateButtonTo;
    String s;
    DatabaseHelper orderHelper;
    Calendar calendar = Calendar.getInstance();

    Date dateFrom, dateTo;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        orderHelper = new DatabaseHelper(OrderScreen.this);

        // initialise form widget
        setContentView(R.layout.activity_orderscreen);

        // Initialize
        numberOfPeople = (EditText) findViewById(R.id.numberOfPeople);
        numberOfRooms = (EditText) findViewById(R.id.numberOfRooms);

        reserve = (Button) findViewById(R.id.baddorder);
        dateButtonFrom = (Button) findViewById(R.id.selectDateButton);

        dateButtonFrom.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(OrderScreen.this, listenerFrom, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });


        dateButtonTo.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(OrderScreen.this, listenerTo, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });

        reserve.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                reserve.setClickable(false);
                String numberOfPeopleText = numberOfPeople.getText().toString();
                String numberOfRoomsText = numberOfRooms.getText().toString();

                boolean isInputDataValid = validateInputData(numberOfPeopleText, numberOfRoomsText);

                if (isInputDataValid) {
                    int people = Integer.parseInt(numberOfPeopleText);
                    int rooms = Integer.parseInt(numberOfRoomsText);

                    Intent intent = getIntent();
                    Bundle extras = intent.getExtras();
                    String order = (String) extras.get("order");

                    orderHelper.addOrder(order, "test", numberOfRoomsText, numberOfPeopleText, dateFrom.toString(), dateTo.toString());

                    Intent i = new Intent(OrderScreen.this, Last.class);
                    startActivity(i);
//            finish();

                }
            }
        });
        this.s = getIntent().getStringExtra("order");
        numberOfPeople.setRawInputType(InputType.TYPE_CLASS_NUMBER);
        numberOfRooms.setRawInputType(InputType.TYPE_CLASS_NUMBER);
        reserve.setClickable(true);
//        reserve.setOnClickListener(this);
    }

    DatePickerDialog.OnDateSetListener listenerFrom = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int mount, int day) {
            dateFrom = new Date(year, mount, day);
        }
    };

    DatePickerDialog.OnDateSetListener listenerTo = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int mount, int day) {
            dateTo = new Date(year, mount, day);
        }
    };

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        reserve.setClickable(true);

        orderHelper = new DatabaseHelper(OrderScreen.this);
        //orderHelper.getWritableDatabase();
    }

    @Override
    protected void onRestart() {
        // TODO Auto-generated method stub
        super.onRestart();
        reserve.setClickable(true);

    }

    private boolean validateInputData(String people, String rooms) {
        boolean valid = true;
        if (!isValidNumber(people)) {
            numberOfPeople.setError(String.format(ERROR_MSG, "people"));
            valid = false;
        }

        if (!isValidNumber(rooms)) {
            numberOfRooms.setError(String.format(ERROR_MSG, "rooms"));
            valid = false;
        }

        return valid;
    }

    private boolean isValidNumber(String number) {
        Pattern pattern = Pattern.compile(NUMBERS_REGEX);
        Matcher matcher = pattern.matcher(number);

        return matcher.find();
    }

    public boolean checkType() {
        return false;
    }

    public void onStop() {
        super.onStop();
        orderHelper.close();
        //orderHelper.cursor.close();
    }
}