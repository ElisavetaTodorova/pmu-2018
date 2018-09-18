package com.example.hotel.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hotel.Customer;
import com.example.hotel.DatabaseHelper;
import com.example.hotel.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Register extends Activity {
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$";
    private static final String ERROR_MSG = "%s must not be empty.";
    EditText name, email, passwordEditText;
    Button register;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        try {
            dbHelper = new DatabaseHelper(Register.this);
        } catch (Exception e) {
            // TODO: Maybe log the exception
            e.printStackTrace();
        }

        register = (Button) findViewById(R.id.breg);
        name = (EditText) findViewById(R.id.etname);
        email = (EditText) findViewById(R.id.etemail);
        passwordEditText = (EditText) findViewById(R.id.etphone);


        register.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                // Send the person email if the email is an existing one he should receive a message
                String customerName = name.getText().toString();
                String mail = email.getText().toString();
                String password = passwordEditText.getText().toString();

                boolean isInputDataValid = validateInputData(customerName, password, mail);


                if (isInputDataValid) {

                    boolean userExists = dbHelper.isUserAlreadyRegistered(customerName);

                    if (!userExists) {
                        Customer customer = new Customer();
                        customer.setName(customerName);
                        customer.setEmail(mail);
                        customer.setPassword(password);

                        dbHelper.addCustomer(customer);

                        Intent i = new Intent(Register.this, MainPage.class);
                        Bundle bundle = new Bundle();
                        // This is the way you can transfer data to the other activity
                        bundle.putString("cust_name", customerName);
                        i.putExtras(bundle);
                        startActivity(i);
                    } else {
                        Toast.makeText(Register.this, String.format("User with username %s already exists", customerName), Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(Register.this, "Please fill all input fields with the appropriate data!", Toast.LENGTH_LONG).show();
                }

            }

        });


    }

    private boolean isValidEmailAddress(String email) {
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);

        return matcher.find();
    }

    private boolean validateInputData(String usernameText, String passwordText, String emailText) {
        boolean valid = true;

        if ("".equals(usernameText)) {
            name.setError(String.format(ERROR_MSG, "username"));
            valid = false;
        }


        if ("".equals(passwordText)) {
            passwordEditText.setError(String.format(ERROR_MSG, "password"));
            valid = false;
        }

        if (!isValidEmailAddress(emailText)) {
            email.setError("You must enter a valid email address");
            valid = false;
        }

        return valid;
    }

    public void onResume() {
        super.onResume();
        dbHelper = new DatabaseHelper(Register.this);
    }

    public void onStop() {
        super.onStop();
        dbHelper.close();
    }


}
