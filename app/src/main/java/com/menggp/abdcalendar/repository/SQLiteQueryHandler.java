package com.menggp.abdcalendar.repository;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import com.menggp.abdcalendar.datamodel.DateHandler;
import com.menggp.abdcalendar.datamodel.Event;
import com.menggp.abdcalendar.datamodel.EventAlertType;
import com.menggp.abdcalendar.datamodel.EventMonthFilter;
import com.menggp.abdcalendar.datamodel.EventType;
import com.menggp.abdcalendar.datamodel.EventTypeFilter;

/*
    Класс реализует взаимодействие с БД на уровне запросов
 */
class SQLiteQueryHandler {

    /*
        Метод возвращает записи EVENT в соответствии с фильтрами и сортировками - в виде курсора
            типы сортировки для аргумента sortType:
                0 - по умолчанию - от текущей даты
                1 - от начала года
                2 - по имени по возрастанию
     */
    static Cursor getEvents(SQLiteDatabase db, String nameFilter, EventTypeFilter typeFilter, EventMonthFilter monthFilter, int sortType) {
        // Массив строк - содержащеий получаемые в запросе поля
        String[] columns = new String[] {
                DatabaseHelper.COL_EVENT_ID,
                DatabaseHelper.COL_EVENT_NAME,
                DatabaseHelper.COL_EVENT_DATE,
                DatabaseHelper.COL_EVENT_TYPE,
                DatabaseHelper.COL_EVENT_SINCE_YEAR,
                DatabaseHelper.COL_EVENT_COMMENT,
                DatabaseHelper.COL_EVENT_IMG,
                DatabaseHelper.COL_EVENT_ALERT_TYPE
        };

        // Строка условия
        String whereClause = "";
        // Аргументы условия
        String[] whereArgs = null;
        // Строка сортировки
        String orderByClause = "";

        // Блок условия гарантированного исполнения - для формироания комплексного условия запроса
        whereClause += "_id != 0";

        // Блок фильра по типу
        if ( typeFilter!=null && typeFilter.filterExist() ) {
            whereClause += " AND ";
            whereClause += DatabaseHelper.COL_EVENT_TYPE + " NOT IN ( 'x', ";   // начало строки условия
            if ( !typeFilter.isBirthdayOn() ) whereClause += "'"+EventType.convertToString(EventType.BIRTHDAY)+"', ";
            if ( !typeFilter.isAnniversaryOn() ) whereClause += "'"+EventType.convertToString(EventType.ANNIVERSARY)+"', ";
            if ( !typeFilter.isMemodateOn() ) whereClause += "'"+EventType.convertToString(EventType.MEMODATE)+"', ";
            if ( !typeFilter.isHolidayOn() ) whereClause += "'"+EventType.convertToString(EventType.HOLIDAY)+"', ";
            if ( !typeFilter.isOtherOn() ) whereClause += "'"+EventType.convertToString(EventType.OTHER)+"', ";
            whereClause += " 'y')";     // завершение строки условия
        }

        // Блок фильтра по месяцу
        if ( monthFilter!=null && monthFilter.filterExist() ) {
            whereClause += " AND ";
            whereClause += "strftime('%m',"+DatabaseHelper.COL_EVENT_DATE+") NOT IN ( 'x', ";      // начало строки условия
            if ( !monthFilter.isMonth01() ) whereClause += "'01', ";
            if ( !monthFilter.isMonth02() ) whereClause += "'02', ";
            if ( !monthFilter.isMonth03() ) whereClause += "'03', ";
            if ( !monthFilter.isMonth04() ) whereClause += "'04', ";
            if ( !monthFilter.isMonth05() ) whereClause += "'05', ";
            if ( !monthFilter.isMonth06() ) whereClause += "'06', ";
            if ( !monthFilter.isMonth07() ) whereClause += "'07', ";
            if ( !monthFilter.isMonth08() ) whereClause += "'08', ";
            if ( !monthFilter.isMonth09() ) whereClause += "'09', ";
            if ( !monthFilter.isMonth10() ) whereClause += "'10', ";
            if ( !monthFilter.isMonth11() ) whereClause += "'11', ";
            if ( !monthFilter.isMonth12() ) whereClause += "'12', ";
            whereClause += " 'y')";     // завершение строки условия
        }

        // Блок фильтрации по имени события
        if ( !nameFilter.isEmpty() ) {
            whereClause += " AND ";
            whereClause += DatabaseHelper.COL_EVENT_NAME + " LIKE ?";
            whereArgs = new String[] {"%"+ nameFilter +"%"};
        }

        // Блок сортировки
        switch (sortType) {
            case 0: // тип 0 - от текущей даты
                // Два варианта поведения:
                //  1 - обычное: если не задана фильтрация по месяцам
                //  2 - с начала месяца - если задана фильтрация по месяцам - чтобы не дробить текущий месяц
                if ( monthFilter==null || !monthFilter.filterExist() ) {
                    orderByClause += "strftime('%m-%d'," + DatabaseHelper.COL_EVENT_DATE + ", '-" +
                            (DateHandler.getNowMonth() - 1) + " months', '-" +     // смещение месяцев-1
                            (DateHandler.getNowDay() - 1) + " days')";             // смещение дней-1
                } else {
                    orderByClause += "strftime('%m-%d'," + DatabaseHelper.COL_EVENT_DATE + ", '-" +
                            (DateHandler.getNowMonth() - 1) + " months')";     // смещение месяцев-1
                }
                break;
            case 1: // тип 1 - от начала года
                orderByClause += DatabaseHelper.COL_EVENT_DATE;
                break;
            case 2: // тип 2 - по имени события
                orderByClause += "UPPER("+DatabaseHelper.COL_EVENT_NAME+")";
                break;
        }


        return db.query(
                DatabaseHelper.TABLE_EVENTS,        // целевая таблица
                columns,                            // поля
                whereClause,                        // блок условий
                whereArgs,                          // аргументы условий
                null,                      // блок группировки
                null,                       // блок HAVING
                orderByClause                       // блок сортировки
        );
    } // end_method

