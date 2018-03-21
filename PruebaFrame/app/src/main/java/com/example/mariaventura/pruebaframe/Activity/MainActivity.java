package com.example.mariaventura.pruebaframe.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.*;
import android.view.*;


import com.example.mariaventura.pruebaframe.Fragment.MainFragment;
import com.example.mariaventura.pruebaframe.R;

import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*LinearLayout linearlayout = (LinearLayout)findViewById(R.id.postContent);

        LayoutInflater inflater = LayoutInflater.from(this);


        for(int i =0; i<4; i++) {


            View a = inflater.inflate(R.layout.post_item, null, true);

           // TextView postUsername = (TextView)findViewById(R.id.userNamePost);
            //postUsername.setText("Este es el post"+ i);

            linearlayout.addView(a);*/


        // Creación del fragmento principal
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new MainFragment(),"MainFragment")
                    .commit();
        }

    }

    }




