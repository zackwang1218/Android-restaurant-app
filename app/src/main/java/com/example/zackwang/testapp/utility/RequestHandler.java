package com.example.zackwang.testapp.utility;

import com.example.zackwang.testapp.tools.ReservationDTO;

import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by Zack Wang on 2015/10/4.
 */
public class RequestHandler {
    private static final String LOGIN_URL = "http://192.168.2.2:8080/researchserver/Login"; //"http://192.168.2.2:8080/Research/Login";
    private static final String SIGNUP_URL = "http://192.168.2.2:8080/researchserver/Registration";
    private static final String ANONYMOUS_LOGIN_URL = "http://192.168.2.2:8080/researchserver/Anonymouslogin";
    private static final String BOOKING_AVAILABLE_URL = "http://192.168.2.2:8080/researchserver/EnqueryAvailability";
    private static final int REQUEST_TIMEOUT = 2000;
    private XmlHandler xmlHandler = new XmlHandler();

    public String sendLoginRequest(String accountXML) {
        InputStream is = null;
        String resultStr;
        try {
            URL url = new URL(LOGIN_URL);
            int len = 200;

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setReadTimeout(REQUEST_TIMEOUT);
            conn.setConnectTimeout(REQUEST_TIMEOUT);
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            //conn.setUseCaches(false);
            conn.setRequestProperty("Content-Type", "text/xml; charset=UTF-8");
            //conn.setRequestProperty("Connection", "Keep-Alive");
            //conn.setRequestProperty("Content-Length", String.valueOf(accountXML.length()));
            //conn.connect();

            DataOutputStream dataOutputStream = new DataOutputStream(conn.getOutputStream());
            dataOutputStream.writeBytes(accountXML);
            dataOutputStream.flush();
            dataOutputStream.close();
            int i = conn.getResponseCode();
            //ShowAlertMsg("Output succees");

            //if (conn.getResponseCode() != 200) {
            //    ShowAlertMsg("Connection failed");
            //}
            //is = conn.getInputStream();
            //String temp = inputstreamToString(is);
            System.out.println("response code: " + i);
            InputStream in = conn.getInputStream();
            resultStr = xmlHandler.ReadLoginFeedBack(in);
            return resultStr;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String sendSignUpRequest(String accountXML) {
        InputStream is = null;
        String resultStr;
        try {
            URL url = new URL(SIGNUP_URL);
            int len = 500;

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setReadTimeout(REQUEST_TIMEOUT);
            conn.setConnectTimeout(REQUEST_TIMEOUT);
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            //conn.setUseCaches(false);
            conn.setRequestProperty("Content-Type", "text/xml; charset=UTF-8");
            //conn.setRequestProperty("Connection", "Keep-Alive");
            //conn.setRequestProperty("Content-Length", String.valueOf(accountXML.length()));
            //conn.connect();

            DataOutputStream dataOutputStream = new DataOutputStream(conn.getOutputStream());
            dataOutputStream.writeBytes(accountXML);
            dataOutputStream.flush();
            dataOutputStream.close();
            int i = conn.getResponseCode();
            //ShowAlertMsg("Output succees");

            resultStr = xmlHandler.ReadRegisterFeedBack(conn.getInputStream());
            return resultStr;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String anonymousLogin() {
        InputStream inputStream;
        String resultStr;
        try {
            URL url = new URL(ANONYMOUS_LOGIN_URL);
            int len = 200;
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(REQUEST_TIMEOUT);
            connection.setConnectTimeout(REQUEST_TIMEOUT);
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.connect();

            inputStream = connection.getInputStream();
            resultStr = readIt(inputStream, len);
            return resultStr;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    public ReservationDTO sendBookingEnqueryRequest(String bookingXML) {
        InputStream is = null;
        String resultStr;
        try {
            URL url = new URL(BOOKING_AVAILABLE_URL);
            int len = 500;

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setReadTimeout(REQUEST_TIMEOUT);
            conn.setConnectTimeout(REQUEST_TIMEOUT);
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            //conn.setUseCaches(false);
            conn.setRequestProperty("Content-Type", "text/xml; charset=UTF-8");
            //conn.setRequestProperty("Connection", "Keep-Alive");
            //conn.setRequestProperty("Content-Length", String.valueOf(accountXML.length()));
            //conn.connect();

            DataOutputStream dataOutputStream = new DataOutputStream(conn.getOutputStream());
            dataOutputStream.writeBytes(bookingXML);
            dataOutputStream.flush();
            dataOutputStream.close();
            int i = conn.getResponseCode();
            //return a ReservationDTO object contains all the available tables and details of that day
            return xmlHandler.ReadBookingSearchResult(conn.getInputStream());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String readIt(InputStream stream, int len) throws IOException {
        Reader reader;
        reader = new InputStreamReader(stream, "UTF-8");
        char[] buffer = new char[len];
        reader.read(buffer);
        return new String(buffer);

    }

    private static String inputstreamToString(InputStream is) {

        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        String line;
        try {

            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return sb.toString();

    }


}
