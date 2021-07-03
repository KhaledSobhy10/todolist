package com.example.todolist.Utilities;

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


}
