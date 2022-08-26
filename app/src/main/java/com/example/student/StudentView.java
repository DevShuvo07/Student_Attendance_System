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
import android.widget.EditText;
import android.widget.Spinner;

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

public class StudentView extends BaseActivity {
    EditText date;
    Calendar mcurrentdate;
    int day,month,year;
    Spinner program , course_code , intake, sec;
    String[] Intake , Course_code , Program, Sec;
    private boolean isfirstselection = true;
    private Button find;
    String selectedProgram, selectedIntake, selectedCourse_code, selectedSection, total_attend;
    public com.example.student.VariableClass VariableClass;
    int std_total_class;
    private ArrayList<String> students;
    java.util.List<String> programList = new ArrayList<>();
    java.util.List<String> intakeList = new ArrayList<>();
    java.util.List<String> sectionlist = new ArrayList<>();
    java.util.List<String> course_code_list = new ArrayList<>();
    List<String> attend_std_total = new ArrayList<>();
    public String username,password,otp,td_code,std_id,std_name,std_attendance,CR,role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        VariableClass variable = VariableClass.getInstance();
        setTitle(" CAS - "+ variable.getDomain());
        td_code = variable.getTd_code();

        find = (Button) findViewById(R.id.Find);
        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                total_class(selectedCourse_code,selectedIntake,selectedSection,selectedProgram);
                getstudentdata(selectedCourse_code,selectedIntake,selectedSection);
                Intent Intent = new Intent(getApplicationContext(),View_student.class);
                startActivity(Intent);
            }
        });

        program = (Spinner) findViewById(R.id.program);
        intake = (Spinner) findViewById(R.id.Intake);
        sec = (Spinner) findViewById(R.id.section);
        course_code = (Spinner) findViewById(R.id.Course_code);

        /*date = (EditText) findViewById(R.id.Date);
        mcurrentdate = Calendar.getInstance();
        day = mcurrentdate.get(Calendar.DAY_OF_MONTH);
        month = mcurrentdate.get(Calendar.MONTH);
        year = mcurrentdate.get(Calendar.YEAR);
        month = month +1;
        date.setText(day+"/"+month+"/"+year);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(StudentView.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month + 1;
                        date.setText(dayOfMonth+"/"+month+"/"+year);
                    }
                },year, month, day);
                datePickerDialog.show();
            }
        });*/

        course_code.setEnabled(false);
        intake.setEnabled(false);
        sec.setEnabled(false);
        getProgramData();

        program.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                selectedProgram = programList.get(position);

                course_code_list.clear();

                //getOtp();

                getCoursedata(selectedProgram);

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
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        sec.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                selectedSection = sectionlist.get(position);
                total_class(selectedCourse_code,selectedIntake,selectedSection,selectedProgram);
                getstudentdata(selectedCourse_code,selectedIntake,selectedSection);
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
    private void getCoursedata(String program) {
        //final String OTP = Otp.getText().toString();
        //test.setText(otp);
        Student_details student_details=Student_details.getInstance();
        student_details.setDEPT(program);
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
    private void getstudentdata(String course_code,String intake,String section) {
        List<String> STD_ID = new ArrayList<>();
        List<String> STD_NAME = new ArrayList<>();
        List<String> Attend = new ArrayList<>();
        List<String> STD_CR = new ArrayList<>();
        String list_view = getResources().getString(R.string.list_view);
        VariableClass variable = VariableClass.getInstance();
        final String code = "student_data";


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
                        std_name = ob.getString("std_name");
                        std_attendance = ob.getString("std_atndnc");
                        Log.e("std_attendance: ", std_attendance);
                        //int std_attn = Integer.valueOf(total_attend);
                        //int attn_lenth = std_attendance.length();
                        //Log.e("attn_lenth: ", String.valueOf(attn_lenth));
                        Log.e("std_total_class: ", String.valueOf(std_total_class));
                        //float attn = ((attn_lenth/2)*100)/std_total_class;
                        //Log.d( "round: ", String.valueOf(attn));
                        //int atn_total = (int) Math.round(attn);
                        String Attendance = String.valueOf(std_attendance);
                        //student_id.setText(std_id);
                        //list.add(std_id);
                        STD_ID.add(std_id);
                        STD_NAME.add(std_name);
                        Attend.add(Attendance);
                        Log.e("Attend: ", Attend.get(i));
                        STD_CR.add(CR);
                        variable.setSTD_ATTEND(Attend);
                        variable.setStudent_id(std_id);
                        variable.setStd_name(std_name);
                        Student_details student_details = Student_details.getInstance();
                        student_details.setProgram(course_code);
                        student_details.setIntake(intake);
                        student_details.setSection(section);
                        variable.setSTD_id(STD_ID);
                        variable.setSTD_name(STD_NAME);

                    }
                    Log.e( "onResponse: ", String.valueOf(variable.getStudentId()));
                    Log.e("response ", String.valueOf(variable.getSTD_id()));
                    Log.e("response ", String.valueOf(variable.getSTD_name()));
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
                params.put("data_view", list_view);
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
    private void total_class(String selectedCourse_code, String selectedIntake, String selectedSection, String Program) {
        VariableClass variableClass = VariableClass.getInstance();
        String teacher_code = variableClass.getTd_code();
        Log.e("td code:  ", teacher_code);
        String S_no = variableClass.getS_no();
        String code = "total_class";
        StringRequest request = new StringRequest(Request.Method.POST, "http://192.168.0.20/android_project/All.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.e("Response ", response);
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray array = jsonObject.getJSONArray("data");
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject ob = array.getJSONObject(i);
                        std_total_class = ob.getInt("Number_of_class");
                        VariableClass variableClass = com.example.student.VariableClass.getInstance();
                        variableClass.setAtten_total(std_total_class);
                        //std_total_class = Integer.valueOf(total_attend);
                        Log.e("onResponse: ", String.valueOf(std_total_class));
                        //Log.e("onResponse: ", String.valueOf(std_total_class));

                    }
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
                params.put("Teacher_Code", teacher_code);
                params.put("code",code);
                params.put("S_no", S_no);
                params.put("Dept", Program);
                params.put("course_code", selectedCourse_code);
                params.put("intake", selectedIntake);
                params.put("section", selectedSection);
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
            AlertDialog.Builder builder = new AlertDialog.Builder(StudentView.this);
            builder.setTitle(R.string.app_name);
            builder.setIcon(R.mipmap.ic_launcher);
            builder.setMessage("Do you want to SignOut?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent in = new Intent(StudentView.this,Login.class);
                            in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(in);
                            finish();
//                            Intent intent = new Intent(StudentView.this, Login.class);
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

            Intent intent = new Intent(StudentView.this, teacher_Profile.class);
            startActivity(intent);
        }
        if (item.getItemId()==R.id.About)
        {

            Intent intent = new Intent(StudentView.this, About.class);
            startActivity(intent);
        }

        if (item.getItemId()==R.id.DashboardId)
        {
            Intent Intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(Intent);
        }

        if (item.getItemId()==R.id.exit)
        {

            AlertDialog.Builder builder = new AlertDialog.Builder(StudentView.this);
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



























        /*Intake = getResources().getStringArray(R.array.Intake);
        Sec = getResources().getStringArray(R.array.Section);
        Program = getResources().getStringArray(R.array.Program);
        Course_code = getResources().getStringArray(R.array.Course_code);

        program = (Spinner) findViewById(R.id.program);
        intake = (Spinner) findViewById(R.id.Intake);
        sec = (Spinner) findViewById(R.id.section);
        course_code = (Spinner) findViewById(R.id.Course_code);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,R.layout.spinner_view,R.id.spinner_view,Program);
        program.setAdapter(adapter1);
        program.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (isfirstselection==true){
                    isfirstselection = false;
                }
                else {


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,R.layout.spinner_view,R.id.spinner_view,Course_code);
        course_code.setAdapter(adapter2);
        course_code.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (isfirstselection==true){
                    isfirstselection = false;
                }
                else {


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this,R.layout.spinner_view,R.id.spinner_view,Intake);
        intake.setAdapter(adapter3);
        intake.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (isfirstselection==true){
                    isfirstselection = false;
                }
                else {


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ArrayAdapter<String> adapter4 = new ArrayAdapter<String>(this,R.layout.spinner_view,R.id.spinner_view,Sec);
        sec.setAdapter(adapter4);
        sec.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (isfirstselection==true){
                    isfirstselection = false;
                }
                else {


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/



}
