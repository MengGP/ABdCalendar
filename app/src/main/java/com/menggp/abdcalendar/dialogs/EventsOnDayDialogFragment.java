package com.menggp.abdcalendar.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.menggp.abdcalendar.MainActivity;
import com.menggp.abdcalendar.R;
import com.menggp.abdcalendar.adapters.EventDialogAdapter;
import com.menggp.abdcalendar.datamodel.DateHandler;
import com.menggp.abdcalendar.datamodel.Event;
import com.menggp.abdcalendar.datamodel.EventTypeFilter;
import com.menggp.abdcalendar.repository.DatabaseAdapter;

import java.util.List;

/*
    Диалог выводящий особытия выбранного на календаре дня
 */
public class EventsOnDayDialogFragment extends DialogFragment {

    EventsOnDayDialogDatable eventsOnDayDialogDatable;

    // связываем интерфейс EventsOnDayDialogDatable с контекстом Activity (из которой вызывается диалог)
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        eventsOnDayDialogDatable = (EventsOnDayDialogDatable) context;
    } // end_method

    ListView eventOnDayListView;
    EventDialogAdapter eventDialogAdapter;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        // Получаем переданные данные о фильтрации по типу в объек EventTypeFilter
        Bundle args = getArguments();
        String date = args.getString(MainActivity.DATE_DB_NOTATION);
        int year = args.getInt(MainActivity.YEAR_ON_CALENDAR_VIEW);
        EventTypeFilter typeFilter = new EventTypeFilter(
                args.getBoolean(MainActivity.EV_TYPE_BIRTHDAY_ON),
                args.getBoolean(MainActivity.EV_TYPE_ANNIVERSARY_ON),
                args.getBoolean(MainActivity.EV_TYPE_MEMODATE_ON),
                args.getBoolean(MainActivity.EV_TYPE_HOLIDAY_ON),
                args.getBoolean(MainActivity.EV_TYPE_OTHER_ON)
        );

        // Объект resource - для использования в вызываемых методах
        Resources res = getResources();

        // Строка заголовка диалога
        String dateString = DateHandler.convertDbToHumanNotation(res, date);

        // Получаем события из БД - для текущей даты
        DatabaseAdapter dbAdapter = new DatabaseAdapter( this.getContext() );
        List<Event> eventsOnDay = dbAdapter.getEventsOnDay(typeFilter, date);

        // События с датой 29е февраля - в невисокосные годы - отображаются 28го
        //      - если такие есть, добавлем их в список и меняем заголовок
        if ( date.equals("02-28") && !DateHandler.isLeapYear(year) ) {
            List<Event> eventsOnDayLeap = dbAdapter.getEventsOnDay(typeFilter, "02-29");
            if ( !eventsOnDayLeap.isEmpty() ) {
                eventsOnDay.addAll(eventsOnDayLeap);
                dateString = res.getString(R.string.dialog_29_feb_no_leap);
            }
        }

        // Получаем разметку и ее элементы
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate( R.layout.dialog_events_on_day, null );
        eventOnDayListView = (ListView) view.findViewById(R.id.event_list_in_calendar_dialog);

        // Создаем адаптер для списка событий дня в диалоге
        eventDialogAdapter = new EventDialogAdapter(
                this.getContext(),                  // контекст
                R.layout.list_item_event_calendar,  // разметка
                eventsOnDay                         // данные
        );
        // Устанавливаем адатре для списка на разметке
        eventOnDayListView.setAdapter( eventDialogAdapter );

        // Слушатель короткого нажатия на событие в дилоге
        AdapterView.OnItemClickListener onEventClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Event event = eventDialogAdapter.getItem(position);
                if (event!=null)
                    eventsOnDayDialogDatable.getEventInfoDialogFromCalendarView( event.getId() );
                dismiss();
            }
        };
        eventOnDayListView.setOnItemClickListener(onEventClickListener);

        // Слушатель длинного нажатия на событие в диалоге
        AdapterView.OnItemLongClickListener onEventLongClickListener = new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                // Получаем событие из адаптера
                Event event = eventDialogAdapter.getItem( position );
                // Если значение не null - вызываем EventActivityInfo и передаем в нее ID события
                if (event!=null)
                    eventsOnDayDialogDatable.getEventActivityInfoFromCalendarView( event.getId() );
                dismiss();
                // true - не переходим в обработку короткого нажатия
                return true;
            }
        };
        eventOnDayListView.setOnItemLongClickListener(onEventLongClickListener);


        // Создаем конструктор диалога и возвращаем построенный с его помощью диалог
        AlertDialog.Builder builder = new AlertDialog.Builder( getActivity() );
        return builder
                .setTitle( dateString )                // заголовок
                .setIcon( R.drawable.act_bar_main_calendar )                                       // иконка в заголовке
                .setView( view )                                                    // разметка
                .setPositiveButton(R.string.dialog_back_action, null)        // назад
                .create();


    } // end_method
} // end_class
