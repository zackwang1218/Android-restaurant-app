package com.example.zackwang.testapp;

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
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import com.example.zackwang.testapp.activities.HomeMenuActivity;
import com.example.zackwang.testapp.activities.RegisterActivity;
import com.example.zackwang.testapp.entity.User;
import com.example.zackwang.testapp.utility.MyValidator;
import com.example.zackwang.testapp.utility.RequestHandler;
import com.example.zackwang.testapp.utility.XmlHandler;


public class HomeScreen extends AppCompatActivity {

    private User user;
    private String loginXML;
    private MyValidator myValidator;
    private XmlHandler xmlHandler;
    private RequestHandler requestHandler;
    private String resultString = "";
    private AlertDialog processDialog;
    private String anonymousID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        xmlHandler = new XmlHandler();
        requestHandler = new RequestHandler();
        myValidator = new MyValidator();

        //background = (RelativeLayout) findViewById(R.id.home);
        //btnGreen = (Button) findViewById(R.id.button1);
        //btnBlue = (Button) findViewById(R.id.button2);
    }

    public void home_screen_DirectToRegister(View view) {
        Intent i = new Intent(this, RegisterActivity.class);
        startActivity(i);
    }

    public class LoginAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            resultString = requestHandler.sendLoginRequest(loginXML);
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            handleLoginPostBack();
            super.onPostExecute(s);
        }
    }

    public class AnonymouLoginAsyncTask extends AsyncTask<Void, Void, String> {

        // anonymous ID is the unique ID returned by server, used to identify user in next request

        @Override
        protected String doInBackground(Void... params) {
            anonymousID = requestHandler.anonymousLogin();
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            handleAnonymousLogin();
            super.onPostExecute(s);
        }
    }

    public void prepareAccount() {
        user = new User();
        final EditText usernameText = (EditText) findViewById(R.id.login_username);
        user.setUsername(usernameText.getText().toString());
        final EditText passwordText = (EditText) findViewById(R.id.login_password);
        user.setPassword(passwordText.getText().toString());
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_home_screen, menu);
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


    public void home_screen_Login(View view) {
        prepareAccount();
        String validationErrorMsg = myValidator.validateLoginForm(user);
        if (validationErrorMsg != null) {
            ShowAlertMsg(validationErrorMsg);
        } else {
            BuildProcessDialog("Loading");
            processDialog.show();
            loginXML = xmlHandler.MakeLoginXml(user);
            ConnectivityManager connMgr = (ConnectivityManager)
                    getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                new LoginAsyncTask().execute();
            } else {
                processDialog.dismiss();
                ShowAlertMsg("No network connection available.");
            }
        }
    }

    public void home_anonymousLogin(View view) {
        BuildProcessDialog("Loading...");
        processDialog.show();
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            new AnonymouLoginAsyncTask().execute();
        } else {
            processDialog.dismiss();
            ShowAlertMsg("No network connection available.");
        }
    }

    private void handleLoginPostBack() {
        processDialog.dismiss();
        //resultString = "True";
        if (resultString == null) {
            ShowAlertMsg("unable to connect");
        } else if (resultString.equals("true")) {
            SharedPreferences sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("username", user.getUsername());
            editor.putString("password", user.getPassword());
            editor.commit();
            Intent i = new Intent(this, HomeMenuActivity.class);
            i.putExtra("userInfo", user.getUsername());

            Toast.makeText(this, "success", Toast.LENGTH_SHORT).show();
            startActivity(i);
        } else if (resultString.equals("false")) {
            ShowAlertMsg("Wrong username or password, please try again.");
        } else {
            ShowAlertMsg("other error");
        }
    }

    private void handleAnonymousLogin() {
        processDialog.dismiss();
        anonymousID = "test";
        if (anonymousID == null) {
            ShowAlertMsg("unable to connect");
        } else {

            SharedPreferences sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("anonymousID", anonymousID);
            editor.commit();
            Toast.makeText(this, "success", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(this, HomeMenuActivity.class);
            startActivity(i);
        }
    }
}
