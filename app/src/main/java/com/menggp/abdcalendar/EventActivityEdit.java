package com.menggp.abdcalendar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.os.Bundle;
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
public class EventActivityEdit extends AppCompatActivity {

    private static final String LOG_TAG = "EventActivityEdit";

    DatabaseAdapter dbAdapter;
    static long eventId = 0;
    Event event;

    TextView eventDateBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_edit);

        // Объект resources для вызываемых методов
        Resources res = getResources();

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
            eventDateBox.setText( DateHandler.convertDbToHumanNotation(res, event.getEventDate()));
            // год начала события
            eventSinceYearBox.setSelection( eventSinceYearSpinnerAdapter.getPosition( String.valueOf(event.getEventSinceYear()) ) );



        } else {
            // код для случая создания нового события
        }

    } // end_method

    public void eventDatePickerDialog(View view) {
        Toast.makeText(getApplicationContext(), "EventDatePickerDialog", Toast.LENGTH_SHORT).show();
        EventDatePickerDialogFragment dialog = new EventDatePickerDialogFragment();
        Bundle args = new Bundle();
        args.putInt("day", DateHandler.getDayFromDbDate(event.getEventDate()));
        args.putInt("month", DateHandler.getMonthFromDbDate(event.getEventDate()));
        dialog.setArguments(args);
        dialog.show(getSupportFragmentManager(),"EventDatePickerDialogFragment");
    } // end_method

} // end_class








































