package com.soboard.soulter.soboard.DB;

import android.database.Cursor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Administrator on 2018/7/10.
 */

public class GetLocalTime {
    public static String getLocalTime(Cursor cursor, String whatEntryWhatTimeStamp){
        //获取数据库里的timestamp的时间,但由于它是格林尼治标准时间,比中国的timezone少8h
        //所以先将此时间(还只是纯的string类型)转换为date类型,再将其timezone改成格林尼治时间
        //此时时间还没变,因为我们只是做了以下事情:
        //1.string类型的时间 换成 date类型的时间
        //2.date类型的时间设置timezone
        String timeBefore = cursor.getString(cursor.getColumnIndex(whatEntryWhatTimeStamp));
        SimpleDateFormat sdfBefore = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdfBefore.setTimeZone(TimeZone.getTimeZone("GMT+0:00"));
        Date date = null;
        try {
            date = sdfBefore.parse(timeBefore);
        }catch (ParseException e){
            e.printStackTrace();
        }
        //再改timezone
        SimpleDateFormat sdfAfter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdfAfter.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));



        String localTime = sdfAfter.format(date);

        return localTime;
    }
}