    /*
        Метод возвращает все события на заданную дату
            - фильтр по типу события
            - сортировка по имени события
     */
    static Cursor getEventsOnDay(SQLiteDatabase db, EventTypeFilter typeFilter, String date) {
        // Массив строк - содержащеий получаемые в запросе поля
        String[] columns = new String[] {
                DatabaseHelper.COL_EVENT_ID,
                DatabaseHelper.COL_EVENT_NAME,
                DatabaseHelper.COL_EVENT_DATE,
                DatabaseHelper.COL_EVENT_TYPE,
                DatabaseHelper.COL_EVENT_SINCE_YEAR,
                DatabaseHelper.COL_EVENT_COMMENT,
                DatabaseHelper.COL_EVENT_IMG,
                DatabaseHelper.COL_EVENT_ALERT_TYPE
        };

        // Условие отбора по дате
        String whereClause = "strftime('%m-%d',"+DatabaseHelper.COL_EVENT_DATE + ") = ?";
        String[] whereArgs = new String[] { date };

        // Блок условия фильра по типу
        if ( typeFilter.filterExist() ) {
            whereClause += " AND ";
            whereClause += DatabaseHelper.COL_EVENT_TYPE + " NOT IN ( 'x', ";   // начало строки условия
            if ( !typeFilter.isBirthdayOn() ) whereClause += "'"+EventType.convertToString(EventType.BIRTHDAY)+"', ";
            if ( !typeFilter.isAnniversaryOn() ) whereClause += "'"+EventType.convertToString(EventType.ANNIVERSARY)+"', ";
            if ( !typeFilter.isMemodateOn() ) whereClause += "'"+EventType.convertToString(EventType.MEMODATE)+"', ";
            if ( !typeFilter.isHolidayOn() ) whereClause += "'"+EventType.convertToString(EventType.HOLIDAY)+"', ";
            if ( !typeFilter.isOtherOn() ) whereClause += "'"+EventType.convertToString(EventType.OTHER)+"', ";
            whereClause += " 'y')";     // завершение строки условия
        }

        // Строка сортировки - сортировка по имени события
        String orderByClause = "UPPER("+DatabaseHelper.COL_EVENT_NAME+")";

        return db.query(
                DatabaseHelper.TABLE_EVENTS,        // целевая таблица
                columns,                            // поля
                whereClause,                        // блок условий
                whereArgs,                          // аргументы условий
                null,                      // блок группировки
                null,                       // блок HAVING
                orderByClause                       // блок сортировки
        );
    } // end_method

    /*
        Метод возвращает все записи EVENT - в виде курсора: без фильтров и сортировок
     */
    static Cursor getAllEvents(SQLiteDatabase db) {
        // Массив строк - содержащеий получаемые в запросе поля
        String[] columns = new String[] {
                DatabaseHelper.COL_EVENT_ID,
                DatabaseHelper.COL_EVENT_NAME,
                DatabaseHelper.COL_EVENT_DATE,
                DatabaseHelper.COL_EVENT_TYPE,
                DatabaseHelper.COL_EVENT_SINCE_YEAR,
                DatabaseHelper.COL_EVENT_COMMENT,
                DatabaseHelper.COL_EVENT_IMG,
                DatabaseHelper.COL_EVENT_ALERT_TYPE
        };

        return db.query(
                DatabaseHelper.TABLE_EVENTS,        // целевая таблица
                columns,                            // поля
                null,                      // блок условий
                null,                  // аргументы условий
                null,                      // блок группировки
                null,                       // блок HAVING
                null                       // блок сортировки
        );
    } // end_method

    /*
        Метод возвращает запись EVENT по ID
     */
    static Cursor getEventByID(SQLiteDatabase db, long id) {
        // Строка с описанием запроса
        String query = String.format(
                "SELECT * FROM %s WHERE %s=?",
                DatabaseHelper.TABLE_EVENTS,
                DatabaseHelper.COL_EVENT_ID
        );

        return db.rawQuery(
                query,                              // запрос
                new String[]{ String.valueOf(id) }  // массив строк - аргументов
        );
    } // end_method

