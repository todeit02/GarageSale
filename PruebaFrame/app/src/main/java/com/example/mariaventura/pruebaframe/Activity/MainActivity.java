package com.example.mariaventura.pruebaframe.Activity;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.*;
import android.view.*;

import com.example.mariaventura.pruebaframe.R;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("","In the oncreate ");
    }



    public void onStart()
    {
        super.onStart();
        Log.d("","In the onStart() ");
    }
    public void onRestart()
    {
        super.onRestart();
        Log.d("","In the onRestart ");
    }
    public void onResume()
    {
        super.onResume();
        Log.d("","In the onResume() ");
    }
    public void onPause()
    {
        super.onPause();
        Log.d("","In the onPause() ");
    }
    public void onStop()
    {
        super.onStop();
        Log.d("","In the onStop() ");
    }
    public void onDestroy()
    {
        super.onDestroy();
        Log.d("","In the onDestroy() ");
    }
    public void onClick(View view)
    {
        Button btn = (Button) view;
        Toast.makeText(this, btn.getText() + " was clicked!", Toast.LENGTH_SHORT).show();
    }

}
