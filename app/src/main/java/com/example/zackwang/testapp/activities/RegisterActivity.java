package com.example.zackwang.testapp.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.zackwang.testapp.HomeScreen;
import com.example.zackwang.testapp.R;
import com.example.zackwang.testapp.entity.User;
import com.example.zackwang.testapp.utility.MyValidator;
import com.example.zackwang.testapp.utility.RequestHandler;
import com.example.zackwang.testapp.utility.XmlHandler;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class RegisterActivity extends AppCompatActivity {

    private Spinner titleSpinner;
    private XmlHandler xmlHandler;
    private RequestHandler requestHandler;
    private String registerXML;
    private String resultString;
    private User user;
    private MyValidator myValidator;
    private AlertDialog processDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        xmlHandler = new XmlHandler();
        requestHandler = new RequestHandler();
        myValidator = new MyValidator();
        addItemOnTitleSpinner();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register, menu);
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

    public class RegisterAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            resultString = requestHandler.sendSignUpRequest(params[0]);
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            handleRegisterPostBack();
            super.onPostExecute(s);
        }
    }

    public void register_DirectToHomeScreen(View view) {
        Intent i = new Intent(this, HomeScreen.class);
        startActivity(i);
    }

    public void register_registerAccount(View view) throws NoSuchAlgorithmException {
        prepareAccount();
        String validationErrorMsg = myValidator.validateRegisterForm(user);
        if (validationErrorMsg != null) {
            ShowAlertMsg(validationErrorMsg);
        } else {
            BuildProcessDialog("Loading...");
            processDialog.show();
            registerXML = xmlHandler.MakeSignUpXml(user);

            ConnectivityManager connMgr = (ConnectivityManager)
                    getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                new RegisterAsyncTask().execute(registerXML);
            } else {
                processDialog.dismiss();
                ShowAlertMsg("No network connection available.");
            }
        }
    }

    private void prepareAccount() {
        user = new User();
        final EditText usernameText = (EditText) findViewById(R.id.register_username);
        user.setUsername(usernameText.getText().toString());
        final EditText passwordText = (EditText) findViewById(R.id.register_password);
        user.setPassword(passwordText.getText().toString());
        final Spinner titleSpinner = (Spinner) findViewById(R.id.spinner_title);
        user.setTitle(titleSpinner.getSelectedItem().toString());
        final EditText fullnameText = (EditText) findViewById(R.id.register_fullname);
        user.setFullname(fullnameText.getText().toString());
        final EditText emailText = (EditText) findViewById(R.id.register_email);
        user.setEmail(emailText.getText().toString());
        final EditText phoneText = (EditText) findViewById(R.id.register_phone);
        user.setPhone(phoneText.getText().toString());

    }

    private void handleRegisterPostBack() {
        processDialog.dismiss();
        if (resultString == null) {
            ShowAlertMsg("unable to connect");
        } else if (resultString.equals("true")) {

            SharedPreferences sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("username", user.getUsername());
            editor.putString("password", null);
            editor.commit();
            Intent i = new Intent(this, HomeScreen.class);
            i.putExtra("userInfo", user.getUsername());

            Toast.makeText(this, "success", Toast.LENGTH_SHORT).show();
            startActivity(i);
        } else if (resultString.equals("false")) {
            ShowAlertMsg("Register fail");
        } else {
            ShowAlertMsg("other error");
        }
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

    private void addItemOnTitleSpinner() {
        titleSpinner = (Spinner) findViewById(R.id.spinner_title);
        List<String> titleList = new ArrayList<>();
        titleList.add("Mr");
        titleList.add("Mrs");
        titleList.add("Miss");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, titleList);
        titleSpinner.setAdapter(arrayAdapter);
    }
}
