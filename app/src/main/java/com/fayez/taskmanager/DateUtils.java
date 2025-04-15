package com.fayez.taskmanager;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RequiresApi(api = Build.VERSION_CODES.O)
public class DateUtils {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static String formatDateToString(LocalDate localDate) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return localDate.format(formatter);
        }
        else
            return  localDate.format(formatter);
    }

    public static LocalDate parseStringToDate(String dateString) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return LocalDate.parse(dateString, formatter);
        }
        else return LocalDate.parse(dateString, formatter);
    }

}
