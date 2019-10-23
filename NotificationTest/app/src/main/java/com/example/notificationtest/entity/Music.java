package com.example.notificationtest.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.notificationtest.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hy
 * @Date 2019/10/19 0019
 **/
@SuppressWarnings("unused")
public class Music implements Parcelable {

    private String name;
    private String icon;
    private int resId;

    public Music() {
    }

    public Music(String name, String icon, int url) {
        this.name = name;
        this.icon = icon;
        this.resId = url;
    }

    protected Music(Parcel in) {
        name = in.readString();
        icon = in.readString();
        resId = in.readInt();
    }

    public static final Creator<Music> CREATOR = new Creator<Music>() {
        @Override
        public Music createFromParcel(Parcel in) {
            return new Music(in);
        }

        @Override
        public Music[] newArray(int size) {
            return new Music[size];
        }
    };

    public static List<Music> getDefaultData(){
        List<Music> musicList = new ArrayList<>();
        musicList.add(new Music("青城山下白素贞", "ic_music", R.raw.music1));
        musicList.add(new Music("二珂-平凡之路", "ic_music2", R.raw.music2));
        musicList.add(new Music("A.I.N.Y（爱你）", "ic_music3", R.raw.music3));
        return musicList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(icon);
        dest.writeInt(resId);
    }
}
