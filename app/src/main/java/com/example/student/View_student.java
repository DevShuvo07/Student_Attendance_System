package com.example.student;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class View_student extends BaseActivity {


    EditText date;
    Calendar mcurrentdate;
    DatePickerDialog picker;
    int day_calender,monthofyear,yearall;
    String[] student,name,attend, STD_CR;
    private BottomSheetBehavior bottomSheetBehavior;
    private LinearLayout linearLayout;
    private ToggleButton up_down;
    private Button add, select_CR, remove_CR ;
    Spinner attend_day;
    private ProgressDialog progressDialog;
    String[] day;
    java.util.List<String> Student_Id = new ArrayList<>();
    List<String> total_student_attendance = new ArrayList();
    String std_id,std_name;
    private boolean isfirstselection = true;
    public String Attend_day,sum_of_attend,A1,A2;
    public char c,c1;
    String std_attendance;
    String std_attn_total,selected_student,realtime_date, CR,STD_NAme, check = String.valueOf(0), Old_attnd;
    int attn_class = 1, check_attn = 0;
    int count , total = 0,total_1 = 0, count1=0;


    String currentDateTimeString = DateFormat.getDateInstance().format(new Date());
    Date currentTime = new Date(System.currentTimeMillis());
    SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm aa", Locale.ENGLISH);
    String currentTimeString = dateFormat.format(currentTime);
//    Calendar calendar = Calendar.getInstance();
//    long current_Time = calendar.getTimeInMillis();
//    TimeZone current = calendar.getTimeZone();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_student);
        VariableClass variableClass = VariableClass.getInstance();

        total = variableClass.getSTD_id().size();
        total_1 = variableClass.getSTD_id().size();
        count = total;
        setTitle("List Of Student ( "+total+" / "+total_1+" )");

        select_CR = findViewById(R.id.CR);
        select_CR.setVisibility(View.VISIBLE);
        remove_CR = findViewById(R.id.remove_CR);
        remove_CR.setVisibility(View.VISIBLE);

        init();
        up_down.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

                }
                else{
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            }
        });
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View view, int newstate) {
                if (newstate == BottomSheetBehavior.STATE_EXPANDED){
                    up_down.setChecked(true);
                }
                else {
                    up_down.setChecked(false);
                }
            }

            @Override
            public void onSlide(@NonNull View view, float v) {

            }
        });

        date = (EditText) findViewById(R.id.Date);
        date.setInputType(InputType.TYPE_NULL);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cldr = Calendar.getInstance();
                int dayofmonth = cldr.get(Calendar.DAY_OF_MONTH);
                int month1 = cldr.get(Calendar.MONTH);
                int year1 = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(View_student.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int day) {
                                date.setText(day + "/" + (month + 1) + "/" + year);
                                //realtime_date = date.getText().toString();
                                day_calender = day;
                                monthofyear = month+1;
                                yearall = year;
                            }
                        }
                , year1, month1, dayofmonth);
                picker.show();


            }
        });
//        mcurrentdate = Calendar.getInstance();
//        day_calender = mcurrentdate.get(Calendar.DAY_OF_MONTH);
//        month = mcurrentdate.get(Calendar.MONTH);
//        year = mcurrentdate.get(Calendar.YEAR);
//        month = month + 1;
//        date.setText(day_calender + "/" + month + "/" + year);
//        date.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                DatePickerDialog datePickerDialog = new DatePickerDialog(com.example.student.View_student.this, new DatePickerDialog.OnDateSetListener() {
//                    @Override
//                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                        month = month + 1;
//                        date.setText(dayOfMonth + "/" + month + "/" + year);
//                    }
//                }, year, month, day_calender);
//                datePickerDialog.show();
//
//            }
//        });



//        add = (Button) findViewById(R.id.add_present);
//        add.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getApplicationContext(),"Attendance added successfully",Toast.LENGTH_SHORT).show();
//            }
//        });

        // Get listview checkbox.
        final ListView listViewWithCheckbox = (ListView)findViewById(R.id.View_student);

        // Initiate listview data.
        final List<DTO_STudent> initItemList = this.getInitViewItemDtoList();

        // Create a custom list view adapter with checkbox control.
        final List_View_student listViewDataAdapter = new List_View_student(initItemList,getApplicationContext());

        listViewDataAdapter.notifyDataSetChanged();

        // Set data adapter to list view.
        listViewWithCheckbox.setAdapter(listViewDataAdapter);

        // When list view item is clicked.
        listViewWithCheckbox.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int itemIndex, long l) {
                // Get user selected item.
                Object itemObject = adapterView.getAdapter().getItem(itemIndex);

                // Translate the selected item to DTO object.
                DTO_STudent itemDto = (DTO_STudent) itemObject;

                // Get the checkbox.
                CheckBox itemCheckbox = (CheckBox) view.findViewById(R.id.checkbox);

                // Reverse the checkbox and clicked item check state.
                if(itemDto.isChecked())
                {
                    count--;
                    setTitle("List Of Student ( "+count+" / "+total_1+" )");
                    itemCheckbox.setChecked(false);
                    itemDto.setChecked(false);
                }else
                {
                    count++;
                    setTitle("List Of Student ( "+count+" / "+total_1+" )");
                    itemCheckbox.setChecked(true);
                    itemDto.setChecked(true);
                }

                //Toast.makeText(getApplicationContext(), "select item text : " + itemDto.getItemText(), Toast.LENGTH_SHORT).show();
            }

        });

        day = getResources().getStringArray(R.array.presentday);
        attend_day = (Spinner) findViewById(R.id.day);
        CheckBox chk = findViewById(R.id.checbox_new_class);
        TextView refresh = findViewById(R.id.refresh);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, R.layout.spinner_view, R.id.spinner_view, day);
        attend_day.setAdapter(adapter2);
        attend_day.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (isfirstselection == true) {
                    attn_class = 0;
                    attn_class = Integer.parseInt(attend_day.getSelectedItem().toString());
                    //std_attn_day = "A1";
                    //c = std_attn_day.charAt(1);
                    Log.e("atten_class: ", String.valueOf(attn_class));
                } else {
                    Log.e( "onItemSelected: ", "nothing");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        chk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(chk.isChecked()==true){
                    total_class();
                    check_attn = attn_class;
                }
                else if (chk.isChecked()==false){
                    check = Old_attnd;
                    check_attn = 0;
                    Log.e("attendance ", check);
                }

            }
        });



        add = (Button) findViewById(R.id.add_present);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog = new AlertDialog.Builder(View_student.this).create();
                alertDialog.setMessage("Are you sure to add selected listview items?");

                alertDialog.setButton(Dialog.BUTTON_POSITIVE, "Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
//                        progressDialog.setMessage("Please Wait, We are Inserting Your Data on Server");
//                        progressDialog.show();
                        int size = variableClass.getSTD_id().size();
                        total = 0;
                        for(int i=0;i<size;i++)
                        {

                            DTO_STudent sTudent = initItemList.get(i);
                            if (sTudent.isChecked())
                            {

                                total++;
                                //initItemList.remove(i);
                                //Student_Id.add(variableClass.getSTD_id().get(i));
                                //getstudentdata(variableClass.getSTD_id().get(i));
                                attendance(day_calender, monthofyear, variableClass.getSTD_id().get(i));
                                Update_total_class();

                                //Log.e("ATTN_Class: ", variableClass.getSTD_ATTEND().get(i));
                                //add_student(std_attn_total,variableClass.getSTD_id().get(i));
                                Log.e( "onClick: ", variableClass.getSTD_id().get(i));

                                //add_student(variableClass.getSTD_id().get(i), 0);
                                Log.e( "onItemSelected: ", String.valueOf(size));
                                //arraylist.add(variableClass.getStudent_id_add_hoc_list().get(i));
                                //Log.e("onClick: ", String.valueOf(variableClass.getSTD_id().get(i)));
                                //deletestudentID(String.valueOf(initItemList.get(i).getItemText()));

                            }
                        }
                        Log.e("onClick: ", String.valueOf(attn_class));
                        Log.d( "onClick: ", String.valueOf(day_calender));
                        Log.d( "onClick: ", String.valueOf(monthofyear));
                        Log.d( "onClick: ", String.valueOf(yearall));
                        //attendance(day_calender,monthofyear,"A1A2");
                        setTitle("List Of Student ( "+total+" / 40 )");
                        listViewDataAdapter.notifyDataSetChanged();

                    }
                });

                alertDialog.show();
            }
        });
        select_CR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog = new AlertDialog.Builder(View_student.this).create();
                alertDialog.setMessage("Are you sure?");

                alertDialog.setButton(Dialog.BUTTON_POSITIVE, "Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
//                        progressDialog.setMessage("Please Wait, We are Inserting Your Data on Server");
//                        progressDialog.show();
                        int size = variableClass.getSTD_id().size();
                        total = 0;
                        for(int i=0;i<size;i++)
                        {

                            DTO_STudent sTudent = initItemList.get(i);
                            if (sTudent.isChecked())
                            {

                                total++;
                                //initItemList.remove(i);
                                //Student_Id.add(variableClass.getSTD_id().get(i));
                                //getCR(variableClass.getSTD_id().get(i));
                                updateCR(variableClass.getSTD_id().get(i));

                                //Log.e("ATTN_Class: ", variableClass.getSTD_ATTEND().get(i));
                                //add_student(std_attn_total,variableClass.getSTD_id().get(i));
                                Log.e( "onClick: ", variableClass.getSTD_id().get(i));

                            }
                        }
                        setTitle("List Of Student ( "+total+" / 40 )");
                        listViewDataAdapter.notifyDataSetChanged();

                    }
                });

                alertDialog.show();
            }
        });
        remove_CR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog = new AlertDialog.Builder(View_student.this).create();
                alertDialog.setMessage("Are you sure?");

                alertDialog.setButton(Dialog.BUTTON_POSITIVE, "Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
//                        progressDialog.setMessage("Please Wait, We are Inserting Your Data on Server");
//                        progressDialog.show();
                        int size = variableClass.getSTD_id().size();
                        total = 0;
                        for(int i=0;i<size;i++)
                        {

                            DTO_STudent sTudent = initItemList.get(i);
                            if (sTudent.isChecked())
                            {

                                total++;
                                //initItemList.remove(i);
                                //Student_Id.add(variableClass.getSTD_id().get(i));
                                //viewCR1(variableClass.getSTD_id().get(i));
                                RemoveCR(variableClass.getSTD_id().get(i));

                                //Log.e("ATTN_Class: ", variableClass.getSTD_ATTEND().get(i));
                                //add_student(std_attn_total,variableClass.getSTD_id().get(i));
                                Log.e( "onClick: ", variableClass.getSTD_id().get(i));

                            }
                        }
                        setTitle("List Of Student ( "+total+" / 40 )");
                        listViewDataAdapter.notifyDataSetChanged();

                    }
                });

                alertDialog.show();
            }
        });
        Button total_std_select = findViewById(R.id.total_selected_student);
        total_std_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                printInvoice();
                Toast.makeText(getApplicationContext(),"Student data saved as pdf successfully.....",Toast.LENGTH_SHORT).show();
