package com.example.toothscheduleproject;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;

public class SignUp extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        Spinner spinner = findViewById(R.id.spYear);
        Spinner spinner2 = findViewById(R.id.spMonth);
        Spinner spinner3 = findViewById(R.id.spDay);


        ArrayAdapter monthAdapter = ArrayAdapter.createFromResource(this, R.array.year, android.R.layout.simple_spinner_dropdown_item);

        monthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(monthAdapter);

        ArrayAdapter monthAdapter2 = ArrayAdapter.createFromResource(this, R.array.day, android.R.layout.simple_spinner_dropdown_item);

        monthAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(monthAdapter2);

        ArrayAdapter monthAdapter3 = ArrayAdapter.createFromResource(this, R.array.month, android.R.layout.simple_spinner_dropdown_item);

        monthAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner3.setAdapter(monthAdapter3);






    }}



