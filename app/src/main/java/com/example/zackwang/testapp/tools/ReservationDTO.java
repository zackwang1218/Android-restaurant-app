package com.example.zackwang.testapp.tools;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zack Wang on 2015/10/16.
 */
public class ReservationDTO implements Parcelable{
    private String userid;
    private String anonymousID;
    private String resultFlag;
    private List<Table> tableList = new ArrayList<>();

    public ReservationDTO(Parcel in) {
        userid = in.readString();
        anonymousID = in.readString();
        resultFlag = in.readString();
    }

    public static final Creator<ReservationDTO> CREATOR = new Creator<ReservationDTO>() {
        @Override
        public ReservationDTO createFromParcel(Parcel in) {
            return new ReservationDTO(in);
        }

        @Override
        public ReservationDTO[] newArray(int size) {
            return new ReservationDTO[size];
        }
    };

    public ReservationDTO() {

    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getAnonymousID() {
        return anonymousID;
    }

    public void setAnonymousID(String anonymousID) {
        this.anonymousID = anonymousID;
    }

    public List<Table> getTableList() {
        return tableList;
    }

    public void setTableList(List<Table> tableList) {
        this.tableList = tableList;
    }

    public String getResultFlag() {
        return resultFlag;
    }

    public void setResultFlag(String resultFlag) {
        this.resultFlag = resultFlag;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(userid);
        dest.writeString(anonymousID);
        dest.writeString(resultFlag);
    }
}
