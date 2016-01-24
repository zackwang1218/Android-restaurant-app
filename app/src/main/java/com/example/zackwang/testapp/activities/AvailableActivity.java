package com.example.zackwang.testapp.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.zackwang.testapp.R;
import com.example.zackwang.testapp.entity.Reservation;

import java.util.ArrayList;
import java.util.List;

public class AvailableActivity extends AppCompatActivity {
    private Reservation reservation;
    private List<String> sectionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_available);

        reservation = getIntent().getParcelableExtra("Reservation");
        //populateListView();

    }

    private void populateListView() {
        sectionList = new ArrayList<>();
        sectionList.add("section1");
        sectionList.add("section2");
        sectionList.add("section3");

        ArrayAdapter<String> myAdapter = new ArrayAdapter<>(this, R.layout.activity_available, sectionList);
        ListView sectionListView = (ListView) findViewById(R.id.sectionListView);
        sectionListView.setAdapter(myAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_available, menu);
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

    public void available_DirectBack(View view) {
        finish();
    }

    public void available_confirmBooking(View view) {
        SharedPreferences sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        //editor.putString("username", user.getUsername());
        //editor.putString("password", user.getPassword());
        editor.commit();
    }
}
