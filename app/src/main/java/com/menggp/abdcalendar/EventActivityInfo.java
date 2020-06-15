package com.menggp.abdcalendar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.menggp.abdcalendar.datamodel.DateHandler;
import com.menggp.abdcalendar.datamodel.Event;
import com.menggp.abdcalendar.datamodel.EventAlertType;
import com.menggp.abdcalendar.datamodel.EventType;
import com.menggp.abdcalendar.dialogs.EventDelConfirmationDialogDatable;
import com.menggp.abdcalendar.dialogs.EventDelConfirmationDialogFragment;
import com.menggp.abdcalendar.repository.DatabaseAdapter;

/*
    Activity - подробная информационная страница события
        - выводит полную информациб о собятии
        - переход на стриницу редактирования
        - кнопка удаления
 */
public class EventActivityInfo extends AppCompatActivity implements EventDelConfirmationDialogDatable {

    private static final String LOG_TAG = "EventActivityInfo";
    // public static final String SHOW_EVENT_ACTIVITY_EDIT = "com.menggp.SHOW_EVENT_ACTIVITY_EDIT";

    DatabaseAdapter dbAdapter;

    static long eventId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_info);

        // настройка Action bar
        ActionBar actionBar = getSupportActionBar();            // получем доступ к action bar
        actionBar.setTitle(R.string.title_event_activity_info); // меняем заголовок
        actionBar.setHomeButtonEnabled(true);                   // активируем кнопку "home"
        actionBar.setDisplayHomeAsUpEnabled(true);              // отображаем кнопку "home"

        // Получаем данные из предыдущей Activity
        Bundle extras = getIntent().getExtras();
        if ( extras != null ) {
            eventId = extras.getLong("id");
        }

        // проверяем значение eventId - если > 0 - заполняем поля
        //  иначе - перанправляем на MainActivity
        if ( eventId>0 ) {
            // получаем event по ID из БД
            dbAdapter = new DatabaseAdapter(this);
            Event event = dbAdapter.getEvent( eventId );

            // получаем элементы с разметки
            ImageView eventImgView = (ImageView)findViewById(R.id.event_img_on_info);
            TextView eventNameView = (TextView)findViewById(R.id.event_name_on_info);
            TextView eventDateView = (TextView)findViewById(R.id.event_date_on_info);
            TextView eventSinceYearView = (TextView)findViewById(R.id.event_since_year_on_info);
            TextView eventTypeView = (TextView)findViewById(R.id.event_type_on_info);
            TextView eventAlertTypeView = (TextView)findViewById(R.id.event_alert_type_on_info);
            TextView eventCommentView = (TextView)findViewById(R.id.event_comment_on_info);

            // Создаем объект - ресурсы - для использования в вызываемых методах преобразования значений поле объекта event
            Resources res = getResources();

            // Заполняем разметку данными из полученного события
            // изображение события
            eventImgView.setImageResource( event.getEventImg() );
            // имя события
            eventNameView.setText( event.getEventName() );
            // дата события
            eventDateView.setText(DateHandler.convertDbToHumanNotation(res, event.getEventDate()));
            // возраст события
            String eventAges="";
            if ( event.getEventSinceYear()==0 ) eventAges = "--";
            else {
                int pastYears = DateHandler.timePastYear( event.getEventSinceYear() );
                eventAges += pastYears+" ";
                eventAges += res.getQuantityText(R.plurals.years, pastYears) + " c ";
                eventAges += event.getEventSinceYear();
            }
            eventSinceYearView.setText( eventAges );
            // Тип события
            eventTypeView.setText(EventType.convertToHumanNotation(res, event.getEventType()));
            // Оповещение
            eventAlertTypeView.setText(EventAlertType.convertToHumanNotation(res, event.getEventAlertType()));
            // Комментарий
            eventCommentView.setText( event.getEventComment() );

        } else goMainActivity();

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
        dialog.show(getSupportFragmentManager(),"delConfirmationDioalodFragment");
    } // end_method

    /*
        Метод вызывает EventEditActivity и передает в нее ID текущего события
     */
    private void goEventActivityEdit() {
        Intent intent = new Intent(MainActivity.SHOW_EVENT_ACTIVITY_EDIT);
        intent.putExtra("id",eventId );
        startActivity(intent);
    } // end_class

    /*
        Обработка нажатия меню в Action bar
        - кнопка "home" ( дефолтный ID = android.R.id.home )
        - кнопка "delete" ( ID = 1 )
        - кнопка "Edit" ( ID = 2 )
    */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home: goMainActivity(); break;
            case 1 :
                delConfirmation();
                break;
            case 2 :
                goEventActivityEdit();
                break;
        }
        return super.onOptionsItemSelected(item);
    } // end_method

    /*
        Определение меню в Action Bar
         */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
         /*
            1 - delete btn
            2 - edit btn
         */
        menu.add(0
                ,1
                ,0
                ,"DeleteBtn")
                .setIcon(R.drawable.act_bar_trash_bin)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

        menu.add(0
                ,2
                ,1
                ,"EditBtn")
                .setIcon(R.drawable.act_bar_edit_pencil)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

        return true;
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

} // end_class;