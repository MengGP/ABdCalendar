package com.menggp.abdcalendar.repository;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.menggp.abdcalendar.datamodel.Event;
import com.menggp.abdcalendar.datamodel.EventAlertType;
import com.menggp.abdcalendar.datamodel.EventType;

/*
    Класс реализует взаимодействие с БД
        - описывает запросы к БД
 */
class SQLiteQueryHandler {

    // Метод возвращает все записи EVENT - в виде курсора
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

    // Метод возвращает запись EVENT по ID
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

    // Метод добавляет в таблицу EVENTS запись
    static long insertEvent(SQLiteDatabase db, Event event) {
        // Создаем объект ContentValues - для передачи в БД
        ContentValues cv = new ContentValues();
        cv.put( DatabaseHelper.COL_EVENT_NAME, event.getEventName() );
        cv.put( DatabaseHelper.COL_EVENT_DATE, event.getEventDate() );
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

    // Метод удаляет запись из таблицы EVENTS
    static long deleteEvent(SQLiteDatabase db, long eventId) {
        String whereClause = DatabaseHelper.COL_EVENT_ID + " = ?";
        String[] whereArgs = new String[] { String.valueOf(eventId) };
        return db.delete(DatabaseHelper.TABLE_EVENTS, whereClause, whereArgs);
    } // end_method

    static long updateEvent(SQLiteDatabase db, Event event) {
        String whereClause = DatabaseHelper.COL_EVENT_ID + "=" + String.valueOf( event.getId() );

        // Создаем объект ContentValues - с данными для обновления
        ContentValues cv = new ContentValues();
        cv.put( DatabaseHelper.COL_EVENT_NAME, event.getEventName() );
        cv.put( DatabaseHelper.COL_EVENT_DATE, event.getEventDate() );
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




} // end_class
