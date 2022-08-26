package com.example.student;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity implements LogOutListener{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((myapp) getApplication()).registersessionlistener(this);
        ((myapp) getApplication()).startUserSession();
    }

    @Override
    public void onUserInteraction() {
        /*super.onUserInteraction();
        ((myapp) getApplication()).onUserInterected();*/
    }

    @Override
    public void onsessionlogout() {
        //Intent intent = new Intent(this, Login.class);
        //Intent Intent = new Intent(android.content.Intent.ACTION_MAIN);
        //startActivity(intent);

        SharedPreferences  preferences =getSharedPreferences("login", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove("key_name");
        editor.remove("key_pass");
        editor.commit();
        Intent in = new Intent(this,Login.class);
        in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(in);
        finish();
        System.exit(0);
    }
}
