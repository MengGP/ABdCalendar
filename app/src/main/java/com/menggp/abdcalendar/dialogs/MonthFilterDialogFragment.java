package com.menggp.abdcalendar.dialogs;

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

import com.menggp.abdcalendar.MainActivity;
import com.menggp.abdcalendar.R;
import com.menggp.abdcalendar.datamodel.EventMonthFilter;

/*
    Дилог фильтра по типу месяцам
 */
public class MonthFilterDialogFragment extends DialogFragment {

    private MonthFilterDialogDatable monthFilterDialogDatable;

    CheckBox monthBox01;
    CheckBox monthBox02;
    CheckBox monthBox03;
    CheckBox monthBox04;
    CheckBox monthBox05;
    CheckBox monthBox06;
    CheckBox monthBox07;
    CheckBox monthBox08;
    CheckBox monthBox09;
    CheckBox monthBox10;
    CheckBox monthBox11;
    CheckBox monthBox12;

    EventMonthFilter monthFilter;

    // связываем интерфейс MonthFilterDialogDatable с контекстом Activity (из которой вызывается диалог)
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        monthFilterDialogDatable = (MonthFilterDialogDatable) context;
    } // end_method

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        Bundle args = getArguments();

        // Проверка переменных - при ошибочных параметрах выводим диалог об ошибке
        if ( args!=null ) {
            // Получаем переданные в диалог данные фильтрации
            monthFilter = new EventMonthFilter(
                    getArguments().getBoolean(MainActivity.EV_MONTH_ON_01),
                    getArguments().getBoolean(MainActivity.EV_MONTH_ON_02),
                    getArguments().getBoolean(MainActivity.EV_MONTH_ON_03),
                    getArguments().getBoolean(MainActivity.EV_MONTH_ON_04),
                    getArguments().getBoolean(MainActivity.EV_MONTH_ON_05),
                    getArguments().getBoolean(MainActivity.EV_MONTH_ON_06),
                    getArguments().getBoolean(MainActivity.EV_MONTH_ON_07),
                    getArguments().getBoolean(MainActivity.EV_MONTH_ON_08),
                    getArguments().getBoolean(MainActivity.EV_MONTH_ON_09),
                    getArguments().getBoolean(MainActivity.EV_MONTH_ON_10),
                    getArguments().getBoolean(MainActivity.EV_MONTH_ON_11),
                    getArguments().getBoolean(MainActivity.EV_MONTH_ON_12)
            );

            // Получаем элементы разметки
            LayoutInflater inflater = getActivity().getLayoutInflater();
            View view = inflater.inflate(R.layout.dialog_month_filter, null);
            monthBox01 = (CheckBox) view.findViewById(R.id.ev_month_on_dialog_january);
            monthBox02 = (CheckBox) view.findViewById(R.id.ev_month_on_dialog_february);
            monthBox03 = (CheckBox) view.findViewById(R.id.ev_month_on_dialog_march);
            monthBox04 = (CheckBox) view.findViewById(R.id.ev_month_on_dialog_april);
            monthBox05 = (CheckBox) view.findViewById(R.id.ev_month_on_dialog_may);
            monthBox06 = (CheckBox) view.findViewById(R.id.ev_month_on_dialog_june);
            monthBox07 = (CheckBox) view.findViewById(R.id.ev_month_on_dialog_july);
            monthBox08 = (CheckBox) view.findViewById(R.id.ev_month_on_dialog_august);
            monthBox09 = (CheckBox) view.findViewById(R.id.ev_month_on_dialog_september);
            monthBox10 = (CheckBox) view.findViewById(R.id.ev_month_on_dialog_october);
            monthBox11 = (CheckBox) view.findViewById(R.id.ev_month_on_dialog_november);
            monthBox12 = (CheckBox) view.findViewById(R.id.ev_month_on_dialog_december);

            // Помещаем данные на разметкк
            monthBox01.setChecked(monthFilter.isMonth01());
            monthBox02.setChecked(monthFilter.isMonth02());
            monthBox03.setChecked(monthFilter.isMonth03());
            monthBox04.setChecked(monthFilter.isMonth04());
            monthBox05.setChecked(monthFilter.isMonth05());
            monthBox06.setChecked(monthFilter.isMonth06());
            monthBox07.setChecked(monthFilter.isMonth07());
            monthBox08.setChecked(monthFilter.isMonth08());
            monthBox09.setChecked(monthFilter.isMonth09());
            monthBox10.setChecked(monthFilter.isMonth10());
            monthBox11.setChecked(monthFilter.isMonth11());
            monthBox12.setChecked(monthFilter.isMonth12());

            // Слушательль для блока "Снять все"
            RelativeLayout deselectAllBlock = (RelativeLayout) view.findViewById(R.id.dialog_deselect_all_months);
            View.OnClickListener onDeselectAllClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    monthBox01.setChecked(false);
                    monthBox02.setChecked(false);
                    monthBox03.setChecked(false);
                    monthBox04.setChecked(false);
                    monthBox05.setChecked(false);
                    monthBox06.setChecked(false);
                    monthBox07.setChecked(false);
                    monthBox08.setChecked(false);
                    monthBox09.setChecked(false);
                    monthBox10.setChecked(false);
                    monthBox11.setChecked(false);
                    monthBox12.setChecked(false);
                }
            };
            deselectAllBlock.setOnClickListener(onDeselectAllClickListener);

            // Слушательль для блока "Выделить все"
            RelativeLayout selectAllBlock = (RelativeLayout) view.findViewById(R.id.dialog_select_all_months);
            View.OnClickListener onSelectAllClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    monthBox01.setChecked(true);
                    monthBox02.setChecked(true);
                    monthBox03.setChecked(true);
                    monthBox04.setChecked(true);
                    monthBox05.setChecked(true);
                    monthBox06.setChecked(true);
                    monthBox07.setChecked(true);
                    monthBox08.setChecked(true);
                    monthBox09.setChecked(true);
                    monthBox10.setChecked(true);
                    monthBox11.setChecked(true);
                    monthBox12.setChecked(true);
                }
            };
            selectAllBlock.setOnClickListener(onSelectAllClickListener);

            // Слушатель действия - flushAction
            DialogInterface.OnClickListener flushAction = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(getContext(), R.string.toast_type_filter_flush, Toast.LENGTH_SHORT).show();
                    // Устанавиваем все типы событий как активные и передаем объект EventTypeFilter в активити
                    monthFilter.setAllTrue();

                    monthFilterDialogDatable.updMonthFilter(monthFilter);
                }
            }; // end_listener

            // Слушатель действия - yesAction
            DialogInterface.OnClickListener yesAction = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(getContext(), R.string.toast_type_filter_changed, Toast.LENGTH_SHORT).show();
                    // Устанавиваем фильтрацию в соответствии с выбором
                    monthFilter = new EventMonthFilter(
                            monthBox01.isChecked(), monthBox02.isChecked(), monthBox03.isChecked(),
                            monthBox04.isChecked(), monthBox05.isChecked(), monthBox06.isChecked(),
                            monthBox07.isChecked(), monthBox08.isChecked(), monthBox09.isChecked(),
                            monthBox10.isChecked(), monthBox11.isChecked(), monthBox12.isChecked()
                    );
                    monthFilterDialogDatable.updMonthFilter(monthFilter);
                }
            }; // end_listener

            // Создаем конструктор диалога и возвращаем построенный с его помощью диалог
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            return builder
                    .setTitle(R.string.dialog_type_filter_type_filter)                // заголовок
                    .setIcon(R.drawable.filter)                                       // иконка в заголовке
                    .setView(view)                                                    // разметка
                    .setNeutralButton(R.string.dialog_flush_action, flushAction)       // сбросить фильтр
                    .setNegativeButton(R.string.dialog_cancel_action, null)     // отмена
                    .setPositiveButton(R.string.dialog_yes_action, yesAction)        // да
                    .create();
        } else {
            // Создаем конструктор диалога и возвращаем построенный с его помощью диалог
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            return builder
                    .setTitle(R.string.dialog_error)             // заголовок
                    .setIcon(R.drawable.warning)                 // иконка в заголовке
                    .setMessage(R.string.dialog_bad_dialog_text)
                    .setPositiveButton(R.string.dialog_cancel_action, null) // cancel
                    .create();
        }

    } // end_method
} // end_class
