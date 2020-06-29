package com.menggp.abdcalendar.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.menggp.abdcalendar.MainActivity;
import com.menggp.abdcalendar.R;
import com.menggp.abdcalendar.adapters.StrSpinnerAdapter;
import com.menggp.abdcalendar.datamodel.DateHandler;

import java.util.ArrayList;
import java.util.Calendar;

/*
    ДИалог выбора года на виде калентаря
 */
public class ChoiceYearDialogFragment extends DialogFragment {

    ChoiceMonthAndYearDialogDatable choiceMonthAndYearDialogDatable;

    int setYearBoxSpinnerCountdown = 1;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        choiceMonthAndYearDialogDatable = (ChoiceMonthAndYearDialogDatable) context;
    } // end_method

    int currYear;
    int currMonth;
    StrSpinnerAdapter yearsSpinnerAdapter;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        Bundle args = getArguments();

        // Проверка переменных - при ошибочных параметрах выводим диалог об ошибке
        if ( args !=null ) {
            // Получаем переданные в диалог данные
            currYear = args.getInt(MainActivity.CURR_YEAR_VIEW);
            currMonth = args.getInt(MainActivity.CURR_MONTH_ON_VIEW);

            // Получаем разметк и ее элементы
            LayoutInflater inflater = getActivity().getLayoutInflater();
            View view = inflater.inflate(R.layout.dialog_year_choise, null);
            RelativeLayout setNowDate = (RelativeLayout) view.findViewById(R.id.dialog_set_now_date);
            Spinner setYearBox = (Spinner) view.findViewById(R.id.year_on_calendar_view);

            // Спиннер выбора года - для вида календаря
            ArrayList<String> yearFutureListSrt = DateHandler.getYearsFutureStrList();
            yearsSpinnerAdapter = new StrSpinnerAdapter(
                    this.getContext(),
                    R.layout.spinner_dialog_date_picker_item,
                    R.layout.spinner_dialog_date_picker_dropdown,
                    yearFutureListSrt
            );
            setYearBox.setAdapter(yearsSpinnerAdapter);
            // Устанавливаем в спиннере текущий год
            setYearBox.setSelection(yearsSpinnerAdapter.getPosition(String.valueOf(currYear)));

            // Слушатель установки текущего месяца
            setNowDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    choiceMonthAndYearDialogDatable.selectMonthAndYearOnCalendarView(Calendar.getInstance());
                    dismiss();
                }
            });

            // Слушатель выбора года в спиннере
            setYearBox.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    // При открыти диалога - срабаытвает setOnItemSelectedListener, не обрабатываем первое срабатывание
                    if (setYearBoxSpinnerCountdown > 0) setYearBoxSpinnerCountdown--;
                    else {
                        Calendar currCal = Calendar.getInstance();
                        currYear = Integer.parseInt(setYearBox.getItemAtPosition(position).toString());
                        currCal.set(currYear, currMonth, 1);
                        choiceMonthAndYearDialogDatable.selectMonthAndYearOnCalendarView(currCal);
                        dismiss();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });

            // Создаем конструктор диалога и возвращаем построенный с его помощью диалог
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            return builder
                    .setTitle(R.string.dialog_year_choice_title)                // заголовок
                    .setIcon(R.drawable.month_choice)                            // иконка в заголовке
                    .setView(view)                                            // разметка
                    .setPositiveButton(R.string.dialog_cancel_action, null)   // да
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
    } // end_class

} // end_class
