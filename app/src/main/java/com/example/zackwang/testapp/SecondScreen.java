package com.example.zackwang.testapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.zackwang.testapp.entity.User;

public class SecondScreen extends AppCompatActivity {
    private User currentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_screen);

        Bundle userInfo = getIntent().getExtras();
        if (userInfo == null) {
            return;
        }
        currentUser = new User();
        //String username = userInfo.getString("userInfo");
        //currentUser.setUsername(username);
        //final TextView helloMsg = (TextView)findViewById(R.id.textView_hello);
        //helloMsg.setText("Hello! "+username);
    }

    public void OnClickBook(View view) {

    }

    public void OnClickMenu(View view) {
        Intent i = new Intent(this, MenuActivity.class);
        startActivity(i);
    }

    public void OnClick_logout(View view) {
        Intent i = new Intent(this, HomeScreen.class);
        startActivity(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_second_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void secondScreen_onClick_back(View view) {
        Intent i = new Intent(this, HomeScreen.class);
        startActivity(i);
    }

    private void loadUserFromDevice() {
        currentUser = new User();
        SharedPreferences sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "N/A");
        currentUser.setUsername(username);
    }

    private void ShowAlertMsg(String msg) {
        AlertDialog.Builder a_builder = new AlertDialog.Builder(this);
        a_builder.setMessage(msg)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = a_builder.create();
        alert.show();
    }

    public void OnClick_getUserTest(View view) {
        loadUserFromDevice();
        ShowAlertMsg(currentUser.getUsername());
    }
}
