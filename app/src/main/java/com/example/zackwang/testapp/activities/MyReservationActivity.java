package com.example.zackwang.testapp.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zackwang.testapp.R;

public class MyReservationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_reservation);

        loadExistingReservation();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_my_reservation, menu);
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

    private void loadExistingReservation() {
        SharedPreferences sharedPreferences = getSharedPreferences("Reservation", Context.MODE_PRIVATE);
        String date = sharedPreferences.getString("date", "");
        String time = sharedPreferences.getString("time", "");
        String duration = sharedPreferences.getString("duration", "");
        String people = sharedPreferences.getString("people", "");

        TextView textViewDate = (TextView) findViewById(R.id.my_reservation_TextView_date);
        textViewDate.setText("Date: " + date);
        TextView textViewTime = (TextView) findViewById(R.id.my_reservation_TextView_time);
        textViewTime.setText("Time: " + time);
        TextView textViewDuration = (TextView) findViewById(R.id.my_reservation_TextView_duration);
        textViewDuration.setText("Duration: " + duration);
        TextView textViewNumOfPeople = (TextView) findViewById(R.id.my_reservation_TextView_numOfPeople);
        textViewNumOfPeople.setText("Number of People: " + people);
    }

    public void my_reservation_cancelBooking(View view) {
        ShowAlertMsg("Do you want to cancel this reservation?");
    }

    public void my_reservation_close(View view) {
        finish();
    }

    private void ShowAlertMsg(String msg) {
        AlertDialog.Builder a_builder = new AlertDialog.Builder(this);
        a_builder.setMessage(msg)
                .setCancelable(false)
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences sharedPreferences = getSharedPreferences("Reservation", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.clear();
                        editor.commit();
                        Toast.makeText(MyReservationActivity.this, "Reservation has been removed", Toast.LENGTH_SHORT).show();
                        finish();

                        dialog.cancel();
                    }
                })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = a_builder.create();
        alert.show();
    }
}
