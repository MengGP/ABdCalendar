package com.menggp.abdcalendar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.menggp.abdcalendar.adapters.EventImgSpinnerAdapter;
import com.menggp.abdcalendar.adapters.StrSpinnerAdapter;
import com.menggp.abdcalendar.datamodel.DateHandler;
import com.menggp.abdcalendar.datamodel.Event;
import com.menggp.abdcalendar.datamodel.EventImgDefaultCollection;
import com.menggp.abdcalendar.repository.DatabaseAdapter;

import java.util.ArrayList;

/*
    Класс описыает Activity - редактирование события
 */
public class EventActivityEdit extends AppCompatActivity implements EventDatePickerDialogDatable {

    private static final String LOG_TAG = "EventActivityEdit";

    Resources res;
    DatabaseAdapter dbAdapter;
    static long eventId = 0;
    Event event;
    String eventDateStr;

    TextView eventDateBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_edit);

        // Объект resources для вызываемых методов
        res = getResources();

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
        // Поле выбора даты
        eventDateBox = (TextView) findViewById(R.id.event_date_on_edit);
        // Поле выбора года начала события
        Spinner eventSinceYearBox = (Spinner)findViewById(R.id.event_since_year_on_edit);
        ArrayList<String> yearsStrList = DateHandler.getYearsStrList(res);
        StrSpinnerAdapter eventSinceYearSpinnerAdapter = new StrSpinnerAdapter(
                this,
                R.layout.spinner_event_since_year_item,
                R.layout.spinner_event_since_year_dropdown,
                yearsStrList
        );
        eventSinceYearBox.setAdapter(eventSinceYearSpinnerAdapter);


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
            event = dbAdapter.getEvent( eventId );

            // Устанавливаем данные текущего события в форму
            // Текущее изображение в качесве выбранного в спинере изображния
            int eventImgPosition = eventImgSpinnerAdapter.getPosition( event.getEventImg() );
            eventImgBox.setSelection( eventImgPosition );
            // имя события
            eventNameBox.setText( event.getEventName() );
            // дата события
            eventDateStr = event.getEventDate();
            eventDateBox.setText( DateHandler.convertDbToHumanNotation(res, eventDateStr));
            // год начала события
            eventSinceYearBox.setSelection( eventSinceYearSpinnerAdapter.getPosition( String.valueOf(event.getEventSinceYear()) ) );



        } else {
            // код для случая создания нового события
        }

    } // end_method

    public void eventDatePickerDialog(View view) {
        EventDatePickerDialogFragment dialog = new EventDatePickerDialogFragment();
        Bundle args = new Bundle();
        args.putInt("day", DateHandler.getDayFromDbDate(eventDateStr) );
        args.putInt("month", DateHandler.getMonthFromDbDate(eventDateStr));
        dialog.setArguments(args);
        dialog.show(getSupportFragmentManager(),"EventDatePickerDialogFragment");
    } // end_method

    /*
        Метод реализует изменение даты совершенное в диалоге выбора даты
     */
    @Override
    public void setEventDateOnEdit(int month, int day) {
        // Формируем строковое предстваление даты в формате БД
        eventDateStr = "";
        if ( month < 10 ) eventDateStr += "0" + month + "-";
        else eventDateStr += month + "-";
        if ( day < 10 ) eventDateStr += "0" + day;
        else eventDateStr += day;

        // Устанавливаем новое значение на разметке
        eventDateBox.setText( DateHandler.convertDbToHumanNotation(res, eventDateStr));
    } // end_method
} // end_class








































