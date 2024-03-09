package com.exam.remoteexamination;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    //UI Elements Classes
    TextInputLayout nameInput, emailInput, mobileInput;
    TextInputEditText name, email, mobile;
    TextView age;
    Spinner gender;
    Button submit;
    Calendar cal;
    int year, month, day, difference;
    DatePickerDialog datePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //UI Elements initialization
            //for textInputLayouts
        nameInput = findViewById(R.id.nameInput);
        emailInput = findViewById(R.id.emailInput);
        mobileInput = findViewById(R.id.mobileInput);

            //for textInputEditTexts
        name = findViewById(R.id.fullName);
        email = findViewById(R.id.email);
        mobile = findViewById(R.id.mobileNum);

            //for textView
        age = findViewById(R.id.age);

            //for spinner
        gender = findViewById(R.id.gender);

            //for button
        submit = findViewById(R.id.submit);

            //for Calendar
        cal = Calendar.getInstance();

            //int values
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);
        day = cal.get(Calendar.DAY_OF_MONTH);


        //Spinner values
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.genders, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(com.google.android.material.R.layout.support_simple_spinner_dropdown_item);
        gender.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();

      //Validation for name
        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!isValidName(s.toString())){
                    nameInput.setError("Must only contain texts, commas, and periods");
                }
                else{
                    nameInput.setError(null);
                }
            }
        });

     //Validation for email
        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!isValidEmail(s.toString())){
                    emailInput.setError("Invalid email convention");
                }
                else{
                    emailInput.setError(null);
                }
            }
        });

    //Validation for phone
        mobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!isValidPhone(s.toString())){
                    mobileInput.setError("Mobile must be in either 09- or +63- format");
                }
                else{
                    mobileInput.setError(null);
                }
            }
        });
        
    //Show DatePicker for birthday, then calculate age
        age.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //for DatePicker
                datePicker = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        cal.set(Calendar.YEAR, year);
                        cal.set(Calendar.MONTH, month);
                        cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        //Parsing the date from the Picker
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                        String ageDate = dateFormat.format(cal.getTime());
                        ageCalculator(ageDate);
                    }
                }, year, month, day);
                datePicker.show();
            }
        });


    }



    //Validation Methods
    protected boolean isValidName(String pangalan){
        String nameCondition = "^[a-zA-Z.,\\s]+$";
        return pangalan.matches(nameCondition);
    }

    protected boolean isValidEmail(String email){
        String emailCon = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailCon);
    }

    protected boolean isValidPhone(String number){
        String phoneCon = "^(09|\\+639)\\d{9}$";
        return number.matches(phoneCon);
    }
    private void ageCalculator(String ageDate) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            LocalDate currentDate = LocalDate.now();
            if(currentDate != null && ageDate != null){
                difference = Period.between(LocalDate.parse(ageDate), currentDate).getYears();
                if(difference >= 18 ){
                    age.setText(String.format("You are %d years old", difference));
                    age.setTextColor(ContextCompat.getColor(MainActivity.this, com.google.android.material.R.color.m3_dynamic_default_color_primary_text));
                }
                else{
                    age.setText("You must be 18+ to register");
                    age.setTextColor(Color.RED);
                }
            }
        }
    }

}