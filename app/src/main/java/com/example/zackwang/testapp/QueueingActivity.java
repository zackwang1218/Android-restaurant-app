package com.example.zackwang.testapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.zackwang.testapp.activities.HomeMenuActivity;
import com.example.zackwang.testapp.activities.QueueTimeActivity;

public class QueueingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_queueing);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_queueing, menu);
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

    public void queueing_DirectToHomeMenu(View view) {
        Intent i = new Intent(this, HomeMenuActivity.class);
        startActivity(i);
    }

    public void queueing_DirectToQueueTime(View view) {
        Intent i = new Intent(this, QueueTimeActivity.class);
        startActivity(i);
    }
}
