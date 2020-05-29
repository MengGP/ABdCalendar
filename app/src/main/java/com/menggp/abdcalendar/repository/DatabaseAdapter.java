package com.menggp.abdcalendar.repository;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

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



} // end_class
