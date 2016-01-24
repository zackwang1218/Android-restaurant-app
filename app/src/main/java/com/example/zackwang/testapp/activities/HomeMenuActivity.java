package com.example.zackwang.testapp.activities;

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

import com.example.zackwang.testapp.HomeScreen;
import com.example.zackwang.testapp.QueueingActivity;
import com.example.zackwang.testapp.R;

public class HomeMenuActivity extends AppCompatActivity {

    private AlertDialog processDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home_menu, menu);
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

    public void home_menu_DirectToBook(View view) {
        SharedPreferences sharedPreferences = getSharedPreferences("Reservation", Context.MODE_PRIVATE);
        String date = sharedPreferences.getString("date", "");
        if (date.equals("")) {
            Intent i = new Intent(this, BookActivity.class);
            startActivity(i);
        } else {
            Intent i = new Intent(this, MyReservationActivity.class);
            startActivity(i);
        }

    }

    public void home_menu_DirectToQueueing(View view) {
        Intent i = new Intent(this, QueueingActivity.class);
        startActivity(i);
    }

    public void home_menu_ShowSideMenu(View view) {
        Intent i = new Intent(this, SideMenuActivity.class);
        startActivity(i);
    }

    public void home_menu_DirectToHomeScreen(View view) {
        Intent i = new Intent(this, HomeScreen.class);
        startActivity(i);
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

    private void BuildProcessDialog(String msg) {
        AlertDialog.Builder a_builder = new AlertDialog.Builder(this);
        a_builder.setMessage(msg)
                .setCancelable(false);
        processDialog = a_builder.create();
    }

    public void home_menu_DirectToAccount(View view) {
        Intent i = new Intent(this, AccountDetailActivity.class);
        startActivity(i);

    }
}
