package com.menggp.abdcalendar.datamodel;

import android.content.res.Resources;

import com.menggp.abdcalendar.R;

import java.util.ArrayList;

/*
    Enum - типы напоминания события
        - типы напоминаний и статическик методы работы с ними
 */
public enum EventAlertType {

    // values
    NO_ALERT,           // без уведомления                                                          - в адаптере = 0
    SILENT_ALERT,       // тихое уведомления - без синала - задень до и в день события              - в адаптере = 1
    IN_DAY_ALERT,       // уведомление в день события                                               - в адаптере = 2
    DAY_BEFORE_ALERT,   // уведомление за день до события и в день события                          - в адаптере = 3
    INTENSIVE_ALERT,    // уведомление да день до события и в день события - сигнал трижды в день   - в адаптере = 4
    EVERY_MONTH_ALERT;  // ежемесячное уведомлен - в день события                                   - в адаптере = 5

    // Приведение заничения EventAlertType - к String, для хранения в БД
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

    // Приведение значения String - к EventType, для хранения в БД
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

    /*
        Метод возвращает ArrayList<String> со списком типов напиминая в представлении для разметки
     */
    public static ArrayList<String> getEventAlertTypeStrLst(Resources res) {
        ArrayList<String> eventAlertTypeStrList = new ArrayList<>();
        eventAlertTypeStrList.add( convertToHumanNotation(res, NO_ALERT) );
        eventAlertTypeStrList.add( convertToHumanNotation(res, SILENT_ALERT) );
        eventAlertTypeStrList.add( convertToHumanNotation(res, IN_DAY_ALERT) );
        eventAlertTypeStrList.add( convertToHumanNotation(res, DAY_BEFORE_ALERT) );
        eventAlertTypeStrList.add( convertToHumanNotation(res, INTENSIVE_ALERT) );
        eventAlertTypeStrList.add( convertToHumanNotation(res, EVERY_MONTH_ALERT) );
        return eventAlertTypeStrList;
    } // end_method

    /*
        Метод возващает индекс (в адаптере) типа события
     */
    public static int getIndexByEventAlertType(EventAlertType eventAlertType) {
        switch (eventAlertType) {
            case NO_ALERT: return 0;
            case SILENT_ALERT: return 1;
            case IN_DAY_ALERT: return 2;
            case DAY_BEFORE_ALERT: return 3;
            case INTENSIVE_ALERT: return 4;
            case EVERY_MONTH_ALERT: return 5;
        }
        return  -1;
    } // end_method

    /*
        Метод возвращает тип напоминания по его индексу (в адаптере)
     */
    public static EventAlertType getEventAlertTypeByIndex(int index) {
        switch (index) {
            case 0: return NO_ALERT;
            case 1: return SILENT_ALERT;
            case 2: return IN_DAY_ALERT;
            case 3: return DAY_BEFORE_ALERT;
            case 4: return INTENSIVE_ALERT;
            case 5: return EVERY_MONTH_ALERT;
        }
        return  null;
    } // end_method



} // end_enum
