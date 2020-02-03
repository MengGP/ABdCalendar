package com.menggp.abdcalendar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.applandeo.materialcalendarview.CalendarUtils;

import java.util.Calendar;


public class MainActivity extends AppCompatActivity {

    // --- Constants
    public static final String SHOW_SETTING_ACTIVITY = "com.menggp.SHOW_SETTINGS_ACTIVITY";

    // --- Attributes
    public static boolean isCalendarView = true;        // определяет какой вид необходимо отобрахать на главном экране: календарь или список

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (isCalendarView) setContentView(R.layout.activity_main_calendar);
        else setContentView(R.layout.activity_main_list);

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


    } // end_method


} // end_class
