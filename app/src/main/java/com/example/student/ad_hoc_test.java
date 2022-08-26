package com.example.student;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ad_hoc_test extends BaseActivity {

    Spinner program , course_code , intake, sec;
    String selectedProgram, selectedIntake, selectedCourse_code, selectedSection;
    public com.example.student.VariableClass VariableClass;
    private ArrayList<String> students;

    java.util.List<String> programList = new ArrayList<>();
    java.util.List<String> intakeList = new ArrayList<>();
    java.util.List<String> sectionlist = new ArrayList<>();
    java.util.List<String> course_code_list = new ArrayList<>();

    public String username,password,otp,td_code,std_id;
    public CheckBox checkBox1,checkBox2,checkBox3,checkBox4,checkBox5;
    public TextView test_view,teacher_name,add_more;
    public EditText date,student_id,student_id_test,student_id2,student_id3,student_id4,student_id5;
    public Calendar mcurrentdate;
    String[] student;
    List<String> list = new ArrayList<>();
    public int day,month,year;
    Button list_view,find,add_to_list;
    private LinearLayout parentLinearLayout, idview;
    private RelativeLayout addbuttonview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad_hoc_test);
        parentLinearLayout = (LinearLayout) findViewById(R.id.view1);
        idview = (LinearLayout) findViewById(R.id.view);
        addbuttonview = (RelativeLayout) findViewById(R.id.buttom);

        VariableClass variable = VariableClass.getInstance();
        setTitle(" CAS - "+ variable.getDomain());
        td_code = variable.getTd_code();

        course_code = (Spinner) findViewById(R.id.Course_code);
        program = (Spinner) findViewById(R.id.program);
        intake = (Spinner) findViewById(R.id.Intake);
        sec = (Spinner) findViewById(R.id.section);

        student_id = (EditText) findViewById(R.id.std_id);
        checkBox1 = (CheckBox) findViewById(R.id.checkbox2);
        student_id2 = (EditText) findViewById(R.id.std_id1);
        checkBox2 = (CheckBox) findViewById(R.id.checkbox3);
        student_id3 = (EditText) findViewById(R.id.std_id2);
        checkBox3 = (CheckBox) findViewById(R.id.checkbox4);
        student_id4 = (EditText) findViewById(R.id.std_id3);
        checkBox4 = (CheckBox) findViewById(R.id.checkbox5);
        student_id5 = (EditText) findViewById(R.id.std_id4);
        checkBox5 = (CheckBox) findViewById(R.id.checkbox6);




        //getstudentalldata();

        List<String> student_list = new ArrayList<>();
        //variable.getStudent_id_add_hoc_list().clear();
        add_to_list = (Button) findViewById(R.id.add_to_list);
        add_to_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkBox1.isChecked()==true)
                {
                    String std_ID = student_id.getText().toString();
                    if ((!student_id.getText().toString().equals( student_id2.getText().toString())) && (!student_id.getText().toString().equals( student_id3.getText().toString())) && (!student_id.getText().toString().equals( student_id4.getText().toString())) && (!student_id.getText().toString().equals( student_id5.getText().toString()))){

                        student_list.add(std_ID);
                        //VariableClass.setStudent_id_add_hoc_list(student_list);
                        if (checkBox2.isChecked() == true) {
                            String std_ID1 = student_id2.getText().toString();
                            if ((!student_id2.getText().toString().equals( student_id.getText().toString())) && (!student_id2.getText().toString().equals( student_id3.getText().toString())) && (!student_id2.getText().toString().equals( student_id4.getText().toString())) && (!student_id2.getText().toString().equals( student_id5.getText().toString())))
                            {
                                student_list.add(std_ID1);
                                //VariableClass.setStudent_id_add_hoc_list(student_list);
                                if (checkBox3.isChecked() == true) {
                                    String std_ID2 = student_id3.getText().toString();
                                    if ((!student_id3.getText().toString().equals( student_id.getText().toString())) && (!student_id3.getText().toString().equals( student_id2.getText().toString())) && (!student_id3.getText().toString().equals( student_id4.getText().toString())) && (!student_id3.getText().toString().equals( student_id5.getText().toString()))) {
                                        student_list.add(std_ID2);
                                        //VariableClass.setStudent_id_add_hoc_list(student_list);
                                        if (checkBox4.isChecked() == true) {
                                            String std_ID3 = student_id4.getText().toString();
                                            if ((!student_id4.getText().toString().equals( student_id.getText().toString())) && (!student_id4.getText().toString().equals( student_id3.getText().toString())) && (!student_id4.getText().toString().equals( student_id2.getText().toString())) && (!student_id4.getText().toString().equals( student_id5.getText().toString()))) {
                                                student_list.add(std_ID3);
                                                //VariableClass.setStudent_id_add_hoc_list(student_list);
                                                if (checkBox5.isChecked() == true) {
                                                    String std_ID4 = student_id5.getText().toString();
                                                    if ((!student_id5.getText().toString().equals( student_id.getText().toString())) && (!student_id5.getText().toString().equals( student_id3.getText().toString())) && (!student_id5.getText().toString().equals( student_id4.getText().toString())) && (!student_id5.getText().toString().equals( student_id2.getText().toString()))) {
                                                        student_list.add(std_ID4);
                                                        //VariableClass.setStudent_id_add_hoc_list(student_list);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                else if (checkBox4.isChecked() == true) {
                                    String std_ID3 = student_id4.getText().toString();
                                    if ((!student_id4.getText().toString().equals( student_id.getText().toString())) && (!student_id4.getText().toString().equals( student_id3.getText().toString())) && (!student_id4.getText().toString().equals( student_id2.getText().toString())) && (!student_id4.getText().toString().equals( student_id5.getText().toString()))) {
                                        student_list.add(std_ID3);
                                        //VariableClass.setStudent_id_add_hoc_list(student_list);
                                        if (checkBox5.isChecked() == true) {
                                            String std_ID4 = student_id5.getText().toString();
                                            if ((!student_id5.getText().toString().equals( student_id.getText().toString())) && (!student_id5.getText().toString().equals( student_id3.getText().toString())) && (!student_id5.getText().toString().equals( student_id4.getText().toString())) && (!student_id5.getText().toString().equals( student_id2.getText().toString()))) {
                                                student_list.add(std_ID4);
                                                //VariableClass.setStudent_id_add_hoc_list(student_list);
                                            }
                                        }
                                    }
                                }
                                else if (checkBox5.isChecked() == true) {
                                    String std_ID4 = student_id5.getText().toString();
                                    if ((!student_id5.getText().toString().equals( student_id.getText().toString())) && (!student_id5.getText().toString().equals( student_id3.getText().toString())) && (!student_id5.getText().toString().equals( student_id4.getText().toString())) && (!student_id5.getText().toString().equals( student_id2.getText().toString()))) {
                                        student_list.add(std_ID4);
                                        //VariableClass.setStudent_id_add_hoc_list(student_list);
                                    }
                                }
                            }

                        } else if (checkBox3.isChecked() == true) {
                            String std_ID2 = student_id3.getText().toString();
                            if ((!student_id3.getText().toString().equals( student_id.getText().toString())) && (!student_id3.getText().toString().equals( student_id2.getText().toString())) && (!student_id3.getText().toString().equals( student_id4.getText().toString())) && (!student_id3.getText().toString().equals( student_id5.getText().toString()))) {
                                student_list.add(std_ID2);
                                //VariableClass.setStudent_id_add_hoc_list(student_list);
                                if (checkBox4.isChecked() == true) {
                                    String std_ID3 = student_id4.getText().toString();
                                    if ((!student_id4.getText().toString().equals( student_id.getText().toString())) && (!student_id4.getText().toString().equals( student_id3.getText().toString())) && (!student_id4.getText().toString().equals( student_id2.getText().toString())) && (!student_id4.getText().toString().equals( student_id5.getText().toString()))) {
                                        student_list.add(std_ID3);
                                        //VariableClass.setStudent_id_add_hoc_list(student_list);
                                        if (checkBox5.isChecked() == true) {
                                            String std_ID4 = student_id5.getText().toString();
                                            if ((!student_id5.getText().toString().equals( student_id.getText().toString())) && (!student_id5.getText().toString().equals( student_id3.getText().toString())) && (!student_id5.getText().toString().equals( student_id4.getText().toString())) && (!student_id5.getText().toString().equals( student_id2.getText().toString()))) {
                                                student_list.add(std_ID4);
                                                //VariableClass.setStudent_id_add_hoc_list(student_list);
                                            }

                                        }
                                    }

                                } else if (checkBox5.isChecked() == true) {
                                    String std_ID4 = student_id5.getText().toString();
                                    if ((!student_id5.getText().toString().equals( student_id.getText().toString())) && (!student_id5.getText().toString().equals( student_id3.getText().toString())) && (!student_id5.getText().toString().equals( student_id4.getText().toString())) && (!student_id5.getText().toString().equals( student_id2.getText().toString()))) {
                                        student_list.add(std_ID4);
                                        //VariableClass.setStudent_id_add_hoc_list(student_list);
                                    }
                                }
                            }
                        } else if (checkBox4.isChecked() == true) {
                            String std_ID3 = student_id4.getText().toString();
                            if ((!student_id4.getText().toString().equals( student_id.getText().toString())) && (!student_id4.getText().toString().equals( student_id3.getText().toString())) && (!student_id4.getText().toString().equals( student_id2.getText().toString())) && (!student_id4.getText().toString().equals( student_id5.getText().toString()))) {
                                student_list.add(std_ID3);
                                //VariableClass.setStudent_id_add_hoc_list(student_list);
                                if (checkBox5.isChecked() == true) {
                                    String std_ID4 = student_id5.getText().toString();
                                    if ((!student_id5.getText().toString().equals( student_id.getText().toString())) && (!student_id5.getText().toString().equals( student_id3.getText().toString())) && (!student_id5.getText().toString().equals( student_id4.getText().toString())) && (!student_id5.getText().toString().equals( student_id2.getText().toString()))) {
                                        student_list.add(std_ID4);
                                        //VariableClass.setStudent_id_add_hoc_list(student_list);
                                    }
                                }
                            }
                        } else if (checkBox5.isChecked() == true) {
                            String std_ID4 = student_id5.getText().toString();
                            if ((!student_id5.getText().toString().equals( student_id.getText().toString())) && (!student_id5.getText().toString().equals( student_id3.getText().toString())) && (!student_id5.getText().toString().equals( student_id4.getText().toString())) && (!student_id5.getText().toString().equals( student_id2.getText().toString()))) {
                                student_list.add(std_ID4);
                                //VariableClass.setStudent_id_add_hoc_list(student_list);
                            }
                        }
                    }
                    int lenth = list.size();
                    int lenth2 = student_list.size();
                    Log.e("lenth: ", String.valueOf(lenth));
                    Log.e("lenth2: ", String.valueOf(lenth2));
                    if (lenth2 > 0){
                        try {
                            for ( int i = 0; i<lenth2; i++){
                                for (int j=0; j<lenth; j++){
                                    Log.e("id: ", String.valueOf(student_list.get(i)));
                                    Log.e("id2: ", String.valueOf(list.get(j)));
                                    if (list.get(j).equals(student_list.get(i))){
                                        Toast.makeText(getApplicationContext(), "Dublicate ID "+student_list.get(i)+" Need to remove", Toast.LENGTH_SHORT).show();
                                        student_list.clear();
                                        break;
                                    }
                                }

                            }

                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }

                        if (student_list.size() == lenth2){
                            Toast.makeText(getApplicationContext(), "Student list create successfully", Toast.LENGTH_SHORT).show();
                        }
                    }

                    else {
                        Toast.makeText(getApplicationContext(), "Duplicate Found", Toast.LENGTH_SHORT).show();
                    }
                }
                else if (checkBox2.isChecked()==true) {
                    String std_ID1 = student_id2.getText().toString();
                    if ((!student_id2.getText().toString().equals( student_id.getText().toString())) && (!student_id2.getText().toString().equals( student_id3.getText().toString())) && (!student_id2.getText().toString().equals( student_id4.getText().toString())) && (!student_id2.getText().toString().equals( student_id5.getText().toString())))
                    {
                        student_list.add(std_ID1);
                        //VariableClass.setStudent_id_add_hoc_list(student_list);
                        if (checkBox3.isChecked() == true) {
                            String std_ID2 = student_id3.getText().toString();
                            if ((!student_id3.getText().toString().equals( student_id.getText().toString())) && (!student_id3.getText().toString().equals( student_id2.getText().toString())) && (!student_id3.getText().toString().equals( student_id4.getText().toString())) && (!student_id3.getText().toString().equals( student_id5.getText().toString()))) {
                                student_list.add(std_ID2);
                                //VariableClass.setStudent_id_add_hoc_list(student_list);
                                if (checkBox4.isChecked() == true) {
                                    String std_ID3 = student_id4.getText().toString();
                                    if ((!student_id4.getText().toString().equals( student_id.getText().toString())) && (!student_id4.getText().toString().equals( student_id3.getText().toString())) && (!student_id4.getText().toString().equals( student_id2.getText().toString())) && (!student_id4.getText().toString().equals( student_id5.getText().toString()))) {
                                        student_list.add(std_ID3);
                                        //VariableClass.setStudent_id_add_hoc_list(student_list);
                                        if (checkBox5.isChecked() == true) {
                                            String std_ID4 = student_id5.getText().toString();
                                            if ((!student_id5.getText().toString().equals( student_id.getText().toString())) && (!student_id5.getText().toString().equals( student_id3.getText().toString())) && (!student_id5.getText().toString().equals( student_id4.getText().toString())) && (!student_id5.getText().toString().equals( student_id2.getText().toString()))) {
                                                student_list.add(std_ID4);
                                                //VariableClass.setStudent_id_add_hoc_list(student_list);
                                            }
                                        }
                                    }
                                } else if (checkBox5.isChecked() == true) {
                                    String std_ID4 = student_id5.getText().toString();
                                    if ((!student_id5.getText().toString().equals( student_id.getText().toString())) && (!student_id5.getText().toString().equals( student_id3.getText().toString())) && (!student_id5.getText().toString().equals( student_id4.getText().toString())) && (!student_id5.getText().toString().equals( student_id2.getText().toString()))) {
                                        student_list.add(std_ID4);
                                        //VariableClass.setStudent_id_add_hoc_list(student_list);
                                    }
                                }
                            }
                        }
                        else if (checkBox4.isChecked() == true) {
                            String std_ID3 = student_id4.getText().toString();
                            if ((!student_id4.getText().toString().equals( student_id.getText().toString())) && (!student_id4.getText().toString().equals( student_id3.getText().toString())) && (!student_id4.getText().toString().equals( student_id2.getText().toString())) && (!student_id4.getText().toString().equals( student_id5.getText().toString()))) {
                                student_list.add(std_ID3);
                                //VariableClass.setStudent_id_add_hoc_list(student_list);
                                if (checkBox5.isChecked() == true) {
                                    String std_ID4 = student_id5.getText().toString();
                                    if ((!student_id5.getText().toString().equals( student_id.getText().toString())) && (!student_id5.getText().toString().equals( student_id3.getText().toString())) && (!student_id5.getText().toString().equals( student_id4.getText().toString())) && (!student_id5.getText().toString().equals( student_id2.getText().toString()))) {
                                        student_list.add(std_ID4);
                                        //VariableClass.setStudent_id_add_hoc_list(student_list);
                                    }
                                }
                            }
                        }
                        else if (checkBox5.isChecked() == true) {
                            String std_ID4 = student_id5.getText().toString();
                            if ((!student_id5.getText().toString().equals( student_id.getText().toString())) && (!student_id5.getText().toString().equals( student_id3.getText().toString())) && (!student_id5.getText().toString().equals( student_id4.getText().toString())) && (!student_id5.getText().toString().equals( student_id2.getText().toString()))) {
                                student_list.add(std_ID4);
                                //VariableClass.setStudent_id_add_hoc_list(student_list);
                            }
                        }

                    }
                    int lenth = list.size();
                    int lenth2 = student_list.size();
                    Log.e("lenth: ", String.valueOf(lenth));
                    Log.e("lenth2: ", String.valueOf(lenth2));
                    if (lenth2 > 0){
                        try {
                            for ( int i = 0; i<lenth2; i++){
                                for (int j=0; j<lenth; j++){
                                    Log.e("id: ", String.valueOf(student_list.get(i)));
                                    Log.e("id2: ", String.valueOf(list.get(j)));
                                    if (list.get(j).equals(student_list.get(i))){
                                        Toast.makeText(getApplicationContext(), "Dublicate ID "+student_list.get(i)+" Need to remove", Toast.LENGTH_SHORT).show();
                                        student_list.clear();
                                        break;
                                    }
                                }

                            }

                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }

                        if (student_list.size() == lenth2){
                            Toast.makeText(getApplicationContext(), "Student list create successfully", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Duplicate Found", Toast.LENGTH_SHORT).show();
                    }
                }
                else if (checkBox3.isChecked() == true) {
                    String std_ID2 = student_id3.getText().toString();
                    if ((!student_id3.getText().toString().equals( student_id.getText().toString())) && (!student_id3.getText().toString().equals( student_id2.getText().toString())) && (!student_id3.getText().toString().equals( student_id4.getText().toString())) && (!student_id3.getText().toString().equals( student_id5.getText().toString()))) {
                        student_list.add(std_ID2);
                        //VariableClass.setStudent_id_add_hoc_list(student_list);
                        if (checkBox4.isChecked() == true) {
                            String std_ID3 = student_id4.getText().toString();
                            if ((!student_id4.getText().toString().equals( student_id.getText().toString())) && (!student_id4.getText().toString().equals( student_id3.getText().toString())) && (!student_id4.getText().toString().equals( student_id2.getText().toString())) && (!student_id4.getText().toString().equals( student_id5.getText().toString()))) {
                                student_list.add(std_ID3);
                                //VariableClass.setStudent_id_add_hoc_list(student_list);
                                if (checkBox5.isChecked() == true) {
                                    String std_ID4 = student_id5.getText().toString();
                                    if ((!student_id5.getText().toString().equals( student_id.getText().toString())) && (!student_id5.getText().toString().equals( student_id3.getText().toString())) && (!student_id5.getText().toString().equals( student_id4.getText().toString())) && (!student_id5.getText().toString().equals( student_id2.getText().toString()))) {
                                        student_list.add(std_ID4);
                                        //VariableClass.setStudent_id_add_hoc_list(student_list);
                                    }
                                }
                            }
                        } else if (checkBox5.isChecked() == true) {
                            String std_ID4 = student_id5.getText().toString();
                            if ((!student_id5.getText().toString().equals( student_id.getText().toString())) && (!student_id5.getText().toString().equals( student_id3.getText().toString())) && (!student_id5.getText().toString().equals( student_id4.getText().toString())) && (!student_id5.getText().toString().equals( student_id2.getText().toString()))) {
                                student_list.add(std_ID4);
                                //VariableClass.setStudent_id_add_hoc_list(student_list);
                            }
                        }

                    }
                    int lenth = list.size();
                    int lenth2 = student_list.size();
                    Log.e("lenth: ", String.valueOf(lenth));
                    Log.e("lenth2: ", String.valueOf(lenth2));
                    if (lenth2 > 0){
                        try {
                            for ( int i = 0; i<lenth2; i++){
                                for (int j=0; j<lenth; j++){
                                    Log.e("id: ", String.valueOf(student_list.get(i)));
                                    Log.e("id2: ", String.valueOf(list.get(j)));
                                    if (list.get(j).equals(student_list.get(i))){
                                        Toast.makeText(getApplicationContext(), "Dublicate ID "+student_list.get(i)+" Need to remove", Toast.LENGTH_SHORT).show();
                                        student_list.clear();
                                        break;
                                    }
                                }

                            }

                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }

                        if (student_list.size() == lenth2){
                            Toast.makeText(getApplicationContext(), "Student list create successfully", Toast.LENGTH_SHORT).show();
                        }
                    }

                    else{
                        Toast.makeText(getApplicationContext(), "Duplicate Found", Toast.LENGTH_SHORT).show();
                    }

                }
                else if (checkBox4.isChecked() == true) {
                    String std_ID3 = student_id4.getText().toString();
                    if ((!student_id4.getText().toString().equals( student_id.getText().toString())) && (!student_id4.getText().toString().equals( student_id3.getText().toString())) && (!student_id4.getText().toString().equals( student_id2.getText().toString())) && (!student_id4.getText().toString().equals( student_id5.getText().toString()))) {
                        student_list.add(std_ID3);
                        //VariableClass.setStudent_id_add_hoc_list(student_list);
                        if (checkBox5.isChecked() == true) {
                            String std_ID4 = student_id5.getText().toString();
                            if ((!student_id5.getText().toString().equals( student_id.getText().toString())) && (!student_id5.getText().toString().equals( student_id3.getText().toString())) && (!student_id5.getText().toString().equals( student_id4.getText().toString())) && (!student_id5.getText().toString().equals( student_id2.getText().toString()))) {
                                student_list.add(std_ID4);
                                //VariableClass.setStudent_id_add_hoc_list(student_list);
                            }
                        }
                    }
                    int lenth = list.size();
                    int lenth2 = student_list.size();
                    Log.e("lenth: ", String.valueOf(lenth));
                    Log.e("lenth2: ", String.valueOf(lenth2));
                    if (lenth2 > 0){
                        try {
                            for ( int i = 0; i<lenth2; i++){
                                for (int j=0; j<lenth; j++){
                                    Log.e("id: ", String.valueOf(student_list.get(i)));
                                    Log.e("id2: ", String.valueOf(list.get(j)));
                                    if (list.get(j).equals(student_list.get(i))){
                                        Toast.makeText(getApplicationContext(), "Dublicate ID "+student_list.get(i)+" Need to remove", Toast.LENGTH_SHORT).show();
                                        student_list.clear();
                                        break;
                                    }
                                }

                            }

                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }

                        if (student_list.size() == lenth2){
                            Toast.makeText(getApplicationContext(), "Student list create successfully", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Duplicate Found", Toast.LENGTH_SHORT).show();
                    }

                }
                else if (checkBox5.isChecked() == true) {
                    String std_ID4 = student_id5.getText().toString();
                    if ((!student_id5.getText().toString().equals( student_id.getText().toString())) && (!student_id5.getText().toString().equals( student_id3.getText().toString())) && (!student_id5.getText().toString().equals( student_id4.getText().toString())) && (!student_id5.getText().toString().equals( student_id2.getText().toString()))) {
                        student_list.add(std_ID4);
                        //Toast.makeText(getApplicationContext(),"Student list create successfully",Toast.LENGTH_SHORT).show();
                        //VariableClass.setStudent_id_add_hoc_list(student_list);
                    }
                    int lenth = list.size();
                    int lenth2 = student_list.size();
                    Log.e("lenth: ", String.valueOf(lenth));
                    Log.e("lenth2: ", String.valueOf(lenth2));
                    if (lenth2 > 0){
                        try {
                            for ( int i = 0; i<lenth2; i++){
                                for (int j=0; j<lenth; j++){
                                    Log.e("id: ", String.valueOf(student_list.get(i)));
                                    Log.e("id2: ", String.valueOf(list.get(j)));
                                    if (list.get(j).equals(student_list.get(i))){
                                        Toast.makeText(getApplicationContext(), "Dublicate ID "+student_list.get(i)+" Need to remove", Toast.LENGTH_SHORT).show();
                                        student_list.clear();
                                        break;
                                    }
                                }

                            }

                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }

                        if (student_list.size() == lenth2){
                            Toast.makeText(getApplicationContext(), "Student list create successfully", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Duplicate Found", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(),"Ooops, you are selecting dumy id. Please! Insert real Student ID...",Toast.LENGTH_SHORT).show();
                }

                int listSize = student_list.size();
                //variable.getStudent_id_add_hoc_list().clear();
                variable.setStudent_id_add_hoc_list(student_list);

                for (int i = 0; i<listSize; i++){
                    Log.e("response: ", student_list.get(i) );

                }

            }
        });

        find = (Button) findViewById(R.id.Find);
        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                idview.setVisibility(View.VISIBLE);
                addbuttonview.setVisibility(View.VISIBLE);
            }

        });

        list_view = findViewById(R.id.list_view);
        list_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (student_list.size()!= 0)
                {
                    Intent Intent = new Intent(getApplicationContext(),ad_hoc_list.class);
                    startActivity(Intent);
                }
                else{
                    Toast.makeText(getApplicationContext(),"Please! add student first.",Toast.LENGTH_SHORT).show();
                }

            }
        });

//        add_more = findViewById(R.id.add_more);
//        add_more = (TextView) findViewById(R.id.add_more);
//        add_more.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                final View rowView = inflater.inflate(R.layout.add_student_field, null);
//                TextView std_id = rowView.findViewById(R.id.std_id_add);
//                VariableClass variable = VariableClass.getInstance();
//                std_id.setText(variable.getStudent_Id());
//                // Add the new row before the add field button.
//                parentLinearLayout.addView(rowView, parentLinearLayout.getChildCount() - 1);
//
//            }
//        });






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
                DatePickerDialog datePickerDialog = new DatePickerDialog(com.example.student.ad_hoc_test.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month + 1;
                        date.setText(dayOfMonth + "/" + month + "/" + year);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });*/


        course_code.setEnabled(false);
        intake.setEnabled(false);
        sec.setEnabled(false);

        //getotpdata();
        //getMovieData();
        getProgramData();


        program.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                selectedProgram = programList.get(position);

                course_code_list.clear();

                //getOtp();

                getCoursedata(selectedProgram);
                student_list.clear();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        course_code.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                selectedCourse_code = course_code_list.get(position);

                intakeList.clear();
                getintakedata(selectedCourse_code,selectedProgram);
                student_list.clear();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        intake.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                selectedIntake = intakeList.get(position);
                sectionlist.clear();
                getsectiondata(selectedIntake,selectedProgram,selectedCourse_code);
                student_list.clear();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        sec.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                selectedSection = sectionlist.get(position);
                getstudentdata(selectedCourse_code,selectedIntake,selectedSection,selectedProgram);
                student_list.clear();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    private void getProgramData() {
        //final String OTP = Otp.getText().toString();
        //test.setText(otp);
        final String code = "pro";

        StringRequest request1 = new StringRequest(Request.Method.POST, "http://192.168.0.20/android_project/All.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray array = jsonObject.getJSONArray("data");
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject ob = array.getJSONObject(i);
                        programList.add(ob.getString("DEPT"));

                        //std_Course = Arrays.toString(programList.toArray());
                        //getCoursedata(otp,std_Course);

                    }
                    loadProgramData();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("teacher_code", td_code);
                params.put("code", code);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request1);
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
    private void getotpdata() {

        StringRequest request1 = new StringRequest(Request.Method.POST,"http://192.168.0.20/android_project/login2.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    JSONArray array=jsonObject.getJSONArray("data");
                    //Intent intent = new Intent(ad_hoc_test.this,ad_hoc_test.class);
                    for (int i = 0; i<1 ; i++) {
                        JSONObject ob = array.getJSONObject(i);
                        otp = ob.getString("OTP");
                       // VariableClass VariableClass = new VariableClass();
                        //VariableClass.OTP = otp;
                        
                        getProgramData();



                        //intent.putExtra("otp", otp);

                    }
                    //startActivity(intent);
                    //finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("password", password);
                return params;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(request1);
    }
    private void getCoursedata(String program) {
        //final String OTP = Otp.getText().toString();
        //test.setText(otp);
        final String code = "course";

        StringRequest request1 = new StringRequest(Request.Method.POST, "http://192.168.0.20/android_project/All.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray array = jsonObject.getJSONArray("data");
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject ob = array.getJSONObject(i);
                        course_code_list.add(ob.getString("Course_Code"));

                    }
                    loadCourse_code();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("teacher_code", td_code);
                params.put("program", program);
                params.put("code", code);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request1);
    }
    private void getintakedata(String course_code,String Program) {
        //final String OTP = Otp.getText().toString();
        //test.setText(otp);
        final String code = "intake";

        StringRequest request1 = new StringRequest(Request.Method.POST, "http://192.168.0.20/android_project/All.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray array = jsonObject.getJSONArray("data");
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject ob = array.getJSONObject(i);
                        intakeList.add(ob.getString("intake"));

                    }
                    loadintakedata();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("course_code", course_code);
                params.put("program", Program);
                params.put("teacher_code", td_code);
                params.put("code", code);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request1);
    }
    private void getsectiondata(String intake,String Program,String course_code) {
        //final String OTP = Otp.getText().toString();
        //test.setText(otp);
        final String code = "sec";

        StringRequest request1 = new StringRequest(Request.Method.POST, "http://192.168.0.20/android_project/All.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray array = jsonObject.getJSONArray("data");
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject ob = array.getJSONObject(i);
                        sectionlist.add(ob.getString("section"));

                    }
                    loadsection();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("intake", intake);
                params.put("course_code", course_code);
                params.put("program", Program);
                params.put("teacher_code", td_code);
                params.put("code", code);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request1);
    }
    private void getstudentdata(String course_code,String intake,String section,String program) {

        VariableClass variable = VariableClass.getInstance();
        String ad_hoc = getResources().getString(R.string.ad_hoc);
        final String code = "adhoc_id";

        StringRequest request = new StringRequest(Request.Method.POST, "http://192.168.0.20/android_project/All.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                variable.getStudentId().clear();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    //List<String> list = new ArrayList<>();
                    JSONArray array = jsonObject.getJSONArray("data");
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject ob = array.getJSONObject(i);
                        std_id = ob.getString("std_id");
                        list.add(std_id);
                        variable.setStudent_Id(std_id);
                        Student_details student_details = Student_details.getInstance();
                        student_details.setDEPT(program);
                        student_details.setProgram(course_code);
                        student_details.setIntake(intake);
                        student_details.setSection(section);
                        variable.setStudentId(list);


                    }
                    int length = variable.getStudent_Id().length();
                    Log.e( "length: ", String.valueOf(length));
                    int length2 = list.size();
                    Log.e( "length2: ", String.valueOf(length2));
                    student_id.setText(list.get(1));
                    student_id2.setText(list.get(1));
                    student_id3.setText(list.get(1));
                    student_id4.setText(list.get(1));
                    student_id5.setText(list.get(1));
                    Log.e( "onResponse: ", String.valueOf(variable.getStudentId()));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("data_view", ad_hoc);
                params.put("intake", intake);
                params.put("course_code", course_code);
                params.put("section", section);
                params.put("code", code);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
    public void onDelete(View v) {
        parentLinearLayout.removeView((View) v.getParent());
    }


    private void getstudentalldata() {
        Student_details student_details = Student_details.getInstance();
        String Course_Code = student_details.getProgram();
        String Intake = student_details.getIntake();
        String Section = student_details.getSection();
        VariableClass variable = VariableClass.getInstance();
        final List<String> stdId = new ArrayList<>();
        variable.getStudentId().clear();
        StringRequest request = new StringRequest(Request.Method.POST, "http://192.168.0.20/android_project/getstudentid_all.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray array = jsonObject.getJSONArray("data");
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject ob = array.getJSONObject(i);
                        String student = ob.getString("std_id");
                        stdId.add(student);
                    }
                   // variable.setStudentId((ArrayList<String>) stdId);
                    Log.e("onResponse: ", String.valueOf(stdId));


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("section", Section);
                params.put("course_code", Course_Code);
                params.put("intake", Intake);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId()==R.id.SignOutMenuId)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(ad_hoc_test.this);
            builder.setTitle(R.string.app_name);
            builder.setIcon(R.mipmap.ic_launcher);
            builder.setMessage("Do you want to SignOut?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent in = new Intent(ad_hoc_test.this,Login.class);
                            in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(in);
                            finish();
//                            Intent intent = new Intent(ad_hoc_test.this, Login.class);
//                            Intent Intent = new Intent(android.content.Intent.ACTION_MAIN);
//                            startActivity(intent);
//                            finish();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }
        if (item.getItemId()==R.id.Teacher_profile)
        {

            Intent intent = new Intent(ad_hoc_test.this, teacher_Profile.class);
            startActivity(intent);
        }

        if (item.getItemId()==R.id.DashboardId)
        {
            Intent Intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(Intent);
        }
        if (item.getItemId()==R.id.About)
        {

            Intent intent = new Intent(ad_hoc_test.this, About.class);
            startActivity(intent);
        }

        if (item.getItemId()==R.id.exit)
        {

            AlertDialog.Builder builder = new AlertDialog.Builder(ad_hoc_test.this);
            builder.setTitle(R.string.app_name);
            builder.setIcon(R.mipmap.ic_launcher);
            builder.setMessage("Do you want to exit?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent Intent = new Intent(android.content.Intent.ACTION_MAIN);
                            Intent.addCategory(Intent.CATEGORY_HOME);
                            Intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(Intent);
                            finish();
                            System.exit(0);
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }

        return super.onOptionsItemSelected(item);

    }


}