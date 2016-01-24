package com.example.zackwang.testapp.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.example.zackwang.testapp.R;
import com.example.zackwang.testapp.entity.Reservation;
import com.example.zackwang.testapp.tools.ReservationDTO;
import com.example.zackwang.testapp.tools.Table;

import java.util.ArrayList;

public class PickTableActivity extends AppCompatActivity {
    private ReservationDTO reservationDTO;
    private Reservation reservation = new Reservation();
    private ArrayList<Table> tableArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_table);
        tableArrayList = getIntent().getParcelableArrayListExtra("tablelist");
        initialAllTable();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pick_table, menu);
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

    private void initialAllTable(){
        for(Table table : tableArrayList){
            String tableName = "tp_tb" + table.getId();
            int numID = getResources().getIdentifier(tableName, "id", getPackageName());
            ImageView tableImage = (ImageView)findViewById(numID);
            tableImage.setVisibility(View.VISIBLE);
            tableImage.setImageResource(R.mipmap.tp_available);
        }
    }


    public void pickTable_chooseTable(View view) {
        initialAllTable();
        int numID = view.getId();
        ImageView tableImage = (ImageView)findViewById(numID);
        //change resource image of the selected table
        tableImage.setImageResource(R.mipmap.tp_reserved);
        String tableName = getResources().getResourceEntryName(numID);
        int tableID = tableName.charAt(tableName.length()-1);
        reservation.setTableID(tableID);
    }
}
