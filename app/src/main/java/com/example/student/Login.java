package com.example.student;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.student.model.TeacherData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends BaseActivity {

    Button btn_login;


    EditText et_username,et_password;
    //public VariableClass VariableClass;
    private RequestQueue mQueue;
    public String Response;
    public String name,f_code,room,email,user_name,user_pass;
    public String username,password;
    private CheckBox remember_me;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    TeacherData teacherData = new TeacherData();
    VariableClass variable = VariableClass.getInstance();

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mQueue = Volley.newRequestQueue(this);
        progressDialog = new ProgressDialog(this);

        sharedPreferences = getApplicationContext().getSharedPreferences("login", MODE_PRIVATE);
        String key_name = sharedPreferences.getString("name", "");
        String key_pass = sharedPreferences.getString("pass", "");
        btn_login = (Button) findViewById(R.id.Login_Button);
        et_username = (EditText) findViewById(R.id.user_name);
        et_password = (EditText) findViewById(R.id.Password);
        remember_me = (CheckBox) findViewById(R.id.remember_me);
        final String username = et_username.getText().toString();
        final String password = et_password.getText().toString();


        et_username.setText(sharedPreferences.getString("key_name", ""));
        et_password.setText(sharedPreferences.getString("key_pass", ""));

        String au_name = sharedPreferences.getString("key_name", null);
        String au_pass = sharedPreferences.getString("key_pass", null);
        if (au_name !=null && au_pass != null){
            progressDialog.setMessage("Please Wait.....");
            progressDialog.show();
            teacher_Login();
        }
        else{
            SharedPreferences preferences =getSharedPreferences("login",Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.remove("key_name");
            editor.remove("key_pass");
            editor.commit();
        }

        remember_me.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean b) {
                if (buttonView.isChecked()){
                    editor = sharedPreferences.edit();
                    String username = et_username.getText().toString().trim();
                    String password = et_password.getText().toString().trim();
                    editor.putString("key_name", username);
                    editor.putString("key_pass", password);
                    editor.commit();
                } else{
                    SharedPreferences preferences =getSharedPreferences("login",Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.remove("key_name");
                    editor.remove("key_pass");
                    editor.commit();
                    return;
                }
            }
        });




        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkconnection(Login.this)){

                    progressDialog.setMessage("Login.....");
                    progressDialog.show();

                    //login();
                    //getMovieData();
                    teacher_Login();
                }


            }
        });

    }
    private boolean checkconnection(Login login){
        ConnectivityManager connectivityManager = (ConnectivityManager) login.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wificon = connectivityManager.getNetworkInfo(connectivityManager.TYPE_WIFI);
        NetworkInfo mobilecon = connectivityManager.getNetworkInfo(connectivityManager.TYPE_MOBILE);
        if (((wificon != null && wificon.isConnected()) || (mobilecon != null && mobilecon.isConnected()))){
            return true;
        }
        else{
            CustomDialog();
            return false;

        }

    }
    private void CustomDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
        builder.setTitle(R.string.app_name);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setMessage("Please connect to the internet to process further")
                .setCancelable(false)
                .setPositiveButton("Connect", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        startActivity(new Intent(getApplicationContext(), Login.class));
                        finish();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
    /*public void login1()
    {
        final String username = et_username.getText().toString();
        final String password = et_password.getText().toString();
        StringRequest request = new StringRequest(Request.Method.POST, VariableClass.URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.contains("200"))
                {
                    Intent intent = new Intent(Login.this, MainActivity.class);
                    intent.putExtra("username", username);
                    intent.putExtra("password", password);
                    startActivity(intent);


                }else {
                    Toast.makeText(getApplicationContext(), "Wrong Username or Password", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError{
                Map<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("password", password);
                return params;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(request);
    }*/
    /*private void getMovieData() {

        showDialog();

        final String username = et_username.getText().toString();
        final String password = et_password.getText().toString();

//        JsonArrayRequest movieRequest = new JsonArrayRequest(Request.Method.POST, VariableClass.URL1, null, new Response.Listener<JSONArray>() {
//
//            @Override
//            public void onResponse(JSONArray response) {
//
//                dismissProgressDialog();
//
//                try {
//
//                    Log.e("Response ", response.toString());
//
//                    teacherDataList.clear();
//
//                    for(int i=0; i<response.length(); i++) {
//
//                        JSONObject object = response.getJSONObject(i);
//
//                        TeacherData teacherData = new TeacherData();
//
//                        teacherData.setName(object.getString("Name"));
//                        teacherData.setUserName(object.getString("username"));
//                        teacherData.setfCode(object.getString("F_code"));
//                        teacherData.setEmail(object.getString("Email"));
//                        teacherData.setRoom(object.getString("Room"));
//                        teacherData.setPassword(object.getString("password"));
//                        teacherData.setOTP(object.getString("OTP"));
//
//                        teacherDataList.add(teacherData);
//                    }
//
//                    Log.e("StudentView Size ", ""+teacherDataList.size());
//                }
//                catch (Exception e) {
//                    Log.e("Response Error ", e.getMessage());
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.e("GetMovieData ", error.getMessage());
//            }
//        });

        StringRequest movieRequest = new StringRequest(Request.Method.POST, VariableClass.URL1,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        dismissProgressDialog();
                        try {

                            Log.e("Response ", response);

                            JSONObject jsonResponse = new JSONObject(response);
                            JSONObject user = jsonResponse.getJSONObject("user");

                            teacherData = new TeacherData();

                            teacherData.setName(user.getString("Name"));
                            teacherData.setUserName(user.getString("username"));
                            teacherData.setfCode(user.getString("F_code"));
                            teacherData.setEmail(user.getString("Email"));
                            teacherData.setRoom(user.getString("Room"));
                            teacherData.setPassword(user.getString("password"));
                            teacherData.setOTP(user.getString("OTP"));

                            Log.e("OTP ", teacherData.getOTP());



                        } catch (Exception e) {
                            e.printStackTrace();
                            //login unsuccessful, notify user
                            Toast.makeText(Login.this, "Wrong username/password!", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }
        ) {
            // here is params will add to your url using post method
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("password", password);
                return params;
            }
        };

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(movieRequest);
    }*/
    public void teacher_Login(){
        String Domain, key;
        Domain = "BUBT";
        key = "Attend_BUBT";
        final String username = et_username.getText().toString();
        final String password = et_password.getText().toString();
        Log.e("teacher_Login: ", username);
        final String code = "login";
        StringRequest request = new StringRequest(Request.Method.POST, VariableClass.getURL1(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {

                    Log.e("Response ", response);

                    JSONObject jsonObject=new JSONObject(response);
                    JSONArray array=jsonObject.getJSONArray("data");
                    for (int i = 0; i < array.length(); i++) {

                        JSONObject ob = array.getJSONObject(i);

                        variable.setOTP(ob.getString("OTP"));
                        variable.setUser_Name(ob.getString("username"));
                        variable.setTd_code(ob.getString("F_code"));
                        variable.setTd_email(ob.getString("Email"));
                        variable.setTd_room(ob.getString("Room"));
                        variable.setUser_Pass(ob.getString("password"));
                        variable.setTd_name(ob.getString("Name"));
                        variable.setS_no(ob.getString("S_no"));
                        variable.setS_name(ob.getString("s_name"));
                        variable.setImagestring(ob.getString("imgdata"));
                        variable.setDomain(Domain);

                        Log.e("Room ", variable.getTd_room());
                        Log.e("S_Name ", variable.getS_name());
                        Log.e("S_no ", variable.getS_no());

                        if(variable.getOTP() != null) {
                            Intent Intent = new Intent(Login.this,MainActivity.class);
                            startActivity(Intent);
                        }

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(Login.this, "Wrong username/password!", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(Login.this, "Server Error. Please try again later.", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError{
                Map<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("password", password);
                params.put("domain", Domain);
                params.put("key", key);
                params.put("code", code);
                return params;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(request);

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
}