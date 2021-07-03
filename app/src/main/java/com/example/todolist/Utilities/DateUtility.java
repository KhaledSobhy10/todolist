package com.example.todolist.Utilities;

import android.text.format.DateFormat;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtility {
    public static final String datePattern = "dd/MM/yyyy";


    public static String getCurrentDate() {
        return formatDate(new Date(), datePattern);
    }

    public static String formatDate(Date date, String pattern) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, Locale.ENGLISH);
        return simpleDateFormat.format(date);
    }

    public static String IntegerDateToString(int year, int month, int day) {
        month++;
        StringBuilder stringBuilder = new StringBuilder();
        if (day < 10) {
            stringBuilder.append('0');
        }
        stringBuilder.append(day);
        stringBuilder.append('/');
        if (month < 10) {
            stringBuilder.append('0');
        }
        stringBuilder.append(month);
        stringBuilder.append('/');
        stringBuilder.append(year);
        return stringBuilder.toString();
    }


    public static boolean isValidData(String strDate) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(datePattern);
        simpleDateFormat.setLenient(false);
        try {
            Date date = simpleDateFormat.parse(strDate);
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);

            Date todayDate = calendar.getTime();

            return todayDate.compareTo(date) <= 0;
        } catch (ParseException e) {
            Log.d("date", "isValidData: false");
            return false;
        }

    }


    public static String getDay(String stringDate) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(datePattern);
        String dayOfTheWeek = null;
        try {
            Date date = simpleDateFormat.parse(stringDate);
             dayOfTheWeek = (String) DateFormat.format("EEEE", date); // Thursday
            Log.d("hello", "getDay: "+dayOfTheWeek);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return dayOfTheWeek;
    }

    public static String getTodayDayOfWeek() {
      return (String) DateFormat.format("EEEE", new Date());
    }

}
