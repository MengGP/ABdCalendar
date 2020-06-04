package com.menggp.abdcalendar.datamodel;

import android.content.res.Resources;

import com.menggp.abdcalendar.R;

public enum EventAlertType {

    // values
    NO_ALERT,           // без уведомления
    SILENT_ALERT,       // тихое уведомления - без синала - задень до и в день события
    IN_DAY_ALERT,       // уведомление в день события
    DAY_BEFORE_ALERT,   // уведомление за день до события и в день события
    INTENSIVE_ALERT,    // уведомление да день до события и в день события - сигнал трижды в день
    EVERY_MONTH_ALERT;  // ежемесячное уведомлен - в день события

    // Приведение заничения EventAlertType - к String
    public static String convertToString(EventAlertType type) {
        if ( type == null ) return null;
        switch (type) {
            case NO_ALERT: return "no_alert";
            case SILENT_ALERT: return "silent_alert";
            case IN_DAY_ALERT: return "in_day_alert";
            case DAY_BEFORE_ALERT: return "day_before_alert";
            case INTENSIVE_ALERT: return "intensive_alert";
            case EVERY_MONTH_ALERT: return "every_month_alert";
        }
        return  null;
    } // end_method

    // Приведение значения String - к EventType
    public static EventAlertType convertToEventAlertType(String type) {
        if ( type == null ) return null;
        switch (type) {
            case "no_alert": return NO_ALERT;
            case "silent_alert": return SILENT_ALERT;
            case "in_day_alert": return IN_DAY_ALERT;
            case "day_before_alert": return DAY_BEFORE_ALERT;
            case "intensive_alert": return INTENSIVE_ALERT;
            case "every_month_alert": return EVERY_MONTH_ALERT;
        }
        return null;
    } // end_method

    // Приведение к предствлениею для списка
    public static String convertToHumanNotation(Resources res, EventAlertType eventAlertType) {
        switch (eventAlertType) {
            case NO_ALERT: return res.getString(R.string.no_alert);
            case SILENT_ALERT: return res.getString(R.string.silent_alert);
            case IN_DAY_ALERT: return res.getString(R.string.in_day_alert);
            case DAY_BEFORE_ALERT: return res.getString(R.string.day_before_alert);
            case INTENSIVE_ALERT: return res.getString(R.string.intensive_alert);
            case EVERY_MONTH_ALERT: return res.getString(R.string.every_month_alert);
        }
        return null;
    } // end_method


} // end_enum
