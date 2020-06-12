package com.menggp.abdcalendar.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.menggp.abdcalendar.R;
import com.menggp.abdcalendar.adapters.StrSpinnerAdapter;
import com.menggp.abdcalendar.datamodel.DateHandler;

import java.util.ArrayList;

/*
    Класс дилого выбора даты события:
        - в виде 2х спиннеров - день / месяц
 */
public class EventDatePickerDialogFragment extends DialogFragment {

    private static final String LOG_TAG="EventDatePickerDialog";

    private EventDatePickerDialogDatable eventDatePickerDialogDatable;

    Spinner eventMonthBox;
    Spinner eventDayBox;
    StrSpinnerAdapter eventMonthSpinnerAdapter;
    StrSpinnerAdapter eventDaySpinnerAdapter;
    int eventMonth;
    int eventDay;

    // связываем интерфейс EventDatePickerDialogDatable с контекстом вызывающей Activity
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        eventDatePickerDialogDatable = (EventDatePickerDialogDatable) context;
    } // end_method

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // Получаем переданные в диалог данные
        eventMonth = getArguments().getInt("month");
        eventDay = getArguments().getInt("day");

        // Получаем разметку и элемнты с нее
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate( R.layout.dialog_event_date_picker, null);

        Resources res = getResources();
        // Спиннер выбора месяца
        eventMonthBox = (Spinner)view.findViewById(R.id.event_date_month_on_edit);
        ArrayList<String> monthListStr = DateHandler.getMonthStrList(res);
        eventMonthSpinnerAdapter = new StrSpinnerAdapter(
                this.getContext(),
                R.layout.spinner_event_date_picker_item,
                R.layout.spinner_event_date_picker_dropdown,
                monthListStr
        );
        eventMonthBox.setAdapter(eventMonthSpinnerAdapter);

        // Спиннер выбора даты
        eventDayBox = (Spinner)view.findViewById(R.id.event_date_day_on_edit);
        ArrayList<String> dayListStr = DateHandler.getDayStrList( eventMonth );
        eventDaySpinnerAdapter = new StrSpinnerAdapter(
                this.getContext(),
                R.layout.spinner_event_date_picker_item,
                R.layout.spinner_event_date_picker_dropdown,
                dayListStr
        );
        eventDayBox.setAdapter(eventDaySpinnerAdapter);

        // Помещаем начальные данные на разметку
        eventMonthBox.setSelection( eventMonth-1 );         // смещение номера месяца - 1, в спиннере нумерация месяцев с 0
        eventDayBox.setSelection( eventDay-1 );             // смещение дня - 1, в спиннере нумерация с 0

        // Слушатель Spinner - выбора месяца
        AdapterView.OnItemSelectedListener monthSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                eventMonth = position + 1;      // указываем какой месяц выбран - с коррекцией на 1
                // обновляем список дней в eventDaySpinnerAdapter - в соответствии с месяцем
                eventDaySpinnerAdapter.clear();
                eventDaySpinnerAdapter.addAll( DateHandler.getDayStrList( eventMonth ));
                eventDaySpinnerAdapter.notifyDataSetChanged();

                // При необходимости корректируем день
                // если день больше 29  и месяц 02-февраль - меняем день на 29е
                if ( (eventDay > 29) && (eventMonth==2) ) {
                    eventDay=29;
                    eventDayBox.setSelection(eventDay-1);
                }
                // если день 31 и месяц: 04-апрель ИЛИ 06-июнь ИЛИ 09-сетнябрь ИЛИ 11-ноябрь - меняем на 30е
                else if ( (eventDay==31) && (eventMonth==4 || eventMonth==6 || eventMonth==9 || eventMonth==11) ) {
                    eventDay = 30;
                    eventDayBox.setSelection(eventDay-1);
                }
                // иначе не делем с днем ничего
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        };
        eventMonthBox.setOnItemSelectedListener( monthSelectedListener );

        // Слушатель Spinner - выбора дня
        AdapterView.OnItemSelectedListener daySelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                eventDay = position + 1;      // указываем какой день выбран - с коррекцией на 1
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        };
        eventDayBox.setOnItemSelectedListener( daySelectedListener );

        // Слушатель действия - подтверждения изменения даты
        DialogInterface.OnClickListener accept = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getContext(), R.string.toast_date_changed, Toast.LENGTH_SHORT).show();
                eventDatePickerDialogDatable.setEventDateOnEdit(eventMonth,eventDay);
            }
        }; // end_listener

        // Создаем конструктор диалога и возвращаем построенный с его помощью диалог
        AlertDialog.Builder builder = new AlertDialog.Builder( getActivity() );
        return builder
                .setTitle(R.string.dialog_event_date_picker)                        // заголовок
                .setIcon(R.drawable.act_bar_main_calendar)                          // иконка в заголовке
                .setView( view )                                                    // разметка
                .setNegativeButton(R.string.dialog_cancel_action, null)     // cancel
                .setPositiveButton(R.string.dialog_yes_action, accept)        // yes
                .create();
    } // end_method

} // end_class
