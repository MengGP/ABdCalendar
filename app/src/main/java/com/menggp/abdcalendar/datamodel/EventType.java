package com.menggp.abdcalendar.datamodel;

import android.content.res.Resources;

import com.menggp.abdcalendar.R;

import java.util.ArrayList;

/*
     Enum - типы события
        - типы событий и статическик методы работы с ними
 */
public enum EventType {

    // values
    BIRTHDAY,       // день рожедения   - в адаптере = 0
    ANNIVERSARY,    // годовщина        - в адаптере = 1
    MEMODATE,       // памятная дата    - в адаптере = 2
    HOLIDAY,        // праздник         - в адаптере = 3
    OTHER;          // другое           - в адаптере = 4

    /*
        Метод приводит значение EventType к String - для хранения в БД
     */
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

    /*
        Метод приводит значени String (в нотаци БД) к EventType
     */
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

    /*
        Метод приводит значение EventType - к прадставлению для разметки
     */
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

    /*
        Метод возвращает ArrayList<String> со списком типов событий в представлении для разметки
     */
    public static ArrayList<String> getEventTypeStrLst(Resources res) {
        ArrayList<String> eventTypeStrList = new ArrayList<>();
        eventTypeStrList.add( convertToHumanNotation(res, BIRTHDAY) );
        eventTypeStrList.add( convertToHumanNotation(res, ANNIVERSARY) );
        eventTypeStrList.add( convertToHumanNotation(res, MEMODATE) );
        eventTypeStrList.add( convertToHumanNotation(res, HOLIDAY) );
        eventTypeStrList.add( convertToHumanNotation(res, OTHER) );
        return eventTypeStrList;
    } // end_method

    /*
        Метод возващает индекс (в адаптере) типа события
     */
    public static int getIndexByEventType(EventType eventType) {
        switch (eventType) {
            case BIRTHDAY: return 0;
            case ANNIVERSARY: return 1;
            case MEMODATE: return 2;
            case HOLIDAY: return 3;
            case OTHER: return 4;
        }
        return  -1;
    } // end_method

    /*
        Метод возвращает тип события по его индексу (в адаптере)
     */
    public static EventType getEventTypeByIndex(int index) {
        switch (index) {
            case 0: return BIRTHDAY;
            case 1: return ANNIVERSARY;
            case 2: return MEMODATE;
            case 3: return HOLIDAY;
            case 4: return OTHER;
        }
        return  null;
    } // end_method

} // end_enum
