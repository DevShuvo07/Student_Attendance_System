package com.example.student;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Calendar;

public class AD_Hoc extends AppCompatActivity {
    EditText date , Add_id;
    Calendar mcurrentdate;
    int day,month,year;
    database mydb;
    Spinner program , course_code , intake, sec;
    String selectedProgram, selectedIntake, selectedCourse_code, selectedSection;
    java.util.List<String> programList = new ArrayList<>();
    java.util.List<String> intakeList = new ArrayList<>();
    java.util.List<String> sectionlist = new ArrayList<>();
    java.util.List<String> course_code_list = new ArrayList<>();
    //java.util.List<studentdatamodel> studentList = new ArrayList<>();

    String[] Intake, Course_code, Program,Sec;
    private boolean isfirstselection = true;

    private Button find,add,list_view;
    private TextView add_more;
    private String OTP;
    private LinearLayout parentLinearLayout, idview;
    private RelativeLayout addbuttonview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad_hoc);
        parentLinearLayout = (LinearLayout) findViewById(R.id.view1);
        idview = (LinearLayout) findViewById(R.id.view);
        addbuttonview = (RelativeLayout) findViewById(R.id.buttom);
        VariableClass variableClass = VariableClass.getInstance();
        setTitle(" CAS - "+ variableClass.getDomain());

        Intent intent = getIntent();
        OTP = intent.getStringExtra("OTP");

        find = (Button) findViewById(R.id.Find);
        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                idview.setVisibility(View.VISIBLE);
                addbuttonview.setVisibility(View.VISIBLE);
            }

        });

        /*date = (EditText) findViewById(R.id.Date);
        mcurrentdate = Calendar.getInstance();
        day = mcurrentdate.get(Calendar.DAY_OF_MONTH);
        month = mcurrentdate.get(Calendar.MONTH);
        year = mcurrentdate.get(Calendar.YEAR);
        month = month + 1;
        date.setText(day + "/" + month + "/" + year);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(com.example.student.AD_Hoc.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month + 1;
                        date.setText(dayOfMonth + "/" + month + "/" + year);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });*/

        mydb = new database(this);
        program = findViewById(R.id.program);
        intake = findViewById(R.id.Intake);
        sec = findViewById(R.id.section);
        course_code = findViewById(R.id.Course_code);
        find = findViewById(R.id.Find);
        list_view = findViewById(R.id.list_view);
        add_more = findViewById(R.id.add_more);
        add_more = (TextView) findViewById(R.id.add_more);
        add_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View rowView = inflater.inflate(R.layout.add_student_field, null);
                // Add the new row before the add field button.
                parentLinearLayout.addView(rowView, parentLinearLayout.getChildCount() - 1);

            }
        });



        list_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Intent = new Intent(AD_Hoc.this,MainActivity.class);
                startActivity(Intent);
            }
        });

        course_code.setEnabled(false);
        intake.setEnabled(false);
        sec.setEnabled(false);

        loadProgramData();

        program.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                selectedProgram = programList.get(position);

                database db = new database(getApplicationContext());
                course_code_list.clear();
                course_code_list = db.getcourse_code(selectedProgram);

                loadCourse_code();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        course_code.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                selectedCourse_code = course_code_list.get(position);

                database db = new database(getApplicationContext());
                intakeList.clear();
                intakeList = db.getintake(selectedCourse_code);

                loadintakedata();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        intake.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                selectedIntake = intakeList.get(position);

                database db = new database(getApplicationContext());
                sectionlist.clear();
                sectionlist = db.getsection(selectedIntake);

                loadsection();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        sec.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                selectedSection = sectionlist.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
    private void loadCourse_code() {
        // Creating adapter for spinner
        final ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, course_code_list);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        course_code.setAdapter(dataAdapter);
        course_code.setFocusable(true);
        course_code.clearFocus();
        course_code.setEnabled(true);
    }


    private void loadProgramData() {
        database db = new database(getApplicationContext());
        programList = db.getprogram();

        // Creating adapter for spinner
        final ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, programList);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        program.setAdapter(dataAdapter);
        program.setFocusable(true);
        program.clearFocus();
    }

    private void loadintakedata() {

        //databasehelper db = new databasehelper(getApplicationContext());
        //StudentView<String> labels = db.getintakedata();

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, intakeList);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        intake.setAdapter(dataAdapter);
        intake.setFocusable(true);
        intake.clearFocus();

        intake.setEnabled(true);
    }
    private void loadsection() {

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, sectionlist);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        sec.setAdapter(dataAdapter);
        sec.setFocusable(true);
        sec.clearFocus();

        sec.setEnabled(true);
    }
    public void onDelete(View v) {
        parentLinearLayout.removeView((View) v.getParent());
    }
}