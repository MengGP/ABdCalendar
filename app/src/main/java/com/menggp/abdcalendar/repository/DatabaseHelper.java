package com.menggp.abdcalendar.repository;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/*
    Класс репозитория - для работы с БД SQLite
        - содержит описание БД
        - реализует доступ к БД
        - реализует создание и обновление схемы БД
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "eventsdb.db";    // имя БД
    private static final int SCHEMA = 1;                    // версия БД

    // Описание таблицы EVENTS
    public static final String TABLE_EVENTS = "events";
    public static final String COL_EVENT_ID = "_id";
    public static final String COL_EVENT_NAME = "event_name";
    public static final String COL_EVENT_DATE = "event_date";
    public static final String COL_EVENT_TYPE = "event_type";
    public static final String COL_EVENT_SINCE_YEAR = "event_since_year";
    public static final String COL_EVENT_COMMENT = "event_comment";
    public static final String COL_EVENT_IMG = "event_img";
    public static final String COL_EVENT_ALERT_TYPE = "event_alert_type";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, SCHEMA);
    } // end_constructor

    // Создание БД - если БД не существует или сменилась версия БД (запускается из метода onUpgrade)
    @Override
    public void onCreate(SQLiteDatabase db) {
        // запрос на создание новой таблицы в БД
        db.execSQL("CREATE TABLE " + TABLE_EVENTS + " (" +
                COL_EVENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_EVENT_NAME + " TEXT," +
                COL_EVENT_DATE + " TEXT," +
                COL_EVENT_TYPE + " TEXT," +
                COL_EVENT_SINCE_YEAR + " INTEGER," +
                COL_EVENT_COMMENT + " TEXT," +
                COL_EVENT_IMG + " INTEGER," +
                COL_EVENT_ALERT_TYPE + " TEXT);"
        );
    } // end_method

    // Метод - проверяет версию БД - если версия отличается от текущей - обновляем БД
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENTS);
        onCreate(db);
    } // end_class


} // end_class
