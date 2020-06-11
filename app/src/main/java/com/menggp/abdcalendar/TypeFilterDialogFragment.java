package com.menggp.abdcalendar;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.menggp.abdcalendar.datamodel.EventTypeFilter;

/*
    Дилог фильтра по типу события
 */
public class TypeFilterDialogFragment extends DialogFragment {

    private TypeFilterDialogDatable typeFilterDialogDatable;

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

    EventTypeFilter typeFilter;

    // связываем интерфейс TypeFilterDialogDatable с контекстом Activity (из которой вызывается диалог)
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        typeFilterDialogDatable = (TypeFilterDialogDatable) context;
    } // end_method

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        // Получаем переданные данные о фильтрации по типу в объек EventTypeFilter
        Bundle args = getArguments();
        EventTypeFilter eventTypeFilter = new EventTypeFilter(
                args.getBoolean(EV_TYPE_BIRTHDAY_ON),
                args.getBoolean(EV_TYPE_ANNIVERSARY_ON),
                args.getBoolean(EV_TYPE_MEMODATE_ON),
                args.getBoolean(EV_TYPE_HOLIDAY_ON),
                args.getBoolean(EV_TYPE_OTHER_ON)
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


        // Создаем конструктор диалога и возвращаем построенный с его помощью диалог
        AlertDialog.Builder builder = new AlertDialog.Builder( getActivity() );
        return builder
                .setTitle( R.string.dialog_type_filter_type_filter )                // заголовок
                .setIcon( R.drawable.filter )                                       // иконка в заголовке
                .setView( view )                                                    // разметка
                .setNeutralButton(R.string.dialog_flush_action, flushAction)       // сбросить фильтр
                .setNegativeButton(R.string.dialog_cancel_action, null)     // отмена
                .setPositiveButton(R.string.dialog_yes_action, yesAction)        // да
                .create();
    } // end_method



} // end_class