    /*
        Метод добавляет в таблицу EVENTS запись
     */
    static long insertEvent(SQLiteDatabase db, Event event) {
        // Создаем объект ContentValues - для передачи в БД
        ContentValues cv = new ContentValues();
        cv.put( DatabaseHelper.COL_EVENT_NAME, event.getEventName() );
        // для поля таблицы EVENTS - events_date - добавляем год (неизменый) для корректной работы с этим полем как с датой функций SQLite
        cv.put( DatabaseHelper.COL_EVENT_DATE, "2020-"+event.getEventDate() );
        cv.put( DatabaseHelper.COL_EVENT_TYPE, EventType.convertToString(event.getEventType()) );
        cv.put( DatabaseHelper.COL_EVENT_SINCE_YEAR, event.getEventSinceYear() );
        cv.put( DatabaseHelper.COL_EVENT_COMMENT, event.getEventComment() );
        cv.put( DatabaseHelper.COL_EVENT_IMG, event.getEventImg() );
        cv.put( DatabaseHelper.COL_EVENT_ALERT_TYPE, EventAlertType.convertToString(event.getEventAlertType()) );

        return db.insert(
                DatabaseHelper.TABLE_EVENTS,
                null,
                cv
        );
    } // end_class

    /*
        Метод удаляет запись из таблицы EVENTS
     */
    static long deleteEvent(SQLiteDatabase db, long eventId) {
        String whereClause = DatabaseHelper.COL_EVENT_ID + " = ?";
        String[] whereArgs = new String[] { String.valueOf(eventId) };
        return db.delete(DatabaseHelper.TABLE_EVENTS, whereClause, whereArgs);
    } // end_method

    /*
        Метод обновляет запись события в БД
     */
    static long updateEvent(SQLiteDatabase db, Event event) {
        String whereClause = DatabaseHelper.COL_EVENT_ID + "=" + event.getId();

        // Создаем объект ContentValues - с данными для обновления
        ContentValues cv = new ContentValues();
        cv.put( DatabaseHelper.COL_EVENT_NAME, event.getEventName() );
        // для поля таблицы EVENTS - events_date - добавляем год (неизменый) для корректной работы с этим полем как с датой функций SQLite
        cv.put( DatabaseHelper.COL_EVENT_DATE, "2020-"+event.getEventDate() );
        cv.put( DatabaseHelper.COL_EVENT_TYPE, EventType.convertToString(event.getEventType()) );
        cv.put( DatabaseHelper.COL_EVENT_SINCE_YEAR, event.getEventSinceYear() );
        cv.put( DatabaseHelper.COL_EVENT_COMMENT, event.getEventComment() );
        cv.put( DatabaseHelper.COL_EVENT_IMG, event.getEventImg() );
        cv.put( DatabaseHelper.COL_EVENT_ALERT_TYPE, EventAlertType.convertToString(event.getEventAlertType()) );

        return
                db.update(
                        DatabaseHelper.TABLE_EVENTS,    // таблица
                        cv,                             // данные для обновления
                        whereClause,                    // условие выбота обновляемой записи
                        null                  // аргументы условия
                );

    } // end_method

    /*
        Метод возвращаеи количество записей в БД - для определенной даты
     */
    static long getEventCountOnDay(SQLiteDatabase db, String date, EventTypeFilter typeFilter) {
        // Условие отбора по дате
        String whereClause = "strftime('%m-%d',"+DatabaseHelper.COL_EVENT_DATE + ") = ?";
        String[] whereArgs = new String[] { date };

        // Блок условия фильра по типу
        if ( typeFilter!=null && typeFilter.filterExist() ) {
            whereClause += " AND ";
            whereClause += DatabaseHelper.COL_EVENT_TYPE + " NOT IN ( 'x', ";   // начало строки условия
            if ( !typeFilter.isBirthdayOn() ) whereClause += "'"+EventType.convertToString(EventType.BIRTHDAY)+"', ";
            if ( !typeFilter.isAnniversaryOn() ) whereClause += "'"+EventType.convertToString(EventType.ANNIVERSARY)+"', ";
            if ( !typeFilter.isMemodateOn() ) whereClause += "'"+EventType.convertToString(EventType.MEMODATE)+"', ";
            if ( !typeFilter.isHolidayOn() ) whereClause += "'"+EventType.convertToString(EventType.HOLIDAY)+"', ";
            if ( !typeFilter.isOtherOn() ) whereClause += "'"+EventType.convertToString(EventType.OTHER)+"', ";
            whereClause += " 'y')";     // завершение строки условия
        }

        return DatabaseUtils.queryNumEntries(db,
                DatabaseHelper.TABLE_EVENTS,
                whereClause,
                whereArgs);
    } // end_method

    // Метод стирает все данные БД
    static void flushDB(SQLiteDatabase db) {
        db.execSQL("DELETE FROM " + DatabaseHelper.TABLE_EVENTS);
    } // end_method



} // end_class
