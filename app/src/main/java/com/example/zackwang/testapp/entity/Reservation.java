package com.example.zackwang.testapp.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Zack Wang on 2015/10/7.
 */
public class Reservation implements Parcelable {
    private String username;
    private String anonymousID;
    private String date;
    private String time;
    private String duration;
    private String numOfPeople;
    private String section;
    private boolean resultFlag;
    private int tableID;

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public boolean isResultFlag() {
        return resultFlag;
    }

    public void setResultFlag(boolean resultFlag) {
        this.resultFlag = resultFlag;
    }

    public int getTableID() {
        return tableID;
    }

    public void setTableID(int tableID) {
        this.tableID = tableID;
    }

    public static Creator<Reservation> getCREATOR() {
        return CREATOR;
    }

    public Reservation(Parcel in) {
        username = in.readString();
        anonymousID = in.readString();
        date = in.readString();
        time = in.readString();
        duration = in.readString();
        numOfPeople = in.readString();
    }

    public static final Creator<Reservation> CREATOR = new Creator<Reservation>() {
        @Override
        public Reservation createFromParcel(Parcel in) {
            return new Reservation(in);
        }

        @Override
        public Reservation[] newArray(int size) {
            return new Reservation[size];
        }
    };

    public Reservation() {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAnonymousID() {
        return anonymousID;
    }

    public void setAnonymousID(String anonymousID) {
        this.anonymousID = anonymousID;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getNumOfPeople() {
        return numOfPeople;
    }

    public void setNumOfPeople(String numOfPeople) {
        this.numOfPeople = numOfPeople;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(username);
        dest.writeString(anonymousID);
        dest.writeString(date);
        dest.writeString(time);
        dest.writeString(duration);
        dest.writeString(numOfPeople);
    }
}
