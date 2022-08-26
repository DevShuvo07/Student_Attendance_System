package com.example.student;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AlertDialog;

public class About extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        //VariableClass variableClass = VariableClass.getInstance();
        setTitle(" About ");
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        return super.onCreateOptionsMenu(menu);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId()==R.id.SignOutMenuId)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(About.this);
            builder.setTitle(R.string.app_name);
            builder.setIcon(R.mipmap.ic_launcher);
            builder.setMessage("Do you want to SignOut?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent in = new Intent(About.this,Login.class);
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

            Intent intent = new Intent(About.this, teacher_Profile.class);
            startActivity(intent);
        }
        if (item.getItemId()==R.id.About)
        {

            Intent intent = new Intent(About.this, About.class);
            startActivity(intent);
        }

        if (item.getItemId()==R.id.DashboardId)
        {
            Intent Intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(Intent);
        }

        if (item.getItemId()==R.id.exit)
        {

            AlertDialog.Builder builder = new AlertDialog.Builder(About.this);
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