//                int size = variableClass.getSTD_id().size();
//                count = 0;
//                for (int j = 0 ; j< size; j++){
//                    DTO_STudent dto = initItemList.get(j);
//                    if (dto.isChecked())
//                    {
//                        count = count + 1;
//                    }
//                }
//                selected_student = String.valueOf(count);
//                Log.e("onCreate: ", selected_student);
//                setTitle("List Of Student ( "+selected_student+" / 40 )");
            }
        });


        Button selectNoneButton = (Button)findViewById(R.id.Uncheck_all);
        selectNoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int size = initItemList.size();
                for(int i=0;i<size;i++)
                {
                    DTO_STudent sTudent = initItemList.get(i);
                    sTudent.setChecked(false);
                }
                total = 0;
                count = 0;
                setTitle("List Of Student ( "+total+" / "+total_1+" )");


                listViewDataAdapter.notifyDataSetChanged();
            }
        });
        Button selectAllButton = (Button)findViewById(R.id.check_all);
        selectAllButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int size = initItemList.size();
                for(int i=0;i<size;i++)
                {
                    DTO_STudent sTudent = initItemList.get(i);
                    sTudent.setChecked(true);
                }
                total = variableClass.getSTD_id().size();
                count = total;
                setTitle("List Of Student ( "+total+" / "+total_1+" )");

                listViewDataAdapter.notifyDataSetChanged();
            }
        });

    }

    private void init() {
        this.linearLayout = findViewById(R.id.popup_buttom);
        this.bottomSheetBehavior = BottomSheetBehavior.from(linearLayout);
        this.up_down = findViewById(R.id.toggle_button);

    }
    private List<DTO_STudent> getInitViewItemDtoList()
    {
        //student = getResources().getStringArray(R.array.Student_id);
        //name = getResources().getStringArray(R.array.std_name);
        //attend = getResources().getStringArray(R.array.Attendance);

        float std_atte;
        VariableClass variable = VariableClass.getInstance();
        for (int i= 0; i<variable.getSTD_ATTEND().size(); i++){
            float attn_lenth = variable.getSTD_ATTEND().get(i).length();
            std_atte = ((attn_lenth/2)*100)/variable.getAtten_total();
            //Log.e( "getInitViewItemDtoList: ", String.valueOf(std_atte));
            int atn_total = (int) Math.ceil(std_atte);
            total_student_attendance.add(String.valueOf(atn_total));
        }
        //attend = variable.getSTD_ATTEND().toArray(new String[variable.getSTD_ATTEND().size()]);

        student = variable.getSTD_id().toArray(new String[variable.getSTD_id().size()]);
        Log.e("response ", String.valueOf(student));
        name = variable.getSTD_name().toArray(new String[variable.getSTD_name().size()]);
        Log.e("response ", String.valueOf(name));

        final List<DTO_STudent> ret = new ArrayList<DTO_STudent>();

        int length = name.length;

        for(int i=0;i<length;i++)
        {
            String itemText = name[i];
            String itemText1 = student[i];
            String itemText2 = total_student_attendance.get(i);

            DTO_STudent sTudent = new DTO_STudent();
            sTudent.setChecked(true);
            sTudent.setItemText1(itemText1);
            sTudent.setItemText2(itemText2);
            sTudent.setItemText(itemText);

            ret.add(sTudent);
        }
        Log.e("response ", String.valueOf(ret));
        return ret;
    }

    private void getCR(String Student_id){
        StringRequest request = new StringRequest(Request.Method.POST, "http://192.168.0.20/android_project/CR_insert.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.contains("ok"))
                {
                    Log.e("onResponse: ", response);
                    Toast.makeText(getApplicationContext(), "CR Select successfully", Toast.LENGTH_SHORT).show();
                }
                else {
                    Log.e("onResponse: ", response);
                    Toast.makeText(getApplicationContext(), "Warning.....", Toast.LENGTH_SHORT).show();
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
                params.put("std_id", Student_id);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
    private void viewCR(String Student_id){
        StringRequest request = new StringRequest(Request.Method.POST, "http://192.168.0.20/android_project/ViewCR.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray array = jsonObject.getJSONArray("data");
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject ob = array.getJSONObject(i);
                        STD_NAme = ob.getString("std_name");
                        Log.e("onResponse: ", STD_NAme);
                        updateCR(Student_id);
                    }
                    return;
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
                params.put("std_id", Student_id);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
    private void viewCR1(String Student_id){
        StringRequest request = new StringRequest(Request.Method.POST, "http://192.168.0.20/android_project/ViewCR.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray array = jsonObject.getJSONArray("data");
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject ob = array.getJSONObject(i);
                        STD_NAme = ob.getString("std_name");
                        Log.e("onResponse: ", STD_NAme);
                        RemoveCR(Student_id);
                    }
                    return;
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
                params.put("std_id", Student_id);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
    private void total_class() {
        VariableClass variableClass = VariableClass.getInstance();
        String teacher_code = variableClass.getTd_code();
        Log.e("td code:  ", teacher_code);
        String s_no = variableClass.getS_no();
        Log.e("s_no  ", s_no);
        Student_details student_details = Student_details.getInstance();
        String course_code = student_details.getProgram();
        Log.e("CC  ", course_code);
        String intake = student_details.getIntake();
        Log.e("intake:  ", intake);
        String Dept = student_details.getDEPT();
        Log.e("Dept:  ", Dept);
        String Section = student_details.getSection();
        Log.e("Sec:  ", Section);
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
                        check = ob.getString("Number_of_class");
                        Log.e("Check: ", check);
                        Old_attnd = check;

                    }
                } catch (JSONException e) {
                    check = String.valueOf(0);
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
                params.put("code", code);
                params.put("S_no", s_no);
                params.put("Dept", Dept);
                params.put("course_code", course_code);
                params.put("intake", intake);
                params.put("section", Section);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
    private void Update_total_class() {
        VariableClass variableClass = VariableClass.getInstance();
        String teacher_code = variableClass.getTd_code();
        Student_details student_details = Student_details.getInstance();
        String course_code = student_details.getProgram();
        String intake = student_details.getIntake();
        String Dept = student_details.getDEPT();
        String Section = student_details.getSection();
        String number_of_class;
        String Code = "update_all_attend";
        int total_class = Integer.parseInt(check);
        total_class = total_class + check_attn;
        if (total_class==0){
            return;
        }
        number_of_class = String.valueOf(total_class);
        Log.e("Update_total_class: ", number_of_class);
        /*if (total_class>0){
            total_class = total_class + attn_class;
            Log.e("Update_total_class: ", String.valueOf(total_class));
            number_of_class = String.valueOf(total_class);
            Log.e("Number of class: ", number_of_class);
        }
        else {
            number_of_class = check;
        }*/

        final String code = "total_class";
        StringRequest request = new StringRequest(Request.Method.POST, "http://192.168.0.20/android_project/All.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("onResponse: ", response);
                if (response.contains("ok")){

                    //Toast.makeText(getApplicationContext(),"successfully.....",Toast.LENGTH_SHORT).show();
                }
                else {
                    //Toast.makeText(getApplicationContext(),"Wrong.....",Toast.LENGTH_SHORT).show();
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
                params.put("code", Code);
                params.put("td_code", teacher_code);
                params.put("Dept", Dept);
                params.put("course_code", course_code);
                params.put("intake", intake);
                params.put("section", Section);
                params.put("number_of_class", number_of_class);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
    private void updateCR( String Student_id){
        VariableClass variableClass = VariableClass.getInstance();
        String S_no = variableClass.getS_no();
        Student_details student_details = Student_details.getInstance();
        String course_code = student_details.getProgram();
        String intake = student_details.getIntake();
        String sec = student_details.getSection();
        String code = "insert_CR";
        Log.e("updateCR: ", S_no);
        //String std_name = STD_NAme + " (CR)";
        //String std_name = STD_NAme;
        Log.e("updateCR: ", code);

        StringRequest request = new StringRequest(Request.Method.POST, "http://192.168.0.20/android_project/All.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.contains("ok"))
                {
                    Log.e("onResponse: ", response);
                    Toast.makeText(getApplicationContext(), "CR Select successfully", Toast.LENGTH_SHORT).show();
                }
                else {
                    Log.e("onResponse: ", response);
                    Toast.makeText(getApplicationContext(), "Warning.....", Toast.LENGTH_SHORT).show();
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
                params.put("code", code);
                params.put("std_id", Student_id);
                params.put("S_no", S_no);
                params.put("intake", intake);
                params.put("sec", sec);
                params.put("course_code", course_code);

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);

    }

    private void RemoveCR(String Student_id){

        VariableClass variableClass = VariableClass.getInstance();
        String S_no = variableClass.getS_no();
        Student_details student_details = Student_details.getInstance();
        String course_code = student_details.getProgram();
        String intake = student_details.getIntake();
        String sec = student_details.getSection();
        String code = "Remove_CR";
        Log.e("updateCR: ", S_no);
        //String i = STD_NAme;
        //String Remove = i.substring(0, i.length() - 5);
        Log.e("RemoveCR: ", code);
        StringRequest request = new StringRequest(Request.Method.POST, "http://192.168.0.20/android_project/All.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.contains("ok"))
                {
                    Log.e("onResponse: ", response);
                    Toast.makeText(getApplicationContext(), "CR Removed successfully", Toast.LENGTH_SHORT).show();
                }
                else {
                    Log.e("onResponse: ", response);
                    Toast.makeText(getApplicationContext(), "Warning.....", Toast.LENGTH_SHORT).show();
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
                params.put("std_id", Student_id);
                params.put("course_code", course_code);
                params.put("intake", intake);
                params.put("sec", sec);
                params.put("code", code);
                params.put("S_no", S_no);

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    private void getstudentdata(String Student_id) {
        Student_details student_details = Student_details.getInstance();
        String course = student_details.getProgram();
        String intake = student_details.getIntake();
        String section = student_details.getSection();
        //Student_ID = Student_id;
        List std_attn = new ArrayList();
        String code = "std_attend_day";
        StringRequest request = new StringRequest(Request.Method.POST, "http://192.168.0.20/android_project/All.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray array = jsonObject.getJSONArray("data");
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject ob = array.getJSONObject(i);
                        Attend_day = ob.getString("std_atndnc");
                        attendance(day_calender,monthofyear,Student_id);
                        //attendance(day_calender,monthofyear,Attend_day);
                        Log.e("onResponse: ",Attend_day);
                        //c = Attend_day.charAt(1);
                        //c1 = Attend_day.charAt(2);
                        //Log.e("onResponse: ", String.valueOf(c));
                        //Log.e("onResponse: ", String.valueOf(c1));
                        //    std_attn.add(String.valueOf(c));
                        //A1 = String.valueOf(c);
                        //A2 = String.valueOf(std_attn.get(2));
                        //sum_of_attend = A1;
                        //Log.e("onResponse: ",sum_of_attend );
                    }
                    return;


                } catch (JSONException e) {
                    e.printStackTrace();
                    //attendance(day_calender,monthofyear,"");
//                        sum_of_attend = String.valueOf(0);
//                        Log.e("onResponse: ",sum_of_attend );
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
                params.put("std_id", Student_id);
                params.put("code", code);
                params.put("intake", intake);
                params.put("course_code", course);
                params.put("section", section);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    private void add_student(String std_atten_all,String Student_id) {
        Student_details student_details = Student_details.getInstance();
        String course = student_details.getProgram();
        String intake = student_details.getIntake();
        String section = student_details.getSection();
        VariableClass variableClass = VariableClass.getInstance();
        String s_no = variableClass.getS_no();
        String std_at = std_atten_all;
        String code = "std_attend_update";
        Log.e("attendance: ", std_at);
//        int std_att_all = Integer.parseInt(String.valueOf(attn_class)) + S1;
//        Log.e("add_student: ", String.valueOf(std_att_all));
//        String final_attn = "A" + std_att_all;
        StringRequest request = new StringRequest(Request.Method.POST, "http://192.168.0.20/android_project/All.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                //progressDialog.dismiss();
                if (response.contains("Yes")){
                    Log.e("onResponse: ", std_atten_all);
                    Log.e("onResponse: ", Student_id);
                    Toast.makeText(getApplicationContext(),"Student update successfully.....",Toast.LENGTH_SHORT).show();
                }
                else{
                    Log.e("onResponse: ", std_atten_all);
                    Log.e("onResponse: ", Student_id);
                    Toast.makeText(getApplicationContext(),"something wrong.",Toast.LENGTH_SHORT).show();
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
                params.put("std_id", Student_id);
                params.put("code", code);
                params.put("S_no", s_no);
                params.put("course_code", course);
                params.put("intake", intake);
                params.put("section", section);
                params.put("attn_day", std_at);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
    public void attendance(int day , int Month, String Student_ID ){

        if (Month == 1)
        {
            if (day == 1)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "11";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 2)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "21";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 3)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "31";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 4)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "41";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 5)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "51";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 6)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "61";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 7)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "71";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 8)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "81";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 9)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "91";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 10)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "A1";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 11)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "B1";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 12)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "C1";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 13)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "D1";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 14)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "E1";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 15)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "F1";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 16)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "G1";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 17)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "H1";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 18)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "I1";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 19)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "J1";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 20)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "K1";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 21)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "L1";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 22)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "M1";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 23)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "N1";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 24)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "O1";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 25)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "P1";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 26)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "Q1";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 27)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "R1";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 28)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "S1";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 29)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "T1";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 30)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "U1";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 31)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "V1";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
        }
        else if (Month == 2)
        {
            if (day == 1)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "12";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 2)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "22";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 3)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "32";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 4)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "42";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 5)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "52";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 6)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "62";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 7)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "72";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 8)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "82";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 9)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "92";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 10)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "A2";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 11)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "B2";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 12)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "C2";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 13)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "D2";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 14)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "E2";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 15)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "F2";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 16)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "G2";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 17)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "H2";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 18)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "I2";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 19)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "J2";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 20)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "K2";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 21)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "L2";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 22)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "M2";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 23)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "N2";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 24)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "O2";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 25)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "P2";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 26)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "Q2";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 27)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "R2";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 28)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "S2";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 29)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "T2";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }

        }
        else if (Month == 3)
        {
            if (day == 1)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "13";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 2)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "23";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 3)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "33";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 4)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "43";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 5)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "53";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 6)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "63";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 7)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "73";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 8)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "83";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 9)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "93";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 10)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "A3";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 11)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "B3";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 12)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "C3";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 13)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "D3";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 14)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "E3";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 15)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "F3";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 16)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "G3";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 17)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "H3";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 18)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "I3";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 19)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "J3";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 20)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "K3";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 21)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "L3";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 22)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "M3";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 23)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "N3";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 24)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "O3";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 25)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "P3";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 26)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "Q3";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 27)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "R3";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 28)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "S3";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 29)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "T3";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 30)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "U3";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 31)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "V3";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
        }
        else if (Month == 4)
        {
            if (day == 1)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "14";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 2)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "24";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 3)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "34";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 4)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "44";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 5)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "54";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 6)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "64";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 7)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "74";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 8)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "84";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 9)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "94";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 10)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "A4";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 11)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "B4";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 12)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "C4";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 13)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "D4";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 14)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "E4";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 15)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "F4";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 16)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "G4";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 17)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "H4";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 18)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "I4";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 19)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "J4";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 20)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "K4";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 21)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "L4";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 22)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "M4";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 23)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "N4";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 24)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "O4";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 25)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "P4";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 26)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "Q4";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 27)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "R4";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 28)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "S4";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 29)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "T4";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 30)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "U4";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }

        }
        else if (Month == 5)
        {
            if (day == 1)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "15";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 2)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "25";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 3)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "35";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 4)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "45";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 5)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "55";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 6)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "65";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 7)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "75";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 8)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "85";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 9)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "95";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 10)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "A5";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 11)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "B5";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 12)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "C5";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 13)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "D5";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 14)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "E5";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 15)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "F5";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 16)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "G5";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 17)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "H5";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 18)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "I5";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 19)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "J5";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 20)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "K5";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 21)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "L5";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 22)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "M5";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 23)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "N5";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 24)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "O5";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 25)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "P5";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 26)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "Q5";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 27)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "R5";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 28)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "S5";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 29)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "T5";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 30)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "U5";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 31)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "V5";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
        }
        else if (Month == 6)
        {
            if (day == 1)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "16";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 2)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "26";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 3)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "36";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 4)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "46";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 5)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "56";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 6)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "66";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 7)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "76";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 8)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "86";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 9)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "96";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 10)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "A6";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 11)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "B6";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 12)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "C6";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 13)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "D6";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 14)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "E6";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 15)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "F6";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 16)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "G6";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 17)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "H6";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 18)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "I6";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 19)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "J6";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 20)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "K6";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 21)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "L6";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 22)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "M6";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 23)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "N6";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 24)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "O6";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 25)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "P6";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 26)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "Q6";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 27)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "R6";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 28)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "S6";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 29)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "T6";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 30)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "U6";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }

        }
        else if (Month == 7)
        {
            if (day == 1)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "17";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 2)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "27";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 3)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "37";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 4)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "47";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 5)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "57";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 6)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "67";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 7)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "77";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 8)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "87";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 9)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "97";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 10)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "A7";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 11)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "B7";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 12)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "C7";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 13)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "D7";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 14)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "E7";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 15)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "F7";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 16)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "G7";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 17)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "H7";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 18)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "I7";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 19)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "J7";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 20)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "K7";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 21)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "L7";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 22)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "M7";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 23)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "N7";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 24)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "O7";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 25)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "P7";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 26)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "Q7";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 27)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "R7";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 28)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "S7";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 29)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "T7";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 30)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "U7";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 31)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "V7";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
        }
        else if(Month == 8)
        {
            if (day == 1)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "18";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 2)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "28";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 3)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "38";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 4)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "48";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 5)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "58";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 6)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "68";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 7)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "78";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 8)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "88";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 9)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "98";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 10)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "A8";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 11)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "B8";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 12)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "C8";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 13)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "D8";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 14)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "E8";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 15)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "F8";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 16)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "G8";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 17)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "H8";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 18)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "I8";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 19)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "J8";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 20)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "K8";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 21)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "L8";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 22)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "M8";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 23)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "N8";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 24)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "O8";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 25)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "P8";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 26)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "Q8";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 27)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "R8";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 28)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "S8";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 29)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "T8";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 30)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "U8";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 31)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "V8";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
        }
        else if(Month == 9)
        {
            if (day == 1)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "19";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 2)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "29";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 3)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "39";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 4)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "49";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 5)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "59";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 6)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "69";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 7)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "79";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 8)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "89";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 9)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "99";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 10)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "A9";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 11)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "B9";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 12)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "C9";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 13)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "D9";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 14)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "E9";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 15)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "F9";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 16)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "G9";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 17)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "H9";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 18)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "I9";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 19)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "J9";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 20)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "K9";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 21)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "L9";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 22)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "M9";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 23)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "N9";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 24)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "O9";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 25)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "P9";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 26)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "Q9";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 27)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "R9";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 28)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "S9";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 29)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "T9";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 30)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "U9";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 31)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "V9";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
        }
        else if(Month == 10)
        {
            if (day == 1)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "1A";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 2)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "2A";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 3)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "3A";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 4)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "4A";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 5)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "5A";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 6)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "6A";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 7)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "7A";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 8)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "8A";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 9)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "9A";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 10)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "AA";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 11)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "BA";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 12)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "CA";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 13)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "DA";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 14)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "EA";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 15)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "FA";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 16)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "GA";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 17)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "HA";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 18)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "IA";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 19)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "JA";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 20)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "KA";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 21)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "LA";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 22)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "MA";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 23)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "NA";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 24)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "OA";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 25)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "PA";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 26)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "QA";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 27)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "RA";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 28)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "SA";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 29)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "TA";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 30)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "UA";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 31)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "VA";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
        }
        else if(Month == 11)
        {
            if (day == 1)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "1B";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 2)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "2B";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 3)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "3B";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 4)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "4B";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 5)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "5B";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 6)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "6B";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 7)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "7B";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 8)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "8B";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 9)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "9B";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 10)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "AB";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 11)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "BB";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 12)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "CB";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 13)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "DB";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 14)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "EB";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 15)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "FB";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 16)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "GB";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 17)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "HB";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 18)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "IB";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 19)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "JB";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 20)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "KB";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 21)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "LB";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 22)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "MB";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 23)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "NB";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 24)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "OB";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 25)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "PB";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 26)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "QB";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 27)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "RB";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 28)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "SB";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 29)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "TB";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 30)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "UB";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 31)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "VB";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
        }
        else if (Month == 12)
        {
            if (day == 1)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "1C";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 2)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "2C";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 3)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "3C";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 4)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "4C";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 5)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "5C";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 6)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "6C";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 7)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "7C";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 8)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "8C";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 9)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "9C";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 10)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "AC";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 11)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "BC";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 12)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "CC";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 13)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "DC";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 14)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "EC";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 15)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "FC";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 16)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "GC";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 17)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "HC";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 18)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "IC";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 19)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "JC";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 20)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "KC";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 21)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "LC";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 22)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "MC";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 23)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "NC";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 24)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "OC";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 25)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "PC";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 26)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "QC";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 27)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "RC";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 28)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "SC";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 29)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "TC";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 30)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "UC";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
            else if (day == 31)
            {
                String allatten = "";

                for (int i = 0;i < attn_class;i++)
                {
                    allatten = allatten + "VC";
                    Log.e( "attendance: ", allatten);
                }
                std_attn_total = std_attendance + allatten;

                Log.e( "attendance: ", std_attn_total);
                Log.e( "attendance: ", String.valueOf(std_attn_total.length()));

                add_student(allatten,Student_ID);
            }
        }


