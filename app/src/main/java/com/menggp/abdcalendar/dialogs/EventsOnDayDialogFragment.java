package com.menggp.abdcalendar.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
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

    ListView eventOnDayListView;
    EventDialogAdapter eventDialogAdapter;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        // Получаем переданные данные о фильтрации по типу в объек EventTypeFilter
        Bundle args = getArguments();
        String date = args.getString(MainActivity.DATE_DB_NOTATION);
        EventTypeFilter typeFilter = new EventTypeFilter(
                args.getBoolean(MainActivity.EV_TYPE_BIRTHDAY_ON),
                args.getBoolean(MainActivity.EV_TYPE_ANNIVERSARY_ON),
                args.getBoolean(MainActivity.EV_TYPE_MEMODATE_ON),
                args.getBoolean(MainActivity.EV_TYPE_HOLIDAY_ON),
                args.getBoolean(MainActivity.EV_TYPE_OTHER_ON)
        );

        // Получаем события из БД - для текущей даты
        DatabaseAdapter dbAdapter = new DatabaseAdapter( this.getContext() );
        List<Event> eventsOnDay = dbAdapter.getEventsOnDay(typeFilter, date);

        // Объект resource - для использования в вызываемых методах
        Resources res = getResources();

        // Строка заголовка диалога
        String dateString = DateHandler.convertDbToHumanNotation(res, date);

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


        /*
        // Слушательль для блока "Снять все"
        RelativeLayout deselectAllBlock = (RelativeLayout)view.findViewById(R.id.dialog_deselect_all_types);
        View.OnClickListener onDeselectAllClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                typeBirthdayBox.setChecked( false );
                typeAnniversaryBox.setChecked( false );
                typeMemodateBox.setChecked( false );
                typeHolydayBox.setChecked( false );
                typeOtherBox.setChecked( false );
            }
        };
        deselectAllBlock.setOnClickListener( onDeselectAllClickListener );

        // Слушательль для блока "Выделить все"
        RelativeLayout selectAllBlock = (RelativeLayout)view.findViewById(R.id.dialog_select_all_types);
        View.OnClickListener onSelectAllClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                typeBirthdayBox.setChecked( true );
                typeAnniversaryBox.setChecked( true );
                typeMemodateBox.setChecked( true );
                typeHolydayBox.setChecked( true );
                typeOtherBox.setChecked( true );
            }
        };
        selectAllBlock.setOnClickListener( onSelectAllClickListener );

        // Слушатель действия - flushAction
        DialogInterface.OnClickListener flushAction = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getContext(), R.string.toast_type_filter_flush, Toast.LENGTH_SHORT).show();
                // Устанавиваем все типы событий как активные и передаем объект EventTypeFilter в активити
                typeFilter = new EventTypeFilter(
                  true,
                  true,
                  true,
                  true,
                  true
                );

                typeFilterDialogDatable.updTypeFilter(typeFilter);
            }
        }; // end_listener

        // Слушатель действия - yesAction
        DialogInterface.OnClickListener yesAction = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getContext(), R.string.toast_type_filter_changed, Toast.LENGTH_SHORT).show();
                // Устанавиваем типы событий в соответствии с выбором и передаем объект EventTypeFilter в активити
                typeFilter = new EventTypeFilter(
                        typeBirthdayBox.isChecked(),
                        typeAnniversaryBox.isChecked(),
                        typeMemodateBox.isChecked(),
                        typeHolydayBox.isChecked(),
                        typeOtherBox.isChecked()
                );

                typeFilterDialogDatable.updTypeFilter(typeFilter);
            }
        }; // end_listener
        */


        // Создаем конструктор диалога и возвращаем построенный с его помощью диалог
        AlertDialog.Builder builder = new AlertDialog.Builder( getActivity() );
        return builder
                .setTitle( dateString )                // заголовок
                .setIcon( R.drawable.act_bar_main_calendar )                                       // иконка в заголовке
                .setView( view )                                                    // разметка
                // .setNeutralButton(R.string.dialog_flush_action, flushAction)       // сбросить фильтр
                // .setNegativeButton(R.string.dialog_cancel_action, null)     // отмена
                .setPositiveButton(R.string.dialog_back_action, null)        // назад
                .create();


    } // end_method
} // end_class
