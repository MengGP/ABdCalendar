package com.menggp.abdcalendar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;



public class MainActivity extends AppCompatActivity {

    public static final String SHOW_SETTING_ACTIVITY = "com.menggp.SHOW_SETTINGS_ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    } // end_method

    /*
    Определение меню в Action Bar
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    } // end_method

    /*
    Обработка нажатия кнопок меню в Action Bar
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.celendarView :
                Toast toast_calendarView = Toast.makeText(this, "Calendar view PRESSED", Toast.LENGTH_SHORT);
                toast_calendarView.show();
                return true;
            case R.id.listView :
                Toast toast_listView = Toast.makeText(this, "List view PRESSED", Toast.LENGTH_SHORT);
                toast_listView.show();
                return true;
            case R.id.settings :
                Intent intentSettings = new Intent(SHOW_SETTING_ACTIVITY);
                startActivity(intentSettings);
                return true;
        }
        return super.onOptionsItemSelected(item);
    } // end_class

} // end_class
