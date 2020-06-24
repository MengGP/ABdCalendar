package com.menggp.abdcalendar.repository;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import com.menggp.abdcalendar.datamodel.Event;
import com.menggp.abdcalendar.datamodel.EventAlertType;
import com.menggp.abdcalendar.datamodel.EventMonthFilter;
import com.menggp.abdcalendar.datamodel.EventType;
import com.menggp.abdcalendar.datamodel.EventTypeFilter;

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

    /*
       Метод - открывает соеднение с БД на запись
     */
    private void open() {
        db = dbHelper.getWritableDatabase();
        // return this;
    } // end_method

    /*
        Метод закрывает соединение с БД
     */
    private void close() {
        dbHelper.close();
    } // end_method

    /*
        Метод возвращает записи EVENT в соответствии с фильтрами и сортировками
            типы сортировки для аргумента sortType:
                0 - по умолчанию - от текущей даты
                1 - от начала года
                2 - по имени по возрастанию
     */
    public  List<Event> getEvents(String nameFilter, EventTypeFilter typeFilter, EventMonthFilter monthFilter, int sortType) {
        ArrayList<Event> events = new ArrayList<>();
        this.open();            // открываем соедиенение с БД

        Cursor cursor = SQLiteQueryHandler.getEvents(db, nameFilter, typeFilter, monthFilter, sortType);

        // Если курсор содержит данные - извлекаем их и помещаем в ArrayList - events
        if (cursor.moveToFirst() ) {
            do {
                long id = cursor.getLong( cursor.getColumnIndex( dbHelper.COL_EVENT_ID) );
                String eventName = cursor.getString( cursor.getColumnIndex(dbHelper.COL_EVENT_NAME) );
                // Для даты получаемой из БД - отбрасываем значение года (которое используется только для удобства хранения в БД)
                String eventDate = cursor.getString( cursor.getColumnIndex(dbHelper.COL_EVENT_DATE) );
                eventDate = eventDate.substring(5,10);
                EventType eventType = EventType.convertToEventType( cursor.getString( cursor.getColumnIndex(dbHelper.COL_EVENT_TYPE) ) );
                int eventSinceYear = cursor.getInt( cursor.getColumnIndex(dbHelper.COL_EVENT_SINCE_YEAR) );
                String eventComment = cursor.getString( cursor.getColumnIndex(dbHelper.COL_EVENT_COMMENT) );
                int eventImg = cursor.getInt( cursor.getColumnIndex(dbHelper.COL_EVENT_IMG) );
                EventAlertType eventAlertType = EventAlertType.convertToEventAlertType( cursor.getString( cursor.getColumnIndex(dbHelper.COL_EVENT_ALERT_TYPE) ) );

                events.add( new Event(id, eventName, eventDate, eventType, eventSinceYear, eventComment, eventImg, eventAlertType) );
            } while ( cursor.moveToNext() );
        }
        cursor.close();

        this.close();           // закрываем соедиенние с БД
        return events;
    } // end_method

    /*
        Перегруженный метод getEvents - без фильтра по имени события
     */
    public  List<Event> getEvents(EventTypeFilter typeFilter, EventMonthFilter monthFilter, int sortType) {
        return this.getEvents("", typeFilter, monthFilter, sortType);
    } // end_method

    /*
        Перегруженный метод getEvents - только фильтр по типу
    */
    public List<Event> getEvents(EventTypeFilter typeFilter, int month) {
        EventMonthFilter monthFilter = new EventMonthFilter();
        // в зависимости от полученого месяца - устанвливаем фильтр только по этому месяцу
        switch (month) {
            case 0: monthFilter.setMonth01(true); break;
            case 1: monthFilter.setMonth02(true); break;
            case 2: monthFilter.setMonth03(true); break;
            case 3: monthFilter.setMonth04(true); break;
            case 4: monthFilter.setMonth05(true); break;
            case 5: monthFilter.setMonth06(true); break;
            case 6: monthFilter.setMonth07(true); break;
            case 7: monthFilter.setMonth08(true); break;
            case 8: monthFilter.setMonth09(true); break;
            case 9: monthFilter.setMonth10(true); break;
            case 10: monthFilter.setMonth11(true); break;
            case 11: monthFilter.setMonth12(true); break;
        }
        int sortType = 1;
        return this.getEvents("", typeFilter, monthFilter, sortType);
    } // end_method

    /*
        Метод возвращает события на указанный день - с фильтрацией по типу
     */
    public List<Event> getEventsOnDay(EventTypeFilter typeFilter, String date) {
        ArrayList<Event> events = new ArrayList<>();
        this.open();

        Cursor cursor = SQLiteQueryHandler.getEventsOnDay(db, typeFilter, date);

        // Если курсор содержит данные - извлекаем их и помещаем в ArrayList - events
        if (cursor.moveToFirst() ) {
            do {
                long id = cursor.getLong( cursor.getColumnIndex( dbHelper.COL_EVENT_ID) );
                String eventName = cursor.getString( cursor.getColumnIndex(dbHelper.COL_EVENT_NAME) );
                // Для даты получаемой из БД - отбрасываем значение года (которое используется только для удобства хранения в БД)
                String eventDate = cursor.getString( cursor.getColumnIndex(dbHelper.COL_EVENT_DATE) );
                eventDate = eventDate.substring(5,10);
                EventType eventType = EventType.convertToEventType( cursor.getString( cursor.getColumnIndex(dbHelper.COL_EVENT_TYPE) ) );
                int eventSinceYear = cursor.getInt( cursor.getColumnIndex(dbHelper.COL_EVENT_SINCE_YEAR) );
                String eventComment = cursor.getString( cursor.getColumnIndex(dbHelper.COL_EVENT_COMMENT) );
                int eventImg = cursor.getInt( cursor.getColumnIndex(dbHelper.COL_EVENT_IMG) );
                EventAlertType eventAlertType = EventAlertType.convertToEventAlertType( cursor.getString( cursor.getColumnIndex(dbHelper.COL_EVENT_ALERT_TYPE) ) );

                events.add( new Event(id, eventName, eventDate, eventType, eventSinceYear, eventComment, eventImg, eventAlertType) );
            } while ( cursor.moveToNext() );
        }
        cursor.close();

        this.close();
        return events;
    } // end_method

    /*
        Метод возвращает все записи EVENT в виде ArrayList<Event>
     */
    public List<Event> getEventsAll() {
        ArrayList<Event> events = new ArrayList<>();    // ArrayList - для возврата результата метода
        this.open();                                    // открываем соединение с БД

        Cursor cursor = SQLiteQueryHandler.getAllEvents(db);

        // Если курсор содержит данные - извлекаем их и помещаем в ArrayList - events
        if (cursor.moveToFirst() ) {
            do {
                long id = cursor.getLong( cursor.getColumnIndex( dbHelper.COL_EVENT_ID) );
                String eventName = cursor.getString( cursor.getColumnIndex(dbHelper.COL_EVENT_NAME) );
                // Для даты получаемой из БД - отбрасываем значение года (которое используется только для удобства хранения в БД)
                String eventDate = cursor.getString( cursor.getColumnIndex(dbHelper.COL_EVENT_DATE) );
                eventDate = eventDate.substring(5,10);
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

    /*
        Метод - возващает количество записей в БД
     */
    public long geEventCount() {
        long count;
        this.open();
        count = DatabaseUtils.queryNumEntries(db, dbHelper.TABLE_EVENTS);
        this.close();
        return  count;
    } // end_method

    /*
        Метод возвращаеи количество записей в БД - для определенной даты
     */
    public long getEventCountOnDay(String date, EventTypeFilter typeFilter) {
        long count;
        this.open();
        count = SQLiteQueryHandler.getEventCountOnDay(db, date, typeFilter);
        this.close();
        return count;
    } // end_method

    /*
        Метод - возвращает объект по ID
     */
    public Event getEvent(long id) {
        Event event = null;
        this.open();

        Cursor cursor = SQLiteQueryHandler.getEventByID(db, id);

        // Если курсор содержит данные - извлекаем их
        if (cursor.moveToFirst() ) {
            // long id = cursor.getLong( cursor.getColumnIndex( dbHelper.COL_EVENT_ID) );   // ID - берем из входного аргумента
            String eventName = cursor.getString( cursor.getColumnIndex(dbHelper.COL_EVENT_NAME) );
            // Для даты получаемой из БД - отбрасываем значение года (которое используется только для удобства хранения в БД)
            String eventDate = cursor.getString( cursor.getColumnIndex(dbHelper.COL_EVENT_DATE) );
            eventDate = eventDate.substring(5,10);
            EventType eventType = EventType.convertToEventType( cursor.getString( cursor.getColumnIndex(dbHelper.COL_EVENT_TYPE) ) );
            int eventSinceYear = cursor.getInt( cursor.getColumnIndex(dbHelper.COL_EVENT_SINCE_YEAR) );
            String eventComment = cursor.getString( cursor.getColumnIndex(dbHelper.COL_EVENT_COMMENT) );
            int eventImg = cursor.getInt( cursor.getColumnIndex(dbHelper.COL_EVENT_IMG) );
            EventAlertType eventAlertType = EventAlertType.convertToEventAlertType( cursor.getString( cursor.getColumnIndex(dbHelper.COL_EVENT_ALERT_TYPE) ) );

            event = new Event(id, eventName, eventDate, eventType, eventSinceYear, eventComment, eventImg, eventAlertType);
        }
        cursor.close();

        this.close();
        return event;
    } // end_method

    /*
        Метод - добавляет запись в таблицу EVENTS
     */
    public long insertEvent(Event event) {
        long result;
        this.open();
        result = SQLiteQueryHandler.insertEvent(db, event);
        this.close();
        return result;
    } // end_method

    /*
        Метод удаляем запись из БД
     */
    public long deleteEvent(long eventId) {
        long result;
        this.open();
        result = SQLiteQueryHandler.deleteEvent(db, eventId);
        this.close();
        return  result;
    } // end_method

    /*
        Метод обновляет запись в БД
     */
    public long update(Event event) {
        long result;
        this.open();
        result = SQLiteQueryHandler.updateEvent(db, event);
        this.close();
        return result;
    } // end_method

    /*
        Метод страет все данные БД
     */
    public void hardResetData() {
        this.open();
        SQLiteQueryHandler.flushDB(db);
        this.close();
    } // end_method




} // end_class
