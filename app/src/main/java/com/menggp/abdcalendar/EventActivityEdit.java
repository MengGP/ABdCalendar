package com.menggp.abdcalendar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.menggp.abdcalendar.adapters.EventImgSpinnerAdapter;
import com.menggp.abdcalendar.adapters.StrSpinnerAdapter;
import com.menggp.abdcalendar.datamodel.DateHandler;
import com.menggp.abdcalendar.datamodel.Event;
import com.menggp.abdcalendar.datamodel.EventAlertType;
import com.menggp.abdcalendar.datamodel.EventImgDefaultCollection;
import com.menggp.abdcalendar.datamodel.EventType;
import com.menggp.abdcalendar.dialogs.EventChangeConfirmationDialogDatable;
import com.menggp.abdcalendar.dialogs.EventChangeConfirmationDialogFragment;
import com.menggp.abdcalendar.dialogs.EventDatePickerDialogDatable;
import com.menggp.abdcalendar.dialogs.EventDatePickerDialogFragment;
import com.menggp.abdcalendar.dialogs.EventDelConfirmationDialogDatable;
import com.menggp.abdcalendar.dialogs.EventDelConfirmationDialogFragment;
import com.menggp.abdcalendar.dialogs.EventNoNameDialogFragment;
import com.menggp.abdcalendar.repository.DatabaseAdapter;

import java.util.ArrayList;

/*
    Класс описыает Activity - редактирование события
 */
public class EventActivityEdit extends AppCompatActivity implements EventDatePickerDialogDatable, EventDelConfirmationDialogDatable, EventChangeConfirmationDialogDatable {

    private static final String LOG_TAG = "EventActivityEdit";
//    public static final String SHOW_EVENT_ACTIVITY_INFO = "com.menggp.SHOW_EVENT_ACTIVITY_INFO";

    Resources res;
    DatabaseAdapter dbAdapter;
    static long eventId = 0;
    static boolean fromMainActivivty = false;
    Event event;
    String eventDateStr;            // дата события в нотации БД
    int eventSinceYear;             // год начала события
    EventType eventType;            // тип события
    EventAlertType eventAlertType;  // тип напоминания

