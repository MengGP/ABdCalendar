package com.menggp.abdcalendar;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.menggp.abdcalendar.datamodel.EventTypeFilter;

/*
    Дилог фильтра по типу события
 */
public class TypeFilterDialogFragment extends DialogFragment {

        // ключи сортировки по типу - получаемые во фагменте
    private static final String EV_TYPE_BIRTHDAY_ON = "ev_type_birthday_on";
    private static final String EV_TYPE_ANNIVERSARY_ON = "ev_type_anniversary_on";
    private static final String EV_TYPE_MEMODATE_ON = "ev_type_memodate_on";
    private static final String EV_TYPE_HOLIDAY_ON = "ev_type_holiday_on";
    private static final String EV_TYPE_OTHER_ON = "ev_type_other_on";

    CheckBox typeBirthdayBox;
    CheckBox typeAnniversaryBox;
    CheckBox typeMemodateBox;
    CheckBox typeHolydayBox;
    CheckBox typeOtherBox;


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        EventTypeFilter eventTypeFilter = new EventTypeFilter(
                getArguments().getBoolean(EV_TYPE_BIRTHDAY_ON),
                getArguments().getBoolean(EV_TYPE_ANNIVERSARY_ON),
                getArguments().getBoolean(EV_TYPE_MEMODATE_ON),
                getArguments().getBoolean(EV_TYPE_HOLIDAY_ON),
                getArguments().getBoolean(EV_TYPE_OTHER_ON)
        );

        // Получаем разметку и ее элементы
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate( R.layout.dialog_type_filter, null);
        typeBirthdayBox = (CheckBox)view.findViewById(R.id.ev_type_birthday_on_dialog);
        typeAnniversaryBox = (CheckBox)view.findViewById(R.id.ev_type_anniversary_on_dialog);
        typeMemodateBox = (CheckBox)view.findViewById(R.id.ev_type_memodate_on_dialog);
        typeHolydayBox = (CheckBox)view.findViewById(R.id.ev_type_holiday_on_dialog);
        typeOtherBox = (CheckBox)view.findViewById(R.id.ev_type_other_on_dialog);

        // Помещаем данные на разметку
        typeBirthdayBox.setChecked( eventTypeFilter.isBirthdayOn() );
        typeAnniversaryBox.setChecked( eventTypeFilter.isAnniversaryOn());
        typeMemodateBox.setChecked( eventTypeFilter.isMemodateOn());
        typeHolydayBox.setChecked( eventTypeFilter.isHolidayOn());
        typeOtherBox.setChecked( eventTypeFilter.isOtherOn());

        /*
        RelativeLayout deseleckAllBlock = (RelativeLayout)view.findViewById(R.id.dialog_deselect_all);

        View.OnClickListener onDeselectAllClockListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        };
        */

        /*
        // Слушатель действия - deleteAction
        DialogInterface.OnClickListener delAction = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getContext(), R.string.toast_del_accepted, Toast.LENGTH_SHORT).show();
                if ( eventId != 0 ) eventDelConfirmationDialogDatable.delEvent( eventId );
            }
        }; // end_listener
         */

        // Создаем конструктор диалога и возвращаем построенный с его помощью диалог
        AlertDialog.Builder builder = new AlertDialog.Builder( getActivity() );
        return builder
                .setTitle( R.string.dialog_type_filter_type_filter )                // заголовок
                .setIcon( R.drawable.filter )                                       // иконка в заголовке
                .setView( view )                                                    // разметка
                .setNeutralButton(R.string.dialog_flush_action, null)       // сбросить фильтр
                .setNegativeButton(R.string.dialog_cancel_action, null)     // отмена
                .setPositiveButton(R.string.dialog_yes_action, null)        // да
                .create();
    } // end_method

    /*
        Метод обрабатывает нажатие кнопки "Снять все" в диалоге
     */
    private void onClickDeselectAll(View view) {
        // Помещаем данные на разметку
        typeBirthdayBox.setChecked( false );
        typeAnniversaryBox.setChecked( false );
        typeMemodateBox.setChecked( false );
        typeHolydayBox.setChecked( false );
        typeOtherBox.setChecked( false );
    } // end_method

    /*
        Метод обрабатывает нажатие кнопки "Выделить все" в диалоге
     */
    private void onClickSelectAll(View view) {
        // Помещаем данные на разметку
        typeBirthdayBox.setChecked( true );
        typeAnniversaryBox.setChecked( true );
        typeMemodateBox.setChecked( true );
        typeHolydayBox.setChecked( true );
        typeOtherBox.setChecked( true );
    } // end_method



} // end_class
