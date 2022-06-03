package com.example.week1;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import java.util.regex.Pattern;

public class CreateAccount extends AppCompatActivity {

    private EditText inputEmailAddress, inputRepeatPassword, inputPassword;
    Button nextButton;
    boolean ce;
    boolean cp;
    public static final String SharedP = "sharedPrefs" ;
    public static final String e = "emaillKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createaccount);

        inputEmailAddress = findViewById(R.id.email_address);//Gets email ID
        inputPassword = findViewById(R.id.password);//Gets password ID
        inputRepeatPassword = findViewById(R.id.repeat_password);//Gets repeat password ID
        nextButton = findViewById(R.id.next_button);//Gets next button ID

        //When button is clicked
        nextButton.setOnClickListener(v -> {

            cp = checkEmail();//Email validation
            ce = checkPassword();//Password validation

            //if validation checks are complete
            if(cp && ce){

                saveAccount();

            }
        });
    }

    //Save account or check if account exists
    private void saveAccount() {

        SharedPreferences sp = getSharedPreferences(SharedP, MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        String email = inputEmailAddress.getText().toString();
        //if account exists
        if(sp.contains(email)){
            showError(inputEmailAddress, "Account already exists");
            return;
        }

        else //Add it to the list
        editor.putString(email, e);
        editor.apply();

        Intent intent = new Intent(this, Success.class);
        startActivity(intent);

    }

    //Checks if password is valid
    private boolean checkPassword() {
        String password = inputPassword.getText().toString();//Converts the text to string
        String repeatPw = inputRepeatPassword.getText().toString();

        //Checks if password length is less than 8
        if (password.length() < 8)
        {
            showError(inputPassword, "Password must be 8 characters long");
            return false;
        }
        //Check for uppercase characters
        String upperCase = "(.*[A-Z].*)";
        if (!password.matches(upperCase))
        {
            showError(inputPassword, "Password must have an uppercase character");
            return false;
        }
        //Check for lowercase characters
        String lowerCase = "(.*[a-z].*)";
        if (!password.matches(lowerCase))
        {
            showError(inputPassword, "Password must have an lowercase character");
            return false;
        }
        //Check for numbers
        String numbers = "(.*[0-9].*)";
        if (!password.matches(numbers))
        {
            showError(inputPassword, "Password must include a number");
            return false;
        }

        //if password does not match
        if (!repeatPw.equals(password)){
            showError(inputRepeatPassword, "Password does not match");
            return false;
        }
        return true;
    }

    //Check if email is valid
    private boolean checkEmail(){
        String email = inputEmailAddress.getText().toString();
        //if it does not contain local, @ and domain
        String pattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        boolean check = emailPatternMatch(email, pattern);
        if(!check){
            showError(inputEmailAddress, "Invalid email");
            return false;
        }

        return true;
    }


    //Email template check
    public static boolean emailPatternMatch(String ea, String p) {
        return Pattern.compile(p)
                .matcher(ea)
                .matches();
    }

    //Displays error
    private void showError(EditText input, String s) {
        input.setError(s);
        input.requestFocus();//Shows the error
    }

}