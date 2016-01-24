package com.example.zackwang.testapp.activities;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.example.zackwang.testapp.R;
import com.example.zackwang.testapp.entity.Reservation;
import com.example.zackwang.testapp.tools.ReservationDTO;
import com.example.zackwang.testapp.utility.RequestHandler;
import com.example.zackwang.testapp.utility.XmlHandler;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class BookActivity extends AppCompatActivity {
    Calendar calendar = Calendar.getInstance();
    Button selectDateBtn;
    Button timePickerEditText;

    private Reservation reservation;
    private XmlHandler xmlHandler;
    private RequestHandler requestHandler;
    private String bookingXML;
    private ReservationDTO reservationSearchResult = new ReservationDTO();
    private String resultFlag;
    private List<String> sectionList;
    private Spinner sectionSpinner;

    private final float MAX_DURATION = 1.5f;
    private final float MIN_DURATION = 0.5f;
    private final int MAX_NUM_PEOPLE = 8;
    private final int MIN_NUM_PEOPLE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        //create section list
        sectionList = new ArrayList<>();
        sectionList.add("section1");
        sectionList.add("section2");

        xmlHandler = new XmlHandler();
        requestHandler = new RequestHandler();
        selectDateBtn = (Button) findViewById(R.id.book_selectDateBtn);
        timePickerEditText = (Button) findViewById(R.id.book_timePickerBtn);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = sdf.format(new Date());
        selectDateBtn.setText(currentDate);
        timePickerEditText.setText(Calendar.HOUR_OF_DAY + ":" + Calendar.MINUTE);

        //create section list
        //initialise section spinner
        sectionList = new ArrayList<>();
        sectionList.add("section1");
        sectionList.add("section2");
        sectionSpinner = (Spinner)findViewById(R.id.book_sectionSpinner);
        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,sectionList);
        sectionSpinner.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_book, menu);
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

    public class BookingSearchAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            reservationSearchResult = requestHandler.sendBookingEnqueryRequest(bookingXML);
            String test = "test";
            return test;
        }

        @Override
        protected void onPostExecute(String s) {
            handleBookingPostBack(reservationSearchResult);
            super.onPostExecute(s);
        }
    }

    private void handleBookingPostBack(ReservationDTO reservationSearchResult) {

        if (reservationSearchResult == null) {
            ShowAlertMsg("unable to connect");
        } else if (reservationSearchResult.getResultFlag().equals("true")) {
            //SharedPreferences sharedPreferences = getSharedPreferences("Reservation", Context.MODE_PRIVATE);
            //SharedPreferences.Editor editor = sharedPreferences.edit();
            //editor.putString("username", reservation.getUsername());
            //editor.putString("anonymousID", reservation.getAnonymousID());
            //editor.putString("date", reservation.getDate());
            //editor.putString("time", reservation.getTime());
            //editor.putString("duration", reservation.getDuration());
            //editor.putString("people", reservation.getNumOfPeople());
            //editor.commit();

            //Intent i = new Intent(this, MyReservationActivity.class);
            //i.putExtra("Reservation", reservation);
            //startActivity(i);

            Intent i = new Intent(this,PickTableActivity.class);
            i.putParcelableArrayListExtra("tablelist", (ArrayList<? extends Parcelable>) this.reservationSearchResult.getTableList());
            startActivity(i);
        } else {
            // can not make this reservation
            //     to be complete.....................
            ShowAlertMsg("Sorry, no seats available");
        }
    }

    public void book_DirectToHomeMenu(View view) {
        finish();
    }


    public void book_searchBookingAvailable(View view) {
        prepareBooking();
        bookingXML = xmlHandler.makeBookingEnqueryXml(reservation);

        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            new BookingSearchAsyncTask().execute();
        } else {
            ShowAlertMsg("No network connection available.");
        }
    }

    private void prepareBooking() {
        reservation = new Reservation();
        SharedPreferences sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");
        String anonymousID = sharedPreferences.getString("anonymousID", "");
        if (!username.equals("")) {
            reservation.setUsername(username);
        } else if (!anonymousID.equals("")) {
            reservation.setAnonymousID(anonymousID);
        }

        String sectionName = sectionSpinner.getSelectedItem().toString();
        reservation.setSection(sectionName.substring(sectionName.length() - 1));

        final Button selectedDate = (Button) findViewById(R.id.book_selectDateBtn);
        reservation.setDate(selectedDate.getText().toString());
        final Button selectedTime = (Button) findViewById(R.id.book_timePickerBtn);
        reservation.setTime(selectedTime.getText().toString());
        final EditText duration = (EditText) findViewById(R.id.book_editText_duration);
        reservation.setDuration(duration.getText().toString());
        final EditText numOfPeople = (EditText) findViewById(R.id.book_editText_numOfPeople);
        reservation.setNumOfPeople(numOfPeople.getText().toString());
    }

    public void book_openDatePicker(View view) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(BookActivity.this, onSelectDateListener,
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMinDate(Calendar.DAY_OF_MONTH);
        datePickerDialog.show();
    }

    public void book_openTimePicker(View view) {
        new TimePickerDialog(BookActivity.this, onTimeSetListener, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false).show();
    }

    DatePickerDialog.OnDateSetListener onSelectDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            selectDateBtn.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
        }
    };
    TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            timePickerEditText.setText(hourOfDay + ":" + minute);
        }
    };


    public void book_increaseDuration(View view) {
        EditText durationText = (EditText) findViewById(R.id.book_editText_duration);
        float currentDuration = Float.parseFloat(String.valueOf(durationText.getText()));
        if (currentDuration < MAX_DURATION) {
            currentDuration += 0.5f;
        }
        durationText.setText(Float.toString(currentDuration));
    }

    public void book_decreaseDuration(View view) {
        EditText durationText = (EditText) findViewById(R.id.book_editText_duration);
        float currentDuration = Float.parseFloat(String.valueOf(durationText.getText()));
        if (currentDuration > MIN_DURATION) {
            currentDuration -= 0.5f;
        }
        durationText.setText(Float.toString(currentDuration));
    }

    public void book_increaseNumOfPeople(View view) {
        EditText numOfPeopleText = (EditText) findViewById(R.id.book_editText_numOfPeople);
        int numOfPeople = Integer.parseInt(String.valueOf(numOfPeopleText.getText()));
        if (numOfPeople < MAX_NUM_PEOPLE) {
            numOfPeople++;
        }
        numOfPeopleText.setText(numOfPeople + "");
    }

    public void book_decreaseNumOfPeople(View view) {
        EditText numOfPeopleText = (EditText) findViewById(R.id.book_editText_numOfPeople);
        int numOfPeople = Integer.parseInt(String.valueOf(numOfPeopleText.getText()));
        if (numOfPeople > MIN_NUM_PEOPLE) {
            numOfPeople--;
        }
        numOfPeopleText.setText(numOfPeople + "");
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

    private void parseSearchPostBack(Document resultDocument) {
        resultDocument.getDocumentElement().normalize();
        NodeList nList1 = resultDocument.getElementsByTagName("data");
        Node nNodeTest = nList1.item(0);
        Element eElement1 = (Element) nNodeTest;
        String test1 = eElement1.getElementsByTagName("table").item(0).getTextContent();
        String test2 = eElement1.getElementsByTagName("table").item(1).getTextContent();
    }
}
