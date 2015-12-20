package com.example.geyingqi.blog.net;

import java.io.Serializable;
import java.util.Comparator;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by geyingqi on 12/20/15.
 */
public class Parameter implements Parcelable,Comparator<Parameter> {

    private String name;//
    private String value;

    public Parameter(){
        super();
    }
    public Parameter(String name,String value){
        super();
        this.name = name;
        this.value = value;
    }

    protected Parameter(Parcel in) {
        name = in.readString();
        value = in.readString();
    }

    public static final Creator<Parameter> CREATOR = new Creator<Parameter>() {
        @Override
        public Parameter createFromParcel(Parcel in) {
            return new Parameter(in);
        }

        @Override
        public Parameter[] newArray(int size) {
            return new Parameter[size];
        }
    };

    @Override
    public int compare(Parameter lhs, Parameter rhs) {
        return 0;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(value);
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }
}