//        switch (Month){
//            case 1 :
//                switch (day){
//                    case 1:
//                        String allatten = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten = allatten + "11";
//                            Log.e( "attendance: ", allatten);
//                        }
//                        std_attn_total = std_attendance + allatten;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//
//                        break;
//                    case 2:
//                        String allatten1 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten1 = allatten1 + "21";
//                            Log.e( "attendance: ", allatten1);
//                        }
//                        std_attn_total = std_attendance + allatten1;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//
//                        break;
//                    case 3:
//                        String allatten2 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten2 = allatten2 + "31";
//                            Log.e( "attendance: ", allatten2);
//                        }
//                        std_attn_total = std_attendance + allatten2;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 4:
//                        String allatten3 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten3 = allatten3 + "41";
//                            Log.e( "attendance: ", allatten3);
//                        }
//                        std_attn_total = std_attendance + allatten3;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 5:
//                        String allatten4 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten4 = allatten4 + "51";
//                            Log.e( "attendance: ", allatten4);
//                        }
//                        std_attn_total = std_attendance + allatten4;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 6:
//                        String allatten5 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten5 = allatten5 + "61";
//                            Log.e( "attendance: ", allatten5);
//                        }
//                        std_attn_total = std_attendance + allatten5;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 7:
//                        String allatten6 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten6 = allatten6 + "71";
//                            Log.e( "attendance: ", allatten6);
//                        }
//                        std_attn_total = std_attendance + allatten6;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 8:
//                        String allatten7 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten7 = allatten7 + "81";
//                            Log.e( "attendance: ", allatten7);
//                        }
//                        std_attn_total = std_attendance + allatten7;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 9:
//                        String allatten8 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten8 = allatten8 + "91";
//                            Log.e( "attendance: ", allatten8);
//                        }
//                        std_attn_total = std_attendance + allatten8;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 10:
//                        String allatten9 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten9 = allatten9 + "A1";
//                            Log.e( "attendance: ", allatten9);
//                        }
//                        std_attn_total = std_attendance + allatten9;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 11:
//                        String allatten10 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten10 = allatten10 + "B1";
//                            Log.e( "attendance: ", allatten10);
//                        }
//                        std_attn_total = std_attendance + allatten10;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 12:
//                        String allatten11 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten11 = allatten11 + "C1";
//                            Log.e( "attendance: ", allatten11);
//                        }
//                        std_attn_total = std_attendance + allatten11;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 13:
//                        String allatten12 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten12 = allatten12 + "D1";
//                            Log.e( "attendance: ", allatten12);
//                        }
//                        std_attn_total = std_attendance + allatten12;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 14:
//                        String allatten13 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten13 = allatten13 + "E1";
//                            Log.e( "attendance: ", allatten13);
//                        }
//                        std_attn_total = std_attendance + allatten13;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 15:
//                        String allatten14 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten14 = allatten14 + "F1";
//                            Log.e( "attendance: ", allatten14);
//                        }
//                        std_attn_total = std_attendance + allatten14;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 16:
//                        String allatten15 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten15 = allatten15 + "G1";
//                            Log.e( "attendance: ", allatten15);
//                        }
//                        std_attn_total = std_attendance + allatten15;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 17:
//                        String allatten16 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten16 = allatten16 + "H1";
//                            Log.e( "attendance: ", allatten16);
//                        }
//                        std_attn_total = std_attendance + allatten16;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 18:
//                        String allatten17 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten17 = allatten17 + "I1";
//                            Log.e( "attendance: ", allatten17);
//                        }
//                        std_attn_total = std_attendance + allatten17;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 19:
//                        String allatten18 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten18 = allatten18 + "J1";
//                            Log.e( "attendance: ", allatten18);
//                        }
//                        std_attn_total = std_attendance + allatten18;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 20:
//                        String allatten19 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten19 = allatten19 + "K1";
//                            Log.e( "attendance: ", allatten19);
//                        }
//                        std_attn_total = std_attendance + allatten19;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 21:
//                        String allatten20 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten20 = allatten20 + "L1";
//                            Log.e( "attendance: ", allatten20);
//                        }
//                        std_attn_total = std_attendance + allatten20;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 22:
//                        String allatten21 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten21 = allatten21 + "M1";
//                            Log.e( "attendance: ", allatten21);
//                        }
//                        std_attn_total = std_attendance + allatten21;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 23:
//                        String allatten22 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten22 = allatten22 + "N1";
//                            Log.e( "attendance: ", allatten22);
//                        }
//                        std_attn_total = std_attendance + allatten22;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 24:
//                        String allatten23 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten23 = allatten23 + "O1";
//                            Log.e( "attendance: ", allatten23);
//                        }
//                        std_attn_total = std_attendance + allatten23;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 25:
//                        String allatten24 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten24 = allatten24 + "P1";
//                            Log.e( "attendance: ", allatten24);
//                        }
//                        std_attn_total = std_attendance + allatten24;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 26:
//                        String allatten25 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten25 = allatten25 + "Q1";
//                            Log.e( "attendance: ", allatten25);
//                        }
//                        std_attn_total = std_attendance + allatten25;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 27:
//                        String allatten26 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten26 = allatten26 + "R1";
//                            Log.e( "attendance: ", allatten26);
//                        }
//                        std_attn_total = std_attendance + allatten26;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 28:
//                        String allatten27 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten27 = allatten27 + "S1";
//                            Log.e( "attendance: ", allatten27);
//                        }
//                        std_attn_total = std_attendance + allatten27;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 29:
//                        String allatten28 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten28 = allatten28 + "T1";
//                            Log.e( "attendance: ", allatten28);
//                        }
//                        std_attn_total = std_attendance + allatten28;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 30:
//                        String allatten29 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten29 = allatten29 + "U1";
//                            Log.e( "attendance: ", allatten29);
//                        }
//                        std_attn_total = std_attendance + allatten29;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 31:
//                        String allatten30 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten30 = allatten30 + "V1";
//                            Log.e( "attendance: ", allatten30);
//                        }
//                        std_attn_total = std_attendance + allatten30;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                }
//                break;
//            case 2:
//                switch (day){
//                    case 1:
//                        String allatten = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten = allatten + "12";
//                            Log.e( "attendance: ", allatten);
//                        }
//                        std_attn_total = std_attendance + allatten;
//
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//
//
//                        break;
//                    case 2:
//                        String allatten1 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten1 = allatten1 + "22";
//                            Log.e( "attendance: ", allatten1);
//                        }
//                        std_attn_total = std_attendance + allatten1;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 3:
//                        String allatten2 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten2 = allatten2 + "32";
//                            Log.e( "attendance: ", allatten2);
//                        }
//                        std_attn_total = std_attendance + allatten2;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 4:
//                        String allatten3 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten3 = allatten3 + "42";
//                            Log.e( "attendance: ", allatten3);
//                        }
//                        std_attn_total = std_attendance + allatten3;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 5:
//                        String allatten4 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten4 = allatten4 + "52";
//                            Log.e( "attendance: ", allatten4);
//                        }
//                        std_attn_total = std_attendance + allatten4;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 6:
//                        String allatten5 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten5 = allatten5 + "62";
//                            Log.e( "attendance: ", allatten5);
//                        }
//                        std_attn_total = std_attendance + allatten5;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 7:
//                        String allatten6 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten6 = allatten6 + "72";
//                            Log.e( "attendance: ", allatten6);
//                        }
//                        std_attn_total = std_attendance + allatten6;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 8:
//                        String allatten7 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten7 = allatten7 + "82";
//                            Log.e( "attendance: ", allatten7);
//                        }
//                        std_attn_total = std_attendance + allatten7;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 9:
//                        String allatten8 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten8 = allatten8 + "92";
//                            Log.e( "attendance: ", allatten8);
//                        }
//                        std_attn_total = std_attendance + allatten8;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 10:
//                        String allatten9 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten9 = allatten9 + "A2";
//                            Log.e( "attendance: ", allatten9);
//                        }
//                        std_attn_total = std_attendance + allatten9;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 11:
//                        String allatten10 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten10 = allatten10 + "B2";
//                            Log.e( "attendance: ", allatten10);
//                        }
//                        std_attn_total = std_attendance + allatten10;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 12:
//                        String allatten11 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten11 = allatten11 + "C2";
//                            Log.e( "attendance: ", allatten11);
//                        }
//                        std_attn_total = std_attendance + allatten11;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 13:
//                        String allatten12 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten12 = allatten12 + "D2";
//                            Log.e( "attendance: ", allatten12);
//                        }
//                        std_attn_total = std_attendance + allatten12;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 14:
//                        String allatten13 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten13 = allatten13 + "E2";
//                            Log.e( "attendance: ", allatten13);
//                        }
//                        std_attn_total = std_attendance + allatten13;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 15:
//                        String allatten14 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten14 = allatten14 + "F2";
//                            Log.e( "attendance: ", allatten14);
//                        }
//                        std_attn_total = std_attendance + allatten14;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 16:
//                        String allatten15 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten15 = allatten15 + "G2";
//                            Log.e( "attendance: ", allatten15);
//                        }
//                        std_attn_total = std_attendance + allatten15;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 17:
//                        String allatten16 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten16 = allatten16 + "H2";
//                            Log.e( "attendance: ", allatten16);
//                        }
//                        std_attn_total = std_attendance + allatten16;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 18:
//                        String allatten17 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten17 = allatten17 + "I2";
//                            Log.e( "attendance: ", allatten17);
//                        }
//                        std_attn_total = std_attendance + allatten17;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 19:
//                        String allatten18 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten18 = allatten18 + "J2";
//                            Log.e( "attendance: ", allatten18);
//                        }
//                        std_attn_total = std_attendance + allatten18;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 20:
//                        String allatten19 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten19 = allatten19 + "K2";
//                            Log.e( "attendance: ", allatten19);
//                        }
//                        std_attn_total = std_attendance + allatten19;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 21:
//                        String allatten20 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten20 = allatten20 + "L2";
//                            Log.e( "attendance: ", allatten20);
//                        }
//                        std_attn_total = std_attendance + allatten20;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 22:
//                        String allatten21 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten21 = allatten21 + "M2";
//                            Log.e( "attendance: ", allatten21);
//                        }
//                        std_attn_total = std_attendance + allatten21;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 23:
//                        String allatten22 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten22 = allatten22 + "N2";
//                            Log.e( "attendance: ", allatten22);
//                        }
//                        std_attn_total = std_attendance + allatten22;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 24:
//                        String allatten23 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten23 = allatten23 + "O2";
//                            Log.e( "attendance: ", allatten23);
//                        }
//                        std_attn_total = std_attendance + allatten23;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 25:
//                        String allatten24 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten24 = allatten24 + "P2";
//                            Log.e( "attendance: ", allatten24);
//                        }
//                        std_attn_total = std_attendance + allatten24;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 26:
//                        String allatten25 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten25 = allatten25 + "Q2";
//                            Log.e( "attendance: ", allatten25);
//                        }
//                        std_attn_total = std_attendance + allatten25;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 27:
//                        String allatten26 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten26 = allatten26 + "R2";
//                            Log.e( "attendance: ", allatten26);
//                        }
//                        std_attn_total = std_attendance + allatten26;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 28:
//                        String allatten27 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten27 = allatten27 + "S2";
//                            Log.e( "attendance: ", allatten27);
//                        }
//                        std_attn_total = std_attendance + allatten27;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 29:
//                        String allatten28 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten28 = allatten28 + "T2";
//                            Log.e( "attendance: ", allatten28);
//                        }
//                        std_attn_total = std_attendance + allatten28;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                }
//                break;
//            case 3:
//                switch (day){
//                    case 1:
//                        String allatten = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten = allatten + "13";
//                            Log.e( "attendance: ", allatten);
//                        }
//                        std_attn_total = std_attendance + allatten;
//
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//
//
//                        break;
//                    case 2:
//                        String allatten1 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten1 = allatten1 + "23";
//                            Log.e( "attendance: ", allatten1);
//                        }
//                        std_attn_total = std_attendance + allatten1;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 3:
//                        String allatten2 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten2 = allatten2 + "33";
//                            Log.e( "attendance: ", allatten2);
//                        }
//                        std_attn_total = std_attendance + allatten2;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 4:
//                        String allatten3 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten3 = allatten3 + "43";
//                            Log.e( "attendance: ", allatten3);
//                        }
//                        std_attn_total = std_attendance + allatten3;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 5:
//                        String allatten4 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten4 = allatten4 + "53";
//                            Log.e( "attendance: ", allatten4);
//                        }
//                        std_attn_total = std_attendance + allatten4;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 6:
//                        String allatten5 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten5 = allatten5 + "63";
//                            Log.e( "attendance: ", allatten5);
//                        }
//                        std_attn_total = std_attendance + allatten5;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 7:
//                        String allatten6 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten6 = allatten6 + "73";
//                            Log.e( "attendance: ", allatten6);
//                        }
//                        std_attn_total = std_attendance + allatten6;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 8:
//                        String allatten7 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten7 = allatten7 + "83";
//                            Log.e( "attendance: ", allatten7);
//                        }
//                        std_attn_total = std_attendance + allatten7;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 9:
//                        String allatten8 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten8 = allatten8 + "93";
//                            Log.e( "attendance: ", allatten8);
//                        }
//                        std_attn_total = std_attendance + allatten8;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 10:
//                        String allatten9 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten9 = allatten9 + "A3";
//                            Log.e( "attendance: ", allatten9);
//                        }
//                        std_attn_total = std_attendance + allatten9;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 11:
//                        String allatten10 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten10 = allatten10 + "B3";
//                            Log.e( "attendance: ", allatten10);
//                        }
//                        std_attn_total = std_attendance + allatten10;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 12:
//                        String allatten11 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten11 = allatten11 + "C3";
//                            Log.e( "attendance: ", allatten11);
//                        }
//                        std_attn_total = std_attendance + allatten11;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 13:
//                        String allatten12 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten12 = allatten12 + "D3";
//                            Log.e( "attendance: ", allatten12);
//                        }
//                        std_attn_total = std_attendance + allatten12;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 14:
//                        String allatten13 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten13 = allatten13 + "E3";
//                            Log.e( "attendance: ", allatten13);
//                        }
//                        std_attn_total = std_attendance + allatten13;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 15:
//                        String allatten14 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten14 = allatten14 + "F3";
//                            Log.e( "attendance: ", allatten14);
//                        }
//                        std_attn_total = std_attendance + allatten14;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 16:
//                        String allatten15 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten15 = allatten15 + "G3";
//                            Log.e( "attendance: ", allatten15);
//                        }
//                        std_attn_total = std_attendance + allatten15;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 17:
//                        String allatten16 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten16 = allatten16 + "H3";
//                            Log.e( "attendance: ", allatten16);
//                        }
//                        std_attn_total = std_attendance + allatten16;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 18:
//                        String allatten17 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten17 = allatten17 + "I3";
//                            Log.e( "attendance: ", allatten17);
//                        }
//                        std_attn_total = std_attendance + allatten17;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 19:
//                        String allatten18 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten18 = allatten18 + "J3";
//                            Log.e( "attendance: ", allatten18);
//                        }
//                        std_attn_total = std_attendance + allatten18;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 20:
//                        String allatten19 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten19 = allatten19 + "K3";
//                            Log.e( "attendance: ", allatten19);
//                        }
//                        std_attn_total = std_attendance + allatten19;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 21:
//                        String allatten20 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten20 = allatten20 + "L3";
//                            Log.e( "attendance: ", allatten20);
//                        }
//                        std_attn_total = std_attendance + allatten20;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 22:
//                        String allatten21 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten21 = allatten21 + "M3";
//                            Log.e( "attendance: ", allatten21);
//                        }
//                        std_attn_total = std_attendance + allatten21;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 23:
//                        String allatten22 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten22 = allatten22 + "N3";
//                            Log.e( "attendance: ", allatten22);
//                        }
//                        std_attn_total = std_attendance + allatten22;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 24:
//                        String allatten23 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten23 = allatten23 + "O3";
//                            Log.e( "attendance: ", allatten23);
//                        }
//                        std_attn_total = std_attendance + allatten23;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 25:
//                        String allatten24 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten24 = allatten24 + "P3";
//                            Log.e( "attendance: ", allatten24);
//                        }
//                        std_attn_total = std_attendance + allatten24;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 26:
//                        String allatten25 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten25 = allatten25 + "Q3";
//                            Log.e( "attendance: ", allatten25);
//                        }
//                        std_attn_total = std_attendance + allatten25;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 27:
//                        String allatten26 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten26 = allatten26 + "R3";
//                            Log.e( "attendance: ", allatten26);
//                        }
//                        std_attn_total = std_attendance + allatten26;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 28:
//                        String allatten27 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten27 = allatten27 + "S3";
//                            Log.e( "attendance: ", allatten27);
//                        }
//                        std_attn_total = std_attendance + allatten27;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 29:
//                        String allatten28 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten28 = allatten28 + "T3";
//                            Log.e( "attendance: ", allatten28);
//                        }
//                        std_attn_total = std_attendance + allatten28;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 30:
//                        String allatten29 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten29 = allatten29 + "U3";
//                            Log.e( "attendance: ", allatten29);
//                        }
//                        std_attn_total = std_attendance + allatten29;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 31:
//                        String allatten30 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten30 = allatten30 + "V3";
//                            Log.e( "attendance: ", allatten30);
//                        }
//                        std_attn_total = std_attendance + allatten30;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                }
//                break;
//            case 4:
//                switch (day){
//                    case 1:
//                        String allatten = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten = allatten + "14";
//                            Log.e( "attendance: ", allatten);
//                        }
//                        std_attn_total = std_attendance + allatten;
//
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//
//
//                        break;
//                    case 2:
//                        String allatten1 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten1 = allatten1 + "24";
//                            Log.e( "attendance: ", allatten1);
//                        }
//                        std_attn_total = std_attendance + allatten1;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 3:
//                        String allatten2 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten2 = allatten2 + "34";
//                            Log.e( "attendance: ", allatten2);
//                        }
//                        std_attn_total = std_attendance + allatten2;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 4:
//                        String allatten3 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten3 = allatten3 + "44";
//                            Log.e( "attendance: ", allatten3);
//                        }
//                        std_attn_total = std_attendance + allatten3;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 5:
//                        String allatten4 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten4 = allatten4 + "54";
//                            Log.e( "attendance: ", allatten4);
//                        }
//                        std_attn_total = std_attendance + allatten4;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 6:
//                        String allatten5 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten5 = allatten5 + "64";
//                            Log.e( "attendance: ", allatten5);
//                        }
//                        std_attn_total = std_attendance + allatten5;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 7:
//                        String allatten6 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten6 = allatten6 + "74";
//                            Log.e( "attendance: ", allatten6);
//                        }
//                        std_attn_total = std_attendance + allatten6;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 8:
//                        String allatten7 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten7 = allatten7 + "84";
//                            Log.e( "attendance: ", allatten7);
//                        }
//                        std_attn_total = std_attendance + allatten7;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 9:
//                        String allatten8 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten8 = allatten8 + "94";
//                            Log.e( "attendance: ", allatten8);
//                        }
//                        std_attn_total = std_attendance + allatten8;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 10:
//                        String allatten9 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten9 = allatten9 + "A4";
//                            Log.e( "attendance: ", allatten9);
//                        }
//                        std_attn_total = std_attendance + allatten9;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 11:
//                        String allatten10 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten10 = allatten10 + "B4";
//                            Log.e( "attendance: ", allatten10);
//                        }
//                        std_attn_total = std_attendance + allatten10;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 12:
//                        String allatten11 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten11 = allatten11 + "C4";
//                            Log.e( "attendance: ", allatten11);
//                        }
//                        std_attn_total = std_attendance + allatten11;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 13:
//                        String allatten12 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten12 = allatten12 + "D4";
//                            Log.e( "attendance: ", allatten12);
//                        }
//                        std_attn_total = std_attendance + allatten12;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 14:
//                        String allatten13 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten13 = allatten13 + "E4";
//                            Log.e( "attendance: ", allatten13);
//                        }
//                        std_attn_total = std_attendance + allatten13;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 15:
//                        String allatten14 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten14 = allatten14 + "F4";
//                            Log.e( "attendance: ", allatten14);
//                        }
//                        std_attn_total = std_attendance + allatten14;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 16:
//                        String allatten15 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten15 = allatten15 + "G4";
//                            Log.e( "attendance: ", allatten15);
//                        }
//                        std_attn_total = std_attendance + allatten15;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 17:
//                        String allatten16 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten16 = allatten16 + "H4";
//                            Log.e( "attendance: ", allatten16);
//                        }
//                        std_attn_total = std_attendance + allatten16;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 18:
//                        String allatten17 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten17 = allatten17 + "I4";
//                            Log.e( "attendance: ", allatten17);
//                        }
//                        std_attn_total = std_attendance + allatten17;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 19:
//                        String allatten18 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten18 = allatten18 + "J4";
//                            Log.e( "attendance: ", allatten18);
//                        }
//                        std_attn_total = std_attendance + allatten18;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 20:
//                        String allatten19 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten19 = allatten19 + "K4";
//                            Log.e( "attendance: ", allatten19);
//                        }
//                        std_attn_total = std_attendance + allatten19;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 21:
//                        String allatten20 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten20 = allatten20 + "L4";
//                            Log.e( "attendance: ", allatten20);
//                        }
//                        std_attn_total = std_attendance + allatten20;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 22:
//                        String allatten21 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten21 = allatten21 + "M4";
//                            Log.e( "attendance: ", allatten21);
//                        }
//                        std_attn_total = std_attendance + allatten21;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 23:
//                        String allatten22 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten22 = allatten22 + "N4";
//                            Log.e( "attendance: ", allatten22);
//                        }
//                        std_attn_total = std_attendance + allatten22;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 24:
//                        String allatten23 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten23 = allatten23 + "O4";
//                            Log.e( "attendance: ", allatten23);
//                        }
//                        std_attn_total = std_attendance + allatten23;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 25:
//                        String allatten24 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten24 = allatten24 + "P4";
//                            Log.e( "attendance: ", allatten24);
//                        }
//                        std_attn_total = std_attendance + allatten24;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 26:
//                        String allatten25 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten25 = allatten25 + "Q4";
//                            Log.e( "attendance: ", allatten25);
//                        }
//                        std_attn_total = std_attendance + allatten25;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 27:
//                        String allatten26 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten26 = allatten26 + "R4";
//                            Log.e( "attendance: ", allatten26);
//                        }
//                        std_attn_total = std_attendance + allatten26;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 28:
//                        String allatten27 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten27 = allatten27 + "S4";
//                            Log.e( "attendance: ", allatten27);
//                        }
//                        std_attn_total = std_attendance + allatten27;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 29:
//                        String allatten28 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten28 = allatten28 + "T4";
//                            Log.e( "attendance: ", allatten28);
//                        }
//                        std_attn_total = std_attendance + allatten28;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 30:
//                        String allatten29 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten29 = allatten29 + "U4";
//                            Log.e( "attendance: ", allatten29);
//                        }
//                        std_attn_total = std_attendance + allatten29;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                }
//                break;
//            case 5:
//                switch (day){
//                    case 1:
//                        String allatten = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten = allatten + "15";
//                            Log.e( "attendance: ", allatten);
//                        }
//                        std_attn_total = std_attendance + allatten;
//
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//
//
//                        break;
//                    case 2:
//                        String allatten1 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten1 = allatten1 + "25";
//                            Log.e( "attendance: ", allatten1);
//                        }
//                        std_attn_total = std_attendance + allatten1;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 3:
//                        String allatten2 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten2 = allatten2 + "35";
//                            Log.e( "attendance: ", allatten2);
//                        }
//                        std_attn_total = std_attendance + allatten2;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 4:
//                        String allatten3 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten3 = allatten3 + "45";
//                            Log.e( "attendance: ", allatten3);
//                        }
//                        std_attn_total = std_attendance + allatten3;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 5:
//                        String allatten4 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten4 = allatten4 + "55";
//                            Log.e( "attendance: ", allatten4);
//                        }
//                        std_attn_total = std_attendance + allatten4;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 6:
//                        String allatten5 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten5 = allatten5 + "65";
//                            Log.e( "attendance: ", allatten5);
//                        }
//                        std_attn_total = std_attendance + allatten5;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 7:
//                        String allatten6 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten6 = allatten6 + "75";
//                            Log.e( "attendance: ", allatten6);
//                        }
//                        std_attn_total = std_attendance + allatten6;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 8:
//                        String allatten7 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten7 = allatten7 + "85";
//                            Log.e( "attendance: ", allatten7);
//                        }
//                        std_attn_total = std_attendance + allatten7;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 9:
//                        String allatten8 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten8 = allatten8 + "95";
//                            Log.e( "attendance: ", allatten8);
//                        }
//                        std_attn_total = std_attendance + allatten8;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 10:
//                        String allatten9 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten9 = allatten9 + "A5";
//                            Log.e( "attendance: ", allatten9);
//                        }
//                        std_attn_total = std_attendance + allatten9;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 11:
//                        String allatten10 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten10 = allatten10 + "B5";
//                            Log.e( "attendance: ", allatten10);
//                        }
//                        std_attn_total = std_attendance + allatten10;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 12:
//                        String allatten11 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten11 = allatten11 + "C5";
//                            Log.e( "attendance: ", allatten11);
//                        }
//                        std_attn_total = std_attendance + allatten11;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 13:
//                        String allatten12 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten12 = allatten12 + "D5";
//                            Log.e( "attendance: ", allatten12);
//                        }
//                        std_attn_total = std_attendance + allatten12;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 14:
//                        String allatten13 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten13 = allatten13 + "E5";
//                            Log.e( "attendance: ", allatten13);
//                        }
//                        std_attn_total = std_attendance + allatten13;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 15:
//                        String allatten14 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten14 = allatten14 + "F5";
//                            Log.e( "attendance: ", allatten14);
//                        }
//                        std_attn_total = std_attendance + allatten14;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 16:
//                        String allatten15 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten15 = allatten15 + "G5";
//                            Log.e( "attendance: ", allatten15);
//                        }
//                        std_attn_total = std_attendance + allatten15;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 17:
//                        String allatten16 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten16 = allatten16 + "H5";
//                            Log.e( "attendance: ", allatten16);
//                        }
//                        std_attn_total = std_attendance + allatten16;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 18:
//                        String allatten17 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten17 = allatten17 + "I5";
//                            Log.e( "attendance: ", allatten17);
//                        }
//                        std_attn_total = std_attendance + allatten17;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 19:
//                        String allatten18 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten18 = allatten18 + "J5";
//                            Log.e( "attendance: ", allatten18);
//                        }
//                        std_attn_total = std_attendance + allatten18;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 20:
//                        String allatten19 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten19 = allatten19 + "K5";
//                            Log.e( "attendance: ", allatten19);
//                        }
//                        std_attn_total = std_attendance + allatten19;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 21:
//                        String allatten20 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten20 = allatten20 + "L5";
//                            Log.e( "attendance: ", allatten20);
//                        }
//                        std_attn_total = std_attendance + allatten20;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 22:
//                        String allatten21 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten21 = allatten21 + "M5";
//                            Log.e( "attendance: ", allatten21);
//                        }
//                        std_attn_total = std_attendance + allatten21;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 23:
//                        String allatten22 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten22 = allatten22 + "N5";
//                            Log.e( "attendance: ", allatten22);
//                        }
//                        std_attn_total = std_attendance + allatten22;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 24:
//                        String allatten23 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten23 = allatten23 + "O5";
//                            Log.e( "attendance: ", allatten23);
//                        }
//                        std_attn_total = std_attendance + allatten23;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 25:
//                        String allatten24 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten24 = allatten24 + "P5";
//                            Log.e( "attendance: ", allatten24);
//                        }
//                        std_attn_total = std_attendance + allatten24;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 26:
//                        String allatten25 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten25 = allatten25 + "Q5";
//                            Log.e( "attendance: ", allatten25);
//                        }
//                        std_attn_total = std_attendance + allatten25;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 27:
//                        String allatten26 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten26 = allatten26 + "R5";
//                            Log.e( "attendance: ", allatten26);
//                        }
//                        std_attn_total = std_attendance + allatten26;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 28:
//                        String allatten27 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten27 = allatten27 + "S5";
//                            Log.e( "attendance: ", allatten27);
//                        }
//                        std_attn_total = std_attendance + allatten27;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 29:
//                        String allatten28 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten28 = allatten28 + "T5";
//                            Log.e( "attendance: ", allatten28);
//                        }
//                        std_attn_total = std_attendance + allatten28;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 30:
//                        String allatten29 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten29 = allatten29 + "U5";
//                            Log.e( "attendance: ", allatten29);
//                        }
//                        std_attn_total = std_attendance + allatten29;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 31:
//                        String allatten30 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten30 = allatten30 + "V5";
//                            Log.e( "attendance: ", allatten30);
//                        }
//                        std_attn_total = std_attendance + allatten30;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                }
//                break;
//            case 6:
//                switch (day){
//                    case 1:
//                        String allatten = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten = allatten + "16";
//                            Log.e( "attendance: ", allatten);
//                        }
//                        std_attn_total = std_attendance + allatten;
//
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//
//
//                        break;
//                    case 2:
//                        String allatten1 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten1 = allatten1 + "26";
//                            Log.e( "attendance: ", allatten1);
//                        }
//                        std_attn_total = std_attendance + allatten1;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 3:
//                        String allatten2 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten2 = allatten2 + "36";
//                            Log.e( "attendance: ", allatten2);
//                        }
//                        std_attn_total = std_attendance + allatten2;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 4:
//                        String allatten3 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten3 = allatten3 + "46";
//                            Log.e( "attendance: ", allatten3);
//                        }
//                        std_attn_total = std_attendance + allatten3;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 5:
//                        String allatten4 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten4 = allatten4 + "56";
//                            Log.e( "attendance: ", allatten4);
//                        }
//                        std_attn_total = std_attendance + allatten4;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 6:
//                        String allatten5 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten5 = allatten5 + "66";
//                            Log.e( "attendance: ", allatten5);
//                        }
//                        std_attn_total = std_attendance + allatten5;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 7:
//                        String allatten6 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten6 = allatten6 + "76";
//                            Log.e( "attendance: ", allatten6);
//                        }
//                        std_attn_total = std_attendance + allatten6;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 8:
//                        String allatten7 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten7 = allatten7 + "86";
//                            Log.e( "attendance: ", allatten7);
//                        }
//                        std_attn_total = std_attendance + allatten7;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 9:
//                        String allatten8 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten8 = allatten8 + "96";
//                            Log.e( "attendance: ", allatten8);
//                        }
//                        std_attn_total = std_attendance + allatten8;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 10:
//                        String allatten9 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten9 = allatten9 + "A6";
//                            Log.e( "attendance: ", allatten9);
//                        }
//                        std_attn_total = std_attendance + allatten9;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 11:
//                        String allatten10 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten10 = allatten10 + "B6";
//                            Log.e( "attendance: ", allatten10);
//                        }
//                        std_attn_total = std_attendance + allatten10;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 12:
//                        String allatten11 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten11 = allatten11 + "C6";
//                            Log.e( "attendance: ", allatten11);
//                        }
//                        std_attn_total = std_attendance + allatten11;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 13:
//                        String allatten12 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten12 = allatten12 + "D6";
//                            Log.e( "attendance: ", allatten12);
//                        }
//                        std_attn_total = std_attendance + allatten12;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 14:
//                        String allatten13 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten13 = allatten13 + "E6";
//                            Log.e( "attendance: ", allatten13);
//                        }
//                        std_attn_total = std_attendance + allatten13;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 15:
//                        String allatten14 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten14 = allatten14 + "F6";
//                            Log.e( "attendance: ", allatten14);
//                        }
//                        std_attn_total = std_attendance + allatten14;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 16:
//                        String allatten15 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten15 = allatten15 + "G6";
//                            Log.e( "attendance: ", allatten15);
//                        }
//                        std_attn_total = std_attendance + allatten15;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 17:
//                        String allatten16 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten16 = allatten16 + "H6";
//                            Log.e( "attendance: ", allatten16);
//                        }
//                        std_attn_total = std_attendance + allatten16;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 18:
//                        String allatten17 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten17 = allatten17 + "I6";
//                            Log.e( "attendance: ", allatten17);
//                        }
//                        std_attn_total = std_attendance + allatten17;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 19:
//                        String allatten18 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten18 = allatten18 + "J6";
//                            Log.e( "attendance: ", allatten18);
//                        }
//                        std_attn_total = std_attendance + allatten18;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 20:
//                        String allatten19 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten19 = allatten19 + "K6";
//                            Log.e( "attendance: ", allatten19);
//                        }
//                        std_attn_total = std_attendance + allatten19;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 21:
//                        String allatten20 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten20 = allatten20 + "L6";
//                            Log.e( "attendance: ", allatten20);
//                        }
//                        std_attn_total = std_attendance + allatten20;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 22:
//                        String allatten21 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten21 = allatten21 + "M6";
//                            Log.e( "attendance: ", allatten21);
//                        }
//                        std_attn_total = std_attendance + allatten21;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 23:
//                        String allatten22 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten22 = allatten22 + "N6";
//                            Log.e( "attendance: ", allatten22);
//                        }
//                        std_attn_total = std_attendance + allatten22;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 24:
//                        String allatten23 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten23 = allatten23 + "O6";
//                            Log.e( "attendance: ", allatten23);
//                        }
//                        std_attn_total = std_attendance + allatten23;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 25:
//                        String allatten24 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten24 = allatten24 + "P6";
//                            Log.e( "attendance: ", allatten24);
//                        }
//                        std_attn_total = std_attendance + allatten24;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 26:
//                        String allatten25 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten25 = allatten25 + "Q6";
//                            Log.e( "attendance: ", allatten25);
//                        }
//                        std_attn_total = std_attendance + allatten25;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 27:
//                        String allatten26 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten26 = allatten26 + "R6";
//                            Log.e( "attendance: ", allatten26);
//                        }
//                        std_attn_total = std_attendance + allatten26;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 28:
//                        String allatten27 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten27 = allatten27 + "S6";
//                            Log.e( "attendance: ", allatten27);
//                        }
//                        std_attn_total = std_attendance + allatten27;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 29:
//                        String allatten28 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten28 = allatten28 + "T6";
//                            Log.e( "attendance: ", allatten28);
//                        }
//                        std_attn_total = std_attendance + allatten28;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 30:
//                        String allatten29 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten29 = allatten29 + "U6";
//                            Log.e( "attendance: ", allatten29);
//                        }
//                        std_attn_total = std_attendance + allatten29;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                }
//                break;
//            case 7 :
//                switch (day){
//                    case 1:
//                        String allatten = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten = allatten + "17";
//                            Log.e( "attendance: ", allatten);
//                        }
//                        std_attn_total = std_attendance + allatten;
//
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//
//
//                        break;
//                    case 2:
//                        String allatten1 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten1 = allatten1 + "27";
//                            Log.e( "attendance: ", allatten1);
//                        }
//                        std_attn_total = std_attendance + allatten1;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 3:
//                        String allatten2 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten2 = allatten2 + "37";
//                            Log.e( "attendance: ", allatten2);
//                        }
//                        std_attn_total = std_attendance + allatten2;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 4:
//                        String allatten3 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten3 = allatten3 + "47";
//                            Log.e( "attendance: ", allatten3);
//                        }
//                        std_attn_total = std_attendance + allatten3;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 5:
//                        String allatten4 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten4 = allatten4 + "57";
//                            Log.e( "attendance: ", allatten4);
//                        }
//                        std_attn_total = std_attendance + allatten4;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 6:
//                        String allatten5 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten5 = allatten5 + "67";
//                            Log.e( "attendance: ", allatten5);
//                        }
//                        std_attn_total = std_attendance + allatten5;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 7:
//                        String allatten6 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten6 = allatten6 + "77";
//                            Log.e( "attendance: ", allatten6);
//                        }
//                        std_attn_total = std_attendance + allatten6;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 8:
//                        String allatten7 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten7 = allatten7 + "87";
//                            Log.e( "attendance: ", allatten7);
//                        }
//                        std_attn_total = std_attendance + allatten7;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 9:
//                        String allatten8 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten8 = allatten8 + "97";
//                            Log.e( "attendance: ", allatten8);
//                        }
//                        std_attn_total = std_attendance + allatten8;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 10:
//                        String allatten9 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten9 = allatten9 + "A7";
//                            Log.e( "attendance: ", allatten9);
//                        }
//                        std_attn_total = std_attendance + allatten9;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 11:
//                        String allatten10 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten10 = allatten10 + "B7";
//                            Log.e( "attendance: ", allatten10);
//                        }
//                        std_attn_total = std_attendance + allatten10;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 12:
//                        String allatten11 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten11 = allatten11 + "C7";
//                            Log.e( "attendance: ", allatten11);
//                        }
//                        std_attn_total = std_attendance + allatten11;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 13:
//                        String allatten12 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten12 = allatten12 + "D7";
//                            Log.e( "attendance: ", allatten12);
//                        }
//                        std_attn_total = std_attendance + allatten12;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 14:
//                        String allatten13 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten13 = allatten13 + "E7";
//                            Log.e( "attendance: ", allatten13);
//                        }
//                        std_attn_total = std_attendance + allatten13;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 15:
//                        String allatten14 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten14 = allatten14 + "F7";
//                            Log.e( "attendance: ", allatten14);
//                        }
//                        std_attn_total = std_attendance + allatten14;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 16:
//                        String allatten15 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten15 = allatten15 + "G7";
//                            Log.e( "attendance: ", allatten15);
//                        }
//                        std_attn_total = std_attendance + allatten15;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 17:
//                        String allatten16 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten16 = allatten16 + "H7";
//                            Log.e( "attendance: ", allatten16);
//                        }
//                        std_attn_total = std_attendance + allatten16;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 18:
//                        String allatten17 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten17 = allatten17 + "I7";
//                            Log.e( "attendance: ", allatten17);
//                        }
//                        std_attn_total = std_attendance + allatten17;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 19:
//                        String allatten18 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten18 = allatten18 + "J7";
//                            Log.e( "attendance: ", allatten18);
//                        }
//                        std_attn_total = std_attendance + allatten18;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 20:
//                        String allatten19 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten19 = allatten19 + "K7";
//                            Log.e( "attendance: ", allatten19);
//                        }
//                        std_attn_total = std_attendance + allatten19;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 21:
//                        String allatten20 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten20 = allatten20 + "L7";
//                            Log.e( "attendance: ", allatten20);
//                        }
//                        std_attn_total = std_attendance + allatten20;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 22:
//                        String allatten21 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten21 = allatten21 + "M7";
//                            Log.e( "attendance: ", allatten21);
//                        }
//                        std_attn_total = std_attendance + allatten21;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 23:
//                        String allatten22 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten22 = allatten22 + "N7";
//                            Log.e( "attendance: ", allatten22);
//                        }
//                        std_attn_total = std_attendance + allatten22;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 24:
//                        String allatten23 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten23 = allatten23 + "O7";
//                            Log.e( "attendance: ", allatten23);
//                        }
//                        std_attn_total = std_attendance + allatten23;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 25:
//                        String allatten24 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten24 = allatten24 + "P7";
//                            Log.e( "attendance: ", allatten24);
//                        }
//                        std_attn_total = std_attendance + allatten24;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 26:
//                        String allatten25 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten25 = allatten25 + "Q7";
//                            Log.e( "attendance: ", allatten25);
//                        }
//                        std_attn_total = std_attendance + allatten25;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 27:
//                        String allatten26 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten26 = allatten26 + "R7";
//                            Log.e( "attendance: ", allatten26);
//                        }
//                        std_attn_total = std_attendance + allatten26;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 28:
//                        String allatten27 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten27 = allatten27 + "S7";
//                            Log.e( "attendance: ", allatten27);
//                        }
//                        std_attn_total = std_attendance + allatten27;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 29:
//                        String allatten28 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten28 = allatten28 + "T7";
//                            Log.e( "attendance: ", allatten28);
//                        }
//                        std_attn_total = std_attendance + allatten28;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 30:
//                        String allatten29 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten29 = allatten29 + "U7";
//                            Log.e( "attendance: ", allatten29);
//                        }
//                        std_attn_total = std_attendance + allatten29;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 31:
//                        String allatten30 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten30 = allatten30 + "V7";
//                            Log.e( "attendance: ", allatten30);
//                        }
//                        std_attn_total = std_attendance + allatten30;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                }
//                break;
//            case 8:
//                switch (day){
//                    case 1:
//                        String allatten = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten = allatten + "18";
//                            Log.e( "attendance: ", allatten);
//                        }
//                        std_attn_total = std_attendance + allatten;
//
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//
//
//                        break;
//                    case 2:
//                        String allatten1 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten1 = allatten1 + "28";
//                            Log.e( "attendance: ", allatten1);
//                        }
//                        std_attn_total = std_attendance + allatten1;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 3:
//                        String allatten2 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten2 = allatten2 + "38";
//                            Log.e( "attendance: ", allatten2);
//                        }
//                        std_attn_total = std_attendance + allatten2;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 4:
//                        String allatten3 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten3 = allatten3 + "48";
//                            Log.e( "attendance: ", allatten3);
//                        }
//                        std_attn_total = std_attendance + allatten3;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 5:
//                        String allatten4 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten4 = allatten4 + "58";
//                            Log.e( "attendance: ", allatten4);
//                        }
//                        std_attn_total = std_attendance + allatten4;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 6:
//                        String allatten5 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten5 = allatten5 + "68";
//                            Log.e( "attendance: ", allatten5);
//                        }
//                        std_attn_total = std_attendance + allatten5;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 7:
//                        String allatten6 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten6 = allatten6 + "78";
//                            Log.e( "attendance: ", allatten6);
//                        }
//                        std_attn_total = std_attendance + allatten6;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 8:
//                        String allatten7 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten7 = allatten7 + "88";
//                            Log.e( "attendance: ", allatten7);
//                        }
//                        std_attn_total = std_attendance + allatten7;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 9:
//                        String allatten8 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten8 = allatten8 + "98";
//                            Log.e( "attendance: ", allatten8);
//                        }
//                        std_attn_total = std_attendance + allatten8;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 10:
//                        String allatten9 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten9 = allatten9 + "A8";
//                            Log.e( "attendance: ", allatten9);
//                        }
//                        std_attn_total = std_attendance + allatten9;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 11:
//                        String allatten10 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten10 = allatten10 + "B8";
//                            Log.e( "attendance: ", allatten10);
//                        }
//                        std_attn_total = std_attendance + allatten10;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 12:
//                        String allatten11 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten11 = allatten11 + "C8";
//                            Log.e( "attendance: ", allatten11);
//                        }
//                        std_attn_total = std_attendance + allatten11;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 13:
//                        String allatten12 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten12 = allatten12 + "D8";
//                            Log.e( "attendance: ", allatten12);
//                        }
//                        std_attn_total = std_attendance + allatten12;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 14:
//                        String allatten13 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten13 = allatten13 + "E8";
//                            Log.e( "attendance: ", allatten13);
//                        }
//                        std_attn_total = std_attendance + allatten13;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 15:
//                        String allatten14 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten14 = allatten14 + "F8";
//                            Log.e( "attendance: ", allatten14);
//                        }
//                        std_attn_total = std_attendance + allatten14;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 16:
//                        String allatten15 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten15 = allatten15 + "G8";
//                            Log.e( "attendance: ", allatten15);
//                        }
//                        std_attn_total = std_attendance + allatten15;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 17:
//                        String allatten16 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten16 = allatten16 + "H8";
//                            Log.e( "attendance: ", allatten16);
//                        }
//                        std_attn_total = std_attendance + allatten16;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 18:
//                        String allatten17 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten17 = allatten17 + "I8";
//                            Log.e( "attendance: ", allatten17);
//                        }
//                        std_attn_total = std_attendance + allatten17;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 19:
//                        String allatten18 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten18 = allatten18 + "J8";
//                            Log.e( "attendance: ", allatten18);
//                        }
//                        std_attn_total = std_attendance + allatten18;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 20:
//                        String allatten19 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten19 = allatten19 + "K8";
//                            Log.e( "attendance: ", allatten19);
//                        }
//                        std_attn_total = std_attendance + allatten19;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 21:
//                        String allatten20 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten20 = allatten20 + "L8";
//                            Log.e( "attendance: ", allatten20);
//                        }
//                        std_attn_total = std_attendance + allatten20;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 22:
//                        String allatten21 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten21 = allatten21 + "M8";
//                            Log.e( "attendance: ", allatten21);
//                        }
//                        std_attn_total = std_attendance + allatten21;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 23:
//                        String allatten22 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten22 = allatten22 + "N8";
//                            Log.e( "attendance: ", allatten22);
//                        }
//                        std_attn_total = std_attendance + allatten22;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 24:
//                        String allatten23 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten23 = allatten23 + "O8";
//                            Log.e( "attendance: ", allatten23);
//                        }
//                        std_attn_total = std_attendance + allatten23;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 25:
//                        String allatten24 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten24 = allatten24 + "P8";
//                            Log.e( "attendance: ", allatten24);
//                        }
//                        std_attn_total = std_attendance + allatten24;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 26:
//                        String allatten25 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten25 = allatten25 + "Q8";
//                            Log.e( "attendance: ", allatten25);
//                        }
//                        std_attn_total = std_attendance + allatten25;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 27:
//                        String allatten26 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten26 = allatten26 + "R8";
//                            Log.e( "attendance: ", allatten26);
//                        }
//                        std_attn_total = std_attendance + allatten26;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 28:
//                        String allatten27 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten27 = allatten27 + "S8";
//                            Log.e( "attendance: ", allatten27);
//                        }
//                        std_attn_total = std_attendance + allatten27;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 29:
//                        String allatten28 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten28 = allatten28 + "T8";
//                            Log.e( "attendance: ", allatten28);
//                        }
//                        std_attn_total = std_attendance + allatten28;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 30:
//                        String allatten29 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten29 = allatten29 + "U8";
//                            Log.e( "attendance: ", allatten29);
//                        }
//                        std_attn_total = std_attendance + allatten29;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 31:
//                        String allatten30 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten30 = allatten30 + "V8";
//                            Log.e( "attendance: ", allatten30);
//                        }
//                        std_attn_total = std_attendance + allatten30;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                }
//                break;
//            case 9:
//                switch (day){
//                    case 1:
//                        String allatten = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten = allatten + "19";
//                            Log.e( "attendance: ", allatten);
//                        }
//                        std_attn_total = std_attendance + allatten;
//
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//
//
//                        break;
//                    case 2:
//                        String allatten1 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten1 = allatten1 + "29";
//                            Log.e( "attendance: ", allatten1);
//                        }
//                        std_attn_total = std_attendance + allatten1;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 3:
//                        String allatten2 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten2 = allatten2 + "39";
//                            Log.e( "attendance: ", allatten2);
//                        }
//                        std_attn_total = std_attendance + allatten2;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 4:
//                        String allatten3 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten3 = allatten3 + "49";
//                            Log.e( "attendance: ", allatten3);
//                        }
//                        std_attn_total = std_attendance + allatten3;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 5:
//                        String allatten4 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten4 = allatten4 + "59";
//                            Log.e( "attendance: ", allatten4);
//                        }
//                        std_attn_total = std_attendance + allatten4;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 6:
//                        String allatten5 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten5 = allatten5 + "69";
//                            Log.e( "attendance: ", allatten5);
//                        }
//                        std_attn_total = std_attendance + allatten5;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 7:
//                        String allatten6 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten6 = allatten6 + "79";
//                            Log.e( "attendance: ", allatten6);
//                        }
//                        std_attn_total = std_attendance + allatten6;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 8:
//                        String allatten7 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten7 = allatten7 + "89";
//                            Log.e( "attendance: ", allatten7);
//                        }
//                        std_attn_total = std_attendance + allatten7;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 9:
//                        String allatten8 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten8 = allatten8 + "99";
//                            Log.e( "attendance: ", allatten8);
//                        }
//                        std_attn_total = std_attendance + allatten8;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 10:
//                        String allatten9 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten9 = allatten9 + "A9";
//                            Log.e( "attendance: ", allatten9);
//                        }
//                        std_attn_total = std_attendance + allatten9;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 11:
//                        String allatten10 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten10 = allatten10 + "B9";
//                            Log.e( "attendance: ", allatten10);
//                        }
//                        std_attn_total = std_attendance + allatten10;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 12:
//                        String allatten11 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten11 = allatten11 + "C9";
//                            Log.e( "attendance: ", allatten11);
//                        }
//                        std_attn_total = std_attendance + allatten11;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 13:
//                        String allatten12 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten12 = allatten12 + "D9";
//                            Log.e( "attendance: ", allatten12);
//                        }
//                        std_attn_total = std_attendance + allatten12;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 14:
//                        String allatten13 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten13 = allatten13 + "E9";
//                            Log.e( "attendance: ", allatten13);
//                        }
//                        std_attn_total = std_attendance + allatten13;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 15:
//                        String allatten14 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten14 = allatten14 + "F9";
//                            Log.e( "attendance: ", allatten14);
//                        }
//                        std_attn_total = std_attendance + allatten14;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 16:
//                        String allatten15 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten15 = allatten15 + "G9";
//                            Log.e( "attendance: ", allatten15);
//                        }
//                        std_attn_total = std_attendance + allatten15;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 17:
//                        String allatten16 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten16 = allatten16 + "H9";
//                            Log.e( "attendance: ", allatten16);
//                        }
//                        std_attn_total = std_attendance + allatten16;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 18:
//                        String allatten17 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten17 = allatten17 + "I9";
//                            Log.e( "attendance: ", allatten17);
//                        }
//                        std_attn_total = std_attendance + allatten17;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 19:
//                        String allatten18 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten18 = allatten18 + "J9";
//                            Log.e( "attendance: ", allatten18);
//                        }
//                        std_attn_total = std_attendance + allatten18;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 20:
//                        String allatten19 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten19 = allatten19 + "K9";
//                            Log.e( "attendance: ", allatten19);
//                        }
//                        std_attn_total = std_attendance + allatten19;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 21:
//                        String allatten20 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten20 = allatten20 + "L9";
//                            Log.e( "attendance: ", allatten20);
//                        }
//                        std_attn_total = std_attendance + allatten20;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 22:
//                        String allatten21 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten21 = allatten21 + "M9";
//                            Log.e( "attendance: ", allatten21);
//                        }
//                        std_attn_total = std_attendance + allatten21;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 23:
//                        String allatten22 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten22 = allatten22 + "N9";
//                            Log.e( "attendance: ", allatten22);
//                        }
//                        std_attn_total = std_attendance + allatten22;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 24:
//                        String allatten23 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten23 = allatten23 + "O9";
//                            Log.e( "attendance: ", allatten23);
//                        }
//                        std_attn_total = std_attendance + allatten23;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 25:
//                        String allatten24 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten24 = allatten24 + "P9";
//                            Log.e( "attendance: ", allatten24);
//                        }
//                        std_attn_total = std_attendance + allatten24;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 26:
//                        String allatten25 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten25 = allatten25 + "Q9";
//                            Log.e( "attendance: ", allatten25);
//                        }
//                        std_attn_total = std_attendance + allatten25;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 27:
//                        String allatten26 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten26 = allatten26 + "R9";
//                            Log.e( "attendance: ", allatten26);
//                        }
//                        std_attn_total = std_attendance + allatten26;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 28:
//                        String allatten27 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten27 = allatten27 + "S9";
//                            Log.e( "attendance: ", allatten27);
//                        }
//                        std_attn_total = std_attendance + allatten27;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 29:
//                        String allatten28 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten28 = allatten28 + "T9";
//                            Log.e( "attendance: ", allatten28);
//                        }
//                        std_attn_total = std_attendance + allatten28;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 30:
//                        String allatten29 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten29 = allatten29 + "U9";
//                            Log.e( "attendance: ", allatten29);
//                        }
//                        std_attn_total = std_attendance + allatten29;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//
//                }
//                break;
//            case 10:
//                switch (day){
//                    case 1:
//                        String allatten = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten = allatten + "1A";
//                            Log.e( "attendance: ", allatten);
//                        }
//                        std_attn_total = std_attendance + allatten;
//
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//
//
//                        break;
//                    case 2:
//                        String allatten1 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten1 = allatten1 + "2A";
//                            Log.e( "attendance: ", allatten1);
//                        }
//                        std_attn_total = std_attendance + allatten1;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 3:
//                        String allatten2 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten2 = allatten2 + "3A";
//                            Log.e( "attendance: ", allatten2);
//                        }
//                        std_attn_total = std_attendance + allatten2;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 4:
//                        String allatten3 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten3 = allatten3 + "4A";
//                            Log.e( "attendance: ", allatten3);
//                        }
//                        std_attn_total = std_attendance + allatten3;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 5:
//                        String allatten4 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten4 = allatten4 + "5A";
//                            Log.e( "attendance: ", allatten4);
//                        }
//                        std_attn_total = std_attendance + allatten4;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 6:
//                        String allatten5 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten5 = allatten5 + "6A";
//                            Log.e( "attendance: ", allatten5);
//                        }
//                        std_attn_total = std_attendance + allatten5;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 7:
//                        String allatten6 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten6 = allatten6 + "7A";
//                            Log.e( "attendance: ", allatten6);
//                        }
//                        std_attn_total = std_attendance + allatten6;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 8:
//                        String allatten7 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten7 = allatten7 + "8A";
//                            Log.e( "attendance: ", allatten7);
//                        }
//                        std_attn_total = std_attendance + allatten7;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 9:
//                        String allatten8 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten8 = allatten8 + "9A";
//                            Log.e( "attendance: ", allatten8);
//                        }
//                        std_attn_total = std_attendance + allatten8;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 10:
//                        String allatten9 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten9 = allatten9 + "AA";
//                            Log.e( "attendance: ", allatten9);
//                        }
//                        std_attn_total = std_attendance + allatten9;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 11:
//                        String allatten10 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten10 = allatten10 + "BA";
//                            Log.e( "attendance: ", allatten10);
//                        }
//                        std_attn_total = std_attendance + allatten10;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 12:
//                        String allatten11 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten11 = allatten11 + "CA";
//                            Log.e( "attendance: ", allatten11);
//                        }
//                        std_attn_total = std_attendance + allatten11;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 13:
//                        String allatten12 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten12 = allatten12 + "DA";
//                            Log.e( "attendance: ", allatten12);
//                        }
//                        std_attn_total = std_attendance + allatten12;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 14:
//                        String allatten13 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten13 = allatten13 + "EA";
//                            Log.e( "attendance: ", allatten13);
//                        }
//                        std_attn_total = std_attendance + allatten13;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 15:
//                        String allatten14 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten14 = allatten14 + "FA";
//                            Log.e( "attendance: ", allatten14);
//                        }
//                        std_attn_total = std_attendance + allatten14;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 16:
//                        String allatten15 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten15 = allatten15 + "GA";
//                            Log.e( "attendance: ", allatten15);
//                        }
//                        std_attn_total = std_attendance + allatten15;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 17:
//                        String allatten16 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten16 = allatten16 + "HA";
//                            Log.e( "attendance: ", allatten16);
//                        }
//                        std_attn_total = std_attendance + allatten16;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 18:
//                        String allatten17 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten17 = allatten17 + "IA";
//                            Log.e( "attendance: ", allatten17);
//                        }
//                        std_attn_total = std_attendance + allatten17;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 19:
//                        String allatten18 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten18 = allatten18 + "JA";
//                            Log.e( "attendance: ", allatten18);
//                        }
//                        std_attn_total = std_attendance + allatten18;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 20:
//                        String allatten19 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten19 = allatten19 + "KA";
//                            Log.e( "attendance: ", allatten19);
//                        }
//                        std_attn_total = std_attendance + allatten19;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 21:
//                        String allatten20 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten20 = allatten20 + "LA";
//                            Log.e( "attendance: ", allatten20);
//                        }
//                        std_attn_total = std_attendance + allatten20;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 22:
//                        String allatten21 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten21 = allatten21 + "MA";
//                            Log.e( "attendance: ", allatten21);
//                        }
//                        std_attn_total = std_attendance + allatten21;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 23:
//                        String allatten22 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten22 = allatten22 + "NA";
//                            Log.e( "attendance: ", allatten22);
//                        }
//                        std_attn_total = std_attendance + allatten22;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 24:
//                        String allatten23 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten23 = allatten23 + "OA";
//                            Log.e( "attendance: ", allatten23);
//                        }
//                        std_attn_total = std_attendance + allatten23;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 25:
//                        String allatten24 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten24 = allatten24 + "PA";
//                            Log.e( "attendance: ", allatten24);
//                        }
//                        std_attn_total = std_attendance + allatten24;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 26:
//                        String allatten25 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten25 = allatten25 + "QA";
//                            Log.e( "attendance: ", allatten25);
//                        }
//                        std_attn_total = std_attendance + allatten25;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 27:
//                        String allatten26 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten26 = allatten26 + "RA";
//                            Log.e( "attendance: ", allatten26);
//                        }
//                        std_attn_total = std_attendance + allatten26;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 28:
//                        String allatten27 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten27 = allatten27 + "SA";
//                            Log.e( "attendance: ", allatten27);
//                        }
//                        std_attn_total = std_attendance + allatten27;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 29:
//                        String allatten28 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten28 = allatten28 + "TA";
//                            Log.e( "attendance: ", allatten28);
//                        }
//                        std_attn_total = std_attendance + allatten28;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 30:
//                        String allatten29 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten29 = allatten29 + "UA";
//                            Log.e( "attendance: ", allatten29);
//                        }
//                        std_attn_total = std_attendance + allatten29;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 31:
//                        String allatten30 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten30 = allatten30 + "VA";
//                            Log.e( "attendance: ", allatten30);
//                        }
//                        std_attn_total = std_attendance + allatten30;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                }
//                break;
//            case 11:
//                switch (day){
//                    case 1:
//                        String allatten = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten = allatten + "1B";
//                            Log.e( "attendance: ", allatten);
//                        }
//                        std_attn_total = std_attendance + allatten;
//
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//
//
//                        break;
//                    case 2:
//                        String allatten1 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten1 = allatten1 + "2B";
//                            Log.e( "attendance: ", allatten1);
//                        }
//                        std_attn_total = std_attendance + allatten1;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 3:
//                        String allatten2 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten2 = allatten2 + "3B";
//                            Log.e( "attendance: ", allatten2);
//                        }
//                        std_attn_total = std_attendance + allatten2;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 4:
//                        String allatten3 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten3 = allatten3 + "4B";
//                            Log.e( "attendance: ", allatten3);
//                        }
//                        std_attn_total = std_attendance + allatten3;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 5:
//                        String allatten4 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten4 = allatten4 + "5B";
//                            Log.e( "attendance: ", allatten4);
//                        }
//                        std_attn_total = std_attendance + allatten4;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 6:
//                        String allatten5 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten5 = allatten5 + "6B";
//                            Log.e( "attendance: ", allatten5);
//                        }
//                        std_attn_total = std_attendance + allatten5;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 7:
//                        String allatten6 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten6 = allatten6 + "7B";
//                            Log.e( "attendance: ", allatten6);
//                        }
//                        std_attn_total = std_attendance + allatten6;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 8:
//                        String allatten7 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten7 = allatten7 + "8B";
//                            Log.e( "attendance: ", allatten7);
//                        }
//                        std_attn_total = std_attendance + allatten7;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 9:
//                        String allatten8 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten8 = allatten8 + "9B";
//                            Log.e( "attendance: ", allatten8);
//                        }
//                        std_attn_total = std_attendance + allatten8;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 10:
//                        String allatten9 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten9 = allatten9 + "AB";
//                            Log.e( "attendance: ", allatten9);
//                        }
//                        std_attn_total = std_attendance + allatten9;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 11:
//                        String allatten10 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten10 = allatten10 + "BB";
//                            Log.e( "attendance: ", allatten10);
//                        }
//                        std_attn_total = std_attendance + allatten10;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 12:
//                        String allatten11 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten11 = allatten11 + "CB";
//                            Log.e( "attendance: ", allatten11);
//                        }
//                        std_attn_total = std_attendance + allatten11;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 13:
//                        String allatten12 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten12 = allatten12 + "DB";
//                            Log.e( "attendance: ", allatten12);
//                        }
//                        std_attn_total = std_attendance + allatten12;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 14:
//                        String allatten13 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten13 = allatten13 + "EB";
//                            Log.e( "attendance: ", allatten13);
//                        }
//                        std_attn_total = std_attendance + allatten13;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 15:
//                        String allatten14 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten14 = allatten14 + "FB";
//                            Log.e( "attendance: ", allatten14);
//                        }
//                        std_attn_total = std_attendance + allatten14;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 16:
//                        String allatten15 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten15 = allatten15 + "GB";
//                            Log.e( "attendance: ", allatten15);
//                        }
//                        std_attn_total = std_attendance + allatten15;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 17:
//                        String allatten16 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten16 = allatten16 + "HB";
//                            Log.e( "attendance: ", allatten16);
//                        }
//                        std_attn_total = std_attendance + allatten16;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 18:
//                        String allatten17 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten17 = allatten17 + "IB";
//                            Log.e( "attendance: ", allatten17);
//                        }
//                        std_attn_total = std_attendance + allatten17;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 19:
//                        String allatten18 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten18 = allatten18 + "JB";
//                            Log.e( "attendance: ", allatten18);
//                        }
//                        std_attn_total = std_attendance + allatten18;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 20:
//                        String allatten19 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten19 = allatten19 + "KB";
//                            Log.e( "attendance: ", allatten19);
//                        }
//                        std_attn_total = std_attendance + allatten19;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 21:
//                        String allatten20 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten20 = allatten20 + "LB";
//                            Log.e( "attendance: ", allatten20);
//                        }
//                        std_attn_total = std_attendance + allatten20;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 22:
//                        String allatten21 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten21 = allatten21 + "MB";
//                            Log.e( "attendance: ", allatten21);
//                        }
//                        std_attn_total = std_attendance + allatten21;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 23:
//                        String allatten22 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten22 = allatten22 + "NB";
//                            Log.e( "attendance: ", allatten22);
//                        }
//                        std_attn_total = std_attendance + allatten22;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 24:
//                        String allatten23 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten23 = allatten23 + "OB";
//                            Log.e( "attendance: ", allatten23);
//                        }
//                        std_attn_total = std_attendance + allatten23;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 25:
//                        String allatten24 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten24 = allatten24 + "PB";
//                            Log.e( "attendance: ", allatten24);
//                        }
//                        std_attn_total = std_attendance + allatten24;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 26:
//                        String allatten25 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten25 = allatten25 + "QB";
//                            Log.e( "attendance: ", allatten25);
//                        }
//                        std_attn_total = std_attendance + allatten25;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 27:
//                        String allatten26 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten26 = allatten26 + "RB";
//                            Log.e( "attendance: ", allatten26);
//                        }
//                        std_attn_total = std_attendance + allatten26;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 28:
//                        String allatten27 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten27 = allatten27 + "SB";
//                            Log.e( "attendance: ", allatten27);
//                        }
//                        std_attn_total = std_attendance + allatten27;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 29:
//                        String allatten28 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten28 = allatten28 + "TB";
//                            Log.e( "attendance: ", allatten28);
//                        }
//                        std_attn_total = std_attendance + allatten28;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 30:
//                        String allatten29 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten29 = allatten29 + "UB";
//                            Log.e( "attendance: ", allatten29);
//                        }
//                        std_attn_total = std_attendance + allatten29;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                }
//                break;
//            case 12:
//                switch (day){
//                    case 1:
//                        String allatten = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten = allatten + "1C";
//                            Log.e( "attendance: ", allatten);
//                        }
//                        std_attn_total = std_attendance + allatten;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 2:
//                        String allatten1 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten1 = allatten1 + "2C";
//                            Log.e( "attendance: ", allatten1);
//                        }
//                        std_attn_total = std_attendance + allatten1;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 3:
//                        String allatten2 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten2 = allatten2 + "3C";
//                            Log.e( "attendance: ", allatten2);
//                        }
//                        std_attn_total = std_attendance + allatten2;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 4:
//                        String allatten3 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten3 = allatten3 + "4C";
//                            Log.e( "attendance: ", allatten3);
//                        }
//                        std_attn_total = std_attendance + allatten3;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 5:
//                        String allatten4 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten4 = allatten4 + "5C";
//                            Log.e( "attendance: ", allatten4);
//                        }
//                        std_attn_total = std_attendance + allatten4;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 6:
//                        String allatten5 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten5 = allatten5 + "6C";
//                            Log.e( "attendance: ", allatten5);
//                        }
//                        std_attn_total = std_attendance + allatten5;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 7:
//                        String allatten6 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten6 = allatten6 + "7C";
//                            Log.e( "attendance: ", allatten6);
//                        }
//                        std_attn_total = std_attendance + allatten6;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 8:
//                        String allatten7 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten7 = allatten7 + "8C";
//                            Log.e( "attendance: ", allatten7);
//                        }
//                        std_attn_total = std_attendance + allatten7;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 9:
//                        String allatten8 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten8 = allatten8 + "9C";
//                            Log.e( "attendance: ", allatten8);
//                        }
//                        std_attn_total = std_attendance + allatten8;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 10:
//                        String allatten9 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten9 = allatten9 + "AC";
//                            Log.e( "attendance: ", allatten9);
//                        }
//                        std_attn_total = std_attendance + allatten9;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 11:
//                        String allatten10 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten10 = allatten10 + "BC";
//                            Log.e( "attendance: ", allatten10);
//                        }
//                        std_attn_total = std_attendance + allatten10;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 12:
//                        String allatten11 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten11 = allatten11 + "CC";
//                            Log.e( "attendance: ", allatten11);
//                        }
//                        std_attn_total = std_attendance + allatten11;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 13:
//                        String allatten12 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten12 = allatten12 + "DC";
//                            Log.e( "attendance: ", allatten12);
//                        }
//                        std_attn_total = std_attendance + allatten12;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 14:
//                        String allatten13 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten13 = allatten13 + "EC";
//                            Log.e( "attendance: ", allatten13);
//                        }
//                        std_attn_total = std_attendance + allatten13;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 15:
//                        String allatten14 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten14 = allatten14 + "FC";
//                            Log.e( "attendance: ", allatten14);
//                        }
//                        std_attn_total = std_attendance + allatten14;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 16:
//                        String allatten15 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten15 = allatten15 + "GC";
//                            Log.e( "attendance: ", allatten15);
//                        }
//                        std_attn_total = std_attendance + allatten15;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 17:
//                        String allatten16 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten16 = allatten16 + "HC";
//                            Log.e( "attendance: ", allatten16);
//                        }
//                        std_attn_total = std_attendance + allatten16;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 18:
//                        String allatten17 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten17 = allatten17 + "IC";
//                            Log.e( "attendance: ", allatten17);
//                        }
//                        std_attn_total = std_attendance + allatten17;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 19:
//                        String allatten18 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten18 = allatten18 + "JC";
//                            Log.e( "attendance: ", allatten18);
//                        }
//                        std_attn_total = std_attendance + allatten18;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 20:
//                        String allatten19 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten19 = allatten19 + "KC";
//                            Log.e( "attendance: ", allatten19);
//                        }
//                        std_attn_total = std_attendance + allatten19;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 21:
//                        String allatten20 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten20 = allatten20 + "LC";
//                            Log.e( "attendance: ", allatten20);
//                        }
//                        std_attn_total = std_attendance + allatten20;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 22:
//                        String allatten21 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten21 = allatten21 + "MC";
//                            Log.e( "attendance: ", allatten21);
//                        }
//                        std_attn_total = std_attendance + allatten21;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 23:
//                        String allatten22 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten22 = allatten22 + "NC";
//                            Log.e( "attendance: ", allatten22);
//                        }
//                        std_attn_total = std_attendance + allatten22;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 24:
//                        String allatten23 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten23 = allatten23 + "OC";
//                            Log.e( "attendance: ", allatten23);
//                        }
//                        std_attn_total = std_attendance + allatten23;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 25:
//                        String allatten24 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten24 = allatten24 + "PC";
//                            Log.e( "attendance: ", allatten24);
//                        }
//                        std_attn_total = std_attendance + allatten24;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 26:
//                        String allatten25 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten25 = allatten25 + "QC";
//                            Log.e( "attendance: ", allatten25);
//                        }
//                        std_attn_total = std_attendance + allatten25;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 27:
//                        String allatten26 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten26 = allatten26 + "RC";
//                            Log.e( "attendance: ", allatten26);
//                        }
//                        std_attn_total = std_attendance + allatten26;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 28:
//                        String allatten27 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten27 = allatten27 + "SC";
//                            Log.e( "attendance: ", allatten27);
//                        }
//                        std_attn_total = std_attendance + allatten27;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 29:
//                        String allatten28 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten28 = allatten28 + "TC";
//                            Log.e( "attendance: ", allatten28);
//                        }
//                        std_attn_total = std_attendance + allatten28;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 30:
//                        String allatten29 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten29 = allatten29 + "UC";
//                            Log.e( "attendance: ", allatten29);
//                        }
//                        std_attn_total = std_attendance + allatten29;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                    case 31:
//                        String allatten30 = "";
//
//                        for (int i = 0;i < attn_class;i++)
//                        {
//                            allatten30 = allatten30 + "VC";
//                            Log.e( "attendance: ", allatten30);
//                        }
//                        std_attn_total = std_attendance + allatten30;
//                        Log.e( "attendance: ", std_attn_total);
//                        add_student(std_attn_total,Student_ID);
//                        Log.e( "attendance: ", String.valueOf(std_attn_total.length()));
//                        break;
//                }
//                break;
//        }
    }


    private void printInvoice(){
        Student_details student_details = Student_details.getInstance();
        VariableClass variableClass = VariableClass.getInstance();
        PdfDocument myPdfDocuments = new PdfDocument();
        Paint myPaint = new Paint();

        PdfDocument.PageInfo myPageInfo = new PdfDocument.PageInfo.Builder(1000,1700,1).create();
        PdfDocument.Page myPage = myPdfDocuments.startPage(myPageInfo);
        Canvas canvas = myPage.getCanvas();

        myPaint.setTextSize(40);
        canvas.drawText("Bangladesh University of Bussiness and Technology",30,80,myPaint);

        myPaint.setTextSize(20);
        canvas.drawText("Road no 8,Rupnagar residential area,mirpur,Dhaka",30,120,myPaint);

//        myPaint.setTextAlign(Paint.Align.RIGHT);
//        canvas.drawText("Intake : ",canvas.getWidth()-40,40,myPaint);
//        canvas.drawText(String.valueOf(student_details.getIntake()),canvas.getWidth()-40,80,myPaint);

        myPaint.setColor(Color.rgb(150,150,150));
        canvas.drawRect(30,150,canvas.getWidth()-30,160,myPaint);

        myPaint.setColor(Color.BLACK);
        canvas.drawText("Date: ",50,200,myPaint);
        canvas.drawText(currentDateTimeString,250,200,myPaint);
        Log.e("Date: ", String.valueOf(currentDateTimeString));

        canvas.drawText("Time: ",620,200,myPaint);
        myPaint.setTextAlign(Paint.Align.RIGHT);
        canvas.drawText(currentTimeString,canvas.getWidth()-40,200,myPaint);
        myPaint.setTextAlign(Paint.Align.LEFT);
       Log.e("Time: ", currentTimeString);

        myPaint.setColor(Color.rgb(150,150,150));
        canvas.drawRect(30,250,canvas.getWidth()-30,300,myPaint);

        myPaint.setColor(Color.WHITE);
        canvas.drawText("Department : ",50,285,myPaint);
        canvas.drawText(String.valueOf(student_details.getDEPT()),250,285,myPaint);
        Log.e("Dept: ", String.valueOf(student_details.getDEPT()));

        canvas.drawText("Semester : ",620,285,myPaint);
        canvas.drawText(String.valueOf(variableClass.getS_name()),750,285,myPaint);
        Log.e("Semester: ", String.valueOf(variableClass.getS_name()));

        myPaint.setColor(Color.BLACK);
        canvas.drawText("Course Code : ",30,350,myPaint);
        canvas.drawText(String.valueOf(student_details.getProgram()),250,350,myPaint);
        Log.e("course : ", String.valueOf(student_details.getProgram()));

        canvas.drawText("Intake : ",620,350,myPaint);
        canvas.drawText(String.valueOf(student_details.getIntake()),720,350,myPaint);
        Log.e("intake: ", String.valueOf(student_details.getIntake()));

        canvas.drawText("Section : ",30,420,myPaint);
        canvas.drawText(String.valueOf(student_details.getSection()),250,420,myPaint);
        Log.e("section: ", String.valueOf(student_details.getSection()));

        myPaint.setColor(Color.rgb(150,150,150));
        canvas.drawRect(30,450,canvas.getWidth()-30,500,myPaint);

        myPaint.setColor(Color.WHITE);
        canvas.drawText("Id",80,485,myPaint);
        canvas.drawText("Name",350,485,myPaint);
        canvas.drawText("Attendance",700,485,myPaint);
        myPaint.setColor(Color.BLACK);
        //canvas.drawText("%",canvas.getWidth()-40,435,myPaint);
        int lenth = variableClass.getSTD_id().size();
        int Y = 500;
        for (int i = 0;i<lenth;i++)
        {
            Y = Y + 30;
            canvas.drawText(variableClass.getSTD_id().get(i),80,Y,myPaint);
            Log.e("ID: ", variableClass.getSTD_id().get(i));
            canvas.drawText(variableClass.getSTD_name().get(i),350,Y,myPaint);
            Log.e("Name: ", variableClass.getSTD_name().get(i));
            canvas.drawText(total_student_attendance.get(i),700,Y,myPaint);
            canvas.drawText("%",740,Y,myPaint);
            Log.e("Attend: ", total_student_attendance.get(i));
        }

        myPaint.setColor(Color.BLACK);
        myPaint.setTextAlign(Paint.Align.LEFT);




        myPdfDocuments.finishPage(myPage);
        File file = new File(this.getExternalFilesDir("/"),student_details.getProgram()+"student_Attendance.pdf");
        try {
            myPdfDocuments.writeTo(new FileOutputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        myPdfDocuments.close();


    }



    private void showDialog() {
        try {
            progressDialog.setMessage(getString(R.string.please_wait));
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    private void dismissProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        dismissProgressDialog();
        super.onDestroy();
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
            AlertDialog.Builder builder = new AlertDialog.Builder(View_student.this);
            builder.setTitle(R.string.app_name);
            builder.setIcon(R.mipmap.ic_launcher);
            builder.setMessage("Do you want to SignOut?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent in = new Intent(View_student.this,Login.class);
                            in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(in);
                            finish();
//                            Intent intent = new Intent(View_student.this, Login.class);
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

            Intent intent = new Intent(View_student.this, teacher_Profile.class);
            startActivity(intent);
        }

        if (item.getItemId()==R.id.DashboardId)
        {
            Intent Intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(Intent);
        }
        if (item.getItemId()==R.id.About)
        {

            Intent intent = new Intent(View_student.this, About.class);
            startActivity(intent);
        }

        if (item.getItemId()==R.id.exit)
        {

            AlertDialog.Builder builder = new AlertDialog.Builder(View_student.this);
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
