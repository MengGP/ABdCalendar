package com.menggp.abdcalendar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.applandeo.materialcalendarview.CalendarUtils;
import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.menggp.abdcalendar.adapters.EventListAdapter;
import com.menggp.abdcalendar.datamodel.Event;
import com.menggp.abdcalendar.repository.DatabaseAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // --- Constants
    public static final String SHOW_SETTING_ACTIVITY = "com.menggp.SHOW_SETTINGS_ACTIVITY";

    // --- Attributes
    public static boolean isCalendarView = true;        // определяет какой вид необходимо отобрахать на главном экране: календарь или список
    DatabaseAdapter dbAdapter;
    EventListAdapter eventListAdapter;
    // Элементы разметки
    ListView eventListView;

    /*
        Метод - onCreate
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Обработка - в зависимости от текущего выбранного вида
        // вид - календаря
        if (isCalendarView) {
            setContentView(R.layout.activity_main_calendar);
        }
        // вид - списка
        else {
            setContentView(R.layout.activity_main_list);

            // Получаем элементы с разметки
            eventListView = (ListView) findViewById(R.id.main_list_event_list);

        }


    } // end_method

    /*
        Метод - onResume
     */
    @Override
    protected void onResume() {
        super.onResume();

        // Подключаем БД
        dbAdapter = new DatabaseAdapter(this);

        // Обработка - в зависимости от текущего выбранного вида
        // вид - календаря
        if (isCalendarView) { }
        // вид - списка
        else {
            // Получаем данные из БД
            List<Event> events = dbAdapter.getEventsGeneral();
            // Создаем адапред для списка EVENT
            eventListAdapter = new EventListAdapter(
                    this,               // контекст
                    R.layout.list_item_event,   // разметка
                    events                      // данные
            );
            // Устанавливаем адаптер для списка
            eventListView.setAdapter( eventListAdapter );
        }

    } // end_method

    /*
        Определение меню в Action Bar
         */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        // установка иконов в зависимости от текущего вида сноговного окна
        int calendarViewIcon, listViewIcon;
        if (isCalendarView) {
            calendarViewIcon = R.drawable.action_bar_main_calendar;
            listViewIcon = R.drawable.action_bar_main_list_gray;
        } else {
            calendarViewIcon = R.drawable.action_bar_main_calendar_gray;
            listViewIcon = R.drawable.action_bar_main_list;
        }

        menu.add(0
                ,1
                ,0
                ,"CalendarView")
                .setIcon(calendarViewIcon)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

        menu.add(0
                ,2
                ,1
                ,"ListView")
                .setIcon(listViewIcon)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

        menu.add(0
                ,3
                ,2
                ,"GoToSettingsActivity")
                .setIcon(R.drawable.action_bar_main_settings)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

        return true;
    } // end_method

    /*
    Обработка нажатия кнопок меню в Action Bar
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        /*
            1 - calendar view
            2 - list view
            3 - settings activity
         */
        switch (id) {
            case 1 :
                if (!isCalendarView) {
                    Toast toast_calendarView = Toast.makeText(this, "Calendar view PRESSED", Toast.LENGTH_SHORT);
                    toast_calendarView.show();
                    isCalendarView = true;
                    recreate();
                }
                return true;
            case 2 :
                if (isCalendarView) {
                    Toast toast_listView = Toast.makeText(this, "List view PRESSED", Toast.LENGTH_SHORT);
                    toast_listView.show();
                    isCalendarView = false;
                    recreate();
                }
                return true;
            case 3 :
                Intent intentSettings = new Intent(SHOW_SETTING_ACTIVITY);
                startActivity(intentSettings);
                return true;
        }
        return super.onOptionsItemSelected(item);
    } // end_class

    //--------------------------------------------------------------------------------------------------------------------------------------
    /*
        Тестовые методы
     */
    public void topBtn(View view){
        TextView tv = (TextView) findViewById(R.id.bottomText);
        tv.setText("TOP bnt was pressed");

        ImageView iv = (ImageView) findViewById(R.id.bottomDrawble);
        iv.setImageDrawable(CalendarUtils.getDrawableText(this, "img", null,  android.R.color.black, 8));

        List<EventDay> events = new ArrayList<>();
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        events.add(new EventDay(
                cal1,
                // R.drawable.action_bar_main_settings,
                // CalendarUtils.getDrawableText(this, "img", null,  android.R.color.black, 8),
                R.drawable.sample_icon,
                //Color.parseColor("#228822")
                android.R.color.holo_green_dark
        ));
        cal2.set(2020, 01, 05);
        events.add(new EventDay(
                cal2,
                R.drawable.sample_icon,
                Color.parseColor("#228822")
        ));

        CalendarView calendarView = (CalendarView)findViewById(R.id.calendarView);
        calendarView.setEvents(events);

        Calendar c1 = Calendar.getInstance();
        c1.set(2020,01,06);
        Calendar c2 = Calendar.getInstance();
        c2.set(2020,01,03);
        List<Calendar> calendars = new ArrayList<>();
        calendars.add(c1);
        calendars.add(c2);



        calendarView.setHighlightedDays(calendars);




    } // end_method


} // end_class
