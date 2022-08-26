package com.example.student;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AlertDialog;

public class MainActivity extends BaseActivity {
    private ImageButton adhoc , list, about;
    public String username,password,otp;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        VariableClass variableClass = VariableClass.getInstance();
        setTitle(" CAS - "+ variableClass.getDomain());


        adhoc = (ImageButton) findViewById(R.id.ad_hoc);
        adhoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ad_hoc_test.class);
                startActivity(intent);
            }
        });
        list = (ImageButton) findViewById(R.id.List);
        list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Intent = new Intent(MainActivity.this, StudentView.class);
                startActivity(Intent);
            }
        });
        about = (ImageButton) findViewById(R.id.about);
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Intent = new Intent(MainActivity.this, About.class);
                startActivity(Intent);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    public void onBackPressed()
    {
        super.onBackPressed(); // this can go before or after your stuff below
        // do your stuff when the back button is pressed
        Intent Intent = new Intent(android.content.Intent.ACTION_MAIN);
        Intent.addCategory(Intent.CATEGORY_HOME);
        Intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        Intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        Intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(Intent);
        finish();
        System.exit(0);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId()==R.id.SignOutMenuId)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle(R.string.app_name);
            builder.setIcon(R.mipmap.ic_launcher);
            builder.setMessage("Do you want to SignOut?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent in = new Intent(MainActivity.this, Login.class);
                            in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            SharedPreferences preferences =getSharedPreferences("login",Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.remove("key_name");
                            editor.remove("key_pass");
                            editor.apply();
                            startActivity(in);
                            finish();
//                            Intent intent = new Intent(MainActivity.this, Login.class);
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

            Intent intent = new Intent(MainActivity.this, teacher_Profile.class);
            startActivity(intent);
        }

        if (item.getItemId()==R.id.DashboardId)
        {
            Intent Intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(Intent);
        }

        if (item.getItemId()==R.id.About)
        {
            Intent Intent = new Intent(MainActivity.this,About.class);
            startActivity(Intent);
        }

        if (item.getItemId()==R.id.exit)
        {

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
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