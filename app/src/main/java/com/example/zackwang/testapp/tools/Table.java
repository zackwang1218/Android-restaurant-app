package com.example.zackwang.testapp.tools;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Zack Wang on 2015/10/16.
 */
public class Table implements Parcelable{
    private int id;
    private String section;

    public Table(Parcel in) {
        id = in.readInt();
        section = in.readString();
    }

    public static final Creator<Table> CREATOR = new Creator<Table>() {
        @Override
        public Table createFromParcel(Parcel in) {
            return new Table(in);
        }

        @Override
        public Table[] newArray(int size) {
            return new Table[size];
        }
    };

    public Table() {

    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(section);
    }
}
