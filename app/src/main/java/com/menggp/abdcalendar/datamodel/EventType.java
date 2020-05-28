package com.menggp.abdcalendar.datamodel;

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

} // end_enum
