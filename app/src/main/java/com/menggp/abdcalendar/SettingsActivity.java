package com.menggp.abdcalendar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // настройка Action bar
        ActionBar actionBar = getSupportActionBar();            // получем доступ к action bar
        actionBar.setTitle(R.string.title_activity_settings);   // меняем заголовок
        actionBar.setHomeButtonEnabled(true);                   // активируем кнопку "home"
        actionBar.setDisplayHomeAsUpEnabled(true);              // отображаем кнопку "home"

    } // end_method

    /*
    Обработка нажатия меню в Action bar
        - кнопка "home" ( дефолтный ID = android.R.id.home )
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home :
                this.finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
} // end_class