    Spinner eventImgBox;
    EditText eventNameBox;
    TextView eventDateBox;
    Spinner eventSinceYearBox;
    Spinner eventTypeBox;
    Spinner eventAlertTypeBox;
    EditText eventCommentBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_edit);

        // настройка Action bar
        ActionBar actionBar = getSupportActionBar();            // получем доступ к action bar
        actionBar.setTitle(R.string.title_event_activity_edit);                 // меняем заголовок
        actionBar.setHomeButtonEnabled(true);                   // активируем кнопку "home"
        actionBar.setDisplayHomeAsUpEnabled(true);              // отображаем кнопку "home"

        // Объект resources для вызываемых методов
        res = getResources();

        // получаем элементы с разметки
        // Настраиваем spinner для изображения события
        eventImgBox = (Spinner)findViewById(R.id.event_img_on_edit);
        ArrayList<Integer> eventImgDefCollection = new EventImgDefaultCollection().getImgCollection();     // получаем коллекцию изображений для spinner
        EventImgSpinnerAdapter eventImgSpinnerAdapter = new EventImgSpinnerAdapter(
                this,                           // контекст
                R.layout.spinner_event_img_item,        // разметка выбраного элемента
                R.layout.spinner_event_img_dropdown,    // разметка выпадающего списка
                eventImgDefCollection                   // данные
        );
        eventImgBox.setAdapter( eventImgSpinnerAdapter );
        int defaultEventImg = eventImgSpinnerAdapter.getPosition( R.drawable.a01_ev_img_default );      // получаем позицию для изображения по умолчанию
        eventImgBox.setSelection( defaultEventImg );                                                    // устанавливаем изображение по умолчанию

        // Поля имени события
        eventNameBox = (EditText)findViewById(R.id.event_name_on_edit);

        // Поле выбора даты
        eventDateBox = (TextView) findViewById(R.id.event_date_on_edit);

        // Поле выбора года начала события
        eventSinceYearBox = (Spinner)findViewById(R.id.event_since_year_on_edit);
        ArrayList<String> yearsStrList = DateHandler.getYearsStrList(res);
        StrSpinnerAdapter eventSinceYearSpinnerAdapter = new StrSpinnerAdapter(
                this,
                R.layout.spinner_event_general_item,
                R.layout.spinner_event_general_dropdown,
                yearsStrList
        );
        eventSinceYearBox.setAdapter(eventSinceYearSpinnerAdapter);

        // Поле выбора типа события
        eventTypeBox = (Spinner)findViewById(R.id.event_type_on_edit);
        ArrayList<String> eventTypeStrList = EventType.getEventTypeStrLst(res);
        StrSpinnerAdapter eventTypeSpinnerAdapter = new StrSpinnerAdapter(
                this,
                R.layout.spinner_event_general_item,
                R.layout.spinner_event_general_dropdown,
                eventTypeStrList
        );
        eventTypeBox.setAdapter(eventTypeSpinnerAdapter);

        // Поле выбора типа напоминания
        eventAlertTypeBox = (Spinner)findViewById(R.id.event_alert_type_on_edit);
        ArrayList<String> eventAlertTypeStrList = EventAlertType.getEventAlertTypeStrLst(res);
        StrSpinnerAdapter eventAlertTypeSpinnerAdapter = new StrSpinnerAdapter(
                this,
                R.layout.spinner_event_general_item,
                R.layout.spinner_event_general_dropdown,
                eventAlertTypeStrList
        );
        eventAlertTypeBox.setAdapter(eventAlertTypeSpinnerAdapter);

        // Поле комментария
        eventCommentBox = (EditText)findViewById(R.id.event_comment_on_edit);

        // Получаем данные из предыдущей Activity
        Bundle extras = getIntent().getExtras();
        if ( extras != null ) {
            eventId = extras.getLong("id");
            fromMainActivivty =extras.getBoolean(MainActivity.FROM_MAIN_ACTIVITY);
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
            eventSinceYear = event.getEventSinceYear();
            if (eventSinceYear==0) eventSinceYearBox.setSelection(0);
            else eventSinceYearBox.setSelection( eventSinceYearSpinnerAdapter.getPosition( String.valueOf(eventSinceYear) ) );
            // тип события
            eventType = event.getEventType();
            eventTypeBox.setSelection( EventType.getIndexByEventType(eventType) );
            // тип напоминания
            eventAlertType = event.getEventAlertType();
            eventAlertTypeBox.setSelection( EventAlertType.getIndexByEventAlertType(eventAlertType) );
            // Комментарий
            eventCommentBox.setText( event.getEventComment() );

        } else {
            // Установка начальных значений
            // Изображение по умолчанию для спиннера изображения
            eventImgBox.setSelection( eventImgSpinnerAdapter.getPosition( R.drawable.a01_ev_img_default ));
            // Дата по умолчанию = "01-01"
            eventDateStr = DateHandler.getNowDayDbNotation();
            eventDateBox.setText( DateHandler.convertDbToHumanNotation(res, eventDateStr) );
            // Год начала события - по умолчаению = "без года"
            eventSinceYear = 0;
            eventSinceYearBox.setSelection(0);      // соответствует 0-ой позиции
            // Тип события по умолчанию = OTHER
            eventType = EventType.OTHER;
            eventTypeBox.setSelection( EventType.getIndexByEventType(eventType) );
            // Тип напоминания по умолчанию = NO_ALERT
            eventAlertType = EventAlertType.NO_ALERT;
            eventAlertTypeBox.setSelection(EventAlertType.getIndexByEventAlertType(eventAlertType) );
        }

        // Слушатель измениния - спиннер выбора года начала события
        AdapterView.OnItemSelectedListener eventSinceYearSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if ( position == 0 )  eventSinceYear = 0;
                else eventSinceYear = Integer.parseInt( eventSinceYearBox.getItemAtPosition(position).toString() );
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        };
        eventSinceYearBox.setOnItemSelectedListener( eventSinceYearSelectedListener );

        // Слушатель изменения - спиннер выбора типа события
        AdapterView.OnItemSelectedListener eventTypeSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                eventType = EventType.getEventTypeByIndex(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        };
        eventTypeBox.setOnItemSelectedListener( eventTypeSelectedListener );

        // Слушатель изменения - спиннер выбора типа напоминания
        AdapterView.OnItemSelectedListener eventAlertTypeSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                eventAlertType = EventAlertType.getEventAlertTypeByIndex(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        };
        eventAlertTypeBox.setOnItemSelectedListener( eventAlertTypeSelectedListener);

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

    /*
       Определение меню в Action Bar
        */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
         /*
            1 - delete btn
            2 - save btn
         */
        // Пункт меню - УДАЛИТЬ, добавляем только в режиме редактирования события, но не для нового
         if (eventId > 0) {
            menu.add(0
                    , 1
                    , 0
                    , "DeleteBtn")
                    .setIcon(R.drawable.delete)
                    .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        }

        menu.add(0
                ,2
                ,1
                ,"EditBtn")
                .setIcon(R.drawable.act_bar_save_floppy)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

        return true;
    } // end_method

    /*
        Обработка нажатия меню в Action bar
        - кнопка "home" ( дефолтный ID = android.R.id.home )
        - кнопка "delete" ( ID = 1 )
        - кнопка "Save" ( ID = 2 )
    */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home: onBackClick(); break;
            case 1 :
                delConfirmation();
                break;
            case 2 :
                saveEvent();
                break;
        }
        return super.onOptionsItemSelected(item);
    } // end_method

    /*
        Метод реализует сохранеине события
     */
    private void saveEvent() {
        // Проверяем заполнение поля "имя события" - имя должно быть, если нет, предупреждающий диалог
        if ( eventNameBox.getText().toString().length()==0 ) {
            EventNoNameDialogFragment dialog = new EventNoNameDialogFragment();
            dialog.show(getSupportFragmentManager(), "EventNoNameDialogFragment");
            return;
        }
        if (eventId > 0) {
            event.setEventName( eventNameBox.getText().toString() );
            event.setEventDate( eventDateStr );
            event.setEventType( eventType );
            event.setEventSinceYear( eventSinceYear );
            event.setEventComment( eventCommentBox.getText().toString() );
            event.setEventImg( Integer.parseInt(eventImgBox.getSelectedItem().toString()) );
            event.setEventAlertType( eventAlertType );
            dbAdapter.update( event );
            Toast.makeText(this, R.string.toast_event_change_saved, Toast.LENGTH_SHORT).show();
        } else {
            // Создаем новый объект Event - с данными с формы
            event = new Event(
                    0,
                    eventNameBox.getText().toString(),
                    eventDateStr,
                    eventType,
                    eventSinceYear,
                    eventCommentBox.getText().toString(),
                    Integer.parseInt(eventImgBox.getSelectedItem().toString()),
                    eventAlertType
            );
            eventId = dbAdapter.insertEvent(event);
            Toast.makeText(this, R.string.toast_event_created, Toast.LENGTH_SHORT).show();
        }

        goEventActivityInfo();
    } // end_method

    /*
    Метод описывает обработка нажатия кнопки назад
 */
    private void onBackClick() {
        // Если событие редактируется
        if (eventId>0) {
            // Проверяем были ли внесены изменения в событие:
            boolean isChanged = false;
            if (!event.getEventName().equals(eventNameBox.getText().toString())) isChanged = true;
            else if (!event.getEventDate().equals(eventDateStr)) isChanged = true;
            else if (!event.getEventType().equals(eventType)) isChanged = true;
            else if (event.getEventSinceYear() != eventSinceYear) isChanged = true;
            else if (!event.getEventComment().equals(eventCommentBox.getText().toString()))
                isChanged = true;
            else if (event.getEventImg() != Integer.parseInt(eventImgBox.getSelectedItem().toString()))
                isChanged = true;
            else if (!event.getEventAlertType().equals(eventAlertType)) isChanged = true;

            // Если были внесены изменения - вызываем диалог подтвержения сохраения из менний, иначе переходим на EventActivityInfo
            if (isChanged) {
                EventChangeConfirmationDialogFragment dialog = new EventChangeConfirmationDialogFragment();
                dialog.show(getSupportFragmentManager(), "EventChangeConfirmationDialogFragment");
            } else goBackActivity();
        }
        // если событие новое
        else {
            // Проверяем - было ли заполнено хотябы одно поле, если да, выводим диалог подтверждения
            boolean isChanged = false;
            if ( eventNameBox.getText().toString().length()>0 ) isChanged = true;
            else if ( !eventDateStr.equals(DateHandler.getNowDayDbNotation()) ) isChanged = true;
            else if ( !eventType.equals(EventType.OTHER) ) isChanged = true;
            else if ( eventSinceYear != 0 ) isChanged = true;
            else if ( eventCommentBox.getText().toString().length()>0 ) isChanged = true;
            else if ( Integer.parseInt(eventImgBox.getSelectedItem().toString()) != R.drawable.a01_ev_img_default )
                isChanged = true;
            else if (!eventAlertType.equals(EventAlertType.NO_ALERT)) isChanged = true;

            // Если были внесены изменения - вызываем диалог подтвержения сохраения из менний, иначе переходим на MainActivity
            if (isChanged) {
                EventChangeConfirmationDialogFragment dialog = new EventChangeConfirmationDialogFragment();
                dialog.show(getSupportFragmentManager(), "EventChangeConfirmationDialogFragment");
            } else goMainActivity();
        }


    } // end_method

    /*
        Метод возращает на предыдущую Activity - если в события не были внесены изменения
            - в зависимости от того с какой ACtivity был осуществлен переход
     */
    private void goBackActivity() {
        if ( fromMainActivivty ) goMainActivity();
        else goEventActivityInfo();
    } // end_method

    /*
       Метод вызывает EventActivityInfo и передает в нее ID текущего события
    */
    private void goEventActivityInfo() {
        Intent intent = new Intent(MainActivity.SHOW_EVENT_ACTIVITY_INFO);
        intent.putExtra("id",eventId );
        startActivity(intent);
    } // end_method

    /*
        Метод - возвращает на MAIN_ACTIVITY
     */
    private void goMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    } // end_method

    /*
        Метод вызывает диалог подтверждения удаления
     */
    private void delConfirmation() {
        EventDelConfirmationDialogFragment dialog = new EventDelConfirmationDialogFragment();
        Bundle args = new Bundle();
        args.putLong("id", eventId);
        dialog.setArguments(args);
        dialog.show(getSupportFragmentManager(),"delConfirmationDialogFragment");
    } // end_method

    /*
        Метод - реализует метод "delEvent(long)" из интерфейса "delConfirmationDialogDatable"
        для текущей Activity
     */
    @Override
    public void delEvent(long eventId) {
        dbAdapter.deleteEvent( eventId );
        goMainActivity();
    } // end_method

    /*
        Метод реализует выход из редактора события без сохраениния изменений
            из интерфейса EventChangeConfirmationDialogDatable
     */
    @Override
    public void noSaveEventChange() {
        goBackActivity();
    } // end_method

    /*
        Метод реализует выход из редактора события с сохраениением изменений
            из интерфейса EventChangeConfirmationDialogDatable
     */
    @Override
    public void saveEventChange() {
        saveEvent();
    } // end_method

} // end_class








































