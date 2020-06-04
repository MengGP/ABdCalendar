package com.menggp.abdcalendar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Spinner;

import com.menggp.abdcalendar.adapters.EventImgSpinnerAdapter;
import com.menggp.abdcalendar.datamodel.Event;
import com.menggp.abdcalendar.datamodel.EventImgDefaultCollection;
import com.menggp.abdcalendar.repository.DatabaseAdapter;

import java.util.ArrayList;

/*
    Класс описыает Activity - редактирование события
 */
public class EventActivityEdit extends AppCompatActivity {

    private static final String LOG_TAG = "EventActivityEdit";

    DatabaseAdapter dbAdapter;
    static long eventId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_edit);

        // получаем элементы с разметки
        // Настраиваем spinner для изображения события
        Spinner eventImgBox = (Spinner)findViewById(R.id.event_img_on_edit);
        ArrayList<Integer> eventImgDefCollection = new EventImgDefaultCollection().getImgCollection();     // получаем коллекцию изображений для spinner
        EventImgSpinnerAdapter eventImgSpinnerAdapter = new EventImgSpinnerAdapter(
                this,                           // контекст
                R.layout.spinner_event_img_item,        // разметка выбраного элемента
                R.layout.spinner_event_img_dropdown,    // разметка выпадающего списка
                eventImgDefCollection                   // данные
        );
        eventImgBox.setAdapter( eventImgSpinnerAdapter );
        int defaultEventImg = eventImgSpinnerAdapter.getPosition( R.drawable.a08_ev_img_default );      // получаем позицию для изображения по умолчанию
        eventImgBox.setSelection( defaultEventImg );                                                    // устанавливаем изображение по умолчанию
        // Поля имени события
        EditText eventNameBox = (EditText)findViewById(R.id.event_name_on_edit);

        // Получаем данные из предыдущей Activity
        Bundle extras = getIntent().getExtras();
        if ( extras != null ) {
            eventId = extras.getLong("id");
        }

        // Создаем БД адаптер
        dbAdapter = new DatabaseAdapter(this);

        // проверяем значение eventId - если > 0 - заполняем поля
        //  иначе - пустые поля для создания нового события
        if ( eventId>0 ) {
            // получаем event по ID из БД
            Event event = dbAdapter.getEvent( eventId );

            // Устанавливаем данные текущего события в форму
            // Текущее изображение в качесве выбранного в спинере изображния
            int eventImgPosition = eventImgSpinnerAdapter.getPosition( event.getEventImg() );
            eventImgBox.setSelection( eventImgPosition );
            // имя события
            eventNameBox.setText( event.getEventName() );

        } else {
            // код для случая создания нового события
        }


    } // end_method

} // end_class