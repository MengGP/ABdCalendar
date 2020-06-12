package com.menggp.abdcalendar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.applandeo.materialcalendarview.CalendarUtils;
import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.menggp.abdcalendar.adapters.EventListAdapter;
import com.menggp.abdcalendar.datamodel.Event;
import com.menggp.abdcalendar.datamodel.EventMonthFilter;
import com.menggp.abdcalendar.datamodel.EventTypeFilter;
import com.menggp.abdcalendar.dialogs.MonthFilterDialogDatable;
import com.menggp.abdcalendar.dialogs.MonthFilterDialogFragment;
import com.menggp.abdcalendar.dialogs.TypeFilterDialogDatable;
import com.menggp.abdcalendar.dialogs.TypeFilterDialogFragment;
import com.menggp.abdcalendar.repository.DatabaseAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity implements TypeFilterDialogDatable, MonthFilterDialogDatable {

    // --- Constants
    public static final String SHOW_SETTING_ACTIVITY = "com.menggp.SHOW_SETTINGS_ACTIVITY";
    public static final String SHOW_EVENT_ACTIVITY_INFO = "com.menggp.SHOW_EVENT_ACTIVITY_INFO";
    public static final String SHOW_EVENT_ACTIVITY_EDIT = "com.menggp.SHOW_EVENT_ACTIVITY_EDIT";

    // --- Prefrences
    private static final String SORT_AND_FILTER_PREFS = "sort_and_filter_prefs";    // имя настроек сортировки и фильтрации
        // ключи сортировки по типу
    private static final String EV_TYPE_BIRTHDAY_ON = "ev_type_birthday_on";
    private static final String EV_TYPE_ANNIVERSARY_ON = "ev_type_anniversary_on";
    private static final String EV_TYPE_MEMODATE_ON = "ev_type_memodate_on";
    private static final String EV_TYPE_HOLIDAY_ON = "ev_type_holiday_on";
    private static final String EV_TYPE_OTHER_ON = "ev_type_other_on";
        // ключ типа сортировки
        // 0 - по умолчанию - от текущей даты
        // 1 - от начала года
        // 2 - по имени по возрастанию
    private static final String EV_SORT = "ev_sort";
        // ключи сортировки по месяцам
    private static final String EV_MONTH_ON_01 = "ev_month_on_01";  // январь
    private static final String EV_MONTH_ON_02 = "ev_month_on_02";  // февраль
    private static final String EV_MONTH_ON_03 = "ev_month_on_03";  // март
    private static final String EV_MONTH_ON_04 = "ev_month_on_04";  // апрель
    private static final String EV_MONTH_ON_05 = "ev_month_on_05";  // май
    private static final String EV_MONTH_ON_06 = "ev_month_on_06";  // июнь
    private static final String EV_MONTH_ON_07 = "ev_month_on_07";  // июль
    private static final String EV_MONTH_ON_08 = "ev_month_on_08";  // август
    private static final String EV_MONTH_ON_09 = "ev_month_on_09";  // сентябрь
    private static final String EV_MONTH_ON_10 = "ev_month_on_10";  // октябрь
    private static final String EV_MONTH_ON_11 = "ev_month_on_11";  // ноябрь
    private static final String EV_MONTH_ON_12 = "ev_month_on_12";  // декабрь

    // --- Attributes
    public static boolean isCalendarView = true;        // определяет какой вид необходимо отобрахать на главном экране: календарь или список
    DatabaseAdapter dbAdapter;
    EventListAdapter eventListAdapter;
    SharedPreferences sortAndFilterPrefs;
    EventTypeFilter eventTypeFilter;
    EventMonthFilter eventMonthFilter;
    int eventSortType = 0;
    boolean isSortExist = true;                         // флаг наличия сортировки отличной от стандартной

    // Элементы разметки
    ListView eventListView;
    ImageView typeFilterLED;
    ImageView monthFilterLED;
    EditText eventNameFilterBox;

    /*
        Метод - onCreate
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Подключаем настройки и чиатем настройки фильтрации (по умолчанию все значения TRUE)
        sortAndFilterPrefs = getSharedPreferences(SORT_AND_FILTER_PREFS, MODE_PRIVATE);
        readFilterPrefs();

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
            eventNameFilterBox = (EditText)findViewById(R.id.event_name_filter_on_list);
            typeFilterLED = (ImageView)findViewById(R.id.type_filter_indicator);
            monthFilterLED = (ImageView)findViewById(R.id.month_filter_on_list);

            // Устанавливаем индикаторы фильтров в зависимости от наличия фильтров
            if ( eventTypeFilter.filterExist() ) typeFilterLED.setImageResource(R.drawable.filter);
            if ( eventMonthFilter.filterExist() ) monthFilterLED.setImageResource(R.drawable.filter);

            // Слушатель длинного нажатия на элемент списка - запускает EventActivityInfo
            eventListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    // Получаем событие из адаптера
                    Event event = eventListAdapter.getItem( position );
                    // Если значение не null - вызываем EventActivityInfo и передаем в нее ID события
                    if (event!=null) {
                        Intent intent = new Intent(SHOW_EVENT_ACTIVITY_INFO);
                        intent.putExtra("id",event.getId() );
                        startActivity(intent);
                    }
                    // true - не переходим в обработку короткого нажатия
                    return true;
                }
            });

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
            List<Event> events = dbAdapter.getEvents(eventNameFilterBox.getText().toString(), eventTypeFilter, eventMonthFilter, eventSortType);
            // Создаем адапред для списка EVENT
            eventListAdapter = new EventListAdapter(
                    this,               // контекст
                    R.layout.list_item_event,   // разметка
                    events                      // данные
            );
            // Устанавливаем адаптер для списка
            eventListView.setAdapter( eventListAdapter );

            // Слушатель изменения текста в поле поиска по имени события
            eventNameFilterBox.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

                // фильтрация при изменении текста
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    eventListAdapter.updAdapterData(s.toString(), eventTypeFilter, eventMonthFilter, eventSortType);
                }

                @Override
                public void afterTextChanged(Editable s) { }
            });


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
            calendarViewIcon = R.drawable.act_bar_main_calendar;
            listViewIcon = R.drawable.act_bar_main_list_gray;
        } else {
            calendarViewIcon = R.drawable.act_bar_main_calendar_gray;
            listViewIcon = R.drawable.act_bar_main_list;
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
                .setIcon(R.drawable.act_bar_main_settings)
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
                    isCalendarView = true;
                    recreate();
                }
                return true;
            case 2 :
                if (isCalendarView) {
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

    /*
        Метод - обрабатывает нажатие кнопки ADD на разметке списка
     */
    public void onAddBtnClickOnList(View view) {
        Intent intent = new Intent(SHOW_EVENT_ACTIVITY_EDIT);
        intent.putExtra("id",0);
        startActivity(intent);
    } // end_method

    /*
        Тестовый метод
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

        // ------------------------------------------------------
        if ( eventNameFilterBox!=null )
            eventListAdapter.updAdapterData(eventNameFilterBox.getText().toString() ,eventTypeFilter, eventMonthFilter, 0);
        else
            Toast.makeText(getApplicationContext(), "eventListAdapter === NULL ", Toast.LENGTH_SHORT).show();

    } // end_method

    /*
        Метод обработки нажтия на "кнопку" фильтра по типу
     */
    public void onClickTypeFilterOnList(View view) {
        TypeFilterDialogFragment dialog = new TypeFilterDialogFragment();
        Bundle args = new Bundle();
        args.putBoolean(EV_TYPE_BIRTHDAY_ON, eventTypeFilter.isBirthdayOn() );
        args.putBoolean(EV_TYPE_ANNIVERSARY_ON, eventTypeFilter.isAnniversaryOn() );
        args.putBoolean(EV_TYPE_MEMODATE_ON, eventTypeFilter.isMemodateOn() );
        args.putBoolean(EV_TYPE_HOLIDAY_ON, eventTypeFilter.isHolidayOn() );
        args.putBoolean(EV_TYPE_OTHER_ON, eventTypeFilter.isOtherOn() );
        dialog.setArguments(args);
        dialog.show(getSupportFragmentManager(),"TypeFilterDialogFragment");
    } // end_method

    /*
        Метод обработки нажтия на "кнопку" сортировки
     */
    public void onClickSortOnList(View view) {
        ImageView viewImg = (ImageView)view.findViewById(R.id.sort_on_list);
        if (isSortExist) {
            isSortExist=false;
            viewImg.setImageResource(R.drawable.sort);
        }
        else {
            isSortExist=true;
            viewImg.setImageResource(R.drawable.sort_disable);
        }
    } // end_method

    // onClickMonthFilterOnList
    /*
        Метод обработки нажтия на "кнопку" фильтра по месяцу
     */
    public void onClickMonthFilterOnList(View view) {
        MonthFilterDialogFragment dialog = new MonthFilterDialogFragment();
        Bundle args = new Bundle();
        args.putBoolean(EV_MONTH_ON_01, eventMonthFilter.isMonth01() );
        args.putBoolean(EV_MONTH_ON_02, eventMonthFilter.isMonth02() );
        args.putBoolean(EV_MONTH_ON_03, eventMonthFilter.isMonth03() );
        args.putBoolean(EV_MONTH_ON_04, eventMonthFilter.isMonth04() );
        args.putBoolean(EV_MONTH_ON_05, eventMonthFilter.isMonth05() );
        args.putBoolean(EV_MONTH_ON_06, eventMonthFilter.isMonth06() );
        args.putBoolean(EV_MONTH_ON_07, eventMonthFilter.isMonth07() );
        args.putBoolean(EV_MONTH_ON_08, eventMonthFilter.isMonth08() );
        args.putBoolean(EV_MONTH_ON_09, eventMonthFilter.isMonth09() );
        args.putBoolean(EV_MONTH_ON_10, eventMonthFilter.isMonth10() );
        args.putBoolean(EV_MONTH_ON_11, eventMonthFilter.isMonth11() );
        args.putBoolean(EV_MONTH_ON_12, eventMonthFilter.isMonth12() );
        dialog.setArguments( args );
        dialog.show(getSupportFragmentManager(), "onClickMonthFilterOnList");
    } // end_method

    /*
        Метод обрабатывает нажатие кнопки очиски строки фильтра по имени
     */
    public void onNameFilterClearClick(View view) {
        eventNameFilterBox.setText("");
    } // end_method

    /*
        Метод считывает настройки фильтрации в объекты настроек
     */
    private void readFilterPrefs() {
        readTypeFilterPrefs();
        readMonthFilterPrefs();
    } // end_method

    /*
        Читаем настройки фильтрации по типу события
     */
    private void readTypeFilterPrefs() {
        eventTypeFilter = new EventTypeFilter(
                sortAndFilterPrefs.getBoolean(EV_TYPE_BIRTHDAY_ON, true),
                sortAndFilterPrefs.getBoolean(EV_TYPE_ANNIVERSARY_ON, true),
                sortAndFilterPrefs.getBoolean(EV_TYPE_MEMODATE_ON, true),
                sortAndFilterPrefs.getBoolean(EV_TYPE_HOLIDAY_ON, true),
                sortAndFilterPrefs.getBoolean(EV_TYPE_OTHER_ON, true)
        );
    } // end_method

    /*
        Читаем настройки фильтрации по месяцу
     */
    public void readMonthFilterPrefs() {
        eventMonthFilter = new EventMonthFilter(
                sortAndFilterPrefs.getBoolean(EV_MONTH_ON_01, true),
                sortAndFilterPrefs.getBoolean(EV_MONTH_ON_02, true),
                sortAndFilterPrefs.getBoolean(EV_MONTH_ON_03, true),
                sortAndFilterPrefs.getBoolean(EV_MONTH_ON_04, true),
                sortAndFilterPrefs.getBoolean(EV_MONTH_ON_05, true),
                sortAndFilterPrefs.getBoolean(EV_MONTH_ON_06, true),
                sortAndFilterPrefs.getBoolean(EV_MONTH_ON_07, true),
                sortAndFilterPrefs.getBoolean(EV_MONTH_ON_08, true),
                sortAndFilterPrefs.getBoolean(EV_MONTH_ON_09, true),
                sortAndFilterPrefs.getBoolean(EV_MONTH_ON_10, true),
                sortAndFilterPrefs.getBoolean(EV_MONTH_ON_11, true),
                sortAndFilterPrefs.getBoolean(EV_MONTH_ON_12, true)
        );
    } // end_method


    /*
        Реализация метода интерфейса TypeFilterDialogDatable
     */
    @Override
    public void updTypeFilter(EventTypeFilter typeFilter) {

        // Создаем редактор prefrences
        SharedPreferences.Editor sortAndFilterPrefsEditor = sortAndFilterPrefs.edit();
        // Редактируем
        sortAndFilterPrefsEditor.putBoolean(EV_TYPE_BIRTHDAY_ON, typeFilter.isBirthdayOn());
        sortAndFilterPrefsEditor.putBoolean(EV_TYPE_ANNIVERSARY_ON, typeFilter.isAnniversaryOn());
        sortAndFilterPrefsEditor.putBoolean(EV_TYPE_MEMODATE_ON, typeFilter.isMemodateOn());
        sortAndFilterPrefsEditor.putBoolean(EV_TYPE_HOLIDAY_ON, typeFilter.isHolidayOn());
        sortAndFilterPrefsEditor.putBoolean(EV_TYPE_OTHER_ON, typeFilter.isOtherOn());
        // Применяем (не асинхронно)
        sortAndFilterPrefsEditor.commit();

        // Обновляем объект eventTypeFilter
        readTypeFilterPrefs();

        // Обновляем данные в адаптере - для вида календаря обновляем только фильтр типа
        if ( eventNameFilterBox!=null )
            eventListAdapter.updAdapterData(eventNameFilterBox.getText().toString() ,eventTypeFilter, eventMonthFilter, 0);
        else
            Toast.makeText(getApplicationContext(), "eventListAdapter === NULL ", Toast.LENGTH_SHORT).show();

        // Меняем индикатор фильтра в зависимости от наличия фильтров
        if ( eventTypeFilter.filterExist() ) typeFilterLED.setImageResource(R.drawable.filter);
        else typeFilterLED.setImageResource(R.drawable.filter_disable);
    } // end_method

    /*
        Реализация метода интерфейса MonthFilterDialogDatable
    */
    @Override
    public void updMonthFilter(EventMonthFilter monthFilter) {

        // Создаем редактор prefrences
        SharedPreferences.Editor sortAndFilterPrefsEditor = sortAndFilterPrefs.edit();
        // Редактируем
        sortAndFilterPrefsEditor.putBoolean(EV_MONTH_ON_01, monthFilter.isMonth01() );
        sortAndFilterPrefsEditor.putBoolean(EV_MONTH_ON_02, monthFilter.isMonth02() );
        sortAndFilterPrefsEditor.putBoolean(EV_MONTH_ON_03, monthFilter.isMonth03() );
        sortAndFilterPrefsEditor.putBoolean(EV_MONTH_ON_04, monthFilter.isMonth04() );
        sortAndFilterPrefsEditor.putBoolean(EV_MONTH_ON_05, monthFilter.isMonth05() );
        sortAndFilterPrefsEditor.putBoolean(EV_MONTH_ON_06, monthFilter.isMonth06() );
        sortAndFilterPrefsEditor.putBoolean(EV_MONTH_ON_07, monthFilter.isMonth07() );
        sortAndFilterPrefsEditor.putBoolean(EV_MONTH_ON_08, monthFilter.isMonth08() );
        sortAndFilterPrefsEditor.putBoolean(EV_MONTH_ON_09, monthFilter.isMonth09() );
        sortAndFilterPrefsEditor.putBoolean(EV_MONTH_ON_10, monthFilter.isMonth10() );
        sortAndFilterPrefsEditor.putBoolean(EV_MONTH_ON_11, monthFilter.isMonth11() );
        sortAndFilterPrefsEditor.putBoolean(EV_MONTH_ON_12, monthFilter.isMonth12() );
        // Применяем (не асинхронно)
        sortAndFilterPrefsEditor.commit();

        // Обновляем объект eventTypeFilter
        readMonthFilterPrefs();

        // Обновляем данные в адаптере - для вида календаря обновляем только фильтр типа
        if ( eventNameFilterBox!=null )
            eventListAdapter.updAdapterData(eventNameFilterBox.getText().toString() ,eventTypeFilter, eventMonthFilter, 0);

        // Меняем индикатор фильтра в зависимости от наличия фильтров
        if ( monthFilter.filterExist() ) monthFilterLED.setImageResource(R.drawable.filter);
        else monthFilterLED.setImageResource(R.drawable.filter_disable);
    } // end_method



} // end_class
