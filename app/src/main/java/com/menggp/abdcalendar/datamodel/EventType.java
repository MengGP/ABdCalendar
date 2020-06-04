package com.menggp.abdcalendar.datamodel;

import android.content.res.Resources;

import com.menggp.abdcalendar.R;

/*
    enum
        - описывает типы событий
 */
public enum EventType {

    // values
    BIRTHDAY,       // день рожедения
    ANNIVERSARY,    // годовщина
    MEMODATE,       // памятная дата
    HOLIDAY,        // праздник
    OTHER;          // другое

    // Приведение заничения EventType - к String
    public static String convertToString(EventType type) {
        if ( type == null ) return null;
        switch (type) {
            case BIRTHDAY: return "birthday";
            case ANNIVERSARY: return "anniversary";
            case MEMODATE: return "memodate";
            case HOLIDAY: return "holiday";
            case OTHER: return "other";
        }
        return  null;
    } // end_method

    // Приведение значения String - к EventType
    public static EventType convertToEventType(String type) {
        if ( type == null ) return null;
        switch (type) {
            case "birthday": return BIRTHDAY;
            case "anniversary": return ANNIVERSARY;
            case "memodate": return MEMODATE;
            case "holiday": return HOLIDAY;
            case "other": return OTHER;
        }
        return null;
    } // end_method

    // Приведение к предствлениею для списка
    public static String convertToHumanNotation(Resources res, EventType eventType) {
        switch (eventType) {
            case BIRTHDAY: return res.getString(R.string.birthday);
            case ANNIVERSARY: return res.getString(R.string.anniversary);
            case MEMODATE: return res.getString(R.string.memodate);
            case HOLIDAY: return res.getString(R.string.holiday);
            case OTHER: return res.getString(R.string.other);
        }
        return null;
    } // end_method

} // end_enum
