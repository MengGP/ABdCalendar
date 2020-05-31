package com.menggp.abdcalendar.repository;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import com.menggp.abdcalendar.datamodel.Event;
import com.menggp.abdcalendar.datamodel.EventAlertType;
import com.menggp.abdcalendar.datamodel.EventType;

import java.util.ArrayList;
import java.util.List;

/*
    Класс реализует адатпер работы с БД
        - для связи модели и БД исопользует класс .repository.DatabaseHelper
        - запросы вынесены в отдельный класс SQLiteQueryHandler
 */
public class DatabaseAdapter {

    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;

    public DatabaseAdapter(Context context) {
        dbHelper = new DatabaseHelper( context.getApplicationContext() );
    } // end_constructor

    // Метод - открывает соеднение с БД на запись
    public DatabaseAdapter open() {
        db = dbHelper.getWritableDatabase();
        return this;
    } // end_method

    // Метод закрывает соединение с БД
    public void close() {
        dbHelper.close();
    } // end_method

    // Метод возвращает записи EVENT в виде ArrayList
    public List<Event> getEventsGeneral() {
        ArrayList<Event> events = new ArrayList<>();    // ArrayList - для возврата результата метода
        this.open();                                    // открываем соединение с БД

        Cursor cursor = SQLiteQueryHandler.getAllEvents(db);

        // Если курсор содержит данные - извлекаем их и помещаем в ArrayList - events
        if (cursor.moveToFirst() ) {
            do {
                long id = cursor.getLong( cursor.getColumnIndex( dbHelper.COL_EVENT_ID) );
                String eventName = cursor.getString( cursor.getColumnIndex(dbHelper.COL_EVENT_NAME) );
                String eventDate = cursor.getString( cursor.getColumnIndex(dbHelper.COL_EVENT_DATE) );
                EventType eventType = EventType.convertToEventType( cursor.getString( cursor.getColumnIndex(dbHelper.COL_EVENT_TYPE) ) );
                int eventSinceYear = cursor.getInt( cursor.getColumnIndex(dbHelper.COL_EVENT_SINCE_YEAR) );
                String eventComment = cursor.getString( cursor.getColumnIndex(dbHelper.COL_EVENT_COMMENT) );
                int eventImg = cursor.getInt( cursor.getColumnIndex(dbHelper.COL_EVENT_IMG) );
                EventAlertType eventAlertType = EventAlertType.convertToEventAlertType( cursor.getString( cursor.getColumnIndex(dbHelper.COL_EVENT_ALERT_TYPE) ) );

                events.add( new Event(id, eventName, eventDate, eventType, eventSinceYear, eventComment, eventImg, eventAlertType) );
            } while ( cursor.moveToNext() );
        }
        cursor.close();

        this.close();                                   // закрываем соедиенение с БД
        return events;
    } // end_method

     // Метод - возващает количество записей в БД
    public long geEventCount() {
        long count;
        this.open();
        count = DatabaseUtils.queryNumEntries(db, dbHelper.TABLE_EVENTS);
        this.close();
        return  count;
    } // end_method

    // Метод - возвращает объект по ID
    public Event getEvent(long id) {
        Event event = null;
        this.open();

        Cursor cursor = SQLiteQueryHandler.getEventByID(db, id);

        // Если курсор содержит данные - извлекаем их
        if (cursor.moveToFirst() ) {
            // long id = cursor.getLong( cursor.getColumnIndex( dbHelper.COL_EVENT_ID) );   // ID - берем из входного аргумента
            String eventName = cursor.getString( cursor.getColumnIndex(dbHelper.COL_EVENT_NAME) );
            String eventDate = cursor.getString( cursor.getColumnIndex(dbHelper.COL_EVENT_DATE) );
            EventType eventType = EventType.convertToEventType( cursor.getString( cursor.getColumnIndex(dbHelper.COL_EVENT_TYPE) ) );
            int eventSinceYear = cursor.getInt( cursor.getColumnIndex(dbHelper.COL_EVENT_SINCE_YEAR) );
            String eventComment = cursor.getString( cursor.getColumnIndex(dbHelper.COL_EVENT_COMMENT) );
            int eventImg = cursor.getInt( cursor.getColumnIndex(dbHelper.COL_EVENT_IMG) );
            EventAlertType eventAlertType = EventAlertType.convertToEventAlertType( cursor.getString( cursor.getColumnIndex(dbHelper.COL_EVENT_ALERT_TYPE) ) );

            new Event(id, eventName, eventDate, eventType, eventSinceYear, eventComment, eventImg, eventAlertType);
        }
        cursor.close();

        this.close();
        return event;
    } // end_method

    // Метод - добавляет запись в таблицу EVENTS
    public long insertEvent(Event event) {
        long result;
        this.open();
        result = SQLiteQueryHandler.insertEvent(db, event);
        this.close();
        return result;
    } // end_method

    public long deleteEvent(long eventId) {
        long result;
        this.open();
        result = SQLiteQueryHandler.deleteEvent(db, eventId);
        this.close();
        return  result;
    } // end_method

    // Метод обновляет запись в таблице EVENTS
    public long update(Event event) {
        long result;
        this.open();
        result = SQLiteQueryHandler.updateEvent(db, event);
        this.close();
        return result;
    } // end_method






} // end_class